package com.roy.app.mvptemplate.presentation.view.base.adapter;

import com.roy.app.mvptemplate.presentation.view.base.adapter.BaseViewModel;

/**
 * Created by Roy on 2018/10/1.
 */
public class SelectableModel extends BaseViewModel{
    protected boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
