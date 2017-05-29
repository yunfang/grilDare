package com.dora.feed.mvp.bean;

import android.content.Context;
import android.view.View;

import com.famlink.frame.mvp.bean.BaseResult;
import com.famlink.frame.util.ToastUtils;
import com.dora.feed.mvp.presenter.CommentDetailOtherPersenterImpl;
import com.dora.feed.mvp.view.CommentDetalOtherView;
import com.dora.feed.view.adapter.DetailVideoAdapter;
import com.dora.feed.view.adapter.DetailWebViewListAdapter;

import java.util.List;

/**
 * Created by wangkai on 16/7/12.
 */
public class DetailDianzanItemHandler implements CommentDetalOtherView {

    Context context;
    List<CommentDetailBean.Data> mList;
    private int position;
    private String intentArticleId; //文章ID 或者是评论ID
    private DetailWebViewListAdapter mAdapter;
    private DetailVideoAdapter mVideoAdapter;


    private boolean isState;
    public DetailDianzanItemHandler(Context context, String intentArticleId, List<CommentDetailBean.Data> mList, DetailWebViewListAdapter mAdapter, int position) {
        this.context = context;
        this.intentArticleId = intentArticleId;
        this.mList = mList;
        this.mAdapter = mAdapter;
        this.position = position;
        isState = true;
    }
    public DetailDianzanItemHandler(Context context, List<CommentDetailBean.Data> mList, DetailVideoAdapter mAdapter, int position) {
        this.context = context;
        this.intentArticleId = String.valueOf(mList.get(position).getDid());
        this.mList = mList;
        this.mVideoAdapter = mAdapter;
        this.position = position;
        isState = false;
    }

    public void myDianzanOnclick(View v){
//        ToastUtils.showToast("点击了我的评论中的点赞按钮" + position);
        new CommentDetailOtherPersenterImpl(this).requestNetWorkLikeForContent(mList.get(position).getDid()+"", 1, "");
    }

    @Override
    public void setOtherData(BaseResult data) {
        if("200".equals(data.getmCode())){

            int thumbNum = Integer.valueOf(mList.get(position).getThumb_up_num());
            mList.get(position).setThumb_up_num(String.valueOf(thumbNum+1));

            if(!isState){
                mVideoAdapter.setmDatas(mList);
                mVideoAdapter.notifyItemChanged(position);
            }else{
                mAdapter.setmDatas(mList);
                mAdapter.notifyItemChanged(position);
            }
            ToastUtils.showToast("点赞成功");
        }else{
            ToastUtils.showToast("点赞失败");
        }

    }

    @Override
    public void setOtherData(List<BaseResult> data) {

    }

    @Override
    public void error(BaseResult data) {
        ToastUtils.showToast(data.getmMessage());
    }
}
