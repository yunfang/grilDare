package com.famlink.frame.mvp.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public class RoomBean extends BaseResult implements Serializable {

    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable{
        @SerializedName("cartSum")
        private String cartSum;

        @SerializedName("list")
        private List<ListData> list;

        @SerializedName("orderSum")
        private String orderSum;

        public class ListData implements Serializable{
            @SerializedName("dateStr")
            private String mdataStr;

            @SerializedName("voList")
            private List<VoList> voLists;

            public class VoList implements  Serializable{

                @SerializedName("browsNum")
                private int nbrowsNum;

                @SerializedName("createTime")
                private String mcreateTime;

                @SerializedName("curTradNum")
                private int mcurTradNum;

                @SerializedName("deliveryValue")
                private String deliveryValue;

                @SerializedName("detailList")
                private List<detailList> mdetailList;

                @SerializedName("endTime")
                private String mendTime;

                @SerializedName("fansNum")
                private int mfansNum;

                @SerializedName("groupId")
                private String mgroupId;

                @SerializedName("id")
                private int mid;

                @SerializedName("isFocus")
                private String misFocus;

                @SerializedName("isRemind")
                private String isRemind;

                @SerializedName("language")
                private String language;

                @SerializedName("latitude")
                private String latitude;

                @SerializedName("location")
                private String location;

                @SerializedName("longitude")
                private String longitude;

                @SerializedName("praiseValue")
                private String praiseValue;

                @SerializedName("previewNum")
                private int previewNum;

                @SerializedName("recommenValue")
                private String recommenValue;

                @SerializedName("roomLogo")
                private String roomLogo;

                @SerializedName("roomNo")
                private String roomNo;

                @SerializedName("roomTitle")
                private String roomTitle;

                @SerializedName("roomType")
                private String roomType;

                @SerializedName("sellerHeadIcon")
                private String sellerHeadIcon;

                @SerializedName("sellerId")
                private String sellerId;

                @SerializedName("sellerNick")
                private String sellerNick;

                @SerializedName("startTime")
                private String startTime;

                @SerializedName("tradNum")
                private int tradNum;

                @SerializedName("watchNum")
                private int watchNum;

                public int getNbrowsNum() {
                    return nbrowsNum;
                }

                public void setNbrowsNum(int nbrowsNum) {
                    this.nbrowsNum = nbrowsNum;
                }

                public String getMcreateTime() {
                    return mcreateTime;
                }

                public void setMcreateTime(String mcreateTime) {
                    this.mcreateTime = mcreateTime;
                }

                public class detailList implements Serializable{

                }

                public int getMcurTradNum() {
                    return mcurTradNum;
                }

                public void setMcurTradNum(int mcurTradNum) {
                    this.mcurTradNum = mcurTradNum;
                }
            }
        }

        public List<ListData> getList() {
            return list;
        }

        public void setList(List<ListData> list) {
            this.list = list;
        }

        public String getCartSum() {
            return cartSum;
        }

        public void setCartSum(String cartSum) {
            this.cartSum = cartSum;
        }
    }
}
