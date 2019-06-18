package com.mi.excelcsv.helper.reader;

import com.mi.excelcsv.helper.reader.impl.CsvBeanReaderImpl;
import org.junit.Test;

import java.io.FileReader;
import java.util.List;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-24 下午4:11
 */
public class CsvBeanReaderTest {

    @Test
    public void testCsvReader() {
        try {
            TestPostValidator postValidator = new TestPostValidator();
            FileReader fileReader = new FileReader("test.csv");
            CsvBeanReaderImpl csvBeanReader = new CsvBeanReaderImpl(fileReader);
            csvBeanReader.setPostValidator(postValidator);
            List<TestBean> list = csvBeanReader.read(TestBean.class);
            list.forEach((i) -> System.out.println(i));
            csvBeanReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
