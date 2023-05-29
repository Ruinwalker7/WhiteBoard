package server.controller;

import common.entity.*;
import server.DataBuffer;
import server.OnlineClientIOCache;
import server.ServerUtil;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
    监听新的用户线程
 */
public class RequestProcessor implements Runnable {
    private Socket currentClientSocket;

    public RequestProcessor(Socket currentClientSocket){
        this.currentClientSocket = currentClientSocket;
    }

    public void run() {
        try{
            OnlineClientIOCache currentClientIOCache = new OnlineClientIOCache(
                    new ObjectInputStream(currentClientSocket.getInputStream()),
                    new ObjectOutputStream(currentClientSocket.getOutputStream()));
            while(true){
                Request request = (Request)currentClientIOCache.getOis().readObject();
                System.out.println("Server读取了客户端的请求:" + request.getAction());
                String actionName = request.getAction();   //获取请求中的动作
                if(actionName.equals("Login")){  //登录
                    login(currentClientIOCache, request);
                }else if("chat".equals(actionName)){       //聊天
                    chat(request);
                }else if("exit".equals(actionName)){       //请求断开连接
                    logout(currentClientIOCache, request);
                    break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    /** 客户端退出 */
    public void logout(OnlineClientIOCache oio, Request request) throws IOException{
        System.out.println(currentClientSocket.getInetAddress().getHostAddress()
                + ":" + currentClientSocket.getPort() + "走了");

        User user = (User)request.getAttribute("user");
        DataBuffer.onlineUserIOCacheMap.remove(user.getNickname());
        DataBuffer.onlineUsersMap.remove(user.getNickname());
        Response response = new Response();
        response.setType(ResponseType.LOGOUT);
        response.setData("logoutUser", user);
        oio.getOos().writeObject(response);  //把响应对象往客户端写
        oio.getOos().flush();
        currentClientSocket.close();

        DataBuffer.onlineUserTableModel.remove(user.getNickname()); //把当前下线用户从在线用户表Model中删除
        ServerUtil.iteratorResponse(response);
    }


    /** 登录 */
    public void login(OnlineClientIOCache currentClientIO, Request request) throws IOException {

        User user =(User)request.getAttribute("user");

        Response response = new Response();  //创建一个响应对象
        if(null != user){
            if(DataBuffer.onlineUsersMap.containsKey(user.getNickname())){ //用户已经登录了
                response.setStatus(ResponseStatus.OK);
                response.setData("msg", "该 用户已经在别处上线了！");
                currentClientIO.getOos().writeObject(response);  //把响应对象往客户端写
                currentClientIO.getOos().flush();
            }else { //正确登录
                DataBuffer.onlineUsersMap.put(user.getNickname(), user); //添加到在线用户

                //设置在线用户
                response.setData("onlineUsers",
                        new CopyOnWriteArrayList<User>(DataBuffer.onlineUsersMap.values()));

                response.setStatus(ResponseStatus.OK);
                response.setData("user", user);
                currentClientIO.getOos().writeObject(response);  //把响应对象往客户端写
                currentClientIO.getOos().flush();

                //通知其它用户有人上线了
                Response response2 = new Response();
                response2.setType(ResponseType.LOGIN);
                response2.setData("loginUser", user);
                ServerUtil.iteratorResponse(response2);

                //把当前上线的用户IO添加到缓存Map中
                DataBuffer.onlineUserIOCacheMap.put(user.getNickname(),currentClientIO);
                System.out.println(DataBuffer.onlineUserIOCacheMap);

                //把当前上线用户添加到OnlineUserTableModel中
                DataBuffer.onlineUserTableModel.add(
                        new String[]{String.valueOf(user.getNickname()),
                                user.getNickname()});
            }
        }else{ //登录失败
            response.setStatus(ResponseStatus.OK);
            response.setData("msg", "账号或密码不正确！");
            currentClientIO.getOos().writeObject(response);
            currentClientIO.getOos().flush();
        }
    }

    /** 聊天 */
    public void chat(Request request) throws IOException {
        Message msg = (Message)request.getAttribute("msg");
        Response response = new Response();
        response.setStatus(ResponseStatus.OK);
        response.setType(ResponseType.CHAT);
        response.setData("txtMsg", msg);
        ServerUtil.appendTxt2MsgListArea(msg.getMessage());
        for(String name : DataBuffer.onlineUserIOCacheMap.keySet()){
            if(msg.getFromUser().getNickname() == name ){
                continue; }
            sendResponse(DataBuffer.onlineUserIOCacheMap.get(name), response);
        }
    }

    /** 踢除用户 */
    public static void remove(User user_) throws IOException{
        User user = new User("老师");
        Message msg = new Message();
        msg.setFromUser(user);
        msg.setSendTime(new Date());
        msg.setToUser(user_);

        StringBuffer sb = new StringBuffer();
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        sb.append(" ").append(df.format(msg.getSendTime())).append(" ");
        sb.append("系统通知您\n  "+"您被强制下线"+"\n");
        msg.setMessage(sb.toString());

        Response response = new Response();
        response.setStatus(ResponseStatus.OK);
        response.setType(ResponseType.REMOVE);
        response.setData("txtMsg", msg);

        OnlineClientIOCache io = DataBuffer.onlineUserIOCacheMap.get(msg.getToUser().getNickname());
        try{
            sendResponse_sys(io, response);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /** 私信 */
    public static void chat_sys(String str,User user_) throws IOException{
        User user = new User("admin");
        Message msg = new Message();
        msg.setFromUser(user);
        msg.setSendTime(new Date());
        msg.setToUser(user_);

        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        StringBuffer sb = new StringBuffer();
        sb.append(" ").append(df.format(msg.getSendTime())).append(" ");
        sb.append("系统通知您\n  "+str+"\n");
        msg.setMessage(sb.toString());

        Response response = new Response();
        response.setStatus(ResponseStatus.OK);
        response.setType(ResponseType.CHAT);
        response.setData("txtMsg", msg);

        OnlineClientIOCache io = DataBuffer.onlineUserIOCacheMap.get(msg.getToUser().getNickname());
        sendResponse_sys(io, response);
    }

    /** 向指定客户端IO的输出流中输出指定响应 */
    private void sendResponse(OnlineClientIOCache onlineUserIO, Response response)throws IOException {
        ObjectOutputStream oos = onlineUserIO.getOos();
        oos.writeObject(response);
        oos.flush();
    }

    /** 发送失败会认为下线 */
    private static void sendResponse_sys(OnlineClientIOCache onlineUserIO, Response response)throws IOException {
        ObjectOutputStream oos = onlineUserIO.getOos();
        try{
            oos.writeObject(response);
            oos.flush();
        }catch (IOException e){
            Message msg = (Message)response.getData("txtMsg");
            DataBuffer.onlineUserIOCacheMap.remove(msg.getToUser().getNickname());
            DataBuffer.onlineUsersMap.remove(msg.getToUser().getNickname());
            DataBuffer.onlineUserTableModel.remove(msg.getToUser().getNickname());
        }

    }
}
