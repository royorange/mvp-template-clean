package com.roy.app.mvptemplate.data.network;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Roy on 2018/2/26.
 */

public class NetParam {
    private Map map;

    public NetParam() {
        map = new HashMap();
    }

    public NetParam addParam(String key,Object value){
        map.put(key,value);
        return this;
    }

    public Map getMap() {
        return map;
    }
}
