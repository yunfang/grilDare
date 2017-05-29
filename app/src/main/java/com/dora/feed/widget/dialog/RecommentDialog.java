package com.dora.feed.widget.dialog;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dora.feed.net.Api;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.util.Utils;
import com.famlink.frame.widget.dialog.BaseDialog;
import com.famlink.frame.widget.dialog.PromptUtils;
import com.dora.feed.R;

/**
 * 推荐给好友Dialog
 * Created by admin on 2016/7/12.
 */
public class RecommentDialog extends BaseDialog implements View.OnClickListener {

    private RecommentDialogOnClickListener listener;
    private Context context;



    public interface  RecommentDialogOnClickListener{
        void OnFriendCicleClick();//朋友圈点击
        void OnWeChatClick();//微信点击
        void OnMicroBlogSina();//新浪微博
        void OnQQFriend(); //qq好友
        void OnQQZone();//qq空间
    }

    public RecommentDialog(Context context, RecommentDialogOnClickListener listener) {
        super(context, R.layout.recomment_bottom_dialog);
        this.context = context;
        this.listener = listener;
        setCanceledOnTouchOutside(true);
        initView();
    }
    private void initView() {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setWindowAnimations(com.famlink.frame.R.style.BottomDialogWindowAnim);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        findViewById(R.id.ll_friend_cicle).setOnClickListener(this);
        findViewById(R.id.ll_wechat).setOnClickListener(this);
        findViewById(R.id.ll_micro_blog_sina).setOnClickListener(this);
        findViewById(R.id.id_cancel).setOnClickListener(this);
        findViewById(R.id.qq_ll).setOnClickListener(this);
        findViewById(R.id.qq_zone_ll).setOnClickListener(this);
        findViewById(R.id.ll_cope_url).setOnClickListener(this);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ll_friend_cicle:
                PromptUtils.showProgressDialog(context, Utils.getString(context, com.dora.feed.R.string.three_loading));
                if(listener != null){
                    listener.OnFriendCicleClick();
                    dismiss();
                }
                break;
            case R.id.ll_wechat:
                PromptUtils.showProgressDialog(context, Utils.getString(context, com.dora.feed.R.string.three_loading));
                if(listener != null){
                    listener.OnWeChatClick();
                    dismiss();
                }
                break;
            case R.id.ll_micro_blog_sina:
                PromptUtils.showProgressDialog(context, Utils.getString(context, com.dora.feed.R.string.three_loading));
                if(listener != null){
                    listener.OnMicroBlogSina();
                    dismiss();
                }
                break;
            case R.id.qq_ll:
                PromptUtils.showProgressDialog(context, Utils.getString(context, com.dora.feed.R.string.three_loading));
                if(listener != null){
                    listener.OnQQFriend();
                    dismiss();
                }
                break;
            case R.id.qq_zone_ll:
                PromptUtils.showProgressDialog(context, Utils.getString(context, com.dora.feed.R.string.three_loading));
                if(listener != null){
                    listener.OnQQZone();
                    dismiss();
                }
                break;
            case R.id.id_cancel:
                if (listener != null) {
                    dismiss();
                }
                break;
            case R.id.ll_cope_url:
                if (android.os.Build.VERSION.SDK_INT > 11) {
                    android.content.ClipboardManager c = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    c.setPrimaryClip(ClipData.newPlainText("", Api.SHARE_LEFT_URL));
                } else {
                    android.text.ClipboardManager c = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    c.setText(Api.SHARE_LEFT_URL);
                }
                ToastUtils.showToast(Utils.getString(context,R.string.cope_sucess));
                dismiss();

                break;


        }

    }
}
