package com.roy.app.mvptemplate.presentation.view.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by Roy on 2018/2/12.
 */

public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter {
    public final static int TYPE_DATA = -2;
    public final static int TYPE_FOOT = -1;

    private SparseArray<Object> typedData;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void registerType(int type){
        if(typedData == null){
            typedData = new SparseArray<>();
        }
        typedData.put(type,new Object());
    }

    public boolean isMutiType(){
        return typedData!=null && typedData.size()>1;
    }

}
