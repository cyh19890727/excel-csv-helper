package com.mi.excelcsv.helper.validation;

import com.mi.excelcsv.helper.exception.InputValidationException;

import java.util.List;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-24 上午10:15
 */
public abstract class AbstractPostValidator {

   public abstract void postValidate(int row, Object currentBean, List currentBeans) throws InputValidationException;

}
