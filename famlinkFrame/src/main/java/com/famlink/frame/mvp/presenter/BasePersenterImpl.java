package com.famlink.frame.mvp.presenter;

/**
 * Created by wangkai on 16/6/24.
 */
public class BasePersenterImpl<T> {
    T view;

    public BasePersenterImpl(T view) {
        this.view = view;
    }
}
