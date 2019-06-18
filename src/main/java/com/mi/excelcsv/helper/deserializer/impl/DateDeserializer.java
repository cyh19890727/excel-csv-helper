package com.mi.excelcsv.helper.deserializer.impl;

import com.mi.excelcsv.helper.deserializer.AbstractCellDeserializer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-14 下午12:46
 */
public class DateDeserializer extends AbstractCellDeserializer {

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd";

    @Override
    public Object deserialize(String cellValue, String[] args) {
        String dateFormat;
        if (args.length > 0) {
            dateFormat = args[0];
        } else {
            dateFormat = DEFAULT_FORMAT;
        }

        final SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(dateFormat);

        try {
            return formatter.parse(cellValue);
        } catch (Exception e) {
            return null;
        }


    }
}
