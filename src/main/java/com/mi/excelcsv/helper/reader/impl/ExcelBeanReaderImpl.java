package com.mi.excelcsv.helper.reader.impl;

import com.mi.excelcsv.helper.annotation.resolver.InputBeanResolver;
import com.mi.excelcsv.helper.exception.ConvertException;
import com.mi.excelcsv.helper.exception.ExcelCsvHelperException;
import com.mi.excelcsv.helper.exception.InputValidationException;
import com.mi.excelcsv.helper.reader.BeanReader;
import com.mi.excelcsv.helper.util.BeanRefelectUtils;
import com.mi.excelcsv.helper.util.StringUtils;
import com.mi.excelcsv.helper.validation.AbstractPostValidator;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-10 下午4:21
 */
public class ExcelBeanReaderImpl implements BeanReader {

    private InputStream inputStream;

    private Workbook workbook;

    private Sheet sheet;

    private int currentRow;

    private Map<String, Integer> titleCellIndexMap;

    private InputBeanResolver resolver;

    private AbstractPostValidator postValidator;

    public ExcelBeanReaderImpl(InputStream inputStream) throws ExcelCsvHelperException {
        try {
            this.inputStream = inputStream;
            workbook = WorkbookFactory.create(inputStream);
        } catch (IOException | EncryptedDocumentException e) {
            throw new ExcelCsvHelperException("create workbook fail", e);
        }

        sheet = workbook.getSheetAt(0);
        // 数据行从第二行开始
        currentRow = 1;
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
        for (int i = currentRow; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
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
                Cell cell = row.getCell(cellIndex);
                // 设置bean
                try {
                    String cellValue = getCellStringValue(cell);
                    InputBeanResolver.FieldConfig fieldConfig = resolver.getTitleFieldConfigMap().get(title);
                    BeanRefelectUtils.setFieldValue(bean, fieldConfig.getField(), cellValue, resolver.getTitleDeserializerConfigMap().get(title));
                } catch (Exception e) {
                    throw new ConvertException("convert bean field value fail", e, i + 1, title);
                }
                // 单元格校验
                Object fieldValue = BeanRefelectUtils.getBeanFieldValueByGetterMethod(bean, resolver.getTitleFieldConfigMap().get(title).getField(), beanType);
                for (InputBeanResolver.ValidatorConfig validatorConfig : resolver.getTitleValidatorConfigsMap().get(title)) {
                    validatorConfig.getValidator().validate(resolver.getTitleFieldConfigMap().get(title).getField().getType(), fieldValue, validatorConfig.getMessage(), i + 1, cellIndex + 1, validatorConfig.getArgs());
                }
            }
            if (postValidator != null) {
                postValidator.postValidate(i + 1, bean, beans);
            }
            beans.add(bean);
        }

        currentRow = sheet.getLastRowNum();
        return beans;
    }

    @Override
    public void close() throws ExcelCsvHelperException {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
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

        Row row = sheet.getRow(0);
        titleCellIndexMap = new HashMap<>();
        for (short i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            String title  = getCellStringValue(cell);
            titleCellIndexMap.put(title, Integer.valueOf(i));
        }
    }

    /**
     *  获取单元格原始内容，已字符串形式返回
     * @return
     */
    private String getCellStringValue(Cell cell) throws ExcelCsvHelperException {
        // 目前只支持NUMERIC、STRING、BOOLEAN类型的单元格
        switch (cell.getCellType()) {
            case BLANK :
                return "";
            case NUMERIC :
                return StringUtils.removeDecimalPartInvalidZero("" + cell.getNumericCellValue());
            case STRING :
                return cell.getStringCellValue();
            case BOOLEAN:
                return "" + cell.getBooleanCellValue();
            default:
                throw new ExcelCsvHelperException("unsupport cell type :" + cell.getCellType().name());
        }
    }

    /**
     *  获取单元格原始内容，已字符串形式返回
     * @return
     */
    private Object getCellValue(Cell cell) throws ExcelCsvHelperException {
        // 目前只支持NUMERIC、STRING、BOOLEAN类型的单元格
        switch (cell.getCellType()) {
            case NUMERIC :
                return cell.getNumericCellValue();
            case STRING :
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            default:
                throw new ExcelCsvHelperException("unsupport cell type :" + cell.getCellType().name());
        }
    }

}
