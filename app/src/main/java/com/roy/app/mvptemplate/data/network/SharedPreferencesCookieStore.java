package com.roy.app.mvptemplate.data.network;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by Roy on 16/8/5.
 */

public class SharedPreferencesCookieStore implements CookieStore{
    public static final String COOKIES = "APP_COOKIES";
    private final SharedPreferences preferences;

    @Inject
    public SharedPreferencesCookieStore(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    private Set<String> read(String key) {
        return preferences.getStringSet(key, null);
    }

    private void write(String key, Set<String> data) {
        preferences.edit().putStringSet(key, data).apply();
    }


    @Override
    public List<Cookie> getCookies(String baseUrl) {
        HttpUrl url = HttpUrl.parse(baseUrl);
        Set<String> data = read(baseUrl);

        if (url != null && data != null) {
            List<Cookie> cookies = new ArrayList<>();
            for (String item : data) {
                Cookie cookie = Cookie.parse(url, item);
                if (cookie != null) {
                    cookies.add(cookie);
                }
            }
            return cookies;
        }

        return Collections.emptyList();
    }

    @Override
    public void putCookies(String baseUrl, List<Cookie> cookies) {
        if (cookies != null) {
            Set<String> data = new HashSet<>();
            for (Cookie cookie : cookies) {
                if (cookie != null) {
                    data.add(cookie.toString());
                }
            }
            write(baseUrl, data);
        }
    }
}
