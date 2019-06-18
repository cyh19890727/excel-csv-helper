package com.mi.excelcsv.helper.writer;

import org.junit.Test;
import org.supercsv.prefs.CsvPreference;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 18-12-25 下午4:56
 */
public class CsvWriterTest {

    /**
     *  csv文件内容如下，
     *  姓名,出生日期,age,是否禁用
     *  aa,2018-12-25 17:01:00,18,否
     */
    @Test
    public void testCsvWriter() throws IOException {

        TestBean testBean = new TestBean();
        testBean.setName("aa");
        testBean.setSex("male");
        testBean.setAge(18);
        testBean.setDate(new Date());
        testBean.setEnable(false);
        List<TestBean> list = new ArrayList<TestBean>();
        list.add(testBean);

        FileWriter fileWriter = new FileWriter("test.csv");
        try (CsvFormatBeanWriter csvWriter = BeanWriterFactory.createCsvWriter(CsvPreference.STANDARD_PREFERENCE, fileWriter);) {
            csvWriter.write(list, TestBean.class);
            csvWriter.write(list, TestBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
