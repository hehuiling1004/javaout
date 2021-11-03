package com.lemon.testcases;

import com.alibaba.fastjson.JSONObject;
import com.lemon.pojo.CasePojo;
import com.lemon.utils.ExcelUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataDrivernTestTest {
   @Test(dataProvider = "registerData")
   public void registerTest(CasePojo casePojo){
      String headers = casePojo.getRequestHeader();
      //转化成json格式，用map接受
      Map headersMap = JSONObject.parseObject(headers);
      String params = casePojo.getInputParams();
      String url = casePojo.getUrl();
      Response response = RestAssured.given().headers(headersMap).body(params).when().post("http://api.lemonban.com/futureloan"+url).
              then().log().all().extract().response();
      String expect = casePojo.getExpected();
      Map<String,Object> expectMap = JSONObject.parseObject(expect);
      Set<String> keySet = expectMap.keySet();
      for (String key : keySet) {
         Object expectValue = expectMap.get(key);
         Object actuallyValue = response.jsonPath().get(key);
         Assert.assertEquals(actuallyValue,expectValue);
      }

   }
   @DataProvider
   public Object [] registerData(){
     List<CasePojo> listDatas  = ExcelUtils.readExcel(0);
      return listDatas.toArray();
   }



}
