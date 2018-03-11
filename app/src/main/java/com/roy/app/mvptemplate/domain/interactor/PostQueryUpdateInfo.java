package com.roy.app.mvptemplate.domain.interactor;

import com.roy.app.mvptemplate.data.executor.PostExecutionThread;
import com.roy.app.mvptemplate.data.executor.ThreadExecutor;
import com.roy.app.mvptemplate.data.network.NetParam;
import com.roy.app.mvptemplate.domain.model.NetResponse;
import com.roy.app.mvptemplate.domain.model.UpdateInfo;
import com.roy.app.mvptemplate.domain.repository.CommonRepository;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Roy on 2018/2/26.
 */

public class PostQueryUpdateInfo extends UseCase<NetResponse<UpdateInfo>,NetParam>{
    private final CommonRepository repository;


    @Inject
    PostQueryUpdateInfo(CommonRepository repository, ThreadExecutor threadExecutor,
                        PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Single<NetResponse<UpdateInfo>> buildUseCaseForResult(NetParam params) {
        RequestBody body =
                RequestBody.create(MediaType.parse("application/json"), buildJsonBody(params.getMap()));
        return repository.getUpdateInfo(body);
    }

}
