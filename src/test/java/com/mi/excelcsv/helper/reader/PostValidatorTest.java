package com.mi.excelcsv.helper.reader;

import com.mi.excelcsv.helper.exception.InputValidationException;
import com.mi.excelcsv.helper.validation.AbstractPostValidator;

import java.util.List;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-24 上午11:06
 */
public class PostValidatorTest extends AbstractPostValidator {

    @Override
    public void postValidate(int row, Object currentBean, List currentBeans) throws InputValidationException {
        System.out.println("row:" + row);
        System.out.println(currentBean);
        currentBeans.forEach((i) -> System.out.println(i));
    }
}
