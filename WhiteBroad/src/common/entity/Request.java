package common.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Request implements Serializable {
    private static final long serialVersionUID = -1237018286305074249L;
    /** 请求动作 */
    private String action;
    private Map<String, Object> attributesMap;

    public Request(){
        this.attributesMap = new HashMap<String, Object>();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Object getAttribute(String name){
        return this.attributesMap.get(name);
    }

    public void setAttribute(String name, Object value){
        this.attributesMap.put(name, value);
    }

}
