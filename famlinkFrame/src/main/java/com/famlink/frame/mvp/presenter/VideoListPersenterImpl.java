package com.famlink.frame.mvp.presenter;

import com.famlink.frame.mvp.bean.RoomBean;
import com.famlink.frame.mvp.model.BaseBridge;
import com.famlink.frame.mvp.model.BaseModel;
import com.famlink.frame.mvp.model.VideoListModelImpl;
import com.famlink.frame.mvp.view.BaseView;

import java.util.List;

/**
 * Created by wangkai on 16/6/24.
 */
public class VideoListPersenterImpl extends BasePersenterImpl<BaseView.VideoListView> implements BasePersenter.VideoListPer, BaseBridge.ImageDetailData{
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
    public void error() {

    }
}
