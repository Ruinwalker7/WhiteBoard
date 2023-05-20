package common.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Response implements Serializable {
    private static final long serialVersionUID = 1689541820872288991L;
    private ResponseStatus status;
    /** 响应数据的类型 */
    private ResponseType type;

    private Map<String, Object> dataMap;


    public Response(){
        this.status = ResponseStatus.OK;
        this.dataMap = new HashMap<String, Object>();
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }


    public void setData(String name, Object value){
        this.dataMap.put(name, value);
    }

    public Object getData(String name){
        return this.dataMap.get(name);
    }

}
