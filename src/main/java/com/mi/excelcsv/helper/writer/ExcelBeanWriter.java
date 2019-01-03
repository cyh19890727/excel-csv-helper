package com.mi.excelcsv.helper.writer;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 18-12-25 下午5:40
 */
public interface ExcelBeanWriter extends BeanWriter {

    /**
     *  增加sheet页
     */
    void addSheet();

    /**
     *  返回sheet页总数
     */
    int getNumberOfSheets();

    /**
     * 切换sheet页到指定的页面
     *
     * @param sheetIndex, sheet页索引，在0到sheet总数减1之间，超出范围会抛出ExcelCsvHelperException异常
     */
    void setSheetIndex(int sheetIndex);

}
