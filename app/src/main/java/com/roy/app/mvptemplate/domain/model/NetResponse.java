package com.roy.app.mvptemplate.domain.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Roy on 16/11/17.
 */

public class NetResponse<T> {
    private int status;
    private T results;
    private long timeStamp;
    @SerializedName("errorMessage")
    private String message;
    private int errorCode;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getBody() {
        return results;
    }

    public void setBody(T results) {
        this.results = results;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
