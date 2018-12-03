package com.roy.app.mvptemplate.presentation.view.base;

/**
 * Created by Roy on 2018/2/18.
 */

public interface BaseView {
    void showLoading();

    void hideLoading();

    void showMessage(int msgId);

    void showMessage(String msg);

    void showNetworkError();

    void showServerError();

    void showAuthorizedError();
}
