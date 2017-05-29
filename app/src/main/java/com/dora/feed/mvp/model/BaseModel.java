package com.dora.feed.mvp.model;

import com.dora.feed.mvp.bean.RoomBean;

/**
 * Created by wangkai on 16/6/24.
 */
public interface BaseModel<T> {
//    void addData(List<T> datas);
//
//    void addData(T data);
//    void error();
//    void netWork(T model);


    interface VideoListData extends BaseModel<RoomBean>
    {
        void netWorkVideoList(int videoId, BaseBridge.ImageDetailData imageDetailData);
    }
    interface TabNewsModel extends BaseModel<BaseBridge.TabIndexsData> {

        void netWorkTabIndexList(BaseBridge.TabIndexsData tabIndexsData);

    }
    interface TabIndexItemModel extends BaseModel<BaseBridge.TabIndexItemsData> {

        void netWorkTabIndexItemList(BaseBridge.TabIndexItemsData tabIndexItemsData);
    }

    interface CommentModel extends BaseModel<BaseBridge.CommentData>{
        void netWorkComment(BaseBridge.CommentData commentData, int page, String id);
    };

    interface MessageModel extends BaseModel<BaseBridge.MessageData>{
        void netWorkMessage(BaseBridge.MessageData messageData, int page, String id);
    };

    interface CommentDetailModel extends BaseModel<BaseBridge.CommentDetailData>{
        //详情页数据获取
        void netWorkCommentDetail(BaseBridge.CommentDetailData CommentDetailData, int page, int id, String trace_id, float bhv_amt);
    };

    interface CommentDetailOtherModel extends BaseModel<BaseBridge.CommentDetailOtherData>{
        //收藏按钮点击操作
        void requestNetWorkFavirateClick(BaseBridge.CommentDetailOtherData CommentDetailData, String user_id, String article_id, int status, String trace_id);
        //评论某条文章
        void requestCommentForContentOrPerson(BaseBridge.CommentDetailOtherData CommentDetailData, String user_id, String article_id, String content, String to_discuss_id, String to_user_id, String to_user_name, String trace_id);
        //评论详情页点赞或者是个人评论的点赞
        void requestNetWorkLikeForContent(BaseBridge.CommentDetailOtherData CommentDetailData, String id, int type, String trace_id);
        //评论点赞
//        void requestNetWorkLikeForPeople(BaseBridge.CommentDetailOtherData CommentDetailData, String id, int status);
    };

    interface FavoriteModel extends BaseModel<BaseBridge.FavoriteData>{
        void netWorkFavorite(BaseBridge.FavoriteData favoriteData, int page, String user_id);
    }
}
