package com.mi.excelcsv.helper.serializer.impl;

import com.mi.excelcsv.helper.exception.ExcelCsvHelperException;
import com.mi.excelcsv.helper.serializer.AbstractCellSerializer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 18-12-24 下午2:55
 */
public class DateSerializer extends AbstractCellSerializer {

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd";

    @Override
    public Object serialize(Object object, String args[]) {
        String dateFormat;
        if (args.length > 0) {
            dateFormat = args[0];
        } else {
            dateFormat = DEFAULT_FORMAT;
        }

        final SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(dateFormat);

        return formatter.format((Date) object);
    }
}
