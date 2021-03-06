package com.mi.excelcsv.helper.writer;

import com.mi.excelcsv.helper.dynamiccolumn.ColumnConfig;
import com.mi.excelcsv.helper.serializer.impl.BooleanSerializer;
import com.mi.excelcsv.helper.type.CellDataType;
import com.mi.excelcsv.helper.writer.impl.CsvDynColWriterImpl;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 19-3-4 下午3:36
 */
public class CsvDynColTest {

    @Test
    public void testDynamicColumn() throws IOException {

        List<ColumnConfig> columnConfigs = new ArrayList<ColumnConfig>();
        columnConfigs.add(new ColumnConfig("测试标题"));
        for (int i = 0; i < 3; i++) {
            columnConfigs.add(new ColumnConfig("动态列" + (i+1)));
        }
        ColumnConfig columnConfig = new ColumnConfig("动态列4");
        columnConfig.setCellDataType(CellDataType.NUMERIC);
        columnConfigs.add(columnConfig);

        columnConfigs.get(2).setCellSerializer(new BooleanSerializer());
        columnConfigs.get(2).setSerializerArgs(new String[]{"yes", "no"});

        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        Map<String, Object> line = new HashMap<String, Object>();
        line.put("测试标题", "测试1");
        line.put("动态列1", "a");
        line.put("动态列2", true);
        line.put("动态列3", new BigDecimal("1.12"));
        line.put("动态列4", new BigDecimal("2.1"));
        datas.add(line);

        line = new HashMap<String, Object>();
        line.put("测试标题", "测试2");
        line.put("动态列1", "b");
        line.put("动态列2", false);
        line.put("动态列3", new Integer("2"));
        line.put("动态列4", new BigDecimal("2.2"));
        datas.add(line);

        line = new HashMap<String, Object>();
        line.put("测试标题", "测试2");
        line.put("动态列1", "c");
        line.put("动态列3", new BigDecimal("3"));
        line.put("动态列4", new BigDecimal("2.3"));
        datas.add(line);

        FileWriter fileWriter = new FileWriter("test_csv_dynamic_column.csv");
        try (CsvDynColWriter columnWriter = new CsvDynColWriterImpl(fileWriter);) {
            columnWriter.writeTitle(columnConfigs);
            columnWriter.write(datas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
