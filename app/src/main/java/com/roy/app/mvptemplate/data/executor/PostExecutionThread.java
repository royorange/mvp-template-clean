package com.roy.app.mvptemplate.data.executor;

import io.reactivex.Scheduler;

/**
 * Created by Roy on 2018/2/22.
 */

public interface PostExecutionThread {
    Scheduler getScheduler();
}
