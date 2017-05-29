package com.dora.feed.mvp.presenter;

import com.dora.feed.mvp.model.BaseBridge;
import com.dora.feed.mvp.view.CommentDetalOtherView;
import com.famlink.frame.mvp.bean.BaseResult;
import com.dora.feed.mvp.model.BaseModel;
import com.dora.feed.mvp.model.CommentDetalOtherModelImpl;

import java.util.List;

/**
 * Created by wangkai on 16/7/19.
 */
public class CommentDetailOtherPersenterImpl extends BasePersenterImpl<CommentDetalOtherView>
        implements BasePersenter.CoomentDetailOtherPresenter, BaseBridge.CommentDetailOtherData {

    private final BaseModel.CommentDetailOtherModel commentDetailModel;
    public CommentDetailOtherPersenterImpl(CommentDetalOtherView view) {
        super(view);
        this.commentDetailModel = new CommentDetalOtherModelImpl();
    }
    @Override
    public void addData(List<BaseResult> datas) {
        view.setOtherData(datas);
    }

    @Override
    public void addData(BaseResult data) {
        view.setOtherData(data);
    }

    @Override
    public void error(BaseResult data) {
        view.error(data);
    }

    @Override
    public void requestNetWorkFavirateClick(String user_id, String article_id, int status, String trace_id) {
        commentDetailModel.requestNetWorkFavirateClick(this, user_id, article_id, status, trace_id);
    }

    @Override
    public void requestNetWorkComment(String user_id, String article_id, String content, String to_discuss_id, String to_user_id, String to_user_name, String trace_id) {
        commentDetailModel.requestCommentForContentOrPerson(this, user_id, article_id, content, to_discuss_id, to_user_id, to_user_name, trace_id);
    }

    @Override
    public void requestNetWorkLikeForContent(String id, int type, String trace_id) {
        commentDetailModel.requestNetWorkLikeForContent(this, id, type, trace_id);
    }

//    @Override
//    public void requestNetWorkLikeForPeople(String id, int status) {
//        commentDetailModel.requestNetWorkLikeForPeople(this, id, status);
//    }

}