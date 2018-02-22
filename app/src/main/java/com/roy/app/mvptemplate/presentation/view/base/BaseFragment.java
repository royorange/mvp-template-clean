package com.roy.app.mvptemplate.presentation.view.base;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roy.app.mvptemplate.presentation.view.ui.BaseView;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Created by Roy on 2018/2/18.
 */

public abstract class BaseFragment<P extends BasePresenter,T extends ViewDataBinding> extends DaggerFragment implements BaseView {
    public T mBinding;
    public P presenter;

    protected abstract int getContentViewId();

    @Inject
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    public P getPresenter() {
        return presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,getContentViewId(),container,false);
        if(presenter!=null){
            presenter.takeView(this);
            Log.i("shijc","BaseFragment,onCreateView presenter:"+presenter);
        }
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(presenter!=null){
            presenter.takeView(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter!=null){
            presenter.dropView();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(int msgId) {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showNetworkError() {

    }

    @Override
    public void showServerError() {

    }

    @Override
    public void showAuthorizedError() {

    }
}
