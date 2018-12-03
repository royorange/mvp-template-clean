package com.roy.app.mvptemplate.presentation.view.base.adapter;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by Roy on 2018/10/1.
 */
public class BaseSectionModel<T extends BaseViewModel> extends SectionEntity<T>{
    public T data = t;

    public BaseSectionModel(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public BaseSectionModel(T t) {
        super(t);
    }
}
