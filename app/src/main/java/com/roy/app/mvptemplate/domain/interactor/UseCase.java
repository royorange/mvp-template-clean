package com.roy.app.mvptemplate.domain.interactor;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.roy.app.mvptemplate.data.executor.PostExecutionThread;
import com.roy.app.mvptemplate.data.executor.ThreadExecutor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.ResourceCompletableObserver;
import io.reactivex.observers.ResourceSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Roy on 2018/2/22.
 */

public abstract class UseCase<T, Params> {

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    private final CompositeDisposable disposables;

    UseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }

    /**
     * Builds an {@link io.reactivex.Observable} which will be used when executing the current {@link UseCase}.
     */
    protected Observable<T> buildUseCaseObservable(Params params){
        return null;
    }

    protected Single<T> buildUseCaseForResult(Params params){
        return null;
    }

    protected Completable buildUseCaseForUpdate(Params params){
        return null;
    }

    public Observable execute(Params params) {
        final Observable<T> observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
        return observable;
    }

    /**
     * Executes the current use case.
     *
     * @param observer {@link io.reactivex.observers.DisposableObserver} which will be listening to the observable build
     * by {@link #buildUseCaseObservable(Params)} ()} method.
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    public Disposable execute(DisposableObserver<T> observer, Params params) {
        Preconditions.checkNotNull(observer);
        return this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribeWith(observer);
    }

    public Disposable execute(ResourceSingleObserver<T> observer, Params params) {
        return buildUseCaseForResult(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribeWith(observer);
    }

    public Disposable execute(ResourceCompletableObserver observer, Params params) {
        return buildUseCaseForUpdate(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribeWith(observer);
    }

    public Single<T> execute(Consumer<? super T> onSuccess, final Consumer<? super Throwable> onError, Params params) {
        final Single<T> observable = this.buildUseCaseForResult(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
//        addDisposable(observable.subscribe(onSuccess,onError));
        return observable;
    }

    public Completable execute(Action complete, Consumer error, Params params) {
        final Completable observable = this.buildUseCaseForUpdate(params);
        observable.subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(observable.subscribe(complete,error));
        return observable;
    }

    public Observable<T> buildObservable(Params params){
        final Observable<T> observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
        return observable;
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    private void addDisposable(Disposable disposable) {
        Preconditions.checkNotNull(disposable);
        Preconditions.checkNotNull(disposables);
        disposables.add(disposable);
    }

    public String buildJsonListBody(List<Map> params){
        JsonArray array = new JsonArray();
        for (Map p:params){
            array.add(makeParamBody(p));
        }
        JsonObject body = new JsonObject();
        body.add("queryBody",array);
        return body.toString();
    }

    public JsonArray buildJsonList(List<Map> params){
        JsonArray array = new JsonArray();
        for (Map p:params){
            array.add(makeParamBody(p));
        }
        return array;
    }

    public String buildJsonBody(Map params){
        JsonObject query = makeParamBody(params);
        JsonObject body = new JsonObject();
        body.add("queryBody",query);
        return body.toString();
    }
    public String buildJsonBody(String params){
        JSONObject body = new JSONObject();
        try {
            body.put("queryBody",params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return body.toString();
    }

    public String buildJsonBody(Object params){
        Map<String,Object> map=new HashMap<>();
        map.put("queryBody",params);
        return new Gson().toJson(map);
    }

    private JsonObject makeParamBody(Map params){
        Iterator<Map.Entry> iterator = params.entrySet().iterator();
        JsonObject query = new JsonObject();
        Gson gson = new Gson();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            if(entry.getValue() instanceof String){
                query.addProperty(entry.getKey().toString(),entry.getValue().toString());
            }else if(entry.getValue() instanceof Integer){
                query.addProperty(entry.getKey().toString(),(Integer)entry.getValue());
            }else if(entry.getValue() instanceof JsonArray){
                query.add(entry.getKey().toString(),((JsonArray)entry.getValue()));
            }else {
                query.add(entry.getKey().toString(),gson.toJsonTree(entry.getValue()));
            }

        }
        return query;
    }
}

