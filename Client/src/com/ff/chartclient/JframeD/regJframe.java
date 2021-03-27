package com.ff.chartclient.JframeD;

import com.ff.chartclient.dao.RegDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;

public class regJframe extends JFrame {

    public void setFrame() {
        setSize(450, 450);//设置窗口大小
        setTitle("注册界面");//设置窗口标题
        setLocationRelativeTo(null);//设置位置,窗口水平垂直居中
        setResizable(false);//窗口大小不能改变
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗口,退出程序

        //创建面板
        JPanel jp = new JPanel(new GridLayout(4, 1));

        //第一行创建流式面板,欢迎行
        JPanel jp1 = new JPanel();
        JLabel jb13 = new JLabel("欢迎注册");
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


        JPanel btnPanel = new JPanel();
        JButton saveBtn = new JButton("保存");
        JButton cancelBtn = new JButton("取消");
        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);

        jp.add(jp1);//面板中添加欢迎栏
        jp.add(accJp);//面板中添加账户栏
        jp.add(pasJp);//面板中添加密码栏
        jp.add(btnPanel);//面板中添加按钮
        this.add(jp);

        //为注册按钮添加监听事件
        saveBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                RegDao regDao = new RegDao();
                regDao.regDao(acount,pass);
            }
        });

        //为取消按钮添加事件监听
        cancelBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
