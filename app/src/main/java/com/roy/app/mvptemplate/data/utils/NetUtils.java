package com.roy.app.mvptemplate.data.utils;

import com.roy.app.mvptemplate.BuildConfig;
import com.roy.app.mvptemplate.data.cache.ConfigManager;
import com.roy.app.mvptemplate.data.network.ApiService;

/**
 * Created by Roy on 2018/3/9.
 */

public class NetUtils {

    public static String getServerHost(ConfigManager config) {
        String url;
        if (!BuildConfig.DEBUG) {
            url =  "m.xxx.cn";
        }else {
            switch (config.getEnv()){
                case ApiService.ENV_PRODUCT:
                    url = "m.xxx.cn";
                    break;
                case ApiService.ENV_STAGE:
                    url =  "stagem.xxx.cn";
                    break;
                case ApiService.ENV_EBF:
                    url = "ebf.xxx.cn";
                    break;
                case ApiService.ENV_QA2:
                    url = "testm.xxx.cn";
                    break;
                default:
                    url = "m.xxx.cn";
            }
        }
        return url;
    }

    public static String getApiHost(ConfigManager config) {
        String url;
        if (!BuildConfig.APP_DEBUG) {
            url =  "api.xxx.cn";
        }else {
            switch (config.getEnv()){
                case ApiService.ENV_PRODUCT:
                    url = "api.xxx.cn";
                    break;
                case ApiService.ENV_STAGE:
                    url =  "stageapi.xxx.cn";
                    break;
                case ApiService.ENV_EBF:
                    url = "ebfapi.xxx.cn";
                    break;
                case ApiService.ENV_QA2:
                    url = "test.xxx.cn";
                    break;
                default:
                    url = "api.xxx.cn";
            }
        }
        return url;
    }

    public static String getApiHostWithScheme(ConfigManager config) {
        return "https://" + getApiHost(config);
    }
}
