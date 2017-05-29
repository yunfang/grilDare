package com.famlink.frame.mvp.model;

import com.famlink.frame.mvp.bean.RoomBean;

/**
 * Created by wangkai on 16/6/24.
 */
public interface BaseModel<T> {
//    void addData(List<T> datas);
//
//    void addData(T data);
//    void error();


    interface VideoListData extends BaseModel<RoomBean>
    {
        void netWorkVideoList(int videoId, BaseBridge.ImageDetailData imageDetailData);
    }
}
