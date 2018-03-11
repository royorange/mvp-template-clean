package com.roy.app.mvptemplate.presentation.di.component;

import android.app.Application;

import com.roy.app.mvptemplate.data.cache.CacheManager;
import com.roy.app.mvptemplate.data.executor.PostExecutionThread;
import com.roy.app.mvptemplate.data.executor.ThreadExecutor;
import com.roy.app.mvptemplate.presentation.MainApplication;
import com.roy.app.mvptemplate.presentation.di.module.ActivityBindingModule;
import com.roy.app.mvptemplate.presentation.di.module.ApplicationModule;
import com.roy.app.mvptemplate.presentation.di.module.NetModule;

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
        NetModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<MainApplication> {

    CacheManager getCacheManager();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
//    ApiService getApiService();

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}