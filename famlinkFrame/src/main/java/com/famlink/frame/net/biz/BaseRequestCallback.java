package com.famlink.frame.net.biz;


import com.famlink.frame.mvp.bean.BaseResult;

/**
 * Created by Administrator on 2016/6/23.
 */
public abstract class BaseRequestCallback <R extends BaseResult> implements RequestCallback<R> {
    @Override
    public void onRequestSuccess(R dataResult) {
        onRequestSucceed(dataResult);
    }

    @Override
    public void onRequestFailure(R dataResult) {
        onRequestFailed(dataResult);
    }

    /**
     * 请求成功回调
     *
     * @param result result
     */
    public abstract void onRequestSucceed(R result);
    /**
     * 请求失败回调
     *
     * @param result result
     */
    public abstract void onRequestFailed(R result);
}
