package com.roy.app.mvptemplate.data.network.exception;

import android.support.annotation.IntDef;

import com.roy.app.mvptemplate.domain.model.NetResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Roy on 17/3/6.
 */

public class RetrofitException extends RuntimeException {
    public static final int HTTP = 0;
    public static final int NETWORK = 1;
    public static final int AUTHORIZED = 2;
    public static final int UNEXCEPTED = 3;
    public static final int BUSINESS = 4;
    public static final int MAINTAIN = 5;
    @IntDef({
            HTTP,
            NETWORK,
            AUTHORIZED,
            UNEXCEPTED,
            BUSINESS,
            MAINTAIN
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Kind {
    }

    public static RetrofitException httpError(String url, Response response, Retrofit retrofit) {
        String message = response.code() + " " + response.message();
        return new RetrofitException(message, url, response, HTTP, null, retrofit,null);
    }

    public static RetrofitException authorizedError(String url, Response response, Retrofit retrofit) {
        String message = response.code() + " " + response.message();
        return new RetrofitException(message, url, response, AUTHORIZED, null, retrofit,null);
    }

    public static RetrofitException networkError(Throwable exception) {
        return new RetrofitException(null, null, null, NETWORK, exception, null,null);
    }

    public static RetrofitException unexpectedError(Throwable exception) {
        return new RetrofitException(exception.getMessage(), null, null, UNEXCEPTED, exception, null,null);
    }

    public static RetrofitException businessError(Throwable exception,NetResponse businessResponse) {
        return new RetrofitException(exception.getMessage(), null, null, BUSINESS, exception, null,businessResponse);
    }

    public static RetrofitException maintainError(Throwable exception) {
        return new RetrofitException(exception.getMessage(), null, null, MAINTAIN, exception, null,null);
    }

    private final String url;
    private final Response response;
    private final @Kind int kind;
    private final Retrofit retrofit;
    private final NetResponse businessResponse;

    RetrofitException(String message, String url, Response response, @Kind int kind, Throwable exception, Retrofit retrofit, NetResponse businessResponse) {
        super(message, exception);
        this.url = url;
        this.response = response;
        this.kind = kind;
        this.retrofit = retrofit;
        this.businessResponse = businessResponse;
    }

    /** The request URL which produced the error. */
    public String getUrl() {
        return url;
    }

    /** Response object containing status code, headers, body, etc. */
    public Response getResponse() {
        return response;
    }

    /** The event kind which triggered this error. */
    public @Kind int getKind() {
        return kind;
    }

    /** The Retrofit this request was executed on */
    public Retrofit getRetrofit() {
        return retrofit;
    }

    public NetResponse getNetResponse() {
        return businessResponse;
    }

    /**
     * HTTP response body converted to specified {@code type}. {@code null} if there is no
     * response.
     *
     * @throws IOException if unable to convert the body to the specified {@code type}.
     */
    public <T> T getErrorBodyAs(Class<T> type) throws IOException {
        if (response == null || response.errorBody() == null) {
            return null;
        }
        Converter<ResponseBody, T> converter = retrofit.responseBodyConverter(type, new Annotation[0]);
        return converter.convert(response.errorBody());
    }
}
