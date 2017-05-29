package com.famlink.frame.widget.pullrecycleview;

import android.support.v7.widget.GridLayoutManager;

/**
 * Created by Stay on 6/3/16.
 * Powered by www.stay4it.com
 */
public class FooterSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
    private BaseListAdapter adapter;
    private BaseDataBindingAdapter mAdapter;
    private int spanCount;

    public FooterSpanSizeLookup(BaseListAdapter adapter, int spanCount) {
        this.adapter = adapter;
        this.spanCount = spanCount;
    }

    public FooterSpanSizeLookup(BaseDataBindingAdapter adapter, int spanCount) {
        this.mAdapter = mAdapter;
        this.spanCount = spanCount;
    }

    @Override
    public int getSpanSize(int position) {
        if (adapter.isLoadMoreFooter(position) || adapter.isSectionHeader(position)) {
            return spanCount;
        }
        if (mAdapter.isLoadMoreFooter(position) || mAdapter.isSectionHeader(position)) {
            return spanCount;
        }
        return 1;
    }
}
