package com.famlink.frame.mvp.model;

import com.famlink.frame.mvp.bean.RoomBean;

import java.util.List;

/**
 * Created by wangkai on 16/6/24.
 */
public interface BaseBridge<T> {
    void addData(List<T> datas);
    void addData(T data);

    void error();

    interface ImageDetailData extends BaseBridge<RoomBean> {
    }

}
