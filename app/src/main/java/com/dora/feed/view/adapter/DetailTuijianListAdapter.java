package com.dora.feed.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.dora.feed.R;
import com.dora.feed.mvp.bean.CommentDetailBean;
import com.dora.feed.mvp.bean.DetailAnswerItemHandler;
import com.dora.feed.mvp.bean.DetailDianzanItemHandler;
import com.dora.feed.mvp.bean.DetailWebItemHandler;
import com.dora.feed.mvp.bean.IndexItemBean;
import com.dora.feed.mvp.bean.IndexItemHandler;
import com.famlink.frame.BR;
import com.famlink.frame.widget.pullrecycleview.BaseDataBindingAdapter;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangkai on 16/7/21.
 */
public class DetailTuijianListAdapter extends BaseDataBindingAdapter {
    private ArrayList<IndexItemBean.IndexDataBean> list;
    public DetailTuijianListAdapter(Context context, ArrayList<IndexItemBean.IndexDataBean> datas) {
        super(context, datas);
        this.context = context;
        this.list = datas;
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, int position, Object data) {
        ((ViewHolder) holder).getBinding().setVariable(BR.mBean, ((IndexItemBean.IndexDataBean) data));
//        ((ViewHolder) holder).getBinding().setVariable(BR.isSettingStar, isSettingStar);
//        ((ViewHolder) holder).getBinding().setVariable(BR.click, new IndexItemHandler(this, list, position));
        ((ViewHolder) holder).getBinding().executePendingBindings();
    }

    @Override
    protected int setResourId(int viewType) {
        return R.layout.layout_fragment_index_main_item_6;
    }

    @Override
    protected int setTypeItem(int position) {
        return getItemType(position);
    }

    @BindingAdapter("app:imgSrc")
    public static void  setImageUrl(ImageView view, String url) {
        if(!TextUtils.isEmpty(url)){
            if(!url.contains(".gif")){
                x.image().bind(view, url);
            }
        }
    }

    private int getItemType(int position) {
        return 0;
    }
    //
    @Override
    public boolean isSectionHeader(int position) {
        return super.isSectionHeader(position);
    }


}