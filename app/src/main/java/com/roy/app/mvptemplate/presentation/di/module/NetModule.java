package com.roy.app.mvptemplate.presentation.di.module;

import com.roy.app.mvptemplate.data.network.ApiService;
import com.roy.app.mvptemplate.domain.repository.CommonRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Roy on 2018/3/11.
 */

@Module
public class NetModule {
    @Singleton
    @Provides
    public CommonRepository provideCartApi(ApiService service){
        return service.api(CommonRepository.class);
    }
}
