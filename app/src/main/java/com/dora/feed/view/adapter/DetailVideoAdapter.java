package com.dora.feed.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.dora.feed.config.Constants;
import com.dora.feed.mvp.bean.CommentDetailBean;
import com.dora.feed.mvp.bean.DetailAnswerItemHandler;
import com.dora.feed.mvp.bean.DetailDianzanItemHandler;
import com.famlink.frame.BR;
import com.famlink.frame.widget.pullrecycleview.BaseDataBindingAdapter;
import com.dora.feed.R;
import com.dora.feed.mvp.bean.DetailWebItemHandler;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangkai on 16/7/27.
 */
public class DetailVideoAdapter extends BaseDataBindingAdapter {
    private List<CommentDetailBean.Data> list;
    private Activity activity;
    private String articleId;
    public DetailVideoAdapter(Context context, Activity activity, String articleId, ArrayList<CommentDetailBean.Data> datas) {
        super(context, datas);
        this.list = datas;
        this.activity = activity;
        this.articleId = articleId;
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, int position, Object data) {
//        ((ViewHolder) holder).getBinding().setVariable(BR.click, new CommentItmeHandler(context, list, position));
//        ((BaseDataBindingAdapter.ViewHolder) holder).getBinding().setVariable(BR.bean, ((CommentDetailBean.Data) data));
        ((ViewHolder) holder).getBinding().setVariable(BR.click, new DetailWebItemHandler(context, list,position));
   /*     ((ViewHolder) holder).getBinding().setVariable(BR.isSuccess, false);*/

        ((ViewHolder) holder).getBinding().setVariable(BR.dianzanClick, new DetailDianzanItemHandler(context, list,  DetailVideoAdapter.this, position));
        ((ViewHolder) holder).getBinding().setVariable(BR.answerClick, new DetailAnswerItemHandler(activity, articleId, list.get(position)));

        ((ViewHolder) holder).getBinding().setVariable(BR.bean, ((CommentDetailBean.Data) data));
        ((ViewHolder) holder).getBinding().executePendingBindings();
    }

    @Override
    protected int setResourId(int viewType) {
//        if(viewType == 0){
//            return R.layout.layout_detail_video_activity_header;
//        }else if(viewType == 1){
//            return R.layout.layout_fragment_index_main_item_6;
//        }else if(viewType == 2){
//            return R.layout.layout_detail_video_all_commend;
//        }else{
            return R.layout.layout_comment_webview_detail_item;
//        }
    }

    @Override
    protected int setTypeItem(int position) {
        return getItemType(position);
    }

    @BindingAdapter("app:imgSrc")
    public static void  setImageUrl(ImageView view, String url) {
        x.image().bind(view, url, Constants.setImageUtils(R.drawable.ic_launcher, R.drawable.ic_launcher, true));
    }

    private int getItemType(int position) {
//        if(position == 0){
//            return 0;
//        }else if(position == 1){
//            return 1;
//        }else{
//            return 2;
//        }
        return 2;
    }
    //
    @Override
    public boolean isSectionHeader(int position) {
        return super.isSectionHeader(position);
    }


}
