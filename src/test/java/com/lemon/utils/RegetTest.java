package com.lemon.utils;

import com.lemon.data.Environment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegetTest {
    public static void main(String[] args) {
        String str ="{" +
                "  \"mobile_phone\": \"{{mobile_phone}}\"," +
                "  \"pwd\": \"{{pwd}}\"" +
                "}";
        String regxExpr = "\\{\\{(.*?)\\}\\}";
        //匹配器
        Pattern pattern = Pattern.compile("\\{\\{(.*?)\\}\\}");
        //匹配对象
        Matcher matcher = pattern.matcher(str);
        String result = str;

        while (matcher.find()) {
            String allFinfStr = matcher.group(0);
            System.out.println("找的的完整字符串" + allFinfStr);
            String innerFindStr = matcher.group(1);
            System.out.println("找的内部字符串" + innerFindStr);
            Environment.envMap.put("mobile_phone", "15900623122");
            Environment.envMap.put("pwd", "123456");
            //找到具体的要替换的值，从环境变量中去找到
            Object replaceValue = Environment.envMap.get(innerFindStr);
            //要替换{moblielphone}为1455444
            //第二次替换要基于第一次替换的结果
            result = result.replace(allFinfStr, replaceValue + "");
            System.out.println(result);
        }

    }



}
