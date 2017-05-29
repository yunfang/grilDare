package com.dora.feed.mvp.bean;


import com.famlink.frame.mvp.bean.BaseResult;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2016/7/28.
 */
public class SearchBeanTip extends BaseResult implements Serializable{

    @SerializedName("data")
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data implements Serializable{

        @SerializedName("suggestion")
        private String suggestion;

        public String getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(String suggestion) {
            this.suggestion = suggestion;
        }
    }


}
