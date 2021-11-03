package com.test.day01;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class Recharge {
    @Test
    public  void recharge(){
        //先注册
        Object registerData = "{\"mobile_phone\":\"13323231302\",\"pwd\":\"lemon123456\",\"type\":1}";
        Response res = RestAssured.
                given().
                    header("X-Lemonban-Media-Type","lemonban.v2").
                    header("Content-Type","application/json").
                    body(registerData).
                when().
                    post("http://api.lemonban.com/futureloan/member/register").
                then().
                    log().all().
                    extract().response();
        //获取注册的手机号
        Object moblilephone = res.jsonPath().get("data.mobile_phone");
        //作为参数传递到登录接口
        Object loginData  = "{\"mobile_phone\":\""+moblilephone+"\",\"pwd\":\"lemon123456\"}";
        Response res1 = RestAssured.
                given().
                header("X-Lemonban-Media-Type","lemonban.v2").
                header("Content-Type","application/json").
                body(loginData).
                when().
                post("http://api.lemonban.com/futureloan/member/login").
                then().
                log().all().
                extract().response();
        //登录后获取token
        Object token = res1.jsonPath().get("data.token_info.token");
        Object userId = res1.jsonPath().get("data.id");
        //调用充值接口
        String jsonData2="{\"member_id\":\""+userId+"\",\"amount\":10000.0}";
        RestAssured.
                given().
                    header("X-Lemonban-Media-Type","lemonban.v2").
                    header("Content-Type","application/json").
                    header("Authorization","Bearer "+token).
                    body(jsonData2).
                when().
                    post("http://api.lemonban.com/futureloan/member/recharge").
                then().
                    log().all().extract().response();

    }
}
