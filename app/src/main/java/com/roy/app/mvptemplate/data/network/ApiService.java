package com.roy.app.mvptemplate.data.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roy.app.mvptemplate.BuildConfig;
import com.roy.app.mvptemplate.data.cache.ConfigManager;
import com.roy.app.mvptemplate.data.network.converter.WebGsonConverterFactory;
import com.roy.app.mvptemplate.data.network.exception.RetrofitException;
import com.roy.app.mvptemplate.data.utils.NetUtils;
import com.roy.app.mvptemplate.domain.constants.UrlConstants;
import com.roy.app.mvptemplate.domain.model.NetResponse;
import com.roy.app.mvptemplate.domain.util.GsonUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by Roy on 2018/3/9.
 */

public class ApiService {
    public static final String REQUEST_HOST = "hostUrl";
    public static final String HOST_API = "api";
    public static final String HOST_SERVER = "server";
    public static final int ENV_PRODUCT = 0;
    public static final int ENV_STAGE = 1;
    public static final int ENV_EBF = 2;
    public static final int ENV_QA2 = 3;
    private static final int TIMEOUT_CONNECTION = 15;
    private static final int TIMEOUT_READING = 15;
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private AppCookieJar mCookieJar;
    private CookieStore store;
    private Retrofit retrofit;
    private OkHttpClient client;
    private ConfigManager config;
    private ConnectivityManager connectivityManager;
    private Gson gson = new Gson();

    @Inject
    public ApiService(CookieStore store, ConfigManager config, Context context) {
        this.mCookieJar = new AppCookieJar(store);
        this.config = config;
        client = buildClient(mCookieJar);
        retrofit = buildRestService(client);
        connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private Retrofit buildRestService(OkHttpClient client){
        Retrofit.Builder builder = new Retrofit.Builder()
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(WebGsonConverterFactory.create(
                        new GsonBuilder()
                                .setLenient()
                                .registerTypeAdapter(Boolean.class, GsonUtil.getBooleanAsIntAdapter())
                                .registerTypeAdapter(boolean.class, GsonUtil.getBooleanAsIntAdapter())
                                .registerTypeAdapter(NetResponse.class,new NetResponseDeserializer<>())
                                .create()))
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .client(client);
        builder.baseUrl(NetUtils.getApiHostWithScheme(config));
        return builder.build();
    }

    private OkHttpClient buildClient(AppCookieJar cookieJar){
        X509TrustManager easyTrustManager = new X509TrustManager() {
            public void checkClientTrusted(
                    X509Certificate[] chain,
                    String authType) throws CertificateException {
                // Oh, I am easy!
            }

            public void checkServerTrusted(
                    X509Certificate[] chain,
                    String authType) throws CertificateException {
                // Oh, I am easy!
            }

            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        };
        SSLContext sc = null;
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {easyTrustManager};
        // Install the all-trusting trust manager
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());

        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_READING,TimeUnit.SECONDS)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
        if(sc != null){
            builder.sslSocketFactory(sc.getSocketFactory(),easyTrustManager);
        }
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.APP_DEBUG?HttpLoggingInterceptor.Level.BODY:HttpLoggingInterceptor.Level.NONE);
        builder.addInterceptor(loggingInterceptor);
        builder.cookieJar(cookieJar);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException,RuntimeException{
                if(!isNetworkConnected()){
                    throw RetrofitException.networkError(null);
                }
                Request original = chain.request();
                Request.Builder requestBuilder = buildNewRequest(original);
                Response response = chain.proceed(requestBuilder.build());
//                checkResponse(response);
                return response;
            }
        });
        return builder.build();
    }

    private void checkResponse(Response response)throws RuntimeException{
        String errorCode = response.header("ErrorCode");
        if(!TextUtils.isEmpty(errorCode)){
            if(errorCode.equals("503")){
                String errorMessage = response.header("ErrorMessage");
                throw RetrofitException.maintainError(new Throwable(errorMessage));
            }
        }
    }

    private Request.Builder buildNewRequest(Request original){
        Request.Builder requestBuilder = original.newBuilder()
//                .header("User-Agent", config.getUserAgent())
                .method(original.method(), original.body());
        //apply host
        String host = original.header(REQUEST_HOST);
        if(host!=null){
            HttpUrl newUrl;
            if (BuildConfig.APP_DEBUG){
                if(host.equals(HOST_SERVER)){
                    host = NetUtils.getServerHost(config);
                }
            }else {
                host = UrlConstants.SERVER_HOST;
            }
            newUrl = original.url().newBuilder()
                    .host(host)
                    .scheme("http")
                    .build();
            requestBuilder.url(newUrl);
        }else {
            if(BuildConfig.APP_DEBUG){
                host = NetUtils.getApiHost(config);
                HttpUrl newUrl = original.url().newBuilder()
                        .host(host)
                        .build();
                requestBuilder.url(newUrl);
            }
        }
        //增加渠道
//        requestBuilder.header("channel","APP");
//        String uid = config.getUserId();
//        if(!TextUtils.isEmpty(uid)){
//            requestBuilder.header("UID",uid);
//        }
//        if(config.isLogin()){
//            requestBuilder.header("Token",config.getApiToken());
//        }
        return requestBuilder;
    }

    public <T> T api(Class<T> clazz){
        if(retrofit == null){
            return null;
        }
        return retrofit.create(clazz);
    }

    private boolean isNetworkConnected() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable() && networkInfo.isConnected();
        }
        return false;
    }
}
