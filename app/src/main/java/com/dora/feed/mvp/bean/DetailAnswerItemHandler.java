package com.dora.feed.mvp.bean;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.IssueKey;
import com.famlink.frame.util.notify.OnDataChangeObserver;
import com.dora.feed.R;
import com.dora.feed.utils.LoginUtils;
import com.dora.feed.view.DetailDialog;
import com.dora.feed.view.LoginActivity;

/**
 * Created by wangkai on 16/7/28.
 */
public class DetailAnswerItemHandler implements OnDataChangeObserver {
    private String to_user_id;
    private String to_discuss_id;
    private String to_user_name;
    Activity activity;
    private CommentDetailBean.Data list;
    private String intentArticleId; //文章ID 或者是评论ID


    public DetailAnswerItemHandler(Activity activity, String intentArticleId, CommentDetailBean.Data list) {
        this.activity = activity;
        this.intentArticleId = intentArticleId;
        this.list = list;
        DataChangeNotification.getInstance().addObserver(IssueKey.LOGIN_ADD_BIND_SCCESS,this);
    }



    //点击了我的评论中的回复按钮
    public void myClick(View v){
//        ToastUtils.showToast("点击了我的评论中的回复按钮");
        if(LoginUtils.isAlreadyLogin()){

            setDetailDialog();
        }else {
            activity.startActivity(new Intent(activity, LoginActivity.class));
        }
    }
    /**
     * 弹出输入框
     */
    DetailDialog detailDialog;

    public void setDetailDialog() {

        detailDialog = new DetailDialog(activity);
        detailDialog.setDetailMessage(intentArticleId, String.valueOf(list.getDid()), list.getUid(), list.getNick());
        detailDialog.show();
        detailDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (detailDialog != null && detailDialog.isShowing()) {
                    detailDialog.dismiss();
                }
            }
            return false;
            }
        });
        Window win = detailDialog.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        win.getDecorView().setBackgroundColor(activity.getResources().getColor(R.color.color_f2f2f2));
        win.setGravity(Gravity.BOTTOM);  //此处可以设置dia
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);


        //显示键盘
        detailDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    }
    @Override
    public void onDataChanged(IssueKey issue, Object o) {
        if(issue.equals(IssueKey.LOGIN_ADD_BIND_SCCESS)){
//            setDetailDialog();
        }
    }
}
