package com.famlink.frame.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.famlink.frame.R;
import com.famlink.frame.mvp.bean.CardBean;
import com.famlink.frame.widget.recycleview.CardRecyclerViewBiz;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangkai on 16/6/14.
 */
public class Card2Activity extends BaseActivity implements CardRecyclerViewBiz.OnItemClickListener, CardRecyclerViewBiz.RefreshLoadMore {
    private CardRecyclerViewBiz reclerView;
    //    @ViewInject(R.id.reclerView) CardRecyclerViewBiz reclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        reclerView.isRemoveAll(false);
        reclerView.isShowFootView(false);
        reclerView.setOnItemClickListener(this);
        reclerView.setItemType(TYPE_ITEM_HORIZONTAL);   //设置布局类型 横向或者是竖向    此方法必须要在设置List之前
        reclerView.setCardList(setList());

    }

    @Override
    public int setLayout() {
        return R.layout.activity_card;
    }

    @Override
    public void setInterfaceView() {
        reclerView = (CardRecyclerViewBiz) findViewById(R.id.reclerView);
        reclerView.setLayoutManager(new LinearLayoutManager(this));
        reclerView.setOnItemClickListener(this);
        reclerView.setOnLoadMore(this);
    }

    @Override
    public void setGenericNodataOrNonetwork() {

    }

    public List<CardBean> setList() {
        List<CardBean> mList = new ArrayList<CardBean>();
        for (int i = 0; i < 10; i++) {
            CardBean mCardBean = new CardBean();
            mCardBean.setContent("第二个界面的测试" + i);
            mList.add(mCardBean);
        }
        return mList;
    }

    @Override
    public void setOnItemClick(View view, int position, Object info) {
        System.out.println("dsadasd");
    }

    @Override
    public void onLoadMore() {

    }
}
