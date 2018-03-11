package com.roy.app.mvptemplate.data.network;

import java.util.List;

import okhttp3.Cookie;

/**
 * Created by Roy on 16/8/5.
 */

public interface CookieStore {

    List<Cookie> getCookies(String baseUrl);

    void putCookies(String baseUrl, List<Cookie> cookies);
}
