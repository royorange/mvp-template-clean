package com.roy.app.mvptemplate.data.cache;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Roy on 2018/2/22.
 */
@Singleton
public class CacheManager {
    private static final String SESSION = "Session";
    private SharedPreferences mSf = null;

    @Inject
    public CacheManager(Context c) {
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

    private String load(String key){
        return mSf.getString(key,"");
    }
}
