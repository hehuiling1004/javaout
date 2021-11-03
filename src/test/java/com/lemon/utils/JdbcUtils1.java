package com.lemon.utils;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @program: javaApi
 * @description:
 * @author: hhl
 * @create: 2021-09-09 10:48
 **/
public class JdbcUtils1 {
    public static Connection getConnection(){
        String url="jdbc:mysql://api.lemonban.com/futureloan?useUnicode=true&characterEncoding=utf-8";
        String user="future";
        String password="123456";
        Connection connection = null;
        try {
            DriverManager.getConnection(url,user,password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
    public void updateSql(String sql){
        Connection connection = getConnection();
        QueryRunner queryRunner = new QueryRunner();
        try {
            queryRunner.update(connection,sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public static void queryOne() throws SQLException {
        Connection connection = getConnection();
        QueryRunner queryRunner = new QueryRunner();
        queryRunner.query(connection, String.valueOf(queryRunner),new MapListHandler());

    }
}
