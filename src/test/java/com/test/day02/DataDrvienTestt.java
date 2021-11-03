package com.test.day02;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.swing.text.StyledEditorKit;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataDrvienTestt {
    @Test(dataProvider = "registData")
    public void register(CasePojo casePojo){
       String resuqstHeader = casePojo.getRequestHeader();
       //json转化成map
        Map requstHeadMap =JSONObject.parseObject(resuqstHeader);
        System.out.println(casePojo.getTitle());
        Response res = RestAssured.given().
                    headers(requstHeadMap).
                    body(casePojo.getInputParams()).
                when().
                    post("http://api.lemonban.com/futureloan"+casePojo.getUrl()).
                then().
                    log().all().
                    extract().response();
        //断言
        String expected = casePojo.getExpected();
        //转化成map
        Map<String,Object> expectedMap = JSONObject.parseObject(expected);
        //便利map
        Set<String> allKeySet = expectedMap.keySet();
        for (String key : allKeySet) {
            //key就是对应的gpagt表达式
            //获取实际相应结果
           Object actualResult =  res.jsonPath().get(key);
           //获取期望的相应结果
            Object expeclRsult = expectedMap.get(key);
            Assert.assertEquals(actualResult,expeclRsult);
        }

    }
    @DataProvider
    public Object [] registData(){
        //读取excel配置参数
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(0);
        File file = new File("src\\test\\resources\\api_testcases_futureloan_v1.xls");
        List<CasePojo> resgdats = ExcelImportUtil.importExcel(file,CasePojo.class,importParams);
        Object [] datas = resgdats.toArray();
        return  datas;
    }
    public static void main(String[] args) {
        //读取excel配置参数
        ImportParams importParams = new ImportParams();
        //取表格中的第几个sheet
        importParams.setStartSheetIndex(0);
        File file = new File("src\\test\\resources\\api_testcases_futureloan_v1.xls");
        //读取excel里面的数据
        List<CasePojo>  casePojoList = ExcelImportUtil.importExcel(file,CasePojo.class,importParams);
        //把集合转化成数字
       Object [] datas = casePojoList.toArray();
    }
}
