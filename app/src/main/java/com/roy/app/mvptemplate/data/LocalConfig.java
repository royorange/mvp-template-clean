package com.roy.app.mvptemplate.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.roy.app.mvptemplate.data.network.ApiService;
import com.roy.app.mvptemplate.domain.constants.UrlConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Roy on 2018/3/9.
 */
@Singleton
public class LocalConfig {
    private static LocalConfig mInstance = null;
    private static final String SESSION = "Session";
    private static SharedPreferences mSf = null;

    @Inject
    public LocalConfig(Context c) {
        mSf = c.getSharedPreferences(SESSION, Context.MODE_PRIVATE);
    }
    public static LocalConfig getInstance() {
        return mInstance;
    }

    public static LocalConfig loadConfig(Context c) {
        if (mInstance == null) {
            synchronized (LocalConfig.class) {
                if (mInstance == null) {
                    mInstance = new LocalConfig(c);
                }
            }
        }
        return mInstance;
    }

    public SharedPreferences getSharedPreference() {
        return mSf;
    }

    public int getEnv() {
        return mSf.getInt("arg_env", getDefaultEnv());
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
}
