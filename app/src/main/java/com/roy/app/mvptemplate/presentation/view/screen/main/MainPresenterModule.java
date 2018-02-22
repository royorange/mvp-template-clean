package com.roy.app.mvptemplate.presentation.view.screen.main;

import com.roy.app.mvptemplate.presentation.di.scope.ActivityScoped;
import com.roy.app.mvptemplate.presentation.di.scope.FragmentScoped;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Roy on 2018/2/18.
 */
@Module
public abstract class MainPresenterModule {

    @ActivityScoped
    @Binds
    abstract MainContract.Presenter mainPresenter(MainPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MainFragment mainFragment();

    @Provides
    @ActivityScoped
    @Named("title")
    static String provideText(MainActivity activity){
        return activity.getIntent().getStringExtra(MainActivity.EXTRA_TITLE);
    }
}
