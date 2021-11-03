package com.lemon.utils;

import com.lemon.data.Environment;
import jdk.nashorn.internal.ir.WhileNode;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionNewtonForm;
import org.apache.xmlbeans.impl.jam.visitor.CompositeMVisitor;
import org.testng.reporters.jq.Main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: javaApi
 * @description: 正则
 * @author: hhl
 * @create: 2021-08-05 16:22
 **/
public class RegetTest1 {
    public static void main(String[] args) {
        //原始数据
        String str ="{" +
                "  \"mobile_phone\": \"{{mobile_phone}}\"," +
                "  \"pwd\": \"{{pwd}}\"" +
                "}";

        //正则表达式
        String regxExpr = "\\{\\{(.*?)\\}\\}";
        Pattern pattern = Pattern.compile(regxExpr);
        Matcher matcher = pattern.matcher(str);
        String result = str;
        while (matcher.find()){
            String allFindStr = matcher.group(0);
            String innerStr = matcher.group(1);
            Environment.envMap.put("mobile_phone","13323234545");
            Environment.envMap.put("pwd","123456");
            //要替换的值
            Object replaceValue = Environment.envMap.get(innerStr);
            result = result.replace(allFindStr,replaceValue+"");
        }
        }
    }

