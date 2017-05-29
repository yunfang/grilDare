package com.dora.feed.mvp.model;

import com.famlink.frame.net.biz.BaseRequestCallback;
import com.dora.feed.mvp.bean.FaoriteBean;
import com.dora.feed.net.ParamsApi;

/**
 * Created by admin on 2016/7/18.
 */
public class FavoriteModelImpl implements BaseModel.FavoriteModel {

    @Override
    public void netWorkFavorite(final BaseBridge.FavoriteData favoriteData, int page, String user_id) {
        ParamsApi.requestFavorite(page,user_id).execute(new BaseRequestCallback<FaoriteBean>() {
            @Override
            public void onRequestSucceed(FaoriteBean result) {
                favoriteData.addData(result);
            }

            @Override
            public void onRequestFailed(FaoriteBean result) {
                favoriteData.error(result);
            }
        });
    }
}
