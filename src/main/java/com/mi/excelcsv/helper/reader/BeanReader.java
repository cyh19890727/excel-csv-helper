package com.mi.excelcsv.helper.reader;

import com.mi.excelcsv.helper.exception.ConvertException;
import com.mi.excelcsv.helper.exception.ExcelCsvHelperException;
import com.mi.excelcsv.helper.exception.InputValidationException;
import com.mi.excelcsv.helper.validation.AbstractPostValidator;

import java.util.List;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-10 下午4:13
 */
public interface BeanReader extends AutoCloseable {

    /**
     *
     * @param beanType, 待读入的bean类型
     * @return bean列表
     */
    <T> List<T> read(Class<T> beanType) throws ExcelCsvHelperException, InputValidationException, ConvertException;

    /**
     *  不需要再执行读入操作时，需要执行close操作关闭流资源
     */
    void close() throws ExcelCsvHelperException;

    default void setPostValidator(AbstractPostValidator postValidator) {}

}
