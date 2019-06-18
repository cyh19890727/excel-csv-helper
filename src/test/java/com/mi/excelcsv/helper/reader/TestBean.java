package com.mi.excelcsv.helper.reader;

import com.mi.excelcsv.helper.annotation.InputColumnFormat;
import com.mi.excelcsv.helper.annotation.NotEmpty;
import com.mi.excelcsv.helper.annotation.Size;
import com.mi.excelcsv.helper.deserializer.impl.BooleanDeserializer;
import com.mi.excelcsv.helper.deserializer.impl.DateDeserializer;

import java.util.Date;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-14 下午12:44
 */
public class TestBean {

    @InputColumnFormat(title = "姓名")
    @NotEmpty(message = "第%s行第%s列姓名不能为空")
    @Size(max = 5, message = "第%s行第%s列姓名长度不能超过5")
    private String name;

    @InputColumnFormat(title = "age")
    private int age;

    @InputColumnFormat(title = "出生日期", deserializer = DateDeserializer.class, args = {"yyyy-MM-dd HH:mm:ss"})
    private Date date;

    @InputColumnFormat(title = "是否禁用", deserializer = BooleanDeserializer.class, args = {"是", "否"})
    private Boolean enable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "name:" + name + ", age:" + age + ", date:" + date + ", enable:" + enable;
    }
}
