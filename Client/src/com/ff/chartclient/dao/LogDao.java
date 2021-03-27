package com.ff.chartclient.dao;

import com.ff.chartclient.JframeD.ChartJframe;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;

public class LogDao {
    public boolean checkDao(String acount, String pass) throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/school_db?characterEncoding=utf8&serverTimezone=UTC";
        Connection connection = null;
        PreparedStatement ps = null ;
        try {
            //与数据库键连接
            connection = DriverManager.getConnection(url, "root", "wyf523");
            String sql = "select 账户,密码 from uers where 账户 = ? and 密码= ? ";
            ps = connection.prepareStatement(sql);
            ps.setString(1, acount);
            ps.setString(2, pass);
            //executeQuery()用于执行查询语句，将查询到的结果封装到ResultSet对象中
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } finally {
            if(connection != null){
                connection.close();
            }
            if (ps != null){
                ps.close();
            }
        }
        return false;
    }
}
