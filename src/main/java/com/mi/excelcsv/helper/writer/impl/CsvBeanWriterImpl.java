package com.mi.excelcsv.helper.writer.impl;

import com.mi.excelcsv.helper.annotation.resolver.BeanAnnoResolver;
import com.mi.excelcsv.helper.exception.ExcelCsvHelperException;
import com.mi.excelcsv.helper.processer.CellSerializerProcesser;
import com.mi.excelcsv.helper.serializer.AbstractCellSerializer;
import com.mi.excelcsv.helper.serializer.impl.DefaultSerializer;
import com.mi.excelcsv.helper.writer.CsvFormatBeanWriter;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.util.List;

/**
 * 标准csv格式的csv writer实现类定义
 *
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 18-12-24 下午5:30
 */
public class CsvBeanWriterImpl implements CsvFormatBeanWriter {

    private CsvBeanWriter csvBeanWriter;

    private boolean titleWritten = false;

    private BeanAnnoResolver beanAnnoResolver;

    public CsvBeanWriterImpl(java.io.Writer writer) {
        this(CsvPreference.STANDARD_PREFERENCE, writer);
    }

    public CsvBeanWriterImpl(CsvPreference csvPreference, java.io.Writer writer) {
        csvBeanWriter = new CsvBeanWriter(writer, csvPreference);
        titleWritten = false;
        beanAnnoResolver = null;
    }


    public <E> void write(List<E> list, Class<E> beanType) throws ExcelCsvHelperException {
        if (beanAnnoResolver == null) {
            beanAnnoResolver = BeanAnnoResolver.getResolerByBeanType(beanType);
        } else {
            if (beanAnnoResolver.getBeanType() != beanType) {
                throw new ExcelCsvHelperException("beanType is changing");
            }
        }
        writeHeader(beanAnnoResolver);
        String[] nameMapping = new String[beanAnnoResolver.getFieldList().size()];
        for (int i = 0; i < beanAnnoResolver.getFieldList().size(); i++) {
            nameMapping[i] = beanAnnoResolver.getFieldList().get(i).getName();
        }

        if (isNeedProcesser()) {
            CellProcessor[] processors = new CellProcessor[beanAnnoResolver.getSerializerList().size()];
            for (int i = 0; i < processors.length; i++) {
                if (beanAnnoResolver.getSerializerList().get(i) != null) {
                    processors[i] = new CellSerializerProcesser(beanAnnoResolver.getSerializerList().get(i),
                            beanAnnoResolver.getArgsList().get(i));
                } else {
                    processors[i] = new CellSerializerProcesser(new DefaultSerializer(), new String[]{});
                }
            }

            try {
                for (Object bean : list) {
                    csvBeanWriter.write(bean, nameMapping, processors);
                }
            } catch (IOException e) {
                throw new ExcelCsvHelperException("CsvBeanWriterImpl write fail", e);
            }
        } else {
            try {
                for (Object bean : list) {
                    csvBeanWriter.write(bean, nameMapping);
                }
            } catch (IOException e) {
                throw new ExcelCsvHelperException("CsvBeanWriterImpl write fail", e);
            }
        }

    }

    public void close() throws ExcelCsvHelperException {
        try {
            csvBeanWriter.close();
        } catch (IOException e) {
            throw new ExcelCsvHelperException("CsvBeanWriterImpl close fail", e);
        }
    }

    private void writeHeader(BeanAnnoResolver resolver) throws ExcelCsvHelperException {
        if (titleWritten) {
            return ;
        }
        titleWritten = true;
        // 写列标题
        String[] headers = new String[resolver.getTitleList().size()];
        headers = resolver.getTitleList().toArray(headers);
        try {
            csvBeanWriter.writeHeader(headers);
        } catch (IOException e) {
            throw new ExcelCsvHelperException("CsvBeanWriterImpl write header fail", e);
        }
    }

    private boolean isNeedProcesser() {
        for (AbstractCellSerializer serializer : beanAnnoResolver.getSerializerList()) {
            if (serializer != null && serializer.getClass() != DefaultSerializer.class) {
                return true;
            }
        }

        return false;
    }
}
