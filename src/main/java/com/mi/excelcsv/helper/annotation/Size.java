package com.mi.excelcsv.helper.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-13 下午3:37
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Size {

    String message() default  "com.mi.excelcsv.helper.exception.ValidatioException";

    int min() default 0;

    int max() default Integer.MAX_VALUE;
}
