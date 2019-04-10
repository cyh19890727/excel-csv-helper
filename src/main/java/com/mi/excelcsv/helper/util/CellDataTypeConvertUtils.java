package com.mi.excelcsv.helper.util;

import com.mi.excelcsv.helper.type.CellDataType;
import org.apache.poi.ss.usermodel.CellType;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 18-12-24 下午5:15
 */
public class CellDataTypeConvertUtils {

    public static CellType getExcelCellTypeByCellType(CellDataType dataType) {
        switch (dataType) {
            case STRING :
                return CellType.STRING;
            case NUMERIC :
                return CellType.NUMERIC;
            case FORMULA :
                return CellType.NUMERIC;
            case BLANK :
                return CellType.BLANK;
            case BOOLEAN :
                return CellType.BOOLEAN;
            case ERROR :
                return CellType.ERROR;
            default :
                return CellType._NONE;
        }
    }
}
