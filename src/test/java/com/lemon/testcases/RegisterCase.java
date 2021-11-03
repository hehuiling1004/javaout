package com.lemon.testcases;

import com.alibaba.fastjson.JSONObject;
import com.lemon.common.BaseTest;
import com.lemon.pojo.CasePojo;
import com.lemon.utils.ExcelUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RegisterCase extends BaseTest {
    @BeforeClass

    @Test(dataProvider = "registerDatas")
    public void loginTest(CasePojo casePojo){
        Response response = request(casePojo);
        assertResponse(casePojo,response);
    }

    @DataProvider
    public Object [] registerDatas(){
        List<CasePojo> listDatas = ExcelUtils.readExcelSeetAllDatas(0);
        return listDatas.toArray();
    }


}
