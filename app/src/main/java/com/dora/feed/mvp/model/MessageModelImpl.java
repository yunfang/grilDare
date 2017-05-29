package com.dora.feed.mvp.model;

import com.famlink.frame.net.biz.BaseRequestCallback;
import com.dora.feed.mvp.bean.MessageBean;
import com.dora.feed.net.ParamsApi;

/**
 * Created by admin on 2016/7/13.
 */
public class MessageModelImpl implements BaseModel.MessageModel {
    @Override
    public void netWorkMessage(final BaseBridge.MessageData messageData, int page, String user_id) {
        ParamsApi.requestMessage(page, user_id).execute(new BaseRequestCallback<MessageBean>() {
            @Override
            public void onRequestSucceed(MessageBean result) {
                messageData.addData(result);
            }

            @Override
            public void onRequestFailed(MessageBean result) {
                messageData.error(result);
            }
        });
    }
}
