package com.ff.chartclient.JframeD;

import com.ff.chartclient.Util.DateUtil;
import jdk.nashorn.internal.scripts.JO;

import javax.rmi.CORBA.Util;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/*
客户端聊天窗口
 */
public class ChartJframe extends JFrame {
    Socket socket;
    DataOutputStream out;
    DataInputStream in;
    String acount;
    JTextArea jta;
    boolean clientFlag = true;

    public ChartJframe(Socket socket, String acount) throws HeadlessException, IOException {
        this.socket = socket;
        this.acount = acount;
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
    }

    public void setFrame() {
        setSize(500, 520);//设置窗口大小
        setTitle(acount+"聊天室");//设置窗口标题
        setLocationRelativeTo(null);//设置位置,窗口水平垂直居中
        setResizable(false);//窗口大小不能改变
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//关闭窗口,退出程序

        JPanel jp = new JPanel();

        //多行文本域
        jta = new JTextArea( 26, 44);
        jta.setEditable(false);//设置文本域不可编辑
        jta.setLineWrap(true);//强制换行
        JScrollPane jScrollPane = new JScrollPane(jta);
        jp.add(jScrollPane);

        //文本框和发送按钮
        JPanel bottomPanel = new JPanel();
        JTextField jt = new JTextField(36);
        JButton jb = new JButton("发送");
        bottomPanel.add(jt);
        bottomPanel.add(jb);

        jp.add(bottomPanel, BorderLayout.SOUTH);
        this.add(jp);

        //添加对话框关闭按钮监听器
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //JOptionPane.showConfirmDialog,确定为0
                int res = JOptionPane.showConfirmDialog(null,"您确定要关闭客户端吗");
                if (res == 0){
                    try {
                        socket.close();//关闭socket
                        System.exit(0);//停止程序
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                clientFlag = false;
            }
        });

        //添加发送按钮的监听器,是否为空
        jb.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = jt.getText();
                if (msg.length() == 0) {
                    JOptionPane.showMessageDialog(null, "消息不能为空");
                    return;
                }
                try {
                    //用户名+显示时间+信息  发送给服务器端
                    out.writeUTF(acount+"\t"+ DateUtil.dateToString("yyyy-MM-dd HH:mm:ss")+"\n" +msg+"\n");
                    jt.setText("");
                } catch (IOException ioException) {
                    dispose();//关闭窗口
                    ioException.printStackTrace();
                }
            }
        });

        new ClientThread().start();//启动客户端线程
    }
    class ClientThread extends Thread{
        @Override
        public void run() {
            while(clientFlag){
                if (!socket.isClosed()){//判断socket是否关闭
                    try {
                        //接收服务器端发来的消息
                        String msg = in.readUTF();
                        jta.append(msg+"\n");
                    } catch (IOException e) {
                        clientFlag = false;
                        JOptionPane.showMessageDialog(null,"服务器连接已断开");
                        dispose();
                    }
                }
            }
        }
    }
}