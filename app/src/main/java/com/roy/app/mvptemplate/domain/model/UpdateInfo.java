package com.roy.app.mvptemplate.domain.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Roy on 2018/2/26.
 */

public class UpdateInfo {
    @SerializedName("version")
    private int versionCode;
    @SerializedName("downloadURL")
    private String url;
    private int policy;
    private String upText;
    private int minSdkVersion;
    private boolean forceUpdate;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPolicy() {
        return policy;
    }

    public void setPolicy(int policy) {
        this.policy = policy;
    }

    public String getUpText() {
        return upText;
    }

    public void setUptText(String upText) {
        this.upText = upText;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public int getMinSdkVersion() {
        return minSdkVersion;
    }

    public void setMinSdkVersion(int minSdkVersion) {
        this.minSdkVersion = minSdkVersion;
    }
}
