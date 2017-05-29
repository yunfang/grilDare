package com.dora.feed.mvp.view;


import com.dora.feed.mvp.bean.CommentBean;
import com.dora.feed.mvp.bean.CommentDetailBean;
import com.dora.feed.mvp.bean.FaoriteBean;
import com.dora.feed.mvp.bean.IndexTitleBean;
import com.dora.feed.mvp.bean.MessageBean;
import com.dora.feed.mvp.bean.RoomBean;
import com.dora.feed.mvp.bean.IndexItemBean;

import java.util.List;

/**
 * Created by wangkai on 16/6/23.
 */
public interface BaseView<T> {

    public void setDatas(List<T> datas);

    public void setData(T data);

    public void netWorkError(T data);

    interface VideoListView extends BaseView<RoomBean> {
//        public void setData(T data);
    }
    interface TabIndexTitleView extends BaseView<IndexTitleBean> {    //分类标题的接口

    }
    interface TabIndexItemView extends BaseView<IndexItemBean>{    //分类列表中的接口

    }
    interface CommentView extends  BaseView<CommentBean>{};   //我的评论接口

    interface MessageView extends BaseView<MessageBean>{};     //消息接口

    interface CommentDetalView extends  BaseView<CommentDetailBean>{};   //详情页面我的评论接口

    interface FavoriteView extends BaseView<FaoriteBean>{};//我的收藏

}
