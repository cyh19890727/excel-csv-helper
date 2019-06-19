package com.mi.excelcsv.helper.serializer.impl;

import com.mi.excelcsv.helper.serializer.AbstractCellSerializer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-6-18 下午5:43
 */
public class LocalDateSerializer extends AbstractCellSerializer {

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd";

    @Override
    public Object serialize(Object object, String args[]) {
        String dateFormat;
        if (args.length > 0) {
            dateFormat = args[0];
        } else {
            dateFormat = DEFAULT_FORMAT;
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(dateFormat);
        return ((LocalDate)object).format(fmt);
    }
}
