package com.roy.app.mvptemplate.presentation.view.base.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Roy on 2018/2/12.
 */

public abstract class DataBindingRecyclerAdapter<T,B extends ViewDataBinding> extends BaseRecyclerViewAdapter<T,BindingViewHolder<B>> {

    public DataBindingRecyclerAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public DataBindingRecyclerAdapter(@Nullable List<T> data) {
        super(data);
    }

    public DataBindingRecyclerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected BindingViewHolder<B> createBaseViewHolder(ViewGroup parent, int layoutResId) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutResId, parent,false);
        return new BindingViewHolder(binding);
    }

}
