package com.famlink.frame.mvp.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/23.
 */
public class BaseResult implements Serializable {

    @SerializedName("code")
    private String mCode;

    @SerializedName("message")
    private String mMessage;

    /** 成功 */
    public final static String OK = "200";

    /** 通用错误 */
    public final static String ERROR = "0";


    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
