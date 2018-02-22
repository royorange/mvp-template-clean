package com.roy.app.mvptemplate.presentation.view.screen.main;

import com.roy.app.mvptemplate.data.cache.CacheManager;
import com.roy.app.mvptemplate.presentation.view.base.RxPresenter;

import javax.inject.Inject;

/**
 * Created by Roy on 2018/2/18.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter{

    @Inject
    CacheManager config;

    @Inject
    public MainPresenter() {
    }

    @Override
    public void startTask() {
        getView().setText(config.getUserId());
    }

}
