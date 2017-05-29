package com.dora.feed.mvp.bean;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.dora.feed.R;
import com.dora.feed.view.ChangeNetWorkDialog;
import com.dora.feed.view.DetailVideoActivity;
import com.dora.feed.view.DetailsX5Activity;
import com.famlink.frame.util.CacheUtils;
import com.famlink.frame.util.LocalContents;
import com.famlink.frame.util.NetUtils;
import com.famlink.frame.util.ToastUtils;
//import com.ximi.gread.view.DetailsActivity;

import java.util.List;

/**
 * Created by wangkai on 16/7/12.
 */
public class MessageItemHandler {

    Context context;
    List<MessageBean.Data> mList;
    private int position;
    public MessageItemHandler(Context context, List<MessageBean.Data> list, int position) {
        this.context = context;
        this.mList = list;
        this.position = position;
    }

    public void myOnclick(View v){
        if(TextUtils.isEmpty(mList.get(position).getPrivate_url())){
            Intent intent = new Intent(context, DetailsX5Activity.class);
            intent.putExtra("intentArticleId",mList.get(position).getAid());
            intent.putExtra("intentPosition", position);
            intent.putExtra("intentTitle",mList.get(position).getTitle());
            intent.putExtra("intentIcon",mList.get(position).getMaster_img());
            intent.putExtra("intentReadCount",mList.get(position).getLook_num());
            intent.putExtra("intentTime",mList.get(position).getAcreate());
            intent.putExtra("intentCommentCount", mList.get(position).getCommend_num());
            intent.putExtra("intentPublicUrl", mList.get(position).getPublic_url());
            context.startActivity(intent);
        }else{
//            intent = new Intent(context, DetailVideoActivity.class);
//            intent.putExtra("intentArticleId",mList.get(position).getAid());
//            intent.putExtra("intentTitle",mList.get(position).getTitle());
//            intent.putExtra("intentIcon",mList.get(position).getMaster_img());
//            intent.putExtra("intentReadCount",mList.get(position).getLook_num());
//            intent.putExtra("intentTime",mList.get(position).getAcreate());
//            intent.putExtra("intentPrivateUrl", mList.get(position).getPrivate_url());
            changeNetworkDialog();
        }

    }

    ChangeNetWorkDialog changeNetWorkDialog;
    private void changeNetworkDialog(){
        if(!NetUtils.isNetworkConnected()){
            ToastUtils.showToast(context.getResources().getString(R.string.toast_net_work_error));
            return;
        }
        if(NetUtils.getNetworkType() == 1){
            intentVideo();
        }else{
            int change_network = CacheUtils.getInstance().getInt(LocalContents.CHANGE_NETWORK, 0);
            if(change_network == 1){
                intentVideo();

            }else {
                changeNetWorkDialog = new ChangeNetWorkDialog(context, new ChangeNetWorkDialog.onClickListener() {
                    @Override
                    public void onClickConfirm() {
                        CacheUtils.getInstance().putInt(LocalContents.CHANGE_NETWORK, 1);
                        intentVideo();
                    }
                });
                changeNetWorkDialog.show();
            }
        }
    }
    private void intentVideo(){
        Intent intent = new Intent(context, DetailVideoActivity.class);
        intent.putExtra("intentArticleId",mList.get(position).getAid());
        intent.putExtra("intentTitle",mList.get(position).getTitle());
        intent.putExtra("intentIcon",mList.get(position).getMaster_img());
        intent.putExtra("intentReadCount",mList.get(position).getLook_num());
        intent.putExtra("intentTime",mList.get(position).getAcreate());
        intent.putExtra("intentPrivateUrl", mList.get(position).getPrivate_url());
//        intent.putExtra("intentTraceId", mList.get(position).getTrace_id());
        context.startActivity(intent);
    }
}
