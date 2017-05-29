package com.famlink.frame.net.biz;


import com.famlink.frame.mvp.bean.BaseResult;

/**
 * Created by Administrator on 2016/6/23.
 */
public interface RequestCallback<R extends BaseResult> {
    /**
     * 当请求成功后回调
     *
     * @param dataResult 数据结果
     */
    public void onRequestSuccess(R dataResult);

    /**
     *
     * 当请求失败后回调
     *
     * @param dataResult 数据结果
     */
    public void onRequestFailure(R dataResult);
}
