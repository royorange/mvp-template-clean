package com.roy.app.mvptemplate.data.network;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.roy.app.mvptemplate.domain.model.NetResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Roy on 2018/3/5.
 */

public class NetResponseDeserializer<T> implements JsonDeserializer<NetResponse<T>> {
    @Override
    public NetResponse<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject root = json.getAsJsonObject();
        JsonElement errorMessage = root.get("errorMessage");
        NetResponse obj = new NetResponse();
        obj.setTimeStamp(root.get("timeStamp").getAsLong());
        if(errorMessage!=null&&errorMessage.isJsonNull()){
            //NetResponse有参数时
            if(typeOfT == NetResponse.class){
                return obj;
            }
            obj.setBody(context.deserialize(root.get("results"),((ParameterizedType) typeOfT).getActualTypeArguments()[0]));
        }else {
            obj = new NetResponse();
            obj.setMessage(errorMessage.getAsString());
            obj.setErrorCode(root.get("errorCode").getAsInt());
        }
        return obj;
    }
}
