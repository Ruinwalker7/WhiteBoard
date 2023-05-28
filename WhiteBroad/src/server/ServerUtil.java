package server;

import server.ui.ServerFrame;

public class ServerUtil {

    /** 把指定文本添加到消息列表文本域中 */
    public static void appendTxt2MsgListArea(String txt) {
        ServerFrame.msgListArea.append(txt);
        ServerFrame.msgListArea.setCaretPosition(ServerFrame.msgListArea.getDocument().getLength());
    }
}
