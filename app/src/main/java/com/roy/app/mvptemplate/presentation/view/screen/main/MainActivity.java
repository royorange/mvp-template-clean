package com.roy.app.mvptemplate.presentation.view.screen.main;

import android.os.Bundle;
import android.util.Log;

import com.roy.app.mvptemplate.R;
import com.roy.app.mvptemplate.databinding.ActivityMainBinding;
import com.roy.app.mvptemplate.presentation.view.base.BaseActivity;
import com.roy.app.mvptemplate.presentation.view.base.BasePresenter;
import com.roy.app.mvptemplate.presentation.view.base.PureActivity;

import javax.inject.Inject;

/**
 * Created by Roy on 2018/2/18.
 */

//public class MainActivity extends BaseActivity<MainContract.Presenter,ActivityMainBinding> implements MainContract.View{
public class MainActivity extends PureActivity<ActivityMainBinding>{

//    @Inject
//    MainContract.Presenter presenter;

    @Inject
    MainFragment fragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

//    @Override
//    protected MainContract.Presenter getPresenter() {
//        return presenter;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("shijc","MainActivity,presenter:"+presenter);
        addFragment(getSupportFragmentManager(),fragment,R.id.container);
    }

//    @Override
//    public void setText(String text) {
//        Log.i("shijc","MainActivity,setText,presenter:"+presenter);
//    }
}
