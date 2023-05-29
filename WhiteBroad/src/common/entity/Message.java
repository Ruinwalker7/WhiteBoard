package common.entity;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private static final long serialVersionUID = 1820192075144114657L;
    /** 消息接收者 */
    private Boolean toTeacher;
    /** 消息发送者 */
    private User fromUser;
    /** 消息内容 */
    private String message;
    /** 发送时间 */
    private Date sendTime;

    public Boolean getToTeacher() {
        return toTeacher;
    }

    public void setToTeacher(Boolean toTeacher) {
        this.toTeacher = toTeacher;
    }

    public User getFromUser() {
        return fromUser;
    }
    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSendTime() {
        return sendTime;
    }
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
