package com.lemon.common;

import com.alibaba.fastjson.JSONObject;
import com.lemon.data.Environment;
import com.lemon.pojo.CasePojo;
import com.lemon.utils.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;

public class BaseTest {
    @BeforeSuite
    public void beforeSuit(){
        //在测试类之前运行，只执行一次
        RestAssured.config =   RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL));
        RestAssured.baseURI = Constants.BASE_URI;
    }
    public Response request(CasePojo casePojo) {
        String headers = casePojo.getRequestHeader();
        Map headerMap = JSONObject.parseObject(headers);
        String params = casePojo.getInputParams();
        String url = casePojo.getUrl();
        //获取请求方式
        String method = casePojo.getMethod();
        Response res = null;
        //,对不同的请求方式进行封装
        if ("get".equalsIgnoreCase(method)){
            res = RestAssured.given().headers(headerMap).when().get(url).
                    then().log().all().extract().response();
        }else if ("post".equalsIgnoreCase(method)){
            res = RestAssured.given().headers(headerMap).body(params).log().all().when().post(url).
                    then().log().all().extract().response();
        }else if ("put".equalsIgnoreCase(method)){
            res = RestAssured.given().headers(headerMap).body(params).when().put(url).
                    then().log().all().extract().response();
        }else if ("patch".equalsIgnoreCase(method)){
            res = RestAssured.given().headers(headerMap).body(params).when().patch(url).
                    then().log().all().extract().response();
        }
        return res;
    }
    public void assertResponse(CasePojo casePojo, Response res) {
        String expected = casePojo.getExpected();
        Map<String,Object> expectedMap = JSONObject.parseObject(expected);
        Set<String> keySet = expectedMap.keySet();
        for (String key : keySet) {
            Object actually = res.jsonPath().get(key);

            Object expect = expectedMap.get(key);

            Assert.assertEquals(actually,expect);

        }
    }
    /**
     * 通过提取表达式，将对应的相应值，保存到环境变量中去
     * @param res 相应信息
     * @param casePojo 实体对象
     */
    public void extractToEnvironment(Response res,CasePojo casePojo){
        String extracter = casePojo.getExtractExper();
        if (extracter !=null) {
            Map<String, Object> extracterMap = JSONObject.parseObject(extracter);
            Set<String> keySet = extracterMap.keySet();
            for (String key : keySet) {
                Object value = extracterMap.get(key);
                Object actuallyValue = res.jsonPath().get((String) value);
                Environment.envMap.put(key, actuallyValue);
            }
        }
    }
    public  void extractToen(Response res,CasePojo casePojo){
        String exe = casePojo.getExtractExper();
        Map<String,Object> exeMap = JSONObject.parseObject(exe);
        Set<String> keySet = exeMap.keySet();
        for (String key : keySet) {
            Object value = exeMap.get(key);
            Object actuallValue = res.jsonPath().get((String)value);
            Environment.envMap.put(key,actuallValue);
        }
    }
    /**
     * 正则替换功能
     * @param
     */
    public static String regexReplace(String orgstr){
        if (orgstr != null) {
            //匹配器
            Pattern pattern = Pattern.compile("\\{\\{(.*?)\\}\\}");
            //匹配对象
            Matcher matcher = pattern.matcher(orgstr);
            String result = orgstr;
            while (matcher.find()) {
                String allFinfStr = matcher.group(0);
                String innerFindStr = matcher.group(1);
                //找到具体的要替换的值，从环境变量中去找到
                Object replaceValue = Environment.envMap.get(innerFindStr);
                //要替换{moblielphone}为1455444
                //第二次替换要基于第一次替换的结果
                result = result.replace(allFinfStr, replaceValue + "");
                System.out.println(result);
            }
            return result;
        }else {
            return orgstr;
        }
    }

    public String replces(String str){
        if (str !=null){
        //正则表达式
        String regxExpr = "\\{\\{(.*?)\\}\\}";
        Pattern pattern = Pattern.compile(regxExpr);
        Matcher matcher = pattern.matcher(str);
        String result = str;
        while (matcher.find()){
            String allFindStr = matcher.group(0);
            String innerStr = matcher.group(1);
            //要替换的值
            Object replaceValue = Environment.envMap.get(innerStr);
            result = result.replace(allFindStr,replaceValue+"");
        }
            return  result;}
        else {
            return  str;
        }

    }

    public CasePojo paramsReplace(CasePojo casePojo){
        //1、请求头
        String requestHeader = casePojo.getRequestHeader();
        casePojo.setRequestHeader(regexReplace(requestHeader));
        //2、接口地址
        String url = casePojo.getUrl();
        casePojo.setUrl(regexReplace(url));
        //3、参数输入
        String inputParams = casePojo.getInputParams();
        casePojo.setInputParams(regexReplace(inputParams));
        //4、期望结果
        String expected = casePojo.getExpected();
        casePojo.setExpected(regexReplace(expected));
        return casePojo;
    }


}
