package com.famlink.frame.widget.recycleview;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangkai on 16/6/14.
 */
public class BaseRecyclerView<T> extends RecyclerView{
    private List<T> CardList = new ArrayList<T>();
    public BaseRecyclerView(Context context) {
        super(context);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public List<T> getCardList() {
        return CardList;
    }

    public void setCardList(List<T> CardList) {
        this.CardList = CardList;
    }



}
