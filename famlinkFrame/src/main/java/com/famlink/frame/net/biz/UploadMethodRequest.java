package com.famlink.frame.net.biz;


import com.famlink.frame.mvp.bean.BaseResult;

/**
 * Created by wangkai on 16/6/23.
 */
public class UploadMethodRequest<R extends BaseResult> extends Request<R> {

    public UploadMethodRequest(Class<R> resultClass, String url) {
        super(2, resultClass, url);
    }
}
