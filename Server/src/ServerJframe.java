import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ServerJframe extends JFrame {
    ServerSocket serverSocket;
    ArrayList<Socket> arrayList;
    JTextArea jta;
    boolean serverFlag = true;
    boolean clientFlag = true;

    public void setFrame() {
        setSize(510, 520);//设置窗口大小
        setTitle("聊天室");//设置窗口标题
        setLocationRelativeTo(null);//设置位置,窗口水平垂直居中
        setResizable(false);//窗口大小不能改变
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//关闭窗口,退出程序

        JPanel jp = new JPanel();
        //多行文本域
        jta = new JTextArea(26, 45);
        jta.setEditable(false);//设置文本域不可编辑
        jta.setLineWrap(true);//强制换行
        JScrollPane jScrollPane = new JScrollPane(jta);
        jp.add(jScrollPane);
        this.add(jp);

        //给窗口关闭添加事件监听
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "确定关闭服务器吗");
                if (res == 0) {
                    try {
                        serverSocket.close();//关闭服务器端
                        dispose();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    serverFlag = false;
                    clientFlag = false;
                    System.exit(0);
                }
            }
        });
    }

    //启动服务器
    public void startServer() {
        try {
            serverSocket = new ServerSocket(9999);
            arrayList = new ArrayList<>();
        } catch (BindException e) {
            dispose();
            JOptionPane.showMessageDialog(null, "端口已被占用");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "服务器异常");
            return;
        }
        try {
            while (serverFlag) {
                //将连接到的客户端socket存入ArrayList
                Socket socket = serverSocket.accept();
                arrayList.add(socket);
                System.out.println("第" + arrayList.size() + "个连接");

                //创建线程,将Socket传入线程
                new ServerThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            dispose();
        }
    }

    //创建内部类,让内部类继承Thread
    class ServerThread extends Thread {
        Socket socket;
        DataInputStream in;

        public ServerThread(Socket socket) throws IOException {
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
        }

        @Override
        public void run() {
            while (clientFlag) {
                String msg = null;
                try {
                    //接收客户端发来的消息
                    msg = in.readUTF();
                    jta.append(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "客户端下线了");
                }

                //遍历ArrayList
                Iterator<Socket> iterator = arrayList.iterator();
                while (iterator.hasNext()) {
                    Socket sk = iterator.next();
                    if (sk.isClosed()) {//判断当前客户端是否关闭
                        iterator.remove();
                        continue;
                    }
                    //向当前遍历的客户端发送消息
                    try {
                        DataOutputStream out = new DataOutputStream(sk.getOutputStream());
                        out.writeUTF(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}

