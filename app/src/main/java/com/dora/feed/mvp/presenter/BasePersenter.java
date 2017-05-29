package com.dora.feed.mvp.presenter;

/**
 * Created by wangkai on 16/6/24.
 */
public class BasePersenter {

    interface VideoListPer{
        void requestNetWork(int videoId);
    }
    interface TabIndexPresenter {
        void requestNetWork();
    }
    interface TabIndexItemPresenter{
        void requestNetWork();
    }
    interface CommentPresenter{//我的评论
        void requestNetWork(int page, String id);
    }
    interface MessagePresenter{//消息
        void requestNetWork(int page, String id);
    }

    interface CoomentDetailPresenter{
        //详情页获取评论信息
        void requestNetWork(int page, int id, String trace_id, float hbv_amt);
    }
    interface CoomentDetailOtherPresenter{

        //收藏按钮点击操作
        void requestNetWorkFavirateClick(String user_id, String article_id, int status, String trace_id);
        //评论某条文章
        void requestNetWorkComment(String user_id, String article_id, String content, String to_discuss_id, String to_user_id, String to_user_name, String trace_id);
        //评论详情页点赞
        void requestNetWorkLikeForContent(String id, int type, String trace_id);
        //评论点赞
//        void requestNetWorkLikeForPeople(String id, int status);

    }
    interface FavoritePresenter{
        void requestNetWork(int page, String user_id);
    }
}
