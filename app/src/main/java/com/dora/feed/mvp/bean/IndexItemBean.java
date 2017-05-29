package com.dora.feed.mvp.bean;

import android.content.Context;
import android.text.TextUtils;

import com.famlink.frame.mvp.bean.BaseResult;
import com.famlink.frame.util.Utils;
import com.google.gson.annotations.SerializedName;
import com.dora.feed.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangkai on 16/7/12.
 */
public class IndexItemBean extends BaseResult implements Serializable {
//    //总页数
//    @SerializedName("page_count")
//    private int page_count;
//    //当前页数
//    @SerializedName("current_page")
//    private int current_page;
    //列表数据
    private List<IndexDataBean> data;
    //星座数据
    private IndexStarDataBean sdata;
    public class IndexDataBean implements Serializable{
        @SerializedName("aid")
        private String aid;

        @SerializedName("title")
        private String title;   //标题

        @SerializedName("author")
        private String author;   //作者

        @SerializedName("atime")
        private String atime;  //发布时间

        @SerializedName("public_url")
        private String public_url;  //Url

        @SerializedName("master_img")
        private String master_img;   //图片地址

        @SerializedName("status")
        private int status;      //card类型分类

        @SerializedName("look_num")
        private String look_num;  //阅读量

        @SerializedName("commend_num")
        private String commend_num;

        @SerializedName("trace_id")
        private String trace_id;

        @SerializedName("private_url")
        private String private_url;  //视频URL

        @SerializedName("durationInMMSS")
        private String durationInMMSS;  //视频时间

        @SerializedName("bid")
        private String bid;

        @SerializedName("acate")
        private String acate;

        private boolean isShow;  //文章是否被查看过

        public boolean isShow() {
            return isShow;
        }

        public void setShow(boolean show) {
            isShow = show;
        }

        private String look_num_main_item_four;  //处理layout_fragment_index_main_item_4中符号问题

        public String getLook_num_main_item_four() {
            return look_num_main_item_four;
        }

        public void setLook_num_main_item_four(String look_num_main_item_four) {
            this.look_num_main_item_four = look_num_main_item_four;
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

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getAtime() {
            return atime;
        }

        public void setAtime(String atime) {
            this.atime = atime;
        }

        public String getPrivate_url() {
            return private_url;
        }

        public void setPrivate_url(String private_url) {
            this.private_url = private_url;
        }

        public String getDurationInMMSS() {
            return durationInMMSS;
        }

        public void setDurationInMMSS(String durationInMMSS) {
            this.durationInMMSS = durationInMMSS;
        }

        public String getTrace_id() {
            return trace_id;
        }

        public void setTrace_id(String trace_id) {
            this.trace_id = trace_id;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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
            this.look_num_main_item_four = look_num;
        }

        public String getCommend_num() {
            return commend_num;
        }

        public void setCommend_num(String commend_num) {
            this.commend_num = commend_num;
        }

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }

        public String getAcate() {
            return acate;
        }

        public void setAcate(String acate) {
            this.acate = acate;
        }
    }

    public class IndexStarDataBean implements Serializable{
        @SerializedName("id")
        private String id;
        @SerializedName("star_name")
        private String star_name;
        @SerializedName("all_point")
        private String all_point;
        @SerializedName("health_point")
        private String health_point;
        @SerializedName("love_point")
        private String love_point;
        @SerializedName("work_point")
        private String work_point;
        @SerializedName("money_point")
        private String money_point;
        @SerializedName("lucky_color")
        private String lucky_color;
        @SerializedName("luncky_number")
        private String luncky_number;
        @SerializedName("create_time")
        private String create_time;
        @SerializedName("update_time")
        private String update_time;
        @SerializedName("summary")
        private String summary;
        @SerializedName("qfriend")
        private String qfriend;
        @SerializedName("love_txt")
        private String love_txt;
        @SerializedName("money_txt")
        private String money_txt;
        @SerializedName("work_txt")
        private String work_txt;
        @SerializedName("day_notice")
        private String day_notice;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStar_name() {
            return star_name;
        }

        public void setStar_name(String star_name) {
            this.star_name = star_name;
        }

        public String getAll_point() {
            return all_point;
        }

        public void setAll_point(String all_point) {
            this.all_point = all_point;
        }

        public String getHealth_point() {
            return health_point;
        }

        public void setHealth_point(String health_point) {
            this.health_point = health_point;
        }

        public String getLove_point() {
            return love_point;
        }

        public void setLove_point(String love_point) {
            this.love_point = love_point;
        }

        public String getWork_point() {
            return work_point;
        }

        public void setWork_point(String work_point) {
            this.work_point = work_point;
        }

        public String getMoney_point() {
            return money_point;
        }

        public void setMoney_point(String money_point) {
            this.money_point = money_point;
        }

        public String getLucky_color() {
            return lucky_color;
        }

        public void setLucky_color(String lucky_color) {
            this.lucky_color = lucky_color;
        }

        public String getLuncky_number() {
            return luncky_number;
        }

        public void setLuncky_number(String luncky_number) {
            this.luncky_number = luncky_number;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getQfriend() {
            return qfriend;
        }

        public void setQfriend(String qfriend) {
            this.qfriend = qfriend;
        }

        public String getLove_txt() {
            return love_txt;
        }

        public void setLove_txt(String love_txt) {
            this.love_txt = love_txt;
        }

        public String getMoney_txt() {
            return money_txt;
        }

        public void setMoney_txt(String money_txt) {
            this.money_txt = money_txt;
        }

        public String getWork_txt() {
            return work_txt;
        }

        public void setWork_txt(String work_txt) {
            this.work_txt = work_txt;
        }

        public String getDay_notice() {
            return day_notice;
        }

        public void setDay_notice(String day_notice) {
            this.day_notice = day_notice;
        }
    }

    public IndexStarDataBean getSdata() {
        return sdata;
    }

    public void setSdata(IndexStarDataBean sdata) {
        this.sdata = sdata;
    }

    public List<IndexDataBean> getData() {
        return data;
    }

    public void setData(List<IndexDataBean> data) {
        this.data = data;
    }

//    public int getCurrent_page() {
//        return current_page;
//    }
//
//    public void setCurrent_page(int current_page) {
//        this.current_page = current_page;
//    }
//
//    public int getPage_count() {
//        return page_count;
//    }
//
//    public void setPage_count(int page_count) {
//        this.page_count = page_count;
//    }
}
