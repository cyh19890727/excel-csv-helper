package com.mi.excelcsv.helper.annotation.resolver;

import com.mi.excelcsv.helper.annotation.InputColumnFormat;
import com.mi.excelcsv.helper.annotation.NotEmpty;
import com.mi.excelcsv.helper.annotation.Size;
import com.mi.excelcsv.helper.deserializer.AbstractCellDeserializer;
import com.mi.excelcsv.helper.exception.ExcelCsvHelperException;
import com.mi.excelcsv.helper.util.BeanRefelectUtils;
import com.mi.excelcsv.helper.validation.Validator;
import com.mi.excelcsv.helper.validation.impl.NotEmptyValidator;
import com.mi.excelcsv.helper.validation.impl.SizeValidator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析bean上配置的InputColumnFormat注解，并存储相关的配置信息
 *
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-10 下午6:22
 */
public class InputBeanResolver {

    private Class beanType;

    private Map<String, FieldConfig> titleFieldConfigMap = new HashMap<>();

    private Map<String, DeserializerConfig> titleDeserializerConfigMap = new HashMap<>();

    private Map<String, List<ValidatorConfig>> titleValidatorConfigsMap = new HashMap<>();

    public static InputBeanResolver getResolerByBeanType(Class beanType) throws ExcelCsvHelperException {
        InputBeanResolver resolver = new InputBeanResolver();
        resolver.setBeanType(beanType);
        List<Field> fieldList = BeanRefelectUtils.getAllFields(beanType);
        Field[] fields = new Field[fieldList.size()];
        fields = BeanRefelectUtils.getAllFields(beanType).toArray(fields);
        for (int i = 0; i < fields.length; i++) {
            InputColumnFormat columnFormat = fields[i].getAnnotation(InputColumnFormat.class);
            // 配置了注解的是需要读取的列
            if (columnFormat != null) {
                // 获取title配置
                String title = columnFormat.title();
                resolver.getTitleFieldConfigMap().put(title, new FieldConfig(fields[i]));
                // 获取反序列化配置
                Class<? extends AbstractCellDeserializer> deserializer = columnFormat.deserializer();
                AbstractCellDeserializer deserializerObj;
                try {
                    deserializerObj = deserializer.newInstance();
                } catch (Exception e) {
                    throw new ExcelCsvHelperException("instantiate cell deserializer object fail");
                }
                resolver.getTitleDeserializerConfigMap().put(title, new DeserializerConfig(deserializerObj, columnFormat.args()));

                // 解析校验器
                resolver.getTitleValidatorConfigsMap().put(title, new ArrayList());
                if (fields[i].getAnnotation(NotEmpty.class) != null) {
                    resolver.getTitleValidatorConfigsMap().get(title).add(new ValidatorConfig(new NotEmptyValidator(),
                            fields[i].getAnnotation(NotEmpty.class).message(), new Object[]{}));
                }
                if (fields[i].getAnnotation(Size.class) != null) {
                    resolver.getTitleValidatorConfigsMap().get(title).add(new ValidatorConfig(new SizeValidator(),
                            fields[i].getAnnotation(Size.class).message(), new Object[]{fields[i].getAnnotation(Size.class).min(),
                            fields[i].getAnnotation(Size.class).max()}));
                }
            }
        }

        return resolver;
    }

    public Class getBeanType() {
        return beanType;
    }

    public void setBeanType(Class beanType) {
        this.beanType = beanType;
    }

    public Map<String, FieldConfig> getTitleFieldConfigMap() {
        return titleFieldConfigMap;
    }

    public void setTitleFieldConfigMap(Map<String, FieldConfig> titleFieldConfigMap) {
        this.titleFieldConfigMap = titleFieldConfigMap;
    }

    public Map<String, DeserializerConfig> getTitleDeserializerConfigMap() {
        return titleDeserializerConfigMap;
    }

    public void setTitleDeserializerConfigMap(Map<String, DeserializerConfig> titleDeserializerConfigMap) {
        this.titleDeserializerConfigMap = titleDeserializerConfigMap;
    }

    public Map<String, List<ValidatorConfig>> getTitleValidatorConfigsMap() {
        return titleValidatorConfigsMap;
    }

    public void setTitleValidatorConfigsMap(Map<String, List<ValidatorConfig>> titleValidatorConfigsMap) {
        this.titleValidatorConfigsMap = titleValidatorConfigsMap;
    }

    public static class FieldConfig {

        private Field field;

        public FieldConfig(Field field) {
            this.field = field;
        }

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }
    }

    public static class DeserializerConfig {

        private AbstractCellDeserializer deserializer;
        
        private String[] args;

        public DeserializerConfig(AbstractCellDeserializer deserializer, String[] args) {
            this.deserializer = deserializer;
            this.args = args;
        }

        public AbstractCellDeserializer getDeserializer() {
            return deserializer;
        }

        public void setDeserializer(AbstractCellDeserializer deserializer) {
            this.deserializer = deserializer;
        }

        public String[] getArgs() {
            return args;
        }

        public void setArgs(String[] args) {
            this.args = args;
        }
    }

    public static class ValidatorConfig {

        private Validator validator;

        private String message;

        private Object[] args;

        public ValidatorConfig(Validator validator, String message, Object[] args) {
            this.validator = validator;
            this.message = message;
            this.args = args;
        }

        public Validator getValidator() {
            return validator;
        }

        public void setValidator(Validator validator) {
            this.validator = validator;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object[] getArgs() {
            return args;
        }

        public void setArgs(Object[] args) {
            this.args = args;
        }
    }
}
