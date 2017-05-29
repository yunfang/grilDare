package com.dora.feed.mvp.presenter;

import com.dora.feed.mvp.bean.CommentBean;
import com.dora.feed.mvp.model.BaseBridge;
import com.dora.feed.mvp.model.CommentModelImpl;
import com.dora.feed.mvp.view.BaseView;
import com.famlink.frame.util.SysoutUtil;
import com.dora.feed.mvp.model.BaseModel;

import java.util.List;

/**
 * Created by admin on 2016/7/13.
 */
public class CommentPersenterImpl extends BasePersenterImpl<BaseView.CommentView> implements BasePersenter.CommentPresenter,BaseBridge.CommentData {

    private BaseModel.CommentModel commentModel;

    public CommentPersenterImpl(BaseView.CommentView view) {
        super(view);
        commentModel = new CommentModelImpl();
    }


    @Override
    public void addData(List<CommentBean> datas) {
        SysoutUtil.out("ddddsdsds");
    }

    @Override
    public void addData(CommentBean data) {
        view.setData(data);
    }

    @Override
    public void error(CommentBean data) {
        view.netWorkError(data);
    }

    @Override
    public void requestNetWork(int page, String id) {
        commentModel.netWorkComment(this,page,id);
    }
}
