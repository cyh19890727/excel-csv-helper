package com.mi.excelcsv.helper.validation;

import com.mi.excelcsv.helper.exception.InputValidationException;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-13 下午3:43
 */
public interface Validator {

    default String formatMessage(String message, int row, int col) {
        return String.format(message, row, col);
    }

    /**
     *
     * @param type, 待校验的Bean属性类型
     * @param value, 待校验的Bean属性值
     * @param message, 校验注解上的message参数
     * @param row, 当前处理的行
     * @param col, 当前处理的列
     * @param objs, 可变参数列表，例如常用的校验注解参数可放在这里
     * @throws InputValidationException
     */
    void validate(Class type, Object value, String message, int row, int col, Object... objs) throws InputValidationException;
}
