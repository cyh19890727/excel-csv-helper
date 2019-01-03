package com.mi.excelcsv.helper.writer;

import org.junit.Test;
import org.supercsv.prefs.CsvPreference;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 18-12-25 下午4:56
 */
public class TestCsvWriter {

    /**
     *  csv文件内容如下，
     *  姓名,出生日期,age,是否禁用
     *  aa,2018-12-25 17:01:00,18,否
     */
    @Test
    public void testCsvWriter() {
        try {
            FileWriter fileWriter = new FileWriter("test.csv");
            TestBean testBean = new TestBean();
            testBean.setName("aa");
            testBean.setSex("male");
            testBean.setAge(18);
            testBean.setDate(new Date());
            testBean.setEnable(false);
            List<TestBean> list = new ArrayList<TestBean>();
            list.add(testBean);

            BeanWriter csvWriter = BeanWriterFactory.createCsvWriter(CsvPreference.STANDARD_PREFERENCE, fileWriter);
            csvWriter.write(list, TestBean.class);
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
