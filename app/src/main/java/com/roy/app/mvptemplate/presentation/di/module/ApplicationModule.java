package com.roy.app.mvptemplate.presentation.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.roy.app.mvptemplate.data.LocalConfig;
import com.roy.app.mvptemplate.data.executor.JobExecutor;
import com.roy.app.mvptemplate.data.executor.PostExecutionThread;
import com.roy.app.mvptemplate.data.executor.ThreadExecutor;
import com.roy.app.mvptemplate.data.network.CookieStore;
import com.roy.app.mvptemplate.data.network.SharedPreferencesCookieStore;
import com.roy.app.mvptemplate.presentation.executor.UIThread;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Roy on 2018/2/18.
 */
/**
 * This is a Dagger module. We use this to bind our Application class as a Context in the AppComponent
 * By using Dagger Android we do not need to pass our Application instance to any module,
 * we simply need to expose our Application as Context.
 * One of the advantages of Dagger.Android is that your
 * Application & Activities are provided into your graph for you.
 * {@link
 * com.roy.app.mvptemplate.presentation.di.component.AppComponent}.
 */
@Module
public abstract class ApplicationModule {
    //expose Application as an injectable context
    @Binds
    abstract Context bindContext(Application application);

    @Binds
    abstract ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor);

    @Binds
    abstract PostExecutionThread providePostExecutionThread(UIThread uiThread);


    @Provides
    static LocalConfig provideLocalConfig(Application application){
        return LocalConfig.loadConfig(application);
    }

    @Provides
    static SharedPreferences provideSharedPreference(LocalConfig config){
        return config.getSharedPreference();
    }

    @Binds
    abstract CookieStore provideSharedPreferencesCookieStore(SharedPreferencesCookieStore cookieStore);

}
