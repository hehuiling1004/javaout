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

public class LoginTest1 extends BaseTest {
    @BeforeClass
    public void beforeclass(){
        //读取第一条数据
        List<CasePojo> datas = ExcelUtils.readExcelSpecifyDatas(2,1,1);
        Response res = request(datas.get(0));
        extervtoev(res,datas.get(0));
        //String moblie = res.jsonPath().get("data.mobile_phone");
       // Environment.envMap.put("moblie_phone",moblie);

    }
    /**
     * 通过提取表达式，将对应的值设置到环境变量中
     *
     */
    public void extervtoev(Response res,CasePojo casePojo){
        String extravt = casePojo.getExtractExper();
        Map<String,Object>  extravtmap = JSONObject.parseObject(extravt);
        Set<String> keyset = extravtmap.keySet();
        for (String key : keyset) {
            Object value = extravtmap.get(key);
            Object actuallvalue = res.jsonPath().get((String) value);
            Environment.envMap.put(key,actuallvalue);
        }
    }
    @Test(dataProvider = "logiddatas")
    public void logintest(CasePojo casePojo){
        //将接口内的参数替换为具体数据
        Response res = request(casePojo);
    }
    public  void replact(String orgstr){
        if (orgstr != null){
            String rege = "\\{\\{(.*?)\\}\\}";
            Pattern pattern = Pattern.compile(rege);
            Matcher matcher = pattern.matcher(orgstr);
            String result = null;
            while (matcher.find()){
                String allfim = matcher.group(0);
                String innerstr = matcher.group(1);
                Object fe = Environment.envMap.get(innerstr);
            }
        }
    };
    @DataProvider
    public Object [] logiddatas(){
        List<CasePojo> datalist = ExcelUtils.readExcelSpecifyDatas(2,2);
        return datalist.toArray();
    }

}
