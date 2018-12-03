package com.roy.app.mvptemplate.presentation.view.base;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roy.app.mvptemplate.R;
import com.roy.app.mvptemplate.data.cache.ConfigManager;
import com.roy.app.mvptemplate.presentation.view.util.StatusBarUtil;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Created by Roy on 2018/2/18.
 */

public abstract class BaseFragment<P extends BasePresenter,T extends ViewDataBinding> extends DaggerFragment implements BaseView {
    public T mBinding;
    public P presenter;

    boolean isFirstLoad = true;

    protected boolean isSelected;

    private Toolbar toolbar;

    @Inject
    protected ConfigManager config;

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
    public void onDestroyView() {
        super.onDestroyView();
        if(presenter!=null){
            presenter.dropView();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isSelected = isVisibleToUser;
        if(isVisibleToUser){
            if(isFirstLoad){
                onFirstLoadData();
                isFirstLoad = false;
            }else {
                loadData();
            }
        }
    }

    /**
     * called when fragment is visible.(except the first time)
     */
    public void loadData(){}

    /**
     * called when the first time fragment is shown
     */
    public void onFirstLoadData(){}

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
        config.logout(getContext());
    }

    public void setupToolbar(View layout){
        setupToolbar(layout,0,false);
    }

    public void setupToolbar(View layout,int title){
        setupToolbar(layout,title,false);
    }

    public void setupToolbar(View layout,int title, boolean isNeedCustomStatusBar){
        if(title > 0){
            toolbar = layout.findViewById(R.id.toolbar);
            if(toolbar != null){
                toolbar.setTitle(title);
            }
        }
        if(!isNeedCustomStatusBar){
            View statusBar = layout.findViewById(R.id.statusLayout);
            if(statusBar != null){
                statusBar.getLayoutParams().height = StatusBarUtil.getStatusBarHeight(getContext());
                statusBar.requestLayout();
            }
        }
    }
}
