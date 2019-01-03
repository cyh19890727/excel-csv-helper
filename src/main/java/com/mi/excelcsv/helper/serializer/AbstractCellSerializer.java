package com.mi.excelcsv.helper.serializer;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 18-12-24 下午1:01
 */
public abstract class AbstractCellSerializer {

    public abstract Object serialize(Object object, String args[]);
}
