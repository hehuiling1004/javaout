package com.lemon.testcases;

import com.lemon.common.BaseTest;
import com.lemon.pojo.CasePojo;
import com.lemon.utils.ExcelUtils;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class LoginCase extends BaseTest {

    @BeforeTest
    public void beforeClass(){
        //需要读取第一条数据
         List<CasePojo> datas = ExcelUtils.readExcelSpecifyDatas(1,1,1);
        //发起接口请求，注册一个用户
         Response res = request(datas.get(0));
         extractToEnvironment(res,datas.get(0));
         //String mobilephone = res.jsonPath().get("data.mobile_phone");
         //将手机号码保存到环境遍历中去
        // Environment.envMap.put("mobile_phone",mobilephone) ;
    }

    @Test(dataProvider = "loginDatas")
    public void loginCase(CasePojo casePojo){
        casePojo = paramsReplace(casePojo);
       // String result = regexReplace(casePojo.getExtractExper());
       // casePojo.setExtractExper(result);
        //发送接口请求
        Response res = request(casePojo);
//断言
        assertResponse(casePojo, res);
    }
    @DataProvider
    public Object [] loginDatas(){
        List<CasePojo> listDatas = ExcelUtils.readExcelSpecifyDatas(1,2);
        return listDatas.toArray();
    }
}
