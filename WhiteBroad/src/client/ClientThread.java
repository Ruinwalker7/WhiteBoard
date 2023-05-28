package client;

import client.ui.ClientFrame;
import client.util.ClientUtil;
import common.entity.*;

import javax.swing.*;
import java.io.IOException;

public class ClientThread extends Thread {
    private JFrame currentFrame;

    public ClientThread(JFrame frame){
        currentFrame = frame;
    }

    public void run() {
        try {
            while (DataBuffer.clientSeocket.isConnected()) {
                Response response = (Response) DataBuffer.ois.readObject();
                ResponseType type = response.getType();
                System.out.println("获取了响应内容：" + type);
//                if (type == ResponseType.LOGIN) {
//                    User newUser = (User)response.getData("loginUser");
//                    DataBuffer.onlineUserListModel.addElement(newUser);
//                    ClientFrame.onlineCountLbl.setText(
//                            "在线用户列表("+ DataBuffer.onlineUserListModel.getSize() +")");
//                    ClientUtil.appendTxt2MsgListArea("【系统消息】用户"+newUser.getNickname() + "上线了！\n");
//                }else if(type == ResponseType.LOGOUT){
//                    User newUser = (User)response.getData("logoutUser");
//                    DataBuffer.onlineUserListModel.removeElement(newUser);
//                    ClientFrame.onlineCountLbl.setText(
//                            "在线用户列表("+ DataBuffer.onlineUserListModel.getSize() +")");
//                    ClientUtil.appendTxt2MsgListArea("【系统消息】用户"+newUser.getNickname() + "下线了！\n");
//                }else if(type == ResponseType.CHAT){ //聊天
//                    Message msg = (Message)response.getData("txtMsg");
//                    ClientUtil.appendTxt2MsgListArea(msg.getMessage());
//                }else if(type == ResponseType.BOARD){
//                    Message msg = (Message)response.getData("txtMsg");
//                    ClientUtil.appendTxt2MsgListArea(msg.getMessage());
//                }else if(type == ResponseType.REMOVE){
//                    ClientFrame.remove();
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

