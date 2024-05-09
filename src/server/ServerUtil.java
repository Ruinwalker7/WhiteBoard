package server;

import common.entity.Response;
import server.ui.ServerFrame;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ServerUtil {

    /** 把指定文本添加到消息列表文本域中 */
    public static void appendTxt2MsgListArea(String txt) {
        ServerFrame.msgListArea.append(txt);
        ServerFrame.msgListArea.setCaretPosition(ServerFrame.msgListArea.getDocument().getLength());
    }
    /** 给所有在线客户都发送响应 */
    public static void iteratorResponse(Response response) throws IOException {
        for(OnlineClientIOCache onlineUserIO : DataBuffer.onlineUserIOCacheMap.values()){
            ObjectOutputStream oos = onlineUserIO.getOos();
            oos.writeObject(response);
            oos.flush();
        }
    }
}
