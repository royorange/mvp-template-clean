package com.roy.app.mvptemplate.presentation.view.base;

import android.databinding.ViewDataBinding;

/**
 * Created by Roy on 2018/3/1.
 */

public class DatabindingActivity<T extends ViewDataBinding> extends BaseActivity<CommonPresenter,T> {

    @Override
    protected int getContentViewId() {
        return 0;
    }
}
