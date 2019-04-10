package com.mi.excelcsv.helper.writer.impl;

import com.mi.excelcsv.helper.dynamiccolumn.ColumnConfig;
import com.mi.excelcsv.helper.exception.ExcelCsvHelperException;
import com.mi.excelcsv.helper.processer.CellSerializerProcesser;
import com.mi.excelcsv.helper.serializer.impl.DefaultSerializer;
import com.mi.excelcsv.helper.writer.CsvDynColWriter;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-3-4 下午2:32
 */
public class CsvDynColWriterImpl implements CsvDynColWriter {

    private CsvMapWriter csvMapWriter;

    private List<ColumnConfig> columnConfigs;

    private String[] headerArr;

    public CsvDynColWriterImpl(java.io.Writer writer) {
        this(CsvPreference.STANDARD_PREFERENCE, writer);
    }

    public CsvDynColWriterImpl(CsvPreference csvPreference, java.io.Writer writer) {
        csvMapWriter = new CsvMapWriter(writer, csvPreference);
    }

    public void writeTitle(List<ColumnConfig> columnConfigs) throws ExcelCsvHelperException {
        if (this.columnConfigs != null) {
            throw new ExcelCsvHelperException("can not write title twice");
        }
        this.columnConfigs = columnConfigs;
        headerArr = new String[columnConfigs.size()];
        for (int i = 0; i < columnConfigs.size(); i++) {
            headerArr[i] = columnConfigs.get(i).getTitle();
        }
        try {
            csvMapWriter.writeHeader(headerArr);
        } catch (IOException e) {
            throw new ExcelCsvHelperException("CsvBeanWriterImpl write header fail", e);
        }
    }

    public void write(List<Map<String, Object>> datas) throws ExcelCsvHelperException {
        if (this.columnConfigs == null) {
            throw new ExcelCsvHelperException("must write title before write data");
        }

        try {
            if (isNeedProcesser()) {
                CellProcessor[] processors = new CellProcessor[columnConfigs.size()];
                for (int i = 0; i < processors.length; i++) {
                    processors[i] = new CellSerializerProcesser(columnConfigs.get(i).getCellSerializer(), columnConfigs.get(i).getSerializerArgs());
                }
                for (Map<String, Object> lineMap : datas) {
                    csvMapWriter.write(lineMap, headerArr, processors);
                }
            } else {
                for (Map<String, Object> lineMap : datas) {
                    csvMapWriter.write(lineMap, headerArr);
                }
            }
        } catch(IOException e) {
            throw new ExcelCsvHelperException("csvDynColWriterImpl write fail", e);
        }
    }

    public void close() throws ExcelCsvHelperException {
        try {
            csvMapWriter.close();
        } catch (IOException e) {
            throw new ExcelCsvHelperException("csvDynColWriterImpl close fail", e);
        }
    }

    private boolean isNeedProcesser() {
        for (ColumnConfig columnConfig : columnConfigs) {
            if (columnConfig.getCellSerializer().getClass() != DefaultSerializer.class) {
                return true;
            }
        }

        return false;
    }
}
