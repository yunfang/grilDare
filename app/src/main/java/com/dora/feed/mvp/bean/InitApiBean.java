package com.dora.feed.mvp.bean;

import com.famlink.frame.mvp.bean.BaseResult;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 设备注册
 * Created by admin on 2016/7/20.
 */
public class InitApiBean extends BaseResult implements Serializable{

    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable{

        @SerializedName("id")
        private String id;  //相当于userid

        @SerializedName("user_name")
        private String user_name;

        @SerializedName("device_id")
        private String device_id;  //设备唯一id标示

        @SerializedName("passwd")
        private String passwd;

        @SerializedName("status")
        private String status;

        @SerializedName("update_time")
        private String update_time; //更新时间

        @SerializedName("create_time")
        private String create_time;  //创建时间

        @SerializedName("chanel_id")
        private String chanel_id;  //渠道id

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getPasswd() {
            return passwd;
        }

        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getChanel_id() {
            return chanel_id;
        }

        public void setChanel_id(String chanel_id) {
            this.chanel_id = chanel_id;
        }
    }


}
