package com.roy.app.mvptemplate.domain.repository;

import com.roy.app.mvptemplate.domain.model.NetResponse;
import com.roy.app.mvptemplate.domain.model.UpdateInfo;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Roy on 2018/2/26.
 */

public interface CommonRepository {
    @POST("/v1/marketing/MktAppVersionCheckController/appIFVersionCheck")
    Single<NetResponse<UpdateInfo>> getUpdateInfo(@Body RequestBody params);
}
