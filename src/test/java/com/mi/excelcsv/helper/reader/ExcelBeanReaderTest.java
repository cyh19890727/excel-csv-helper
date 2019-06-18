package com.mi.excelcsv.helper.reader;

import com.mi.excelcsv.helper.reader.impl.ExcelBeanReaderImpl;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.List;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-14 下午12:50
 */
public class ExcelBeanReaderTest {

    @Test
    public void testExcelReader() {
        try {
            TestPostValidator postValidator = new TestPostValidator();
            FileInputStream fileInputStream = new FileInputStream("test.xlsx");
            ExcelBeanReaderImpl excelBeanReaderImpl = new ExcelBeanReaderImpl(fileInputStream);
            excelBeanReaderImpl.setPostValidator(postValidator);
            List<TestBean> list = excelBeanReaderImpl.read(TestBean.class);
            list.forEach((i) -> System.out.println(i));
            excelBeanReaderImpl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
