package com.mi.excelcsv.helper.deserializer.impl;

import com.mi.excelcsv.helper.deserializer.AbstractCellDeserializer;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-14 下午12:24
 */
public class BooleanDeserializer extends AbstractCellDeserializer {

    private static final int ARGS_LEN = 2;

    @Override
    public Boolean deserialize(String cellValue, String[] args) {
        if (args.length == ARGS_LEN) {
            return cellValue.equals(args[0]) ? Boolean.TRUE : Boolean.FALSE;
        } else {
            return Boolean.FALSE;
        }
    }
}
