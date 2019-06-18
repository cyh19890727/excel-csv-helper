package com.mi.excelcsv.helper.csv;

import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ITokenizer;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.Reader;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-5-24 下午4:03
 */
public class ImproveBeanReader extends CsvBeanReader {

    public ImproveBeanReader(Reader reader, CsvPreference preferences) {
        super(reader, preferences);
    }

    public ImproveBeanReader(ITokenizer tokenizer, CsvPreference preferences) {
        super(tokenizer, preferences);
    }

    public boolean readNextRow() throws IOException {
        return this.readRow();
    }
}
