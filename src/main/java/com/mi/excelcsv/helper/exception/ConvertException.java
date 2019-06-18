package com.mi.excelcsv.helper.exception;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-30 上午10:20
 */
public class ConvertException extends Exception {

    private int row;
    private String title;

    public ConvertException(String message, Throwable cause, int row, String title) {
        super(message, cause);
        this.row = row;
        this.title = title;
    }

    public int getRow() {
        return row;
    }

    public String getTitle() {
        return title;
    }
}
