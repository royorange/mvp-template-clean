package com.roy.app.mvptemplate.presentation.view.screen.main;

import com.roy.app.mvptemplate.data.cache.ConfigManager;
import com.roy.app.mvptemplate.data.network.NetParam;
import com.roy.app.mvptemplate.domain.interactor.PostQueryUpdateInfo;
import com.roy.app.mvptemplate.domain.model.NetResponse;
import com.roy.app.mvptemplate.domain.model.UpdateInfo;
import com.roy.app.mvptemplate.presentation.view.base.PresenterSingleObserver;
import com.roy.app.mvptemplate.presentation.view.base.RxPresenter;

import javax.inject.Inject;

/**
 * Created by Roy on 2018/2/18.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter{

    @Inject
    PostQueryUpdateInfo getVersion;

    @Inject
    ConfigManager config;

    @Inject
    public MainPresenter() {
    }

    @Override
    public void startTask() {
        addDisposable(getVersion.execute(new PresenterSingleObserver<NetResponse<UpdateInfo>>(this){
            @Override
            public void onSuccess(NetResponse<UpdateInfo> value) {
                getView().setText(value.getBody().getUpText());
            }
        },new NetParam().addParam("locationLabel", "APP:VERSION:CHECK:ANDROID").addParam("memberGroupId", 0)));
    }


}
