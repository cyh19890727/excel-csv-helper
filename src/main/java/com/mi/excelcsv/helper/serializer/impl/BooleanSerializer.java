package com.mi.excelcsv.helper.serializer.impl;

import com.mi.excelcsv.helper.serializer.AbstractCellSerializer;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 18-12-24 下午1:03
 */
public class BooleanSerializer extends AbstractCellSerializer {

    private static final int ARGS_LEN = 2;

    @Override
    public Object serialize(Object object, String[] args) {
        Boolean srcObj = (Boolean)object;
        if (args.length == ARGS_LEN) {
            return srcObj.equals(Boolean.TRUE) ? args[0] : args[1];
        } else {
            return srcObj.toString();
        }
    }

}
