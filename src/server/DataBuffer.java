package server;

import common.entity.Ellipse;
import common.entity.Line;
import common.entity.User;
import server.model.entity.OnlineUserTableModel;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentSkipListMap;

/**
* 静态变量
* */
public class DataBuffer {
    // 服务器端套接字
    public static ServerSocket serverSocket;
    //在线用户的IO Map
    public static Map<String, OnlineClientIOCache> onlineUserIOCacheMap;
    //在线用户Map
    public static Map<String, User> onlineUsersMap;
    //服务器配置参数属性集
    public static Properties configProp;

    // 当前在线用户表的Model
    public static OnlineUserTableModel onlineUserTableModel;
    // 当前服务器所在系统的屏幕尺寸
    public static Dimension screenSize;

    public static ArrayList<Line> LineList;

    public static ArrayList<Ellipse> ellipseList;


    static{
        LineList = new ArrayList<>();

        ellipseList = new ArrayList<>();

        onlineUserIOCacheMap = new ConcurrentSkipListMap<>();
        onlineUsersMap = new ConcurrentSkipListMap<>();
        configProp = new Properties();
        onlineUserTableModel = new OnlineUserTableModel();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // 加载服务器配置文件
        try {
            configProp.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("serverconfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
