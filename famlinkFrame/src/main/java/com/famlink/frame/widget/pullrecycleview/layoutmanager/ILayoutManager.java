package com.famlink.frame.widget.pullrecycleview.layoutmanager;

import android.support.v7.widget.RecyclerView;

import com.famlink.frame.widget.pullrecycleview.BaseDataBindingAdapter;
import com.famlink.frame.widget.pullrecycleview.BaseListAdapter;


/**
 * Created by Stay on 5/3/16.
 * Powered by www.stay4it.com
 */
public interface ILayoutManager {
    RecyclerView.LayoutManager getLayoutManager();
    int findLastVisiblePosition();
    void setUpAdapter(BaseListAdapter adapter);
    void setUpAdapter(BaseDataBindingAdapter adapter);
}
