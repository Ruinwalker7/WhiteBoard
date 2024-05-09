package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**用于存储客户端的输入输出流*/
public class OnlineClientIOCache {
    final private ObjectInputStream ois;

    final private ObjectOutputStream oos;

    public OnlineClientIOCache(ObjectInputStream ois, ObjectOutputStream oos){
        this.ois = ois;
        this.oos = oos;
    }

    public ObjectOutputStream getOos(){
        return oos;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

}
