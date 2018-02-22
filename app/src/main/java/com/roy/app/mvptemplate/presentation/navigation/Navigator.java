package com.roy.app.mvptemplate.presentation.navigation;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;

import com.roy.app.mvptemplate.presentation.view.screen.main.MainActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Roy on 2018/2/22.
 */
@Singleton
public class Navigator {
    @Inject
    public Navigator() {
        //empty
    }

    public void navigateToMain(Activity activity,String content) {
        if (activity != null) {
            Intent intentToLaunch = MainActivity.getCallingIntent(activity, content);
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity);
            activity.startActivity(intentToLaunch, options.toBundle());
        }
    }
}
