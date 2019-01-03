package com.mi.excelcsv.helper.processer;

import com.mi.excelcsv.helper.serializer.AbstractCellSerializer;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.util.CsvContext;

/**
 * @author 陈奕鸿
 * @Description
 * @Date 创建于 18-12-24 下午7:26
 */
public class CellSerializerProcesser extends CellProcessorAdaptor {

    private AbstractCellSerializer cellSerializer;
    private String[] args;

    public CellSerializerProcesser(AbstractCellSerializer cellSerializer, String[] args) {
        this.cellSerializer = cellSerializer;
        this.args = args;
    }

    public Object execute(final Object value, final CsvContext context) {
        Object result = cellSerializer.serialize(value, args);
        return next.execute(result, context);
    }
}
