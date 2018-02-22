package com.roy.app.mvptemplate.presentation.view.screen.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.roy.app.mvptemplate.R;
import com.roy.app.mvptemplate.databinding.ActivityMainBinding;
import com.roy.app.mvptemplate.presentation.view.base.PureActivity;

import javax.inject.Inject;

/**
 * Created by Roy on 2018/2/18.
 */

public class MainActivity extends PureActivity<ActivityMainBinding>{
    public static final String EXTRA_TITLE = "extra_title";

    public static Intent getCallingIntent(Context context,String content) {
        Intent callingIntent = new Intent(context, MainActivity.class);
        callingIntent.putExtra(EXTRA_TITLE, content);
        return callingIntent;
    }

    @Inject
    MainFragment fragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("shijc","MainActivity,presenter:"+presenter);
        addFragment(getSupportFragmentManager(),fragment,R.id.container);
    }

}
