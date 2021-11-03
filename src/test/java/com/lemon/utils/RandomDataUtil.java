package com.lemon.utils;

import jdk.nashorn.internal.objects.annotations.Where;
import org.testng.annotations.Test;

import java.util.Random;

/**
 * @program: javaApi
 * @description: 随机生成数据
 * @author: hhl
 * @create: 2021-08-06 16:30
 **/
public class RandomDataUtil {

    public static String randomPhone(){
        //1、先查询数据库里面手机号最大的那一个，在其基础加1
        //2、先生成一个随机号码，在查询数据库，如果数据库中存在，再随机生成，如果不存在满足要求
        String phonePreFix = "133";
        Random random = new Random();

        //随机生成一个0-9内的整数
        for (int i=0;i<8;i++){
            int num = random.nextInt(9);
            phonePreFix = phonePreFix+num;
        }
        System.out.println(phonePreFix);
        return phonePreFix;
}
    public static String getUnregisterPhone(){
        String phone = "";
        while (true){
            phone = randomPhone();
            String sql = "select count(*) from member where moblie_phone='"+phone+"'";
            Long result =(Long) JdbcUtils.queryone(sql);
                //=1继续执行whlie循环
                if (1==result){
                    //说明存在,继续执行
                    continue;
                }else {
                    break;
                    }
                }
        return phone;
    }


    public static void main(String[] args) {
        System.out.println(getUnregisterPhone());
       /* String phonePreFix = "133";
        Random random = new Random();

        //随机生成一个0-9内的整数
        for (int i=0;i<8;i++){
            int num = random.nextInt(9);
            phonePreFix = phonePreFix+num;
        }
        System.out.println("生成的手机号"+phonePreFix);*/
    }
}
