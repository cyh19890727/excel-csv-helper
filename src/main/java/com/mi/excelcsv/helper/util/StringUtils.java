package com.mi.excelcsv.helper.util;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 18-12-21 下午8:52
 */
public class StringUtils {

    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static String removeDecimalPartInvalidZero(String value) {
        int pointLoc = value.indexOf(".");
        if (pointLoc < 0) {
            return value;
        }
        if (pointLoc == value.length() - 1) {
            return value.substring(0, value.length() - 1);
        }

        String decimalPartStr = value.substring(pointLoc + 1, value.length());
        int startIndex = decimalPartStr.length() - 1;
        for (; startIndex >= 0; startIndex--) {
            if (decimalPartStr.charAt(startIndex) != '0') {
                break;
            }
        }
        if (startIndex < 0) {
            return value.substring(0, pointLoc);
        } else {
            return value.substring(0, pointLoc + startIndex + 2);
        }

    }

}
