package com.dora.feed.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.dora.feed.mvp.bean.IndexTitleBean;
import com.dora.feed.view.fragment.IndexMainFragment;

import java.util.List;

/**
 * by 12406 on 2016/5/14.
 */
public class IndexTitleAdapter extends BaseFragmentPagerAdapter<IndexTitleBean> {

    private List<IndexTitleBean> mDatas;
    public IndexTitleAdapter(FragmentManager fm, List<IndexTitleBean> mDatas) {
        super(fm, mDatas);
        this.mDatas = mDatas;
    }

    @Override
    protected Fragment getFragmentItem(int position) {
        return IndexMainFragment.newInstance(mDatas.get(position).getId());
    }

    @Override
    protected CharSequence getTitle(IndexTitleBean data) {
        return data.getName();
    }
}

