package com.dora.feed.mvp.bean;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * Created by wangkai on 16/7/12.
 */
public class DetailWebItemHandler {

    Context context;
    List<CommentDetailBean.Data> mList;
    private int position;
    public DetailWebItemHandler(Context context, List<CommentDetailBean.Data> list, int position) {
        this.context = context;
        this.mList = list;
        this.position = position;
    }

    public void myOnclick(View v){
//        ToastUtils.showToast("点击了我的评论中的文章内容" + position);
//
//        Intent intent = new Intent(context, DetailsActivity.class);
//        intent.putExtra("intentArticleId",mList.get(position).getDid());
//        intent.putExtra("intentTitle",mList.get(position).getNick());
//        intent.putExtra("intentIcon",mList.get(position).getHead_icon());
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
    }
}
