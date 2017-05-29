package com.dora.feed.mvp.presenter;

import com.dora.feed.mvp.bean.FaoriteBean;
import com.dora.feed.mvp.model.BaseBridge;
import com.dora.feed.mvp.model.FavoriteModelImpl;
import com.dora.feed.mvp.view.BaseView;
import com.famlink.frame.util.SysoutUtil;
import com.dora.feed.mvp.model.BaseModel;

import java.util.List;

/**
 * 我的收藏
 * Created by admin on 2016/7/18.
 */
public class FaoritePersenterImpl  extends  BasePersenterImpl<BaseView.FavoriteView> implements BasePersenter.FavoritePresenter,BaseBridge.FavoriteData {

    private BaseModel.FavoriteModel favoriteModel;

    public FaoritePersenterImpl(BaseView.FavoriteView view) {
        super(view);
        favoriteModel = new FavoriteModelImpl();

    }

    @Override
    public void addData(List<FaoriteBean> datas) {
        SysoutUtil.out("ddddsdsds");
    }

    @Override
    public void addData(FaoriteBean data) {
        view.setData(data);
    }

    @Override
    public void error(FaoriteBean data) {
        view.netWorkError(data);
    }

    @Override
    public void requestNetWork(int page, String user_id) {
        favoriteModel.netWorkFavorite(this,page,user_id);
    }
}
