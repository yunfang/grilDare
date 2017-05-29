package com.dora.feed.mvp.bean;

import android.text.TextUtils;

import com.famlink.frame.mvp.bean.BaseResult;
import com.famlink.frame.util.Utils;
import com.google.gson.annotations.SerializedName;
import com.dora.feed.R;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索
 * Created by admin on 2016/7/29.
 */
public class SearchIndexBean extends BaseResult implements Serializable{

    @SerializedName("page_count")
    private int page_count;

    @SerializedName("current_page")
    private String current_page;

    @SerializedName("data")
    private List<Data> data;

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public String getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(String current_page) {
        this.current_page = current_page;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data implements Serializable{

        @SerializedName("id")
        private String id;

        @SerializedName("title")
        private String title;

        @SerializedName("look_num")
        private String look_num;

        @SerializedName("master_img")
        private String master_img;

        @SerializedName("index_name")
        private String index_name;

        @SerializedName("durationinmmss")
        private String durationinmmss;

        @SerializedName("create_time")
        private String create_time;

        @SerializedName("public_url")
        private String public_url;

        @SerializedName("private_url")
        private String private_url;

        @SerializedName("commend_num")
        private String commend_num;//文章点赞数

        public String getPrivate_url() {
            return private_url;
        }

        public void setPrivate_url(String private_url) {
            this.private_url = private_url;
        }

        public String getCommend_num() {
            return commend_num;
        }

        public void setCommend_num(String commend_num) {
            this.commend_num = commend_num;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLook_num() {
            if(TextUtils.isEmpty(look_num)){
                return "0  "+ Utils.getString(Utils.getContext(), R.string.text_reading);
            }else{
                return look_num + "  "+Utils.getString(Utils.getContext(), R.string.text_reading);
            }
        }

        public String getDurationinmmss() {
            return durationinmmss;
        }

        public void setDurationinmmss(String durationinmmss) {
            this.durationinmmss = durationinmmss;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPublic_url() {
            return public_url;
        }

        public void setPublic_url(String public_url) {
            this.public_url = public_url;
        }

        public void setLook_num(String look_num) {
            this.look_num = look_num;
        }

        public String getMaster_img() {
            return master_img;
        }

        public void setMaster_img(String master_img) {
            this.master_img = master_img;
        }

        public String getIndex_name() {
            return index_name;
        }

        public void setIndex_name(String index_name) {
            this.index_name = index_name;
        }
    }
}
