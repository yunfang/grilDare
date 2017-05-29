package com.dora.feed.mvp.bean;

import android.text.TextUtils;

import com.famlink.frame.mvp.bean.BaseResult;
import com.famlink.frame.util.CacheUtils;
import com.famlink.frame.util.DataUtils;
import com.famlink.frame.util.LocalContents;
import com.famlink.frame.util.Utils;
import com.google.gson.annotations.SerializedName;
import com.dora.feed.R;

import java.io.Serializable;
import java.util.List;

/**
 * 我的评论
 * Created by admin on 2016/7/13.
 */
public class CommentBean extends BaseResult implements Serializable{

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

        @SerializedName("did")
        private String did;

        private String login_name;//当前登录者的用户名

        @SerializedName("to_user_name")
        private String to_user_name;  //给谁评论

        @SerializedName("dtime")
        private String dtime;  //评论时间

        @SerializedName("dcontent")
        private String dcontent;  //评论信息

        @SerializedName("thumb_up_num")
        private String thumb_up_num;  //评论点赞数

        @SerializedName("aid")
        private String aid;   //文章id

        @SerializedName("title")
        private String title;//标题

        @SerializedName("master_img")
        private String master_img;  //封面图

        @SerializedName("private_url")
        private String private_url;//视频详情页

        @SerializedName("public_url")
        private String public_url;//阅读全文的url

        @SerializedName("look_num")
        private String look_num;//观看数量

        @SerializedName("commend_num")
        private String commend_num;//文章点赞数

        @SerializedName("atime")
        private String atime;

        public String getCommend_num() {
            return commend_num;
        }

        public void setCommend_num(String commend_num) {
            this.commend_num = commend_num;
        }

        public String getDid() {
            return did;
        }

        public void setDid(String did) {
            this.did = did;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getPublic_url() {
            return public_url;
        }

        public void setPublic_url(String public_url) {
            this.public_url = public_url;
        }

        public String getTo_user_name() {
            return to_user_name;
        }

        public String getLogin_name() {
            return CacheUtils.getInstance().getString(LocalContents.LOGIN_USER_NAME,"");
        }

        public void setLogin_name(String login_name) {
            this.login_name = login_name;
        }

        public void setTo_user_name(String to_user_name) {
            this.to_user_name = to_user_name;
        }

        public String getDtime() {
            if(TextUtils.isEmpty(dtime)){
                return "";
            }else{
                return DataUtils.formatDateForRule(dtime)+"  回复";
            }
        }

        public void setDtime(String dtime) {
            this.dtime = dtime;
        }

        public String getDcontent() {
            return dcontent;
        }

        public void setDcontent(String dcontent) {
            this.dcontent = dcontent;
        }

        public String getThumb_up_num() {
            return thumb_up_num;
        }

        public void setThumb_up_num(String thumb_up_num) {
            this.thumb_up_num = thumb_up_num;
        }

        public String getAid() {
            return aid;
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

        public String getPrivate_url() {
            return private_url;
        }

        public void setPrivate_url(String private_url) {
            this.private_url = private_url;
        }

        public String getAtime() {
            return atime;
        }

        public void setAtime(String atime) {
            this.atime = atime;
        }

        public String getLook_num() {
            if(TextUtils.isEmpty(look_num)){
                return "0  "+ Utils.getString(Utils.getContext(), R.string.text_reading);
            }else{
                return look_num+"  "+Utils.getString(Utils.getContext(), R.string.text_reading);
            }
        }

        public void setLook_num(String look_num) {
            this.look_num = look_num;
        }
    }





}
