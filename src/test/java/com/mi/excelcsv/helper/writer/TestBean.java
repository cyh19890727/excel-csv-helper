package com.mi.excelcsv.helper.writer;

import com.mi.excelcsv.helper.annotation.ColumnFormat;
import com.mi.excelcsv.helper.serializer.impl.BooleanSerializer;
import com.mi.excelcsv.helper.serializer.impl.DateSerializer;
import com.mi.excelcsv.helper.type.CellDataType;

import java.util.Date;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 18-12-21 下午9:53
 */
public class TestBean {

    @ColumnFormat(title = "姓名", order = 1)
    private String name;

    private String sex;

    @ColumnFormat
    private int age;

    @ColumnFormat(title = "出生日期", order = 2, serializer = DateSerializer.class, args = {"yyyy-MM-dd HH:mm:ss"})
    private Date date;

    @ColumnFormat(title = "是否禁用", serializer = BooleanSerializer.class, args = {"是", "否"})
    private Boolean enable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
