package com.mi.excelcsv.helper.serializer.impl;

import com.mi.excelcsv.helper.serializer.AbstractCellSerializer;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-6-18 下午5:54
 */
public class LocalTimeSerializer extends AbstractCellSerializer {

    private static final String DEFAULT_FORMAT = "HH:mm:ss";

    @Override
    public Object serialize(Object object, String args[]) {
        String dateFormat;
        if (args.length > 0) {
            dateFormat = args[0];
        } else {
            dateFormat = DEFAULT_FORMAT;
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(dateFormat);
        return ((LocalTime)object).format(fmt);
    }
}
