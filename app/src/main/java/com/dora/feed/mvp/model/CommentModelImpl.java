package com.dora.feed.mvp.model;

import com.dora.feed.mvp.bean.CommentBean;
import com.dora.feed.net.ParamsApi;
import com.famlink.frame.net.biz.BaseRequestCallback;

/**
 * Created by admin on 2016/7/13.
 */
public class CommentModelImpl implements BaseModel.CommentModel {
    @Override
    public void netWorkComment(final BaseBridge.CommentData commentData,int page,String user_id) {
        ParamsApi.requestComment(page,user_id).execute(new BaseRequestCallback<CommentBean>() {
            @Override
            public void onRequestSucceed(CommentBean result) {
                commentData.addData(result);
            }

            @Override
            public void onRequestFailed(CommentBean result) {
                commentData.error(result);
            }
        });
    }
}
