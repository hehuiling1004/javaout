package com.test.day01;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class Register {
    @Test(enabled = false)
    public void registerLoin(){
        String jsonData = "{\"mobile_phone\":\"13323231345\",\"pwd\":\"lemon123456\",\"type\":1}";
        Response response =
                RestAssured.
                        given().
                            header("X-Lemonban-Media-Type","lemonban.v1").
                            header("Content-Type","application/json").
                            body(jsonData).
                        when().
                            post("http://api.lemonban.com/futureloan/member/register").
                        then().
                            log().all().
                            extract().response();
        //从注册相应结果里面获取注册的手机号
      Object mobilephone = response.jsonPath().get("data.mobile_phone");
      //发起登录接口请求
        System.out.println(mobilephone);
        String jsonData2 = "{\"mobile_phone\":\""+mobilephone+"\",\"pwd\":\"lemon123456\"}";
        RestAssured.given().
                header("X-Lemonban-Media-Type","lemonban.v1").
                header("Content-Type","application/json").
                body(jsonData2).
                when().
                post("http://api.lemonban.com/futureloan/member/login").
                then().
                log().all().
                extract().response();
    }
    @Test
    public void autorTest(){
        String jsonData1 = "{\"mobile_phone\":\"13323231329\",\"pwd\":\"lemon123456\"}";
        Response res = RestAssured.given().
                header("X-Lemonban-Media-Type","lemonban.v2").
                header("Content-Type","application/json").
                body(jsonData1).
                when().
                post("http://api.lemonban.com/futureloan/member/login").
                then().
                log().all().
                extract().response();
        //获取token值
        Object token = res.jsonPath().get("data.token_info.token");
        //获取用户id
        Object userId = res.jsonPath().get("data.id");
        System.out.println(token);
        System.out.println(userId);
        String jsonData2="{\"member_id\":\""+userId+"\",\"amount\":10000.0}";
        Response res2 = RestAssured.given().
                    header("X-Lemonban-Media-Type","lemonban.v2").
                    header("Content-Type","application/json").
                    contentType("application/json").
                    header("Authorization","Bearer "+token).
                    body(jsonData2).
                when().
                    post("http://api.lemonban.com/futureloan/member/recharge").
                then().
                    log().all().
                    extract().response();


    }

}
