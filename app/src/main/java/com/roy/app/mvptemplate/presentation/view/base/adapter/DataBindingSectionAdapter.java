package com.roy.app.mvptemplate.presentation.view.base.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Roy on 2018/2/12.
 */

public abstract class DataBindingSectionAdapter<T extends BaseSectionModel,B extends BindingViewHolder> extends BaseSectionRecyclerViewAdapter<T,B> {

    public DataBindingSectionAdapter(int layoutResId, int sectionHeadResId, List<T> data) {
        super(layoutResId, sectionHeadResId, data);
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
