package com.roy.app.mvptemplate.presentation.view.base;

import io.reactivex.observers.ResourceSingleObserver;

/**
 * Created by Roy on 2018/3/11.
 */

public class PresenterSingleObserver<T> extends ResourceSingleObserver<T> {
    private PresenterImpl presenter;

    public PresenterSingleObserver(PresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onSuccess(T value) {

    }

    @Override
    public void onError(Throwable e) {
        if(presenter.getView() == null){
            return;
        }
        presenter.getView().showServerError();
        presenter = null;
    }



}
