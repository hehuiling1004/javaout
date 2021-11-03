package com.test.day02;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelImportEntity;
import com.alibaba.fastjson.JSONObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class DataDrivenTest {
    @Test(dataProvider = "getRegisterDatas")
    public void register(CasePojo casePojo){
        //json格式转化为map,jsonobjcet
        String resqustHeaders =casePojo.getRequestHeader();
        Map requstHeadMap = JSONObject.parseObject(resqustHeaders);
        //获取接口请求地址
        String url = casePojo.getUrl();
        //获取请求的传参
        String params = casePojo.getInputParams();
        System.out.println(casePojo.getTitle());
       Response response =  RestAssured.
                given().headers(requstHeadMap).
                    body(params).
                when().
                    post("http://api.lemonban.com/futureloan"+url).
                then().
                    log().all().
                    extract().response();
       //断言，excel的期望返回结果和实际返回结果做对比，gpath格式
        //取出期望结果
        String expect = casePojo.getExpected();
        //转化为map格式，通过jsonobject
        Map<String,Object> expectMap = JSONObject.parseObject(expect);
        Set<String> keySet = expectMap.keySet();
        //通过遍历取出每个key的value
        for (String key : keySet) {
            //获取实际的相应结果
           Object acctualResult = response.jsonPath().get(key);
           //获取期望的相应结果
           Object expectResult = expectMap.get(key);
            Assert.assertEquals(acctualResult,expectResult);

        }
    }
    @DataProvider
    public Object[] getRegisterDatas(){
        File file = new File("src\\test\\resources\\api_testcases_futureloan_v1.xls");
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(0);
        List<CasePojo> listdata = ExcelImportUtil.importExcel(file,CasePojo.class,importParams);
        //把集合转化成数字toarray()
        Object [] datas = listdata.toArray();
        return  datas;
    }
    public static void main(String[] args) {
        File file = new File("src\\test\\resources\\api_testcases_futureloan_v1.xls");
        //读取导入excel的一些参数配置
        ImportParams importParams = new ImportParams();
        //设置读取第几个sheet
        importParams.setStartSheetIndex(0);
        //设置读取开始行
        importParams.setStartRows(0);
        //设置读取多少行
        importParams.setReadRows(1);
        //读取excel里面的参数
        List<CasePojo>  listDatas = ExcelImportUtil.importExcel(file,CasePojo.class,importParams);
        System.out.println(listDatas);
        //获取到数据的属性get(0)第几行，获取哪个属性
        listDatas.get(0).getCaseId();

    }
}
