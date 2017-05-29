package com.dora.feed.mvp.presenter;

import com.dora.feed.mvp.bean.IndexTitleBean;
import com.dora.feed.mvp.model.BaseBridge;
import com.dora.feed.mvp.view.BaseView;
import com.dora.feed.mvp.model.BaseModel;
import com.dora.feed.mvp.model.TabIndexTitleModelImpl;

import java.util.List;

/**
 * by 12406 on 2016/5/14.
 */
public class TabIndexTitlePresenterImpl extends BasePersenterImpl<BaseView.TabIndexTitleView>
        implements BasePersenter.TabIndexPresenter, BaseBridge.TabIndexsData {


    private final BaseModel.TabNewsModel tabNewsModel;

    public TabIndexTitlePresenterImpl(BaseView.TabIndexTitleView view) {
        super(view);
        this.tabNewsModel = new TabIndexTitleModelImpl();
    }

    @Override
    public void requestNetWork() {
        tabNewsModel.netWorkTabIndexList(this);
    }

    @Override
    public void addData(List<IndexTitleBean> datas) {
        view.setDatas(datas);
    }

    @Override
    public void addData(IndexTitleBean data) {

    }

    @Override
    public void error(IndexTitleBean data) {
        view.netWorkError(data);
    }
}
