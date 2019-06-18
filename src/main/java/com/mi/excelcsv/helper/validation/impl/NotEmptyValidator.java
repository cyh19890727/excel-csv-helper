package com.mi.excelcsv.helper.validation.impl;

import com.mi.excelcsv.helper.exception.InputValidationException;
import com.mi.excelcsv.helper.util.StringUtils;
import com.mi.excelcsv.helper.validation.Validator;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-13 下午3:44
 */
public class NotEmptyValidator implements Validator {

    @Override
    public void validate(Class type, Object value, String message, int row, int col, Object... objs) throws InputValidationException {
        if (type == String.class) {
            if (StringUtils.isEmpty((String)value)) {
                throw new InputValidationException(formatMessage(message, row, col));
            }
        }
    }
}
