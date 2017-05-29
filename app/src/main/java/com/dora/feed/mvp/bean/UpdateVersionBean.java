package com.dora.feed.mvp.bean;

import com.famlink.frame.mvp.bean.BaseResult;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2016/7/7.
 * 检查升级版本
 */
public class UpdateVersionBean extends BaseResult implements Serializable {

    @SerializedName("versionCode")
    private String versionCode;

    @SerializedName("versionName")
    private String versionName;

    @SerializedName("downloadUrl")
    private String downloadUrl;

    @SerializedName("isForce")
    private String isForce;  // 是否是强制更新     1:强制更新      0:非强制更新

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getIsForce() {
        return isForce;
    }

    public void setIsForce(String isForce) {
        this.isForce = isForce;
    }
}
