package com.mi.excelcsv.helper.dynamiccolumn;

import com.mi.excelcsv.helper.serializer.AbstractCellSerializer;
import com.mi.excelcsv.helper.serializer.impl.DefaultSerializer;
import com.mi.excelcsv.helper.type.CellDataType;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-3-1 下午5:11
 */
public class ColumnConfig {

    /**
     *  标题
     */
    private String title;

    /**
     *  单元格类型，仅针对excel，csv无格式
     */
    private CellDataType cellDataType = CellDataType.NONE;

    /**
     *  单元格序列化器
     */
    private AbstractCellSerializer cellSerializer =  new DefaultSerializer();

    /**
     * 序列化器参数数组
     */
    private String[] serializerArgs = new String[]{};

    public ColumnConfig(String title) {
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setCellDataType(CellDataType cellDataType) {
        this.cellDataType = cellDataType;
    }

    public CellDataType getCellDataType() {
        return cellDataType;
    }

    public AbstractCellSerializer getCellSerializer() {
        return cellSerializer;
    }

    public void setCellSerializer(AbstractCellSerializer cellSerializer) {
        this.cellSerializer = cellSerializer;
    }

    public String[] getSerializerArgs() {
        return serializerArgs;
    }

    public void setSerializerArgs(String []serializerArgs) {
        this.serializerArgs = serializerArgs;
    }

}
