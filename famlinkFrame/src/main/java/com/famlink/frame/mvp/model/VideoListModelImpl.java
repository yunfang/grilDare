package com.famlink.frame.mvp.model;

import com.famlink.frame.mvp.bean.RoomBean;
import com.famlink.frame.net.ParamsApi;
import com.famlink.frame.net.biz.BaseRequestCallback;

/**
 * Created by wangkai on 16/6/24.
 */
public class VideoListModelImpl implements BaseModel.VideoListData {


    @Override
    public void netWorkVideoList(int videoId, final BaseBridge.ImageDetailData imageDetailData) {
        ParamsApi.videoStreamUrl(4224212).execute(new BaseRequestCallback<RoomBean>() {
            @Override
            public void onRequestSucceed(RoomBean result) {
                imageDetailData.addData(result);
            }

            @Override
            public void onRequestFailed(RoomBean result) {
                imageDetailData.addData(result);
            }
        });
    }
}
