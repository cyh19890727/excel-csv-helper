package com.mi.excelcsv.helper.writer;

import com.mi.excelcsv.helper.exception.ExcelCsvHelperException;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-3-4 下午2:01
 */
public interface ExcelDynColWriter extends DynamicColumnWriter {

    /**
     *  刷新excel缓存至输出流, 只能调用一次
     */
    void flush() throws ExcelCsvHelperException;
}
