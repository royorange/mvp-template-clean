package com.roy.app.mvptemplate.presentation.di.component;

import android.app.Application;

import com.roy.app.mvptemplate.presentation.MainApplication;
import com.roy.app.mvptemplate.presentation.di.module.ActivityBindingModule;
import com.roy.app.mvptemplate.presentation.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Roy on 2018/2/18.
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<MainApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}