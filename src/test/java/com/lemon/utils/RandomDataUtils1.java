package com.lemon.utils;

import com.alibaba.fastjson.JSONObject;
import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanProperty;

import javax.swing.text.StyledEditorKit;
import java.util.Random;

/**
 * @program: javaApi
 * @description:
 * @author: hhl
 * @create: 2021-08-10 16:09
 **/
public class RandomDataUtils1 {
    public static String randomPone(){
     //思路，随机生成一个手机号码，查询数据库，存在则继续生成。不存在则满足要求
        String prePhone = "133";
        //实例化
        Random random = new Random();
        for (int i=0;i<8;i++){
            //随机生成0-9范围内的整数
            int num = random.nextInt(9);
            prePhone = prePhone+num;
        }
        return prePhone;
    }
    public static Object getUnregisterPhone(){
        String phone = null;
        while (true) {
           phone = randomPone();
            String sql = "select count(*) from member where moblie_phone='" + phone + "'";
            Long result = ( Long)JdbcUtils.queryone(sql);
            if (1 == result) {
                continue;
            }else {
                break;
            }
        }
        return phone;

    }


}
