package com.dora.feed.mvp.presenter;

import com.dora.feed.mvp.model.BaseBridge;
import com.dora.feed.mvp.view.BaseView;
import com.dora.feed.mvp.bean.RoomBean;
import com.dora.feed.mvp.model.BaseModel;
import com.dora.feed.mvp.model.VideoListModelImpl;

import java.util.List;

/**
 * Created by wangkai on 16/6/24.
 */
public class VideoListPersenterImpl extends BasePersenterImpl<BaseView.VideoListView> implements BasePersenter.VideoListPer, BaseBridge.ImageDetailData {
    private BaseModel.VideoListData videoListData;

    public VideoListPersenterImpl(BaseView.VideoListView view) {
        super(view);
        videoListData = new VideoListModelImpl();
    }

    @Override
    public void requestNetWork(int videoId) {
        videoListData.netWorkVideoList(videoId, this);
    }


    @Override
    public void addData(List<RoomBean> datas) {

    }

    @Override
    public void addData(RoomBean data) {
        view.setData(data);
    }

    @Override
    public void error(RoomBean data) {

    }
}
