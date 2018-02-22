package com.roy.app.mvptemplate.presentation.view.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.roy.app.mvptemplate.presentation.navigation.Navigator;
import com.roy.app.mvptemplate.presentation.view.ui.BaseView;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Roy on 2018/2/18.
 */

public abstract class BaseActivity<P extends BasePresenter,T extends ViewDataBinding> extends DaggerAppCompatActivity implements BaseView{

    public T mBinding;

    public P presenter;

    @Inject
    protected Navigator navigator;

    protected abstract int getContentViewId();

//    protected abstract BasePresenter getPresenter();

    @Inject
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    public P getPresenter(){
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        if(presenter!=null){
            Log.i("shijc","BaseActivity,onCreate presenter:"+presenter);
            presenter.takeView(this);
        }
    }

    protected int bindView(){
        int layoutId = getContentViewId();
        if(layoutId>0){
            mBinding = DataBindingUtil.setContentView(this,getContentViewId());
        }
        return layoutId;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(presenter!=null){
            presenter.takeView(this);
        }
    }

    @Override
    protected void onDestroy() {
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

    public void addFragment (@NonNull FragmentManager fragmentManager,
                             @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
