package com.famlink.frame.widget.pullrecycleview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.famlink.frame.R;
import com.famlink.frame.widget.pullrecycleview.layoutmanager.ILayoutManager;


/**
 * Created by Stay on 5/3/16.
 * Powered by www.stay4it.com
 */
public class PullRecycler extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    public static final int ACTION_PULL_TO_REFRESH = 1;
    public static final int ACTION_LOAD_MORE_REFRESH = 2;
    public static final int ACTION_IDLE = 0;
    private OnRecyclerRefreshListener listener;
    private int mCurrentState = ACTION_IDLE;
    private boolean isLoadMoreEnabled = false;
    private boolean isPullToRefreshEnabled = true;
    private ILayoutManager mLayoutManager;
    private BaseListAdapter adapter;
    private BaseDataBindingAdapter mAdapter;

    public PullRecycler(Context context) {
        super(context);
        setUpView();
    }

    public PullRecycler(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpView();
    }

    public PullRecycler(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpView();
    }

    private void setUpView() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_pull_to_refresh, this, true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
//                if (layoutManager.getChildCount() > 0 && newState == mRecyclerView.SCROLL_STATE_IDLE) {
////                            //加载更多
////                    loadMore.onLoadMore();
//////                Toast.makeText(context, "上拉加载数据", Toast.LENGTH_SHORT).show();
//                    mCurrentState = ACTION_LOAD_MORE_REFRESH;
//                    if(adapter == null){
//                        mAdapter.onLoadMoreStateChanged(true);
//                    }else if(mAdapter == null)
//                    {
//                        adapter.onLoadMoreStateChanged(true);
//                    }
//
//                    mSwipeRefreshLayout.setEnabled(false);
//                    listener.onRefresh(ACTION_LOAD_MORE_REFRESH);

                if (mCurrentState == ACTION_IDLE && isLoadMoreEnabled && checkIfNeedLoadMore()) {
                    mCurrentState = ACTION_LOAD_MORE_REFRESH;
                    if(adapter == null){
                        mAdapter.onLoadMoreStateChanged(true);
                    }else if(mAdapter == null)
                    {
                        adapter.onLoadMoreStateChanged(true);
                    }

                    mSwipeRefreshLayout.setEnabled(false);
                    listener.onRefresh(ACTION_LOAD_MORE_REFRESH);
                }
        }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                if (mCurrentState == ACTION_IDLE && isLoadMoreEnabled && checkIfNeedLoadMore()) {
//                    mCurrentState = ACTION_LOAD_MORE_REFRESH;
//                    if(adapter == null){
//                        mAdapter.onLoadMoreStateChanged(true);
//                    }else if(mAdapter == null)
//                    {
//                        adapter.onLoadMoreStateChanged(true);
//                    }
//
//                    mSwipeRefreshLayout.setEnabled(false);
//                    listener.onRefresh(ACTION_LOAD_MORE_REFRESH);
//                }
            }
        });
    }

    private boolean checkIfNeedLoadMore() {
        int lastVisibleItemPosition = mLayoutManager.findLastVisiblePosition();
        int totalCount = mLayoutManager.getLayoutManager().getItemCount();
        return totalCount - lastVisibleItemPosition <= 1;
    }

    public void enableLoadMore(boolean enable) {
        isLoadMoreEnabled = enable;
    }

    public void enablePullToRefresh(boolean enable) {
        isPullToRefreshEnabled = enable;
        mSwipeRefreshLayout.setEnabled(enable);
    }

    public void setLayoutManager(ILayoutManager manager) {
        this.mLayoutManager = manager;
        mRecyclerView.setLayoutManager(manager.getLayoutManager());
    }
    public RecyclerView getRecyclerView(){
        if(mRecyclerView != null){
            return mRecyclerView;
        }
        return null;
    }
    public void addItemDecoration(RecyclerView.ItemDecoration decoration) {
        if (decoration != null) {
            mRecyclerView.addItemDecoration(decoration);
        }
    }

    public void setAdapter(BaseListAdapter adapter) {
        this.adapter = adapter;
        mRecyclerView.setAdapter(adapter);
        mLayoutManager.setUpAdapter(adapter);
    }

    public void setAdapter(BaseDataBindingAdapter mAdapter) {
        this.mAdapter = mAdapter;
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager.setUpAdapter(mAdapter);
    }

    public void setRefreshing() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    public void setOnRefreshListener(OnRecyclerRefreshListener listener) {
        this.listener = listener;
    }

    @Override
    public void onRefresh() {
        mCurrentState = ACTION_PULL_TO_REFRESH;
        listener.onRefresh(ACTION_PULL_TO_REFRESH);
    }

    public void onRefreshCompleted() {
        switch (mCurrentState) {
            case ACTION_PULL_TO_REFRESH:
                mSwipeRefreshLayout.setRefreshing(false);
                break;
            case ACTION_LOAD_MORE_REFRESH:
                if(adapter == null){
                    mAdapter.onLoadMoreStateChanged(false);
                }else if(mAdapter == null){
                    adapter.onLoadMoreStateChanged(false);
                }


                if (isPullToRefreshEnabled) {
                    mSwipeRefreshLayout.setEnabled(true);
                }
                break;
        }
        mCurrentState = ACTION_IDLE;
    }

    public void setSelection(int position) {
        mRecyclerView.scrollToPosition(position);
    }




    public interface OnRecyclerRefreshListener {
        void onRefresh(int action);
    }
}
