package com.roy.app.mvptemplate.data.network;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by Roy on 16/8/5.
 */

public class AppCookieJar implements CookieJar {
    private CookieStore store;
    private Map<String,List<Cookie>> cookieMap;

    public AppCookieJar(CookieStore store){
        this.store = store;
        cookieMap = new ConcurrentHashMap<>();
    }

    private String baseUrl(HttpUrl url){
        return url.scheme() + "://" + url.host();
    }

    /**
     * get local cookies
     * @param url
     * @return
     */
    public List<Cookie> getCookie(String url){
        URI uri = null;
        List<Cookie> list = null;
        try {
            uri = new URI(url);
            list = new ArrayList<>();
            android.webkit.CookieManager androidCm = android.webkit.CookieManager.getInstance();
            String cookie = androidCm.getCookie(url);
            if(cookie != null){
                String[] cookies = cookie.split(";");
                if(cookies.length>0){
                    for(int i=0;i<cookies.length;i++){
                        Cookie cookieTemp = Cookie.parse(HttpUrl.parse(url), cookies[i]);
                        list.add(cookieTemp);
                    }
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return list != null?list: Collections.<Cookie>emptyList();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        String key = baseUrl(url);
        store.putCookies(key,cookies);
        cookieMap.put(key,cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        if(url.isHttps()){
            return getCookie(baseUrl(url));
        }else {
            return Collections.emptyList();
        }
    }
}
