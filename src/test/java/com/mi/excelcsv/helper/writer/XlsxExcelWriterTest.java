package com.mi.excelcsv.helper.writer;

import com.mi.excelcsv.helper.type.ExcelType;
import org.junit.Test;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 18-12-25 下午4:48
 */
public class XlsxExcelWriterTest {

    /**
     *  excel文件内容如下，
     *  姓名 | 出生日期              | age | 是否禁用
     *  aa  |  2018-12-25 16:58:13 | 18  | 是
     */
    @Test
    public void testExcelWriter() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("test.xlsx");
            List<TestBean> list = new ArrayList<TestBean>();

            TestBean testBean = new TestBean();
            testBean.setName("aa");
            testBean.setSex("male");
            testBean.setAge(18);
            testBean.setDate(new Date());
            testBean.setEnable(true);
            list.add(testBean);

            testBean = new TestBean();
            testBean.setName("bb");
            testBean.setSex("male");
            testBean.setDate(new Date());
            testBean.setEnable(false);
            list.add(testBean);

            testBean = new TestBean();
            testBean.setName("cc");
            testBean.setSex("male");
            testBean.setAge(20);
            testBean.setDate(new Date());
            testBean.setEnable(true);
            list.add(testBean);

            ExcelBeanWriter excelWriter = BeanWriterFactory.createExcelWriter(ExcelType.XLSX_SUFFIX, fileOutputStream);
            excelWriter.write(list, TestBean.class);
            excelWriter.write(list, TestBean.class);
            excelWriter.flush();
            excelWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
