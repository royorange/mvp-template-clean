package com.roy.app.mvptemplate.presentation.view.base.adapter;

import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.util.ProviderDelegate;

import java.util.List;

/**
 * Created by Roy on 2018/10/2.
 */
public abstract class BaseMultipleTypeAdapter<T extends BaseViewModel,V extends BaseHolder> extends MultipleItemRvAdapter<T,V> {
    public BaseMultipleTypeAdapter(@Nullable List<T> data) {
        super(data);
    }

    public ProviderDelegate getProviderDelegate(){
        return mProviderDelegate;
    }

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        if(getProviderDelegate() == null){
            finishInitialize();
        }
        return super.onCreateViewHolder(parent, viewType);
    }
}
