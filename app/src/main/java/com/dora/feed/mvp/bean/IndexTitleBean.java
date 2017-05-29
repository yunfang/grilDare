package com.dora.feed.mvp.bean;

import com.famlink.frame.mvp.bean.BaseResult;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by wangkai on 16/7/11.
 */
public class IndexTitleBean extends BaseResult implements Serializable{
    @SerializedName("versionCode")
    private int id;
    @SerializedName("versionCode")
    private String name;

    public int getId() {
            return id;
        }

    public void setId(int id) {
            this.id = id;
        }

    public String getName() {
            return name;
        }

    public void setName(String name) {
            this.name = name;
        }


}
