# Excel, Csv工具
在待导出的bean字段上配置标题、顺序、单元格类型、字段特殊输出，输出bean列表excel或csv文件只需三行代码，用起来很方便，依赖poi和super-csv工具包。

## 版本功能
### 1.0.1-SNAPSHOT
1. 在待导出的bean上能配置导出格式，包含标题、单元格类型、字段特殊输出、列顺序配置
2. 能导出bean列表到excel，csv文件中，目前excel仅支持xlsx格式

### 1.0.2-SNAPSHOT
1. 支持excel、csv动态列导出功能，用法参考ExcelDynColTest、CsvDynColTest测试类

### 1.0.3-SNAPSHOT
1. 支持将读入的excel、csv的行转换为bean列表，用法参考ExcelBeanReaderTest、CsvBeanReaderTest测试类

## 样例代码
```
public class TestBean {

    @ColumnFormat(title = "姓名", order = 1)
    private String name;

    private String sex;

    @ColumnFormat(dateType = CellDataType.NUMERIC)
    private int age;

    @ColumnFormat(title = "出生日期", order = 2, serializer = DateSerializer.class, args = {"yyyy-MM-dd HH:mm:ss"})
    private Date date;

    @ColumnFormat(title = "是否禁用", serializer = BooleanSerializer.class, args = {"是", "否"})
    private Boolean enable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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
}


FileOutputStream fileOutputStream = new FileOutputStream("test.xlsx");
TestBean testBean = new TestBean();
testBean.setName("aa");
testBean.setSex("male");
testBean.setAge(18);
testBean.setDate(new Date());
testBean.setEnable(true);
List<TestBean> list = new ArrayList<TestBean>();
list.add(testBean);

ExcelBeanWriter excelWriter = BeanWriterFactory.createExcelWriter(ExcelType.XLSX_SUFFIX, fileOutputStream);
excelWriter.write(list, TestBean.class);
excelWriter.flush();

excelWriter.close();

```