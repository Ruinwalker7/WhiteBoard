package client;

import client.model.entity.OnlineUserListModel;
import common.entity.User;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.Properties;

/** 静态变量类*/
public class DataBuffer {
    /** 当前客户端的用户信息 */
    public static User currentUser;
    /** 在线用户列表 */
    public static List<User> onlineUsers;
    /** 当前客户端连接到服务器的套节字 */
    public static Socket clientSeocket;
    /** 当前客户端连接到服务器的输出流 */
    public static ObjectOutputStream oos;
    /** 当前客户端连接到服务器的输入流 */
    public static ObjectInputStream ois;
    /** 服务器配置参数属性集 */
    public static Properties configProp;
    /** 当前客户端的屏幕尺寸 */
    public static Dimension screenSize;
    /** 本客户端的IP地址 */
    public static String ip ;
    /** 固定用来接收文件的端口 */
    public static final int RECEIVE_FILE_PORT = 6667;
    /** 在线用户JList的Model */
    public static OnlineUserListModel onlineUserListModel;

    static{
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //加载服务器配置文件
        configProp = new Properties();
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
            configProp.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("serverconfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DataBuffer(){}
}

