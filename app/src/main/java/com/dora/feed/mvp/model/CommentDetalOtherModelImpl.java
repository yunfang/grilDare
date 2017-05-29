package com.dora.feed.mvp.model;

import com.dora.feed.net.ParamsApi;
import com.famlink.frame.mvp.bean.BaseResult;
import com.famlink.frame.net.biz.BaseRequestCallback;

/**
 * Created by wangkai on 16/7/19.
 */
public class CommentDetalOtherModelImpl implements BaseModel.CommentDetailOtherModel {
    private int page;
    private int id;
    public void CommentDetalOtherModelImpl(){
    }

    @Override
    public void requestNetWorkFavirateClick( final BaseBridge.CommentDetailOtherData commentDetailOtherData, String user_id, String article_id, int status, String trace_id) {
        ParamsApi.requestFavirateClick(user_id, article_id, status, trace_id).execute(new BaseRequestCallback<BaseResult>() {
            @Override
            public void onRequestSucceed(BaseResult result) {
                commentDetailOtherData.addData(result);
            }

            @Override
            public void onRequestFailed(BaseResult result) {
                commentDetailOtherData.error(result);
            }
        });
    }

    @Override
    public void requestCommentForContentOrPerson( final BaseBridge.CommentDetailOtherData commentDetailOtherData, String user_id, String article_id, String content, String to_discuss_id, String to_user_id, String to_user_name, String trace_id) {
        ParamsApi.requestCommentForContentOrPerson(user_id, article_id, content, to_discuss_id, to_user_id, to_user_name, trace_id).execute(new BaseRequestCallback<BaseResult>() {
            @Override
            public void onRequestSucceed(BaseResult result) {
                commentDetailOtherData.addData(result);
            }

            @Override
            public void onRequestFailed(BaseResult result) {
                commentDetailOtherData.error(result);
            }
        });
    }

    @Override
    public void requestNetWorkLikeForContent( final BaseBridge.CommentDetailOtherData commentDetailOtherData, String id, int type, String trace_id) {
        ParamsApi.requestLikeForContent(id, type, trace_id).execute(new BaseRequestCallback<BaseResult>() {
            @Override
            public void onRequestSucceed(BaseResult result) {
                commentDetailOtherData.addData(result);
            }

            @Override
            public void onRequestFailed(BaseResult result) {
                commentDetailOtherData.error(result);
            }
        });
    }

//    @Override
//    public void requestNetWorkLikeForPeople( final BaseBridge.CommentDetailOtherData commentDetailOtherData, final String id, int status) {
//        ParamsApi.requestLikeForPeople(id,status).execute(new BaseRequestCallback<BaseResult>() {
//            @Override
//            public void onRequestSucceed(BaseResult result) {
//                commentDetailOtherData.addData(result);
//            }
//
//            @Override
//            public void onRequestFailed(BaseResult result) {
//                commentDetailOtherData.error(result);
//            }
//        });
//    }
}
