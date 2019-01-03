package com.mi.excelcsv.helper.serializer.impl;

import com.mi.excelcsv.helper.serializer.AbstractCellSerializer;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 18-12-24 下午3:28
 */
public class DefaultSerializer extends AbstractCellSerializer {

    @Override
    public Object serialize(Object object, String args[]) {
        return object;
    }
}
