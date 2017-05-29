package com.dora.feed.mvp.presenter;

import com.dora.feed.mvp.model.TabIndexItemModelImpl;
import com.dora.feed.mvp.view.BaseView;
import com.dora.feed.mvp.bean.IndexItemBean;
import com.dora.feed.mvp.model.BaseBridge;
import com.dora.feed.mvp.model.BaseModel;

import java.util.List;

/**
 * Created by wangkai on 16/7/12.
 */
public class TabIndexItemPresenterImpl extends BasePersenterImpl<BaseView.TabIndexItemView>
        implements BasePersenter.TabIndexItemPresenter, BaseBridge.TabIndexItemsData {

    private final BaseModel.TabIndexItemModel tabNewsModel;
    public TabIndexItemPresenterImpl(BaseView.TabIndexItemView view,  String ids,  String category, int type, int openStar, String starName, String click_ids) {
        super(view);

        this.tabNewsModel = new TabIndexItemModelImpl(ids, category, type, openStar, starName, click_ids);
    }
    @Override
    public void requestNetWork() {
        tabNewsModel.netWorkTabIndexItemList(this);
    }
    @Override
    public void addData(List<IndexItemBean> datas) {
        view.setDatas(datas);
    }

    @Override
    public void addData(IndexItemBean data) {
        view.setData(data);
    }

    @Override
    public void error(IndexItemBean data) {
        view.netWorkError(data);
    }
}
