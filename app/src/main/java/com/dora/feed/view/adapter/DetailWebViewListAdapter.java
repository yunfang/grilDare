package com.dora.feed.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.dora.feed.config.Constants;
import com.dora.feed.mvp.bean.CommentDetailBean;
import com.dora.feed.mvp.bean.DetailAnswerItemHandler;
import com.dora.feed.mvp.bean.DetailDianzanItemHandler;
import com.dora.feed.mvp.bean.DetailWebItemHandler;
import com.famlink.frame.BR;
import com.famlink.frame.widget.pullrecycleview.BaseDataBindingAdapter;
import com.dora.feed.R;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangkai on 16/7/21.
 */
public class DetailWebViewListAdapter extends BaseDataBindingAdapter {
    private List<CommentDetailBean.Data> list;
    private String intentArticleId;
    private Context context;
    private Activity activity;
    public DetailWebViewListAdapter(Context context, Activity activity, String intentArticleId, ArrayList<CommentDetailBean.Data> datas) {
        super(context, datas);
        this.context = context;
        this.activity = activity;
        this.intentArticleId = intentArticleId;
        this.list = datas;
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, int position, Object data) {
        ((ViewHolder) holder).getBinding().setVariable(BR.click, new DetailWebItemHandler(context, list,position));
        ((ViewHolder) holder).getBinding().setVariable(BR.dianzanClick, new DetailDianzanItemHandler(context, intentArticleId, list,  DetailWebViewListAdapter.this, position));
        ((ViewHolder) holder).getBinding().setVariable(BR.answerClick, new DetailAnswerItemHandler(activity, intentArticleId, list.get(position)));

        ((ViewHolder) holder).getBinding().setVariable(BR.bean, ((CommentDetailBean.Data) data));
        ((ViewHolder) holder).getBinding().executePendingBindings();
    }

    @Override
    protected int setResourId(int viewType) {
        return R.layout.layout_comment_webview_detail_item;
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