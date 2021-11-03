package com.test.day02;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONObject;
import io.restassured.RestAssured;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

public class DataDriven {
    @Test(dataProvider = "registerData")
    public void register(CasePojo casePojo){
        //json格式转化为map
       String headers = casePojo.getRequestHeader();
        Map resquestMap = JSONObject.parseObject(headers);
        String params = casePojo.getInputParams();
        String url = casePojo.getUrl();
        RestAssured.given().headers(resquestMap).body(params).
                when().
                post("http://api.lemonban.com/futureloan/member/register"+url).
                then().log().all().extract().response();

    }
    @DataProvider
    public Object[] registerData(){
        //读取excel配置的参数
        ImportParams importParams = new ImportParams();
        //读取第几个sheet
        importParams.setStartSheetIndex(0);
        File file = new File("src\\test\\resources\\api_testcases_futureloan_v1.xls");
        //读取excel里面的参数
        List<CasePojo> casePojoList = ExcelImportUtil.importExcel(file,CasePojo.class,importParams);
        //把集合转化成数组
        Object [] datas = casePojoList.toArray();
        return datas;
    }
}
