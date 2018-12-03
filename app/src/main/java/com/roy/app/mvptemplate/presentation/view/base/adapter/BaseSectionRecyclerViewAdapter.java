package com.roy.app.mvptemplate.presentation.view.base.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.SectionEntity;

import java.util.List;

/**
 * Created by Roy on 2018/10/1.
 */
public abstract class BaseSectionRecyclerViewAdapter<T extends SectionEntity,H extends BaseViewHolder> extends BaseSectionQuickAdapter<T,H> {

    public BaseSectionRecyclerViewAdapter(int layoutResId, int sectionHeadResId, List<T> data) {
        super(layoutResId, sectionHeadResId, data);
    }
}
