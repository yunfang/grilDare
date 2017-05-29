package com.dora.feed.mvp.bean;

import android.view.View;

import com.dora.feed.view.adapter.IndexItemAdapter;
import com.famlink.frame.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangkai on 16/7/12.
 */
public class IndexItemHandler {

    IndexItemAdapter adapter;
    List<IndexItemBean.IndexDataBean> mList;
    private int position;
//    public IndexItemHandler(Context context, List<IndexItemBean> mList) {
//        this.context = context;
//        this.mList = mList;
//    }
    public IndexItemHandler(IndexItemAdapter adapter, ArrayList<IndexItemBean.IndexDataBean> mList, int position) {
        this.adapter = adapter;
        this.mList = mList;
        this.position = position;

    }

    public void myOnclick(View v){
        ToastUtils.showToast("点击了我的评论中的文章内容" + position);
        mList.remove(position);
        adapter.notifyItemChanged(position);
//        System.out.println("mList:" + mList.size());
//        Intent intent = new Intent(context, Card2Activity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
    }
}
