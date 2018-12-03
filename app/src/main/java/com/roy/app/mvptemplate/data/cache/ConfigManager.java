package com.roy.app.mvptemplate.data.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.roy.app.mvptemplate.data.network.ApiService;
import com.roy.app.mvptemplate.domain.constants.UrlConstants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Roy on 2018/2/22.
 */
@Singleton
public class ConfigManager {
    private static final String SESSION = "Session";
    private SharedPreferences mSf = null;

    @Inject
    public ConfigManager(Context c) {
        mSf = c.getSharedPreferences(SESSION, Context.MODE_PRIVATE);
    }

    void writeToFile(File file, String fileContent) {
        if (!file.exists()) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(fileContent);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setUserId(String uid){
        save("uid",uid);
    }

    public String getUserId(){
        return load("uid");
    }

    private void save(String key,String content){
        SharedPreferences.Editor editor = mSf.edit();
        editor.putString(key, content);
        editor.apply();
    }

    private void save(String key,int content){
        SharedPreferences.Editor editor = mSf.edit();
        editor.putInt(key, content);
        editor.apply();
    }

    private String load(String key){
        return mSf.getString(key,"");
    }

    private String load(String key,String defValue){
        return mSf.getString(key,"");
    }

    private int load(String key,int defValue){
        return mSf.getInt(key,defValue);
    }

    public int getEnv() {
        return load("arg_env", getDefaultEnv());
    }

    private int getDefaultEnv(){
        if(UrlConstants.SERVER_PREFIX.equals("m")){
            return ApiService.ENV_PRODUCT;
        }else if(UrlConstants.SERVER_PREFIX.equals("stagem")){
            return ApiService.ENV_STAGE;
        }else if(UrlConstants.SERVER_PREFIX.startsWith("test")){
            return ApiService.ENV_QA2;
        }else {
            return ApiService.ENV_PRODUCT;
        }

    }

    public void logout(Context context){
        setUserId(null);
    }
}
