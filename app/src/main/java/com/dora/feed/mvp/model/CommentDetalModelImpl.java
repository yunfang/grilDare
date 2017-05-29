package com.dora.feed.mvp.model;

import com.dora.feed.mvp.bean.CommentDetailBean;
import com.dora.feed.net.ParamsApi;
import com.famlink.frame.net.biz.BaseRequestCallback;

/**
 * Created by wangkai on 16/7/19.
 */
public class CommentDetalModelImpl implements BaseModel.CommentDetailModel {
    private int page;
    private int id;
    public void CommentDetailModel(){
    }
    @Override
    public void netWorkCommentDetail(final BaseBridge.CommentDetailData CommentDetailData, int page, int id, String trace_id, float bhv_amt) {
        ParamsApi.requestCommentDetail(page,id, trace_id, bhv_amt).execute(new BaseRequestCallback<CommentDetailBean>() {
            @Override
            public void onRequestSucceed(CommentDetailBean result) {
                CommentDetailData.addData(result);
            }

            @Override
            public void onRequestFailed(CommentDetailBean result) {
                CommentDetailData.error(result);
            }
        });
    }
}
