package com.roy.app.mvptemplate.data.network;

import com.roy.app.mvptemplate.data.network.exception.RetrofitException;
import com.roy.app.mvptemplate.domain.model.NetResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by Roy on 17/3/6.
 */

public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {
    private final RxJava2CallAdapterFactory original;

    private RxErrorHandlingCallAdapterFactory() {
        original = RxJava2CallAdapterFactory.create();
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

    @Override
    public CallAdapter<?,?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit));
    }

    private static class RxCallAdapterWrapper implements CallAdapter {
        private final Retrofit retrofit;
        private final CallAdapter<?,?> wrapped;

        public RxCallAdapterWrapper(Retrofit retrofit, CallAdapter<?,?> wrapped) {
            this.retrofit = retrofit;
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType() {
            Type responseType = wrapped.responseType();
            //用于支持completable
            //当使用completable作为response时，由于仍需peek commonResponse中的errorMessage
            //因此将类型强行转换为NetResponse
            if(responseType == Void.class){
                return NetResponse.class;
            }
            return wrapped.responseType();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Object adapt(Call call) {
            Object rxObj = wrapped.adapt(call);
            if(rxObj instanceof Completable){
                return ((Completable) wrapped.adapt(call)).onErrorResumeNext(new Function<Throwable, CompletableSource>() {
                    @Override
                    public CompletableSource apply(Throwable throwable) throws Exception {
                        return Completable.error(asRetrofitException(throwable));
                    }
                });
            }else if(rxObj instanceof Single){
                return ((Single) wrapped.adapt(call)).onErrorResumeNext(new Function<Throwable, SingleSource>() {
                    @Override
                    public SingleSource<Object> apply(Throwable throwable) throws Exception {
                        return Single.error(asRetrofitException(throwable));
                    }
                });
            }else if(rxObj instanceof Maybe){
                return ((Maybe) wrapped.adapt(call)).onErrorResumeNext(new Function<Throwable, MaybeSource>() {
                    @Override
                    public MaybeSource apply(Throwable throwable) throws Exception {
                        return Maybe.error(asRetrofitException(throwable));
                    }
                });
            }else {
                return ((Observable) wrapped.adapt(call)).onErrorResumeNext(new Function<Throwable, ObservableSource>() {
                    @Override
                    public ObservableSource apply(Throwable throwable) throws Exception {
                        return Observable.error(asRetrofitException(throwable));
                    }
                });
            }
        }

        private RetrofitException asRetrofitException(Throwable throwable) {
            if(throwable instanceof RetrofitException){
                return (RetrofitException)throwable;
            }else if (throwable instanceof HttpException) {
                // We had non-200 http error
                HttpException httpException = (HttpException) throwable;
                Response response = httpException.response();
                //for authorized reject
                int responseCode = httpException.code();
                if(responseCode == 401){
                    return RetrofitException.authorizedError(response.raw().request().url().toString(), response, retrofit);
                }else {

                }
            }

            return RetrofitException.unexpectedError(throwable);
        }
    }
}
