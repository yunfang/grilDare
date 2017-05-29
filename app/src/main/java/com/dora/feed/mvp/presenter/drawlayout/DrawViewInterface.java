package com.dora.feed.mvp.presenter.drawlayout;

/**
 * Created by wangkai on 16/6/15.
 */
public interface DrawViewInterface {
    void switchIndexs(boolean isShow);                     //我的首页
    void switchFavorites();                  //我的收藏
    void switchComment();                    //我的评论
    void switchMessage();                   //消息
    void switchShareFriend();               //推荐给好友
    void switchSetting();                    //设置
    void switchFeedback();               //意见反馈
}
