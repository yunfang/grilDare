package com.famlink.frame.net.biz;


import com.famlink.frame.mvp.bean.BaseResult;

/**
 * Created by Administrator on 2016/6/23.
 */
public class GetMethodRequest<R extends BaseResult> extends Request<R> {


    public GetMethodRequest(Class<R> resultClass, String url) {
        super(0, resultClass, url);
    }

}
