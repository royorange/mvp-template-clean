package com.roy.app.mvptemplate.presentation.view.base.adapter;

import android.databinding.ViewDataBinding;

/**
 * Created by Roy on 17/2/5.
 */

public class ViewDataHolder<T extends ViewDataBinding> extends BindingViewHolder<T> {
    public ViewDataHolder(T binding) {
        super(binding);
    }
}
