package com.dora.feed.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.famlink.frame.util.Utils;
import com.famlink.frame.view.fragment.BaseFragment;
import com.dora.feed.R;
import com.dora.feed.databinding.LayoutFragmentRecommendBinding;

/**
 * 推荐好友
 * Created by admin on 2016/7/11.
 */
public class RecommendFragment extends BaseFragment<LayoutFragmentRecommendBinding> {

    @Override
    public void onBaseCreateView(View view, Bundle savedInstanceState) {
        getBinding().setStr2("推荐给好友");
    }

    @Override
    public int setLayout() {
        return R.layout.layout_fragment_recommend;
    }

    @Override
    public void setInterfaceView() {

    }

    @Override
    public void setGenericNodataOrNonetwork() {

    }

    @Override
    protected void dataCallback(int result, Object obj) {

    }

}
