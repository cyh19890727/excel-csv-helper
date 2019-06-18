package com.mi.excelcsv.helper.deserializer.impl;

import com.mi.excelcsv.helper.deserializer.AbstractCellDeserializer;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-10 下午5:40
 */
public class DefaultDeserializer extends AbstractCellDeserializer {

    @Override
    public Object deserialize(String cellValue, String[] args) {
        return cellValue;
    }
}
