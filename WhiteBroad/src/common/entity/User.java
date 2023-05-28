package common.entity;

import javax.swing.*;
import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 5942011574971970871L;
    private String nickname;

    public User(String nickname){
        if(nickname.equals("")||nickname==null)
        {
            this.nickname = "未命名";
        }else{
            this.nickname = nickname;
        }
    }


    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public String getNickname(){
        return this.nickname;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if(nickname == null){
            if(other.nickname != null)
                return false;
        }else if(!nickname.equals(other.nickname))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getName()
                + ",nickname=" + this.nickname
                + "]";
    }
}
