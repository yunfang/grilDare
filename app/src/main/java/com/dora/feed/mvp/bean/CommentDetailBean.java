package com.dora.feed.mvp.bean;

import android.text.TextUtils;

import com.famlink.frame.mvp.bean.BaseResult;
import com.famlink.frame.util.DataUtils;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangkai on 16/7/19.
 */
public class CommentDetailBean extends BaseResult implements Serializable{
    @SerializedName("total")
    private int total;
    @SerializedName("page_count")
    private int page_count;
    @SerializedName("current_page")
    private int current_page;
    @SerializedName("data")
    private List<Data> data;

    public class Data implements Serializable{
        @SerializedName("did")
        private int did;
        @SerializedName("uid")
        private String uid;
        @SerializedName("to_discuss_id")
        private String to_discuss_id;
        @SerializedName("to_user_id")
        private String to_user_id;
        @SerializedName("to_user_name")
        private String to_user_name;
        @SerializedName("dtime")
        private String dtime;
        @SerializedName("content")
        private String content;
        @SerializedName("thumb_up_num")
        private String thumb_up_num;
        @SerializedName("floor_num")
        private String floor_num;
        @SerializedName("nick")
        private String nick;
        @SerializedName("tc")
        private String tc;
        @SerializedName("head_icon")
        private String head_icon;

        public int getDid() {
            return did;
        }

        public void setDid(int did) {
            this.did = did;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getTo_discuss_id() {
            return to_discuss_id;
        }

        public void setTo_discuss_id(String to_discuss_id) {
            this.to_discuss_id = to_discuss_id;
        }

        public String getTo_user_id() {
            return to_user_id;
        }

        public void setTo_user_id(String to_user_id) {
            this.to_user_id = to_user_id;
        }

        public String getTo_user_name() {
            if(TextUtils.isEmpty(to_user_name)){
                return "";
            }else{
                return to_user_name+": ";
            }
        }

        public void setTo_user_name(String to_user_name) {
            this.to_user_name = to_user_name;
        }

        public String getDtime() {
            if(TextUtils.isEmpty(dtime)){
                return "";
            }else{
                return DataUtils.formatDateForRule(dtime);
            }
        }

        public void setDtime(String dtime) {
            this.dtime = dtime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getThumb_up_num() {
            return thumb_up_num;
        }

        public void setThumb_up_num(String thumb_up_num) {
            this.thumb_up_num = thumb_up_num;
        }

        public String getFloor_num() {
            return floor_num;
        }

        public void setFloor_num(String floor_num) {
            this.floor_num = floor_num;
        }

        public String getTc() {
            return tc;
        }

        public void setTc(String tc) {
            this.tc = tc;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getHead_icon() {
            return head_icon;
        }

        public void setHead_icon(String head_icon) {
            this.head_icon = head_icon;
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
