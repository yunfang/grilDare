package com.dora.feed.mvp.bean;

import android.text.TextUtils;

import com.famlink.frame.mvp.bean.BaseResult;
import com.famlink.frame.util.Utils;
import com.google.gson.annotations.SerializedName;
import com.dora.feed.R;

import java.io.Serializable;
import java.util.List;

/**
 * 我的收藏
 * Created by admin on 2016/7/18.
 */
public class FaoriteBean extends BaseResult {

    @SerializedName("title")
    private String title;

    @SerializedName("page_count")
    private String page_count;

    @SerializedName("current_page")
    private String current_page;

    @SerializedName("data")
    private List<Data> data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(String current_page) {
        this.current_page = current_page;
    }

    public String getPage_count() {
        return page_count;
    }

    public void setPage_count(String page_count) {
        this.page_count = page_count;
    }

    public class Data implements Serializable{

        @SerializedName("aid")
        private String aid;   //文章id

        @SerializedName("title")
        private String title;  //标题

        @SerializedName("author")
        private String author;   //作者

        @SerializedName("publish_time")
        private String publish_time;  //

        @SerializedName("public_url")
        private String public_url;  //阅读全文的url

        @SerializedName("master_img")
        private String master_img;  //封面

        @SerializedName("look_num")
        private String look_num;  ////观看数量

        @SerializedName("commend_num")
        private String commend_num; //文章点赞数

        @SerializedName("durationInMMSS")
        private String durationInMMSS;  //视频时间

        @SerializedName("private_url")
        private String private_url;//视频的链接地址

        @SerializedName("atime")
        private String atime;//时间

        public String getAtime() {
            return atime;
        }

        public void setAtime(String atime) {
            this.atime = atime;
        }

        public String getDurationInMMSS() {
            return durationInMMSS;
        }

        public void setDurationInMMSS(String durationInMMSS) {
            this.durationInMMSS = durationInMMSS;
        }

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

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public String getPublic_url() {
            return public_url;
        }

        public void setPublic_url(String public_url) {
            this.public_url = public_url;
        }

        public String getMaster_img() {
            return master_img;
        }

        public void setMaster_img(String master_img) {
            this.master_img = master_img;
        }

        public String getLook_num() {
            if(TextUtils.isEmpty(look_num)){
                return "0" + Utils.getString(Utils.getContext(), R.string.text_reading);
            }else{
                return look_num+" "+ Utils.getString(Utils.getContext(), R.string.text_reading);
            }
        }

        public void setLook_num(String look_num) {
            this.look_num = look_num;
        }
    }

}
