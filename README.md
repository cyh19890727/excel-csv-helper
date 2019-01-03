# Excel, Csv工具
在待导出的bean字段上配置标题、顺序、单元格类型、字段特殊输出，输出bean列表到excel或csv文件只需三行代码，用起来很方便，依赖poi和super-csv工具包。

## 版本功能
### 1.0-SNAPSHOT
1. 在待导出的bean上能配置导出格式，包含标题、单元格类型、字段特殊输出、列顺序配置
2. 能导出bean列表到excel，csv文件中，目前excel仅支持xlsx格式
3. 用poi的SXSSFWorkbook类导出excel，无需将大量待刷新到IO流的记录缓存到内存，避免大量缓存导致OOM，当待导出的记录较多时，可以采用分页的方式多次调用write方法


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

BeanWriter excelWriter = BeanWriterFactory.createExcelWriter(ExcelType.XLSX_SUFFIX, fileOutputStream);
excelWriter.write(list, TestBean.class);
excelWriter.close();

```

## 使用问题
大家在使用过程中遇到问题，欢迎反馈给我，个人邮箱是896169578@qq.com，我看到后会及时回复。