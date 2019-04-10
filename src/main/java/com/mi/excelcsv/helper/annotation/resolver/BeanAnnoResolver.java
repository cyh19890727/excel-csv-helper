package com.mi.excelcsv.helper.annotation.resolver;

import com.mi.excelcsv.helper.annotation.ColumnFormat;
import com.mi.excelcsv.helper.exception.ExcelCsvHelperException;
import com.mi.excelcsv.helper.serializer.AbstractCellSerializer;
import com.mi.excelcsv.helper.type.CellDataType;
import com.mi.excelcsv.helper.util.BeanRefelectUtils;
import com.mi.excelcsv.helper.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 解析bean上配置的ColumnFormat注解，并存储相关的配置信息
 *
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 18-12-24 下午4:11
 */
public class BeanAnnoResolver {

    private Class beanType;

    private List<Field> fieldList = new ArrayList<Field>();

    private List<String> titleList = new ArrayList<String>();

    private List<CellDataType> cellDataTypeList = new ArrayList<CellDataType>();

    private List<AbstractCellSerializer> serializerList = new ArrayList<AbstractCellSerializer>();

    private List<String[]> argsList = new ArrayList<String[]>();

    private List<OrderIndex> orderIndexList = new ArrayList<OrderIndex>();

    private BeanAnnoResolver() {

    }

    public static BeanAnnoResolver getResolerByBeanType(Class beanType) throws ExcelCsvHelperException {
        BeanAnnoResolver resolver = new BeanAnnoResolver();
        resolver.setBeanType(beanType);
        List<Field> fieldList = BeanRefelectUtils.getAllFields(beanType);
        Field[] fields = new Field[fieldList.size()];
        fields = BeanRefelectUtils.getAllFields(beanType).toArray(fields);
        for (int i = 0; i < fields.length; i++) {
            ColumnFormat columnFormat = fields[i].getAnnotation(ColumnFormat.class);
            // 配置了注解的是需要输出的列
            if (columnFormat != null) {
                resolver.getFieldList().add(fields[i]);
                // 获取title配置
                String title = columnFormat.title();
                if (!StringUtils.isEmpty(title)) {
                    resolver.getTitleList().add(title);
                } else {
                    // 没有配置title，默认已域成员的名字为title
                    resolver.getTitleList().add(fields[i].getName());
                }
                // 获取序列化配置
                resolver.getCellDataTypeList().add(columnFormat.cellType());
                Class<? extends AbstractCellSerializer> serializer = columnFormat.serializer();
                try {
                    resolver.getSerializerList().add(serializer.newInstance());
                } catch (Exception e) {
                    throw new ExcelCsvHelperException("instantiate cell serializer object fail");
                }
                resolver.getArgsList().add(columnFormat.args());
                // 设置order
                int index = resolver.getOrderIndexList().size();
                resolver.getOrderIndexList().add(new OrderIndex(columnFormat.order(), index));
            }
        }

        // 按照order值大小调整orderIndexList元素顺序
        resolver.adjustOrder(resolver.getOrderIndexList());
        // 按照orderIndexList列表顺序依次调整其他列表
        resolver.setFieldList(resolver.adjustListByOrder(resolver.getFieldList(), resolver.getOrderIndexList()));
        resolver.setTitleList(resolver.adjustListByOrder(resolver.getTitleList(), resolver.getOrderIndexList()));
        resolver.setCellDataTypeList(resolver.adjustListByOrder(resolver.getCellDataTypeList(), resolver.getOrderIndexList()));
        resolver.setSerializerList(resolver.adjustListByOrder(resolver.getSerializerList(), resolver.getOrderIndexList()));
        resolver.setArgsList(resolver.adjustListByOrder(resolver.getArgsList(), resolver.getOrderIndexList()));

        return resolver;
    }

    public Class getBeanType() {
        return beanType;
    }

    public List<Field> getFieldList() {
        return fieldList;
    }

    public List<String> getTitleList() {
        return titleList;
    }

    public List<CellDataType> getCellDataTypeList() {
        return cellDataTypeList;
    }

    public List<AbstractCellSerializer> getSerializerList() {
        return serializerList;
    }

    public List<String[]> getArgsList() {
        return argsList;
    }

    public List<OrderIndex> getOrderIndexList() {
        return orderIndexList;
    }

    protected void setBeanType(Class beanType) {
        this.beanType = beanType;
    }

    protected void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    protected void setTitleList(List<String> titleList) {
        this.titleList = titleList;
    }

    protected void setCellDataTypeList(List<CellDataType> cellDataTypeList) {
        this.cellDataTypeList = cellDataTypeList;
    }

    protected void setSerializerList(List<AbstractCellSerializer> serializerList) {
        this.serializerList = serializerList;
    }

    protected void setArgsList(List<String[]> argsList) {
        this.argsList = argsList;
    }

    protected void setOrderIndexList(List<OrderIndex> orderIndexList) {
        this.orderIndexList = orderIndexList;
    }

    /**
     * 按照order值调整列表顺序
     */
    private void adjustOrder(List<OrderIndex> orderIndexList) {
        int maxOrder = ColumnFormat.NO_ORDER;
        for (OrderIndex orderIndex : orderIndexList) {
            if (orderIndex.getOrder() != ColumnFormat.NO_ORDER && orderIndex.getOrder() > maxOrder) {
                maxOrder = orderIndex.getOrder();
            }
        }

        if (maxOrder != ColumnFormat.NO_ORDER) {
            // 对没有明确指定order值的域配置order值
            int startOrder = maxOrder + 1;
            for (OrderIndex orderIndex : orderIndexList) {
                if (orderIndex.getOrder() == ColumnFormat.NO_ORDER) {
                    orderIndex.setOrder(startOrder++);
                }
            }
        }

        // orderIndexList按照order从小到大排序
        Collections.sort(orderIndexList, new Comparator<OrderIndex>() {
            public int compare(OrderIndex o1, OrderIndex o2) {
                int order1 = o1.getOrder();
                int order2 = o2.getOrder();
                if (order1 < order2) {
                    return -1;
                } else if (order1 == order2) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
    }

    /**
     * 根据orderIndexList 列表的顺序调整原列表src
     *
     * @param src, 需要调整顺序的列表，长度需要跟orderIndexList一致
     * @param orderIndexList， 已经调整了顺序的orderIndexList
     * @return 返回调整顺序后的列表
     */
    private <E> List<E> adjustListByOrder(List<E> src, List<OrderIndex> orderIndexList) {
        List<E> dst = new ArrayList<E>(src.size());
        for (int i = 0; i < orderIndexList.size(); i++) {
            dst.add(src.get(orderIndexList.get(i).getIndex()));
        }

        return dst;
    }


    /**
     *  存储beanColumnFormate中配置order值及在list中的索引，用于排序
     */
    private static class OrderIndex {

        private int order;

        private int index;

        public OrderIndex(int order, int index) {
            this.order = order;
            this.index = index;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
