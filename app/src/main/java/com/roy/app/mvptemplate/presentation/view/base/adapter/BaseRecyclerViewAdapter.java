package com.roy.app.mvptemplate.presentation.view.base.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wisburg.finance.app.presentation.view.base.SelectableModel;

import java.util.List;

/**
 * Created by Roy on 2018/10/1.
 */
public abstract class BaseRecyclerViewAdapter<T,H extends BaseHolder> extends BaseQuickAdapter<T,H>{
    private int lastPosition = 0;

    public BaseRecyclerViewAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public BaseRecyclerViewAdapter(@Nullable List<T> data) {
        super(data);
    }

    public BaseRecyclerViewAdapter(int layoutResId) {
        super(layoutResId);
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public void updateSelectView(int newPosition) {
        if (newPosition == lastPosition || newPosition < 0 || getData() == null) {
            return;
        }
        Object target = getData().get(newPosition);
        if (target instanceof SelectableModel) {
            if (lastPosition >= 0) {
                ((SelectableModel) getData().get(lastPosition)).setSelected(false);
                notifyItemChanged(lastPosition);
            }
            lastPosition = newPosition;
            ((SelectableModel) target).setSelected(true);
            notifyItemChanged(newPosition);
        }
    }
}
