package com.famlink.frame.net.biz;


import com.famlink.frame.mvp.bean.BaseResult;

/**
 * Created by Administrator on 2016/6/23.
 */
public class PostMethodRequest<R extends BaseResult> extends Request<R> {


    public PostMethodRequest( Class<R> resultClass, String url) {
        super(1, resultClass, url);
    }
}
