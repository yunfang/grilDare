package com.dora.feed.mvp.bean;

import android.text.TextUtils;

import com.famlink.frame.mvp.bean.BaseResult;
import com.famlink.frame.util.DataUtils;
import com.famlink.frame.util.Utils;
import com.google.android.exoplayer.util.Util;
import com.google.gson.annotations.SerializedName;
import com.dora.feed.R;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;

/**
 * 我的消息
 * Created by admin on 2016/7/13.
 */
public class MessageBean extends BaseResult implements Serializable{

    @SerializedName("page_count")
    private int  page_count;

    @SerializedName("current_page")
    private int current_page;

    @SerializedName("data")
    private List<Data> data;

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

    public class Data implements Serializable{

        @SerializedName("uid")
        private String uid;

        @SerializedName("nick")
        private String nick;

        @SerializedName("head_icon")
        private String head_icon;

        @SerializedName("dcontent")
        private String dcontent;

        @SerializedName("aid")
        private String aid;

        @SerializedName("title")
        private String title;

        @SerializedName("master_img")
        private String master_img;

        @SerializedName("look_num")
        private String look_num;

        @SerializedName("acreate")
        private String acreate;

        @SerializedName("private_url")
        private String private_url;

        @SerializedName("commend_num")
        private String commend_num;//文章点赞数

        @SerializedName("ctime")
        private String ctime;

        @SerializedName("did")
        private String did;

        @SerializedName("public_url")
        private String public_url;

        public String getPublic_url() {
            return public_url;
        }

        public void setPublic_url(String public_url) {
            this.public_url = public_url;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getDid() {
            return did;
        }

        public void setDid(String did) {
            this.did = did;
        }

        public String getCtime() {
            if(TextUtils.isEmpty(ctime)){
                return "";

            }else{
                return DataUtils.formatDateForRule(ctime);
            }
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getCommend_num() {
            return commend_num;
        }

        public void setCommend_num(String commend_num) {
            this.commend_num = commend_num;
        }

        public String getPrivate_url() {
            return private_url;
        }

        public void setPrivate_url(String private_url) {
            this.private_url = private_url;
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

        public String getDcontent() {
            return dcontent;
        }

        public void setDcontent(String dcontent) {
            this.dcontent = dcontent;
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

        public String getMaster_img() {
            return master_img;
        }

        public void setMaster_img(String master_img) {
            this.master_img = master_img;
        }

        public String getLook_num() {
            if(TextUtils.isEmpty(look_num)){
                return "0  "+ Utils.getString(Utils.getContext(), R.string.text_reading);
            }else{
                return look_num +"  "+Utils.getString(Utils.getContext(), R.string.text_reading);
            }
        }

        public void setLook_num(String look_num) {
            this.look_num = look_num;
        }

        public String getAcreate() {
            return acreate;
        }

        public void setAcreate(String acreate) {
            this.acreate = acreate;
        }

    }
}
