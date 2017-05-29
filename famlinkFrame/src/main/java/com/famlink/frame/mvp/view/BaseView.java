package com.famlink.frame.mvp.view;



import com.famlink.frame.mvp.bean.RoomBean;

import java.util.List;

/**
 * Created by wangkai on 16/6/23.
 */
public interface BaseView<T> {

    public void setDatas(List<T> datas);

    public void setData(T data);

    interface VideoListView extends BaseView<RoomBean> {
//        public void setData(T data);
    }
}
