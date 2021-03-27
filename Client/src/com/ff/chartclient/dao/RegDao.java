package com.ff.chartclient.dao;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegDao {

    public void regDao(String acount , String pass){
        String url = "jdbc:mysql://127.0.0.1:3306/school_db?characterEncoding=utf8&serverTimezone=UTC";
        //与数据库键连接
        try {
            Connection connection = DriverManager.getConnection(url, "root", "wyf523");
            String sql = "insert into uers(账户,密码)" + "values(?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, acount);
            ps.setString(2, pass);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "注册成功");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
