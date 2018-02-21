package com.roy.app.mvptemplate.presentation.view.base;

import com.roy.app.mvptemplate.presentation.view.ui.BaseView;

/**
 * Created by Roy on 2018/2/18.
 */

public class PresenterImpl<T extends BaseView> implements BasePresenter<T>  {

    private T view;

    public T getView(){
        return view;
    }

    @Override
    public void takeView(T view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }
}
