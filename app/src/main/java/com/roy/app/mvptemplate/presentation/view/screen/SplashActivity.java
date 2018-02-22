package com.roy.app.mvptemplate.presentation.view.screen;

import android.os.Bundle;
import android.os.Handler;

import com.roy.app.mvptemplate.R;
import com.roy.app.mvptemplate.databinding.ActivitySplashBinding;
import com.roy.app.mvptemplate.presentation.view.base.PureActivity;

public class SplashActivity extends PureActivity<ActivitySplashBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigator.navigateToMain(SplashActivity.this,"come from splash");
            }
        },2000);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }
}
