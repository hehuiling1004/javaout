package com.lemon.testcases;
import com.alibaba.fastjson.JSONObject;
import com.lemon.common.BaseTest;
import com.lemon.data.Environment;
import com.lemon.pojo.CasePojo;
import com.lemon.utils.ExcelUtils;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: javaApi
 * @description: login
 * @author: hhl
 * @create: 2021-08-05 17:17
 **/
public class LoginTest2 extends BaseTest {
    /**
     * 前置条件，先注册一个账号，在拿到注册的账号去登录
     * @return
     */
    @BeforeClass
    public void beforeClass(){
        //读取前置数据
        List<CasePojo> datas = ExcelUtils.readExcelSpecifyDatas(2,1,1);
        //发起接口请求，注册一个数据
        /**
         * Response res = request(datas.get(0));
         String moblie_phone = res.jsonPath().get("data.mobile_phone");
         //存储到环境变量中
         Environment.envMap.put("moblie_phone",moblie_phone);
         */
        Response res = request(datas.get(0));
        extractToEnvironment(datas.get(0),res);

    }
    @Test(dataProvider = "loginDatas")
    public void loginCase(CasePojo casePojo){
        //发起接口请求前，先把变量替换为具体的值
        casePojo = paramsReplace(casePojo);
       Response res =  request(casePojo);

    }

    public static void extractToEnvironment(CasePojo casePojo,Response res){
       String extractStr = casePojo.getExtractExper();
       Map<String,Object>  map = JSONObject.parseObject(extractStr);
       Set<String> keySet = map.keySet();
        for (String key : keySet) {
            //key为变量名，提前的是gpath表达式
            Object expectValue = map.get(key);
            Object value = res.jsonPath().get((String)expectValue);
            //将相对应的键和值保存的环境变量中
            Environment.envMap.put(key,value);
        }
    }
   @DataProvider
    public Object[] loginDatas(){
       List<CasePojo> datas = ExcelUtils.readExcelSpecifyDatas(2,2);
       return datas.toArray();
   }
}
