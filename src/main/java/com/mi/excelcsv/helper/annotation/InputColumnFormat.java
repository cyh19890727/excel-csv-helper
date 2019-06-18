package com.mi.excelcsv.helper.annotation;

import com.mi.excelcsv.helper.deserializer.AbstractCellDeserializer;
import com.mi.excelcsv.helper.deserializer.impl.DefaultDeserializer;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-10 下午5:35
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InputColumnFormat {

    String title() default "";

    Class<? extends AbstractCellDeserializer> deserializer() default DefaultDeserializer.class;

    String[] args() default {};
}
