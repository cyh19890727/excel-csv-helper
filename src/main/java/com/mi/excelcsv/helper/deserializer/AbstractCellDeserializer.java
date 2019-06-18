package com.mi.excelcsv.helper.deserializer;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-10 下午5:39
 */
public abstract class AbstractCellDeserializer {

    public abstract Object deserialize(String cellValue, String args[]);
}
