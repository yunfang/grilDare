package com.dora.feed.mvp.presenter;

import com.dora.feed.mvp.bean.CommentDetailBean;
import com.dora.feed.mvp.view.BaseView;
import com.dora.feed.mvp.model.BaseBridge;
import com.dora.feed.mvp.model.BaseModel;
import com.dora.feed.mvp.model.CommentDetalModelImpl;

import java.util.List;

/**
 * Created by wangkai on 16/7/19.
 */
public class CommentDetailPersenterImpl extends BasePersenterImpl<BaseView.CommentDetalView>
        implements BasePersenter.CoomentDetailPresenter, BaseBridge.CommentDetailData {

    private final BaseModel.CommentDetailModel commentDetailModel;
    public CommentDetailPersenterImpl(BaseView.CommentDetalView view) {
        super(view);
        this.commentDetailModel = new CommentDetalModelImpl();
    }

    @Override
    public void addData(List<CommentDetailBean> datas) {
        view.setDatas(datas);
    }

    @Override
    public void addData(CommentDetailBean data) {
        view.setData(data);
    }

    @Override
    public void error(CommentDetailBean data) {

    }

    @Override
    public void requestNetWork(int page, int id, String trace_id, float bhv_amt) {
        commentDetailModel.netWorkCommentDetail(this, page, id, trace_id, bhv_amt);
    }
}