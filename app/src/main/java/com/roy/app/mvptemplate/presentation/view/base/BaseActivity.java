package com.roy.app.mvptemplate.presentation.view.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.roy.app.mvptemplate.R;
import com.roy.app.mvptemplate.data.cache.ConfigManager;
import com.roy.app.mvptemplate.presentation.navigation.Navigator;

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

    @Inject
    protected ConfigManager config;

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
        Snackbar.make(mBinding.getRoot(),msgId,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String msg) {
        Snackbar.make(mBinding.getRoot(),msg,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showNetworkError() {
        Snackbar.make(mBinding.getRoot(),getString(R.string.network_failed),Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showServerError() {
        Snackbar.make(mBinding.getRoot(), R.string.network_failed_server,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showAuthorizedError() {
        config.logout(getApplicationContext());
    }

    public Navigator getNavigator() {
        return navigator;
    }

    public ConfigManager getConfig(){
        return config;
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
