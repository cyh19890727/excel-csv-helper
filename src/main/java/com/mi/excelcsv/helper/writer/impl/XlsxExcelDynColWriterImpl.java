package com.mi.excelcsv.helper.writer.impl;

import com.mi.excelcsv.helper.dynamiccolumn.ColumnConfig;
import com.mi.excelcsv.helper.exception.ExcelCsvHelperException;
import com.mi.excelcsv.helper.serializer.impl.DefaultSerializer;
import com.mi.excelcsv.helper.type.CellDataType;
import com.mi.excelcsv.helper.util.CellDataTypeConvertUtils;
import com.mi.excelcsv.helper.writer.ExcelDynColWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-3-1 下午5:34
 */
public class XlsxExcelDynColWriterImpl implements ExcelDynColWriter {

    private static final int DEFAULT_WINDOW_SIZE = 100;

    private SXSSFWorkbook workbook;

    private SXSSFSheet sheet;

    private OutputStream outputStream;

    private List<ColumnConfig> columnConfigs;

    private boolean flushed = false;

    public XlsxExcelDynColWriterImpl(OutputStream outputStream) {
        this(DEFAULT_WINDOW_SIZE, outputStream);
    }

    public XlsxExcelDynColWriterImpl(int windowSize, OutputStream outputStream) {
        this.workbook = new SXSSFWorkbook(windowSize);
        this.sheet = workbook.createSheet();
        this.outputStream = outputStream;
    }

    public void writeTitle(List<ColumnConfig> columnConfigs) throws ExcelCsvHelperException {
        if (this.columnConfigs != null) {
            throw new ExcelCsvHelperException("can not write title twice");
        }

        this.columnConfigs = columnConfigs;

        SXSSFRow row = sheet.createRow(0);
        for (int i = 0; i < columnConfigs.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnConfigs.get(i).getTitle());
            cell.setCellType(CellType.STRING);
        }
    }

    public void write(List<Map<String, Object>> datas) throws ExcelCsvHelperException {
        if (this.columnConfigs == null) {
            throw new ExcelCsvHelperException("must write title before write data");
        }

        int rowNum = sheet.getPhysicalNumberOfRows();
        for (Map lineMap : datas) {
            SXSSFRow row = sheet.createRow(rowNum++);
            for (int i = 0 ; i < columnConfigs.size(); i++) {
                Cell cell = row.createCell(i);
                ColumnConfig columnConfig = columnConfigs.get(i);
                if (lineMap.containsKey(columnConfig.getTitle())) {
                    Object value = lineMap.get(columnConfig.getTitle());
                    if (value == null) {
                        cell.setCellType(CellType.BLANK);
                        continue;
                    }
                    if (columnConfig.getCellSerializer().getClass() != DefaultSerializer.class) {
                        value = columnConfig.getCellSerializer().serialize(value, columnConfig.getSerializerArgs());
                    }
                    setCellValue(cell, value, columnConfig);
                } else {
                    cell.setCellType(CellType.BLANK);
                }
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

    private void setCellValue(Cell cell, Object value, ColumnConfig columnConfig) {
        if (columnConfig.getCellDataType() == CellDataType.NONE) {
            // value类型为Number类型，设置NUMERIC单元格类型，其他类型先都按照STRING单元格类型处理，后续依需要再升级
            if (value instanceof Number) {
                cell.setCellType(CellType.NUMERIC);
                cell.setCellValue(Double.parseDouble(value.toString()));
            } else {
                cell.setCellType(CellType.STRING);
                cell.setCellValue(value.toString());
            }
            return;
        }

        cell.setCellType(CellDataTypeConvertUtils.getExcelCellTypeByCellType(columnConfig.getCellDataType()));
        switch (columnConfig.getCellDataType()) {
            case NUMERIC:
                cell.setCellValue(Double.parseDouble(value.toString()));
                break;
            case BLANK:
                break;
            case STRING:
            default:
                cell.setCellValue(value.toString());
                break;
        }
    }
}
