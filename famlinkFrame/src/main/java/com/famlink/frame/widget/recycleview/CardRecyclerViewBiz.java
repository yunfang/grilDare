package com.famlink.frame.widget.recycleview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;


import com.famlink.frame.mvp.bean.CardBean;
import com.famlink.frame.view.adapter.BaseRecyclerViewAdapter;
import com.famlink.frame.view.adapter.CardAdapter;

import java.util.List;

/**
 * Created by wangkai on 16/6/14.
 */
public class CardRecyclerViewBiz extends BaseRecyclerView implements BaseRecyclerViewAdapter.OnItemBaseClickListener<CardBean> {
    private Context context;
    private CardAdapter mAdapter;


    public int itemType;


    public CardRecyclerViewBiz(Context context) {
        super(context);
    }

    public CardRecyclerViewBiz(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.addOnScrollListener(mOnScrollListener);
        mAdapter = new CardAdapter(context, null);

        this.setAdapter(mAdapter);
    }

    @Override
    public void setCardList(List CardList) {
        super.setCardList(CardList);
        setBaseAdapter(CardList);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setTYPE_ITEM(getItemType());
    }
    public void setBaseAdapter(List mList)
    {
        mAdapter.addAll(mList);
    }
    /**
     * Item的点击事件
     * @param view
     * @param position
     * @param info
     */

    @Override
    public void onItemClick(View view, int position, CardBean info) {

        onItemClickListener.setOnItemClick(view, position, info);
    }

    @Override
    public void onItemLongClick(View view, int position, CardBean info) {

    }

    /**
     * 是否删除数据
     * @param isShow
     */
    public void isRemoveAll(boolean isShow){
        if(isShow){
            mAdapter.removeAll();
        }
    }

    /**
     * 是否显示底部上拉刷新  true:显示 false:不显示
     */
    public void isShowFootView(boolean isShow){
        mAdapter.isShowFooter(isShow);

    }
    public enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }
    private LAYOUT_MANAGER_TYPE layoutManagerType;
    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;
    private OnScrollListener mOnScrollListener = new OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LayoutManager layoutManager = getLayoutManager();
            if (layoutManagerType == null) {
                if (layoutManager instanceof LinearLayoutManager) {
                    layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
                } else {
                    throw new RuntimeException(
                            "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
                }
            }

            switch (layoutManagerType) {
                case LINEAR:
                    lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    break;
                case GRID:
                    lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    break;
                case STAGGERED_GRID:
                    StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    if (lastPositions == null) {
                        lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                    }
                    staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                    lastVisibleItemPosition = findMax(lastPositions);
                    break;
            }
        }
        private int findMax(int[] lastPositions) {
            int max = lastPositions[0];
            for (int value : lastPositions) {
                if (value > max) {
                    max = value;
                }
            }
            return max;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            LayoutManager layoutManager = getLayoutManager();
            if (layoutManager.getChildCount() > 0 && newState == SCROLL_STATE_IDLE  && lastVisibleItemPosition + 1 == layoutManager.getItemCount()) {
                //加载更多
                loadMore.onLoadMore();
//                Toast.makeText(context, "上拉加载数据", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    /**
     * 加载更多接口
     */
    public RefreshLoadMore loadMore;

    public void setOnLoadMore(RefreshLoadMore loadMore) {
        this.loadMore = loadMore;
    }

    public interface RefreshLoadMore
    {
        void onLoadMore();
    }

    /**
     * 列表点击事件接口
     */
    public interface OnItemClickListener {
        void setOnItemClick(View view, int position, Object info);
    }
    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {

        this.onItemClickListener = onItemClickListener;
    }

}
