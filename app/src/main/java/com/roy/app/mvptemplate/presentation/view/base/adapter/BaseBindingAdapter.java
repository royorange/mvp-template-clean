package com.roy.app.mvptemplate.presentation.view.base.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Roy on 2018/2/12.
 */

public abstract class BaseBindingAdapter<B extends ViewDataBinding> extends BaseRecyclerAdapter {
    public abstract ViewDataHolder onCreateHolder(ViewGroup parent, int viewType);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if(getItemViewType(position) == TYPE_FOOT){
            bindFoot(holder,position);
        }else {
            ViewDataHolder bindingHolder = (ViewDataHolder)holder;
            bind(bindingHolder,position);
            bindingHolder.getBinding().executePendingBindings();
        }
    }

    public void bind(ViewDataHolder<B> holder, int position){
    }

    public void bindFoot(RecyclerView.ViewHolder holder, int position){

    }

}
