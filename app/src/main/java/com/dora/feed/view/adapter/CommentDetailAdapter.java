package com.dora.feed.view.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.famlink.frame.BR;
import com.famlink.frame.widget.pullrecycleview.BaseDataBindingAdapter;
import com.dora.feed.R;
import com.dora.feed.config.Constants;
import com.dora.feed.mvp.bean.CommentBean;
import com.dora.feed.mvp.bean.CommentDetailBean;
import com.dora.feed.mvp.bean.CommentItmeHandler;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangkai on 16/7/19.
 */
public class CommentDetailAdapter extends BaseDataBindingAdapter {
    private List<CommentBean.Data> list;

    public CommentDetailAdapter(Context context, ArrayList<CommentDetailBean.Data> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, int position, Object data) {
        ((ViewHolder) holder).getBinding().setVariable(BR.click, new CommentItmeHandler(context,list, position));
        ((ViewHolder) holder).getBinding().setVariable(BR.bean, ((CommentDetailBean.Data) data));
        ((ViewHolder) holder).getBinding().executePendingBindings();
    }

    @Override
    protected int setResourId(int viewType) {
        return R.layout.layout_fragment_comment_list_item;
    }

    @Override
    protected int setTypeItem(int position) {
        return getItemType(position);
    }

    @BindingAdapter("app:imgSrc")
    public static void setImageUrl(ImageView view, String url) {
        x.image().bind(view, url);
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