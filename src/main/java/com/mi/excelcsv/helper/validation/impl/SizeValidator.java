package com.mi.excelcsv.helper.validation.impl;

import com.mi.excelcsv.helper.exception.InputValidationException;
import com.mi.excelcsv.helper.validation.Validator;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-13 下午3:55
 */
public class SizeValidator implements Validator  {

    @Override
    public void validate(Class type, Object value, String message, int row, int col, Object... objs) throws InputValidationException {
        if (type == String.class && value != null) {
            int len = ((String) value).length();
            int min = (Integer) objs[0];
            int max = (Integer) objs[1];
            if (len < min || len > max) {
                throw new InputValidationException(formatMessage(message, row, col));
            }
        }
    }
}
