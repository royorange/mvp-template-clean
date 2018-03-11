package com.roy.app.mvptemplate.presentation;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.roy.app.mvptemplate.presentation.di.component.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by Roy on 2018/2/18.
 */

public class MainApplication extends DaggerApplication {
    private RefWatcher refWatcher;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher= setupLeakCanary();
    }

    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }
}
