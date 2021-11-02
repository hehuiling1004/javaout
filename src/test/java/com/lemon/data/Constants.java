package com.lemon.data;

public class Constants {
    //日志输出配置：控制台(false) or 日志文件中(true)
    public static final boolean LOG_TO_FILE=true;
    //Excel文件的路径
    public static final String EXCEL_FILE_PATH=System.getProperty("user.dir")+"/src/test/resources/api_testcases_futureloan_v4.xls";
    //接口BaseUrl地址
    public static final String BASE_URI="http://8.129.52.205:8012/futureloan";
    //数据库baseuri
    public static final String DB_BASE_URI="8.129.52.205:33060";
    //数据库名
    public static final String DB_NAME="futureloan";
    public static final String DB_USERNAME="root";
    public static final String DB_PWD="Lemon123456!";


}
