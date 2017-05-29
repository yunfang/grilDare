package com.dora.feed.mvp.model;

import com.dora.feed.mvp.bean.CommentBean;
import com.dora.feed.mvp.bean.CommentDetailBean;
import com.dora.feed.mvp.bean.IndexTitleBean;
import com.famlink.frame.mvp.bean.BaseResult;
import com.dora.feed.mvp.bean.FaoriteBean;
import com.dora.feed.mvp.bean.MessageBean;
import com.dora.feed.mvp.bean.RoomBean;
import com.dora.feed.mvp.bean.IndexItemBean;

import java.util.List;

/**
 * Created by wangkai on 16/6/24.
 */
public interface BaseBridge<T> {
    void addData(List<T> datas);
    void addData(T data);

    void error(T data);

    interface ImageDetailData extends BaseBridge<RoomBean> {
    }
    interface TabIndexsData extends BaseBridge<IndexTitleBean> {

    }
    interface TabIndexItemsData extends BaseBridge<IndexItemBean> {

    }
    interface CommentData extends BaseBridge<CommentBean>{};

    interface MessageData extends BaseBridge<MessageBean>{};

    interface CommentDetailData extends BaseBridge<CommentDetailBean>{};

    interface CommentDetailOtherData extends BaseBridge<BaseResult>{};

    interface FavoriteData extends  BaseBridge<FaoriteBean>{};

}
