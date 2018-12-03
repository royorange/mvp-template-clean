package com.roy.app.mvptemplate.presentation.view.base.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Roy on 2018/10/2.
 */
public abstract class DataBindingMultipleTypeAdapter<T extends BaseViewModel,B extends BindingViewHolder> extends BaseMultipleTypeAdapter<T,B> {
    public DataBindingMultipleTypeAdapter(@Nullable List<T> data) {
        super(data);
    }

    @Override
    protected B createBaseViewHolder(ViewGroup parent, int layoutResId) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutResId, parent,false);
        return (B)new BindingViewHolder(binding);
    }

    @Override
    protected B createBaseViewHolder(View view) {
        ViewDataBinding binding = DataBindingUtil.bind(view);
        return (B)new BindingViewHolder(binding);
    }

}
