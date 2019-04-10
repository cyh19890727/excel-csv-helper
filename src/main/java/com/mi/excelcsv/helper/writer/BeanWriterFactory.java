package com.mi.excelcsv.helper.writer;

import com.mi.excelcsv.helper.type.ExcelType;
import com.mi.excelcsv.helper.writer.impl.CsvBeanWriterImpl;
import com.mi.excelcsv.helper.writer.impl.XlsxExcelBeanWriterImpl;
import org.supercsv.prefs.CsvPreference;

import java.io.OutputStream;

/**
 * 创建writer的工厂类
 *
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 18-12-24 下午5:34
 */
public class BeanWriterFactory {

    /**
     * 根据指定的excel type创建excel writer
     *
     * @param excelType, excel类型, 目前仅支持xlsx格式的writer
     * @param outputStream, excel内容输出流
     */
    public static ExcelBeanWriter createExcelWriter(ExcelType excelType, OutputStream outputStream) {
        switch (excelType) {
            case XLSX_SUFFIX :
                return new XlsxExcelBeanWriterImpl(outputStream);
            default :
                return new XlsxExcelBeanWriterImpl(outputStream);

        }
    }

    /**
     * 创建csv writer
     *
     * @param writer, csv内容输出流
     */
    public static CsvFormatBeanWriter createCsvWriter(CsvPreference csvPreference, java.io.Writer writer) {
        return new CsvBeanWriterImpl(csvPreference, writer);
    }

}
