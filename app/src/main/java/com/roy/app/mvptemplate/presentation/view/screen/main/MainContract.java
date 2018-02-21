package com.roy.app.mvptemplate.presentation.view.screen.main;

import com.roy.app.mvptemplate.presentation.view.base.BasePresenter;
import com.roy.app.mvptemplate.presentation.view.ui.BaseView;

/**
 * Created by Roy on 2018/2/18.
 */

public class MainContract {
    interface View extends BaseView {
        void setText(String text);
    }

    interface Presenter extends BasePresenter<View> {
        void startTask();
    }
}
