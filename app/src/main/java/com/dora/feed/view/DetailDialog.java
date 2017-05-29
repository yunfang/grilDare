package com.dora.feed.view;

import android.app.Activity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.dora.feed.mvp.view.CommentDetalOtherView;
import com.famlink.frame.core.AbsDialog;
import com.famlink.frame.mvp.bean.BaseResult;
import com.famlink.frame.util.CacheUtils;
import com.famlink.frame.util.LocalContents;
import com.famlink.frame.util.SysoutUtil;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.IssueKey;
import com.dora.feed.R;
import com.dora.feed.mvp.presenter.CommentDetailOtherPersenterImpl;

import java.util.List;

/**
 * Created by wangkai on 16/7/19.
 */
public class DetailDialog extends AbsDialog implements CommentDetalOtherView {

    private CommentDetailOtherPersenterImpl commentDetailOtherPersenter;
    private String article_id;
    private String to_discuss_id;
    private String to_user_id;
    private String to_user_name;

    public DetailDialog(Activity activity, int theme) {
        super(activity, theme);
    }


    public DetailDialog(Activity activity) {
        super(activity);
        setInitView();
    }

    /**
     *
     * @param article_id
     * @param to_discuss_id  会话的ID
     * @param to_user_id     被评论人的ID
     * @param to_user_name   被评论人的名字
     */
    public void setDetailMessage(String article_id, String to_discuss_id, String to_user_id, String to_user_name){
        this.article_id = article_id;
        this.to_discuss_id = to_discuss_id;
        this.to_user_id = to_user_id;
        this.to_user_name = to_user_name;
    }


    @Override
    protected int setLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.layout_detail_popuwindow;

     }

    @Override
    public void setInitView() {
        commentDetailOtherPersenter = new CommentDetailOtherPersenterImpl(this);
        final EditText editTextInput = (EditText) findViewById(R.id.input_edit_msg);
        Button btnSendMsg = (Button) findViewById(R.id.input_send_msg);
        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtils.showToast("发表评论");
                String editTextString = editTextInput.getText().toString();
                if(!TextUtils.isEmpty(editTextString)){
                    String user_id = CacheUtils.getInstance().getString(LocalContents.USER_ID,"");
                    commentDetailOtherPersenter.requestNetWorkComment(user_id, article_id, editTextString, to_discuss_id, to_user_id, to_user_name, "");
                }
            }
        });
    }

    @Override
    protected void dataCallback(int result, Object obj) {

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        SysoutUtil.out("DetailDialog-------onKeyDown");
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        SysoutUtil.out("DetailDialog-------onKeyUp");
//        return super.onKeyUp(keyCode, event);
//    }
//
//    @Override
//    public void onBackPressed() {
//        SysoutUtil.out("DetailDialog-------onBackPressed");
//        super.onBackPressed();
//    }

    @Override
    public void setOtherData(BaseResult data) {
        ToastUtils.showToast(data.getmMessage());
        if (("200").equals(data.getmCode())){
            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.RECOMMEND_REFRESH);
        }
        this.dismiss();
    }

    @Override
    public void setOtherData(List<BaseResult> data) {

    }

    @Override
    public void error(BaseResult data) {
        ToastUtils.showToast("data:" + data.getmMessage());
    }
}
