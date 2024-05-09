package client;

import client.ui.ClientFrame;
import client.util.ClientUtil;
import common.entity.*;
import common.util.IOUtil;
import server.ServerUtil;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

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
                if (type == ResponseType.LOGIN) {
                    User newUser = (User)response.getData("loginUser");
                    DataBuffer.onlineUserListModel.addElement(newUser);
//                    ClientUtil.appendTxt2MsgListArea("【系统消息】用户"+newUser.getNickname() + "上线了！\n");
                }else if(type == ResponseType.LINE){
                    Line line = (Line)response.getData("shape");
                    DataBuffer.g.setStroke(new BasicStroke(line.getF()));
                    DataBuffer.g.setColor(line.getColor());
                    DataBuffer.g.draw(line.getLine2D());
                    DataBuffer.LineList.add(line);
                }
                else if(type == ResponseType.ELLIPSE){
                    Ellipse ellipse = (Ellipse) response.getData("shape");
                    DataBuffer.g.setStroke(new BasicStroke(ellipse.getF()));
                    DataBuffer.g.setColor(ellipse.getColor());
                    DataBuffer.g.draw(ellipse.getEllipse2D());
                    DataBuffer.ellipseList.add(ellipse);
                }
                else if(type == ResponseType.LOGOUT){
                    User newUser = (User)response.getData("logoutUser");
                    DataBuffer.onlineUserListModel.removeElement(newUser);
//                    ClientUtil.appendTxt2MsgListArea("【系统消息】用户"+newUser.getNickname() + "下线了！\n");
                }
                else if(type == ResponseType.CHAT){ //聊天
                    System.out.println("收到了聊天");
                    Message msg = (Message)response.getData("txtMsg");
                    ClientUtil.appendTxt2MsgListArea(msg.getMessage());
                }else if(type == ResponseType.BOARD){
                    Message msg = (Message)response.getData("txtMsg");
                    ClientUtil.appendTxt2MsgListArea(msg.getMessage());
                } else if (type == ResponseType.CLEAR) {
                    DataBuffer.LineList.clear();
                    DataBuffer.ellipseList.clear();
                    ClientFrame.whiteBroad.paint(ClientFrame.whiteBroad.getGraphics());
                }else if(type == ResponseType.FILE){
                    toSendFile(response);
                }else if(type == ResponseType.RECEIVING){
                    receiveFile(response);
                }
            }
        } catch (SocketException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** 准备发送文件	 */
    private void toSendFile(Response response) {
        FileInfo sendFile = (FileInfo)response.getData("file");

        String fileName = sendFile.getSrcName()
                .substring(sendFile.getSrcName().lastIndexOf(File.separator)+1);

        int select = JOptionPane.showConfirmDialog(this.currentFrame,
                "老师向您发送文件 [" + fileName+ "]!\n同意接收吗?",
                "接收文件", JOptionPane.YES_NO_OPTION);
        try {
            Request request = new Request();
            request.setAttribute("sendFile", sendFile);

            if (select == JOptionPane.YES_OPTION) {
                JFileChooser jfc = new JFileChooser();
                jfc.setSelectedFile(new File(fileName));
                int result = jfc.showSaveDialog(this.currentFrame);

                if (result == JFileChooser.APPROVE_OPTION){
                    //设置目的地文件名
                    sendFile.setDestName(jfc.getSelectedFile().getCanonicalPath());
                    //设置目标地的IP和接收文件的端口
                    sendFile.setDestIp(DataBuffer.ip);
                    sendFile.setDestPort(DataBuffer.RECEIVE_FILE_PORT);

                    request.setAction("agreeReceiveFile");
                    ClientUtil.appendTxt2MsgListArea("【文件消息】您已同意接收文件，正在接收文件 ...\n");
                } else {
                    request.setAction("refuseReceiveFile");
                    ClientUtil.appendTxt2MsgListArea("【文件消息】您已拒绝接收文件!\n");
                }
            } else {
                request.setAction("refuseReceiveFile");
                ClientUtil.appendTxt2MsgListArea("【文件消息】您已拒绝接收文件!\n");
            }

            ClientUtil.sendTextRequestWithoutReceive(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** 接收文件 */
    private void receiveFile(Response response) {
        final FileInfo sendFile = (FileInfo)response.getData("sendFile");

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(sendFile.getDestPort());
            socket = serverSocket.accept(); //接收
            bis = new BufferedInputStream(socket.getInputStream());//缓冲读
            bos = new BufferedOutputStream(new FileOutputStream(sendFile.getDestName()));//缓冲写出

            byte[] buffer = new byte[1024];
            int n = -1;
            while ((n = bis.read(buffer)) != -1){
                bos.write(buffer, 0, n);
            }
            bos.flush();
            synchronized (this) {
                ClientUtil.appendTxt2MsgListArea("【文件消息】文件接收完毕!存放在["
                        + sendFile.getDestName()+"]\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            IOUtil.close(bis,bos);

            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

