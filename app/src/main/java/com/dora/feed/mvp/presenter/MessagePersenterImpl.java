package com.dora.feed.mvp.presenter;

import com.dora.feed.mvp.model.MessageModelImpl;
import com.dora.feed.mvp.view.BaseView;
import com.famlink.frame.util.SysoutUtil;
import com.dora.feed.mvp.bean.MessageBean;
import com.dora.feed.mvp.model.BaseBridge;
import com.dora.feed.mvp.model.BaseModel;

import java.util.List;

/**
 * Created by admin on 2016/7/13.
 */
public class MessagePersenterImpl extends BasePersenterImpl<BaseView.MessageView> implements BasePersenter.MessagePresenter,BaseBridge.MessageData{

    private BaseModel.MessageModel messageModel;

    public MessagePersenterImpl(BaseView.MessageView view) {
        super(view);
        messageModel = new MessageModelImpl();
    }


    @Override
    public void addData(List<MessageBean> datas) {
        SysoutUtil.out("ddddsdsds");
    }

    @Override
    public void addData(MessageBean data) {
        view.setData(data);
    }

    @Override
    public void error(MessageBean data) {
        view.netWorkError(data);
    }

    @Override
    public void requestNetWork(int page, String id) {
        messageModel.netWorkMessage(this,page,id);
    }
}
