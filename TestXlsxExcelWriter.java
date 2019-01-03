package com.mi.excelcsv.helper.writer;

import com.mi.excelcsv.helper.constant.ExcelType;
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
public class TestXlsxExcelWriter {

    /**
     *  excel文件内容如下，
     *  姓名 | 出生日期              | age | 是否禁用
     *  aa  |  2018-12-25 16:58:13 | 18  | 是
     */
    @Test
    public void testExcelWriter() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("test.xlsx");
            TestBean testBean = new TestBean();
            testBean.setName("aa");
            testBean.setSex("male");
            testBean.setAge(18);
            testBean.setDate(new Date());
            testBean.setEnable(true);
            List<TestBean> list = new ArrayList<TestBean>();
            list.add(testBean);

            BeanWriter excelWriter = BeanWriterFactory.createExcelWriter(ExcelType.XLSX_SUFFIX, fileOutputStream);
            excelWriter.write(list, TestBean.class);
            excelWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
