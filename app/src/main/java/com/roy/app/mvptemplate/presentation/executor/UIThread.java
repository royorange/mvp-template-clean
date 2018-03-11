package com.roy.app.mvptemplate.presentation.executor;

import com.roy.app.mvptemplate.data.executor.PostExecutionThread;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Roy on 2018/2/22.
 */
@Singleton
public class UIThread implements PostExecutionThread{
    @Inject
    UIThread() {}

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
