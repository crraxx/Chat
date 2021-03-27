package com.ff.chartclient.JframeD;

import com.ff.chartclient.dao.LogDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

/*
客户端登录窗口
 */
public class LoginJframe extends JFrame {

    public void setFrame() {
        setSize(450, 450);//设置窗口大小
        setTitle("登录界面");//设置窗口标题
        setLocationRelativeTo(null);//设置位置,窗口水平垂直居中
        setResizable(false);//窗口大小不能改变
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗口,退出程序

        //创建面板
        JPanel jp = new JPanel(new GridLayout(4, 1));

        //第一行创建流式面板,欢迎行
        JPanel jp1 = new JPanel();
        JLabel jb13 = new JLabel("欢迎登录");
        jb13.setFont(new Font("楷体", Font.CENTER_BASELINE, 35));
        jp1.add(jb13);

        //账户行
        JPanel accJp = new JPanel();
        JLabel accLable = new JLabel("账户");
        accLable.setFont(new Font("楷体", FlowLayout.CENTER, 20));
        //账户框
        JTextField accText = new JTextField(15);
        accJp.add(accLable);
        accJp.add(accText);

        //密码行
        JPanel pasJp = new JPanel();
        JLabel jl1 = new JLabel("密码");
        jl1.setFont(new Font("楷体", FlowLayout.CENTER, 20));
        //密码框
        JPasswordField jPasswordField = new JPasswordField("", 15);
        pasJp.add(jl1);
        pasJp.add(jPasswordField);

        //登录注册按钮
        JPanel btnPanel = new JPanel();
        JButton loginBtn = new JButton("登录");
        JButton regBtn = new JButton("注册");
        btnPanel.add(loginBtn);
        btnPanel.add(regBtn);

        jp.add(jp1);//面板中添加欢迎栏
        jp.add(accJp);//面板中添加账户栏
        jp.add(pasJp);//面板中添加密码栏
        jp.add(btnPanel);//面板中添加按钮
        this.add(jp);

        //为注册按钮添加监听事件
        regBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();//关闭登录窗口
                //调用聊天窗口
                regJframe rf = new regJframe();
                rf.setFrame();
                rf.setVisible(true);//设置界面可见
            }
        });

        //为登陆按钮添加事件监听
        loginBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取密码和账户信息,出现空时候触发提示
                String acount = accText.getText();
                String pass = String.valueOf(jPasswordField.getPassword());
                if (acount.length() == 0) {
                    JOptionPane.showMessageDialog(null, "账号不能为空");
                    return;
                }
                if (pass.length() == 0) {
                    JOptionPane.showMessageDialog(null, "密码不能为空");
                    return;
                }
                LogDao logDao = new LogDao();
                try {
                    boolean res = logDao.checkDao(acount, pass);
                    if (res) {
                        //客户端连接
                        try {
                            Socket socket = new Socket("127.0.0.1", 9999);
                            dispose();//关闭登录窗口
                            //调用聊天窗口
                            ChartJframe jf = new ChartJframe(socket, acount);
                            jf.setFrame();
                            jf.setVisible(true);//设置界面可见
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                            JOptionPane.showMessageDialog(null, "连接服务器失败");//提示未连接
                            System.exit(0);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "账户或密码错误");
                        return;
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(null, "数据库异常");
                }

            }
        });
    }
}
