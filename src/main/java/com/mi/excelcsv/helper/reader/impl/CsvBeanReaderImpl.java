package com.mi.excelcsv.helper.reader.impl;

import com.mi.excelcsv.helper.annotation.resolver.InputBeanResolver;
import com.mi.excelcsv.helper.csv.ImproveBeanReader;
import com.mi.excelcsv.helper.exception.ConvertException;
import com.mi.excelcsv.helper.exception.ExcelCsvHelperException;
import com.mi.excelcsv.helper.exception.InputValidationException;
import com.mi.excelcsv.helper.reader.BeanReader;
import com.mi.excelcsv.helper.util.BeanRefelectUtils;
import com.mi.excelcsv.helper.validation.AbstractPostValidator;
import org.supercsv.prefs.CsvPreference;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-24 下午3:10
 */
public class CsvBeanReaderImpl implements BeanReader {

    private Reader reader;

    private ImproveBeanReader beanReader;

    private InputBeanResolver resolver;

    private Map<String, Integer> titleCellIndexMap;

    private AbstractPostValidator postValidator;

    public CsvBeanReaderImpl(Reader reader) {
        beanReader = new ImproveBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);
    }

    @Override
    public void setPostValidator(AbstractPostValidator postValidator) {
        this.postValidator = postValidator;
    }

    @Override
    public <T> List<T> read(Class<T> beanType) throws ExcelCsvHelperException, InputValidationException, ConvertException {
        if (resolver == null) {
            resolver = InputBeanResolver.getResolerByBeanType(beanType);
        }
        // 解析标题
        readTitleRow();

        List<T> beans = new ArrayList<>();
        while (true) {
            // 读下一行
            try {
                if (!beanReader.readNextRow()) {
                    break;
                }
            } catch (Exception e) {
                throw new ExcelCsvHelperException("read next row fail", e);
            }

            T bean;
            try {
                bean = beanType.newInstance();
            } catch (Exception e) {
                throw new ExcelCsvHelperException("instantiation bean fail", e);
            }
            for (String title : resolver.getTitleFieldConfigMap().keySet()) {
                Integer cellIndex = titleCellIndexMap.get(title);
                if (cellIndex == null) {
                    throw new ExcelCsvHelperException("nonexist title : " + title);
                }
                String cellValue = beanReader.get(cellIndex);
                // 设置bean
                try {
                    InputBeanResolver.FieldConfig fieldConfig = resolver.getTitleFieldConfigMap().get(title);
                    BeanRefelectUtils.setFieldValue(bean, fieldConfig.getField(), cellValue, resolver.getTitleDeserializerConfigMap().get(title));
                } catch (Exception e) {
                    throw new ConvertException("convert bean field value fail", e, beanReader.getRowNumber(), title);
                }
                // 单元格校验
                Object fieldValue = BeanRefelectUtils.getBeanFieldValueByGetterMethod(bean, resolver.getTitleFieldConfigMap().get(title).getField(), beanType);
                for (InputBeanResolver.ValidatorConfig validatorConfig : resolver.getTitleValidatorConfigsMap().get(title)) {
                    validatorConfig.getValidator().validate(resolver.getTitleFieldConfigMap().get(title).getField().getType(), fieldValue, validatorConfig.getMessage(), beanReader.getRowNumber(), cellIndex, validatorConfig.getArgs());
                }
            }
            if (postValidator != null) {
                postValidator.postValidate(beanReader.getRowNumber(), bean, beans);
            }
            beans.add(bean);
        }

        return beans;
    }

    @Override
    public void close() throws ExcelCsvHelperException {
        try {
            beanReader.close();
        } catch (Exception e) {
            throw new ExcelCsvHelperException("close fail", e);
        }
    }

    /**
     *  解析标题
     */
    private void readTitleRow() throws ExcelCsvHelperException {
        if (titleCellIndexMap != null) {
            return ;
        }

        try {
            titleCellIndexMap = new HashMap<>();
            String titles[] = beanReader.getHeader(true);
            for (int i = 0; i < titles.length; i++) {
                titleCellIndexMap.put(titles[i], i + 1);
            }
        } catch (Exception e) {
            throw new ExcelCsvHelperException("read title row fail, ", e);
        }
    }
}
