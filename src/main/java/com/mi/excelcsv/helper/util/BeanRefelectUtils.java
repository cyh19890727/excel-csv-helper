package com.mi.excelcsv.helper.util;

import com.mi.excelcsv.helper.exception.ExcelCsvHelperException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 18-12-21 下午9:22
 */
public class BeanRefelectUtils {

    public static String getBeanGetterMethodName(Field field) {
        String filedName = field.getName();
        char firstChar = filedName.charAt(0);
        return "get" + Character.toUpperCase(firstChar) + filedName.substring(1, filedName.length());
    }

    public static Object getBeanFieldValueByGetterMethod(Object object, Field field, Class type) {
        String getterMethodName = getBeanGetterMethodName(field);
        try {
            Method method = type.getMethod(getterMethodName);
            return method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcelCsvHelperException("getBeanFieldValueByGetterMethod fail");
        }

    }
}
