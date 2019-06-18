package com.mi.excelcsv.helper.reader;

import com.mi.excelcsv.helper.exception.InputValidationException;
import com.mi.excelcsv.helper.validation.AbstractPostValidator;

import java.util.List;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-31 上午9:18
 */
public class TestPostValidator extends AbstractPostValidator {

    @Override
    public void postValidate(int row, Object currentBean, List currentBeans) throws InputValidationException {
        System.out.println("当前处理第" + row + "行");
        System.out.println("当前处理的Bean：" + currentBean);
    }
}
