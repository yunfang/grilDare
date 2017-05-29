package com.dora.feed.mvp.bean;

import android.content.Context;
import android.view.View;

import com.famlink.frame.util.ToastUtils;
//import com.ximi.gread.view.DetailsActivity;

import java.util.List;

/**
 * Created by wangkai on 16/7/12.
 */
public class SearchIndexItmeHandler {

    Context context;
    List<SearchIndexBean.Data> mList;
    private int position;
    public SearchIndexItmeHandler(Context context, List<SearchIndexBean.Data> list, int position) {
        this.context = context;
        this.mList = list;
        this.position = position;
    }




//    public CommentItmeHandler(Context context, int position) {
//        this.context = context;
//        this.position = position;
//    }

    public void myOnclick(View v){
        ToastUtils.showToast("点击了我的评论中的文章内容" + position);

//        Intent intent = new Intent(context, DetailsActivity.class);
//        intent.putExtra("intentArticleId",mList.get(position).getAid());
//        intent.putExtra("intentTitle",mList.get(position).getTitle());
//        intent.putExtra("intentIcon",mList.get(position).getMaster_img());
//        context.startActivity(intent);
//        System.out.println("mList:" + mList.size());
//        Intent intent = new Intent(context, Card2Activity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
    }
}
