package com.mi.excelcsv.helper.annotation;

import com.mi.excelcsv.helper.constant.CellDataType;
import com.mi.excelcsv.helper.serializer.AbstractCellSerializer;
import com.mi.excelcsv.helper.serializer.impl.DefaultSerializer;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  Excel，Csv共用的列格式注解
 *
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 18-12-21 下午8:02
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ColumnFormat {

    int NO_ORDER = -1;

    int order() default NO_ORDER;

    String title() default "";

    CellDataType dateType() default CellDataType.STRING;

    Class<? extends AbstractCellSerializer> serializer() default DefaultSerializer.class;

    String[] args() default {};

}
