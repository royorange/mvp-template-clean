package com.roy.app.mvptemplate.presentation.view.base.adapter;

import android.databinding.ViewDataBinding;

/**
 * Created by Roy on 16/7/25.
 */
public class BindingViewHolder<T extends ViewDataBinding> extends BaseHolder {

    protected final T mBinding;

    public BindingViewHolder(T binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public T getBinding() {
        return mBinding;
    }
}
