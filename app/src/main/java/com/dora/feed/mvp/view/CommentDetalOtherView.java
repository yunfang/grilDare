package com.dora.feed.mvp.view;

import com.famlink.frame.mvp.bean.BaseResult;
import com.famlink.frame.util.show.T;

import java.util.List;

/**
 * Created by wangkai on 16/7/20.
 */
public interface CommentDetalOtherView{

    void setOtherData(BaseResult data);
    void setOtherData(List<BaseResult> data);
    void error(BaseResult data);
}
