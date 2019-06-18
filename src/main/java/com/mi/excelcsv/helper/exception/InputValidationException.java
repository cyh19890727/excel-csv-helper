package com.mi.excelcsv.helper.exception;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-13 下午3:36
 */
public class InputValidationException extends Exception {

    private static final long serialVersionUID = 1L;

    public InputValidationException(String message) {
        super(message);
    }

    public InputValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
