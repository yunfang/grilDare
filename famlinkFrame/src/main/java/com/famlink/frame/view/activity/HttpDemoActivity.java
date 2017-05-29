package com.famlink.frame.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.famlink.frame.R;
import com.famlink.frame.mvp.bean.CardBean;
import com.famlink.frame.mvp.bean.RoomBean;
import com.famlink.frame.mvp.presenter.VideoListPersenterImpl;
import com.famlink.frame.mvp.view.BaseView;
import com.famlink.frame.util.NetUtils;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.widget.recycleview.CardRecyclerViewBiz;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wangkai on 16/6/14.
 */
public class HttpDemoActivity extends BaseActivity implements BaseView.VideoListView, CardRecyclerViewBiz.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, CardRecyclerViewBiz.RefreshLoadMore, BaseActivity.NoDataNetWorkChangeRefreshListener {
//    @ViewInject(R.id.swipe_refresh_widget)
//    SwipeRefreshLayout swipe_refresh_widget;
//    @ViewInject(R.id.reclerView)
//    CardRecyclerViewBiz reclerView;


    private VideoListPersenterImpl mVideo;
    private SwipeRefreshLayout swipe_refresh_widget;
    private CardRecyclerViewBiz reclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        swipe_refresh_widget.setColorSchemeResources(R.color.primary,
                R.color.primary_dark, R.color.primary_light,
                R.color.accent);
        swipe_refresh_widget.setOnRefreshListener(this);
        reclerView.setLayoutManager(new LinearLayoutManager(this));



        mVideo = new VideoListPersenterImpl(this);

        reclerView.setItemType(TYPE_ITEM_VERTICAL);   //设置布局类型 横向或者是竖向    此方法必须要在设置List之前
        reclerView.setCardList(setList());
//        mCardRecyclerView.isRemoveAll(true);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_card;
    }

    @Override
    public void setInterfaceView() {
        swipe_refresh_widget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        reclerView = (CardRecyclerViewBiz) findViewById(R.id.reclerView);

        reclerView.setOnItemClickListener(this);
        reclerView.setOnLoadMore(this);
    }

    @Override
    public void setGenericNodataOrNonetwork() {
        setChangeRefresh(this);
        genericNoData(false, "", "");
        genericNoNetwork(true);
        genericNoNetworkSetting(true);

        if(!NetUtils.isNetworkConnected()){
            showGenericNoNetwork();
        }

    }

    public List<CardBean> setList() {
        List<CardBean> mList = new ArrayList<CardBean>();
        for (int i = 0; i < 15; i++) {
            CardBean mCardBean = new CardBean();
            mCardBean.setContent("北京市望京soho 1025室\n2016-10-10 20：10：20" + i);
            mList.add(mCardBean);
        }
        return mList;
    }

    @Override
    public void setOnItemClick(View view, int position, Object info) {
        ToastUtils.showToast("sssssssssssssssss");

    }

    /**
     * 下拉刷新数据
     */
    @Override
    public void onRefresh() {
        reclerView.isRemoveAll(true);
        reclerView.setCardList(setList());
        mVideo.requestNetWork(4224212);
    }

    /**
     * 加载更多数据
     */
    @Override
    public void onLoadMore() {
        reclerView.isShowFootView(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                reclerView.setCardList(setList());
                reclerView.isShowFootView(false);
            }
        }, 2000);
        ToastUtils.showToast("加载更多");
    }

    @Override
    public void onChangeRefresh() {

    }

    @Override
    public void setDatas(List<RoomBean> datas) {

    }

    @Override
    public void setData(RoomBean data) {
        System.out.println(data.toString());
    }
}