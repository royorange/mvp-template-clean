package com.roy.app.mvptemplate.presentation.view.base;

import com.roy.app.mvptemplate.presentation.view.ui.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Roy on 2018/2/18.
 */

public class RxPresenter<T extends BaseView> extends PresenterImpl<T> {
    private CompositeDisposable mCompositeDisposable;

    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    private void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void dropView() {
        super.dropView();
        unSubscribe();
    }
}
