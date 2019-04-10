package com.mi.excelcsv.helper.type;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 18-12-24 上午11:30
 */
public enum CellDataType {
    /**
     *  string  cell type
     */
    STRING,
    /**
     *  numeric  cell type
     */
    NUMERIC,
    /**
     *  formula  cell type, for excel
     */
    FORMULA,
    /**
     *  blank cell type
     */
    BLANK,
    /**
     *  boolean cell type
     */
    BOOLEAN,
    /**
     *  error cell type,  for excel
     */
    ERROR,
    /**
     *  no cell type
     */
    NONE;
}
