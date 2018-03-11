package com.roy.app.mvptemplate.data.network.converter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.roy.app.mvptemplate.data.network.exception.RetrofitException;
import com.roy.app.mvptemplate.domain.model.NetResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Roy on 2018/3/2.
 */

public class WebGsonConverterFactory extends Converter.Factory {
    private final GsonConverterFactory original;
    private final Gson gson;

    public static WebGsonConverterFactory create() {
        return create(new Gson());
    }

    public static WebGsonConverterFactory create(Gson gson) {
        if(gson == null) {
            throw new NullPointerException("gson == null");
        } else {
            return new WebGsonConverterFactory(gson);
        }
    }

    private WebGsonConverterFactory(Gson gson) {
        this.gson = gson;
        this.original = GsonConverterFactory.create(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = this.gson.getAdapter(TypeToken.get(type));
        return new GsonResponseBodyWrapper(this.gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return original.requestBodyConverter(type,parameterAnnotations,methodAnnotations,retrofit);
    }

    private class GsonResponseBodyWrapper<T> implements Converter<ResponseBody, NetResponse> {
        private final Gson gson;
        private final TypeAdapter<T> adapter;

        GsonResponseBodyWrapper(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        public NetResponse convert(ResponseBody value) throws IOException,RuntimeException {
            JsonReader jsonReader = this.gson.newJsonReader(value.charStream());

            NetResponse var3;
            try {
                var3 = (NetResponse)this.adapter.read(jsonReader);
            } finally {
                value.close();
            }
            if(var3!=null){
                //查看是否有业务错误
                if(!TextUtils.isEmpty(var3.getMessage())){
                    throw RetrofitException.businessError(new Throwable(var3.getMessage()),var3);
                }
            }
            return var3;
        }
    }
}
