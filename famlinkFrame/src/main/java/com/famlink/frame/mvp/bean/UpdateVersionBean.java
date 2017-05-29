package com.famlink.frame.mvp.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2016/7/7.
 * 检查升级版本
 */
public class UpdateVersionBean extends  BaseResult implements Serializable {



    @SerializedName("data")
    private List<Data> data;
    @SerializedName("sdata")
    private SData sdata;

    public SData getSdata() {
        return sdata;
    }

    public void setSdata(SData sdata) {
        this.sdata = sdata;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data implements  Serializable{

        @SerializedName("id")
        private String id;

        @SerializedName("version_code")
        private String versionCode;

        @SerializedName("version_name")
        private String versionName;

        @SerializedName("download_url")
        private String downloadUrl;

        @SerializedName("is_foce")
        private String isForce;  // 是否是强制更新     1:强制更新      0:非强制更新

        @SerializedName("platom")
        private String platom;//0：android   1:ios

        @SerializedName("msg")
        private String msg;//更新的信息

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getPlatom() {
            return platom;
        }

        public void setPlatom(String platom) {
            this.platom = platom;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
    public class SData implements Serializable{
        @SerializedName("unread_num")
        private int unread_num;

        public int getUnread_num() {
            return unread_num;
        }

        public void setUnread_num(int unread_num) {
            this.unread_num = unread_num;
        }
    }








}
