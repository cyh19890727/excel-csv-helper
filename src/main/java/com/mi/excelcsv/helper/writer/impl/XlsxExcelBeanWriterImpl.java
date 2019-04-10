package com.mi.excelcsv.helper.writer.impl;

import com.mi.excelcsv.helper.annotation.resolver.BeanAnnoResolver;
import com.mi.excelcsv.helper.exception.ExcelCsvHelperException;
import com.mi.excelcsv.helper.serializer.impl.DefaultSerializer;
import com.mi.excelcsv.helper.type.CellDataType;
import com.mi.excelcsv.helper.util.BeanRefelectUtils;
import com.mi.excelcsv.helper.util.CellDataTypeConvertUtils;
import com.mi.excelcsv.helper.writer.ExcelBeanWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * xlsx格式的excel writer实现类定义
 *
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 18-12-21 下午7:44
 */
public class XlsxExcelBeanWriterImpl implements ExcelBeanWriter {

    private static final int DEFAULT_WINDOW_SIZE = 100;

    private SXSSFWorkbook workbook;

    private int sheetIndex;

    private OutputStream outputStream;

    private Map<Integer, BeanAnnoResolver> resolverMap;

    private boolean flushed = false;

    public XlsxExcelBeanWriterImpl(OutputStream outputStream) {
        this(DEFAULT_WINDOW_SIZE, outputStream);
    }

    public XlsxExcelBeanWriterImpl(int windowSize, OutputStream outputStream) {
        workbook = new SXSSFWorkbook(windowSize);
        sheetIndex = 0;
        this.outputStream = outputStream;
        resolverMap = new HashMap<Integer, BeanAnnoResolver>();
    }

    /**
     *  返回sheet页总数
     */
    public int getNumberOfSheets() {
        return workbook.getNumberOfSheets();
    }

    /**
     *  增加sheet页
     */
    public void addSheet() {
        workbook.createSheet();
    }

    /**
     * 切换sheet页到指定的页面
     *
     * @param sheetIndex, sheet页索引，在0到sheet总数减1之间，超出返回会抛出ExcelCsvHelperException异常
     */
    public void setSheetIndex(int sheetIndex) throws ExcelCsvHelperException {
        if (sheetIndex < 0 || sheetIndex >= workbook.getNumberOfSheets()) {
            throw new ExcelCsvHelperException("illegal sheetIndex arguments");
        }
    }

    public <E> void write(List<E> list, Class<E> beanType) throws ExcelCsvHelperException {
        // 获取当前操作的sheet
        int sheetNum = workbook.getNumberOfSheets();
        SXSSFSheet sheet;
        if (sheetNum == 0) {
            sheet = workbook.createSheet();
            sheetIndex = 0;
        } else {
            sheet = workbook.getSheetAt(sheetIndex);
        }

        // 判断sheet页存储的bean type是否相同
        if (resolverMap.get(sheetIndex) == null) {
            BeanAnnoResolver resolver = BeanAnnoResolver.getResolerByBeanType(beanType);
            resolverMap.put(sheetIndex, resolver);
        } else {
            if (resolverMap.get(sheetIndex).getBeanType() != beanType) {
                throw new ExcelCsvHelperException("current sheet beanType is changing");
            }
        }

        // 在当前sheet页写入list列表
        int rowNum = sheet.getPhysicalNumberOfRows();
        int i;
        if (rowNum <= 0) {
            createTitleRowInSheet(sheet, resolverMap.get(sheetIndex));
            i = 1;
            for (Object object : list) {
                writeRowToSheet(sheet, resolverMap.get(sheetIndex), object, i++);
            }
        } else {
            i = rowNum;
            for (Object object : list) {
                writeRowToSheet(sheet, resolverMap.get(sheetIndex), object, i++);
            }
        }
    }

    public void flush() throws ExcelCsvHelperException {
        if (flushed) {
            throw new ExcelCsvHelperException("can only flush once");
        }
        try {
            workbook.write(outputStream);
            flushed = true;
        } catch (IOException e) {
            throw new ExcelCsvHelperException("flush to outputstream fail");
        }
    }

    public void close() throws ExcelCsvHelperException {
        workbook.dispose();
        try {
            outputStream.close();
        }  catch (IOException e) {
            throw new ExcelCsvHelperException("close outputstream fail");
        }
    }

    /**
     * 创建标题行
     */
    private void createTitleRowInSheet(SXSSFSheet sheet, BeanAnnoResolver resolver) {
        SXSSFRow row = sheet.createRow(0);
        for (int i = 0; i < resolver.getTitleList().size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(resolver.getTitleList().get(i));
            cell.setCellType(CellType.STRING);
        }
    }

    /**
     * 将当个bean写入行
     */
    private void writeRowToSheet(SXSSFSheet sheet, BeanAnnoResolver resolver, Object element, int rowNum) throws ExcelCsvHelperException {
        SXSSFRow sxssfRow = sheet.createRow(rowNum);
        List<Field> fieldList = resolver.getFieldList();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            Object obj = BeanRefelectUtils.getBeanFieldValueByGetterMethod(element, field, resolver.getBeanType());
            SXSSFCell cell = sxssfRow.createCell(i);
            if (obj == null) {
                cell.setCellType(CellType.BLANK);
                continue;
            }
            Object serializeResult = obj;
            if (resolver.getSerializerList().get(i) != null && resolver.getSerializerList().get(i).getClass() != DefaultSerializer.class) {
                serializeResult = resolver.getSerializerList().get(i).serialize(obj, resolver.getArgsList().get(i));
            }
            setCellValueByCellType(cell, serializeResult, resolver.getCellDataTypeList().get(i));
        }
    }

    /**
     * 依据cellDataType的类型设置cell值，第一期只实现STRING、NUMERIC、BLANK类型
     */
    private void setCellValueByCellType(Cell cell, Object obj, CellDataType cellDataType) {
        if (cellDataType == CellDataType.NONE) {
            // value类型为Number类型，设置CellDataType.NUMERIC，其他类型先都按照String单元格类型处理，后续依项目此处再升级
            if (obj instanceof Number) {
                cell.setCellType(CellType.NUMERIC);
                cell.setCellValue(Double.parseDouble(obj.toString()));
            } else {
                cell.setCellType(CellType.STRING);
                cell.setCellValue(obj.toString());
            }
            return;
        }

        cell.setCellType(CellDataTypeConvertUtils.getExcelCellTypeByCellType(cellDataType));
        switch (cellDataType) {
            case NUMERIC:
                cell.setCellValue(Double.parseDouble(obj.toString()));
                break;
            case BLANK:
                break;
            case STRING:
            default:
                cell.setCellValue(obj.toString());
                break;
        }
    }
}
