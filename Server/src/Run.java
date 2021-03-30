public class Run {
    public static void main(String[] args) {
        ServerJframe serverJframe = new ServerJframe();
        serverJframe.setFrame();
        serverJframe.setVisible(true);
        serverJframe.startServer();//启动服务器
    }
}
