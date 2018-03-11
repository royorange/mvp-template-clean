package com.roy.app.mvptemplate.data.utils;

import com.roy.app.mvptemplate.BuildConfig;
import com.roy.app.mvptemplate.data.cache.CacheManager;
import com.roy.app.mvptemplate.data.network.ApiService;

/**
 * Created by Roy on 2018/3/9.
 */

public class NetUtils {

    public static String getServerHost(CacheManager config) {
        String url;
        if (!BuildConfig.DEBUG) {
            url =  "m.sephora.cn";
        }else {
            switch (config.getEnv()){
                case ApiService.ENV_PRODUCT:
                    url = "m.sephora.cn";
                    break;
                case ApiService.ENV_STAGE:
                    url =  "stagem.sephora.cn";
                    break;
                case ApiService.ENV_EBF:
                    url = "ebf.sephora.cn";
                    break;
                case ApiService.ENV_QA2:
                    url = "testm.sephora.cn";
                    break;
                default:
                    url = "m.sephora.cn";
            }
        }
        return url;
    }

    public static String getApiHost(CacheManager config) {
        String url;
        if (!BuildConfig.APP_DEBUG) {
            url =  "api.sephora.cn";
        }else {
            switch (config.getEnv()){
                case ApiService.ENV_PRODUCT:
                    url = "api.sephora.cn";
                    break;
                case ApiService.ENV_STAGE:
                    url =  "stageapi.sephora.cn";
                    break;
                case ApiService.ENV_EBF:
                    url = "ebfapi.sephora.cn";
                    break;
                case ApiService.ENV_QA2:
                    url = "test.sephora.cn";
                    break;
                default:
                    url = "api.sephora.cn";
            }
        }
        return url;
    }

    public static String getApiHostWithScheme(CacheManager config) {
        return "https://" + getApiHost(config);
    }
}
