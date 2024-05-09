package server;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import server.controller.RequestProcessor;
import server.ui.ServerFrame;
import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    public static void main(String[] args) {

        try {
            BeautyEyeLNFHelper.frameBorderStyle=BeautyEyeLNFHelper.frameBorderStyle.generalNoTranslucencyShadow;
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
            BeautyEyeLNFHelper.translucencyAtFrameInactive = true;
            UIManager.put("RootPane.setupButtonVisible", false);
        }
        catch(Exception e)
        {
            System.out.println("加载炫彩皮肤失败！");
        }

        int port = Integer.parseInt(DataBuffer.configProp.getProperty("port"));

        //初始化服务器套节字
        try {
            DataBuffer.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //启动新线程进行客户端连接监听
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        // 监听客户端的连接
                        Socket socket = DataBuffer.serverSocket.accept();
                        System.out.println("学生来了："
                                + socket.getInetAddress().getHostAddress()
                                + ":" + socket.getPort());
                        //新线程监听
                        new Thread(new RequestProcessor(socket)).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    new  ServerFrame();
    }
}
