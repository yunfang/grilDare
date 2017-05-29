package com.dora.feed.view;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dora.feed.utils.SinaWeibo;
import com.dora.feed.utils.TencentUtils;
import com.dora.feed.utils.WXUtils;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.IssueKey;
import com.famlink.frame.util.notify.ObserverGroup;
import com.famlink.frame.util.notify.OnDataChangeObserver;
import com.famlink.frame.view.activity.BaseActivity;
import com.famlink.frame.widget.dialog.PromptUtils;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.dora.feed.R;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by admin on 2016/7/13.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, IWeiboHandler.Response, OnDataChangeObserver {
    @ViewInject(R.id.id_weixin_login_img)
    ImageView weixin_login;    //微信登陆

    @ViewInject(R.id.id_qq_login_img)
    ImageView qq_login;      //qq登陆

    @ViewInject(R.id.id_weibo_login_img)
    ImageView weibo_login;    //微博登陆

    @ViewInject(R.id.id_text_cancel)
    TextView text_cancel;   //取消
    private TencentUtils tencentUtils;
    private SinaWeibo sinaWeibo;

    @Override
    public int setLayout() {
        mObserverGroup = ObserverGroup.createLoginGroup();
        DataChangeNotification.getInstance().addObserver(IssueKey.CLOSE_LOGIN_ACTIVITY,this,mObserverGroup);
        return R.layout.activity_login;
    }

    @Override
    public void setInterfaceView() {
        weixin_login.setOnClickListener(this);
        qq_login.setOnClickListener(this);
        weibo_login.setOnClickListener(this);
        text_cancel.setOnClickListener(this);
    }

    @Override
    public void setGenericNodataOrNonetwork() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.id_weixin_login_img:
                PromptUtils.showProgressDialog(this,R.string.three_loading);
                WXUtils.loginWithWeiXin(this);
                break;
            case R.id.id_qq_login_img:
                PromptUtils.showProgressDialog(this,R.string.three_loading);
                tencentUtils = new TencentUtils(this);
                tencentUtils.onClickLoginQQ();
                break;
            case R.id.id_weibo_login_img:
                PromptUtils.showProgressDialog(this,R.string.three_loading);
                sinaWeibo = new SinaWeibo(this);
                sinaWeibo.requestLoginWeibo(this);
                break;
            case R.id.id_text_cancel:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PromptUtils.dismissProgressDialog();
        //新浪微博的回调
        if(sinaWeibo != null){
            sinaWeibo.onActivityResult(requestCode, resultCode, data);
        }
        //qq分享和登陆回调
        if(tencentUtils != null){
            tencentUtils.onActivityResultData(requestCode, resultCode, data);
        }
    }

    @Override
    public void onResponse(BaseResponse baseResponse) {
        switch (baseResponse.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                Toast.makeText(this, "成功", Toast.LENGTH_LONG).show();
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                Toast.makeText(this, "取消", Toast.LENGTH_LONG).show();
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                Toast.makeText(this, "失败" + baseResponse.errMsg, Toast.LENGTH_LONG)
                        .show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(tencentUtils != null){
            tencentUtils.releaseResource();
        }
    }

    @Override
    public void onDataChanged(IssueKey issue, Object o) {
        if(issue.equals(IssueKey.CLOSE_LOGIN_ACTIVITY)){
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        DataChangeNotification.getInstance().notifyDataChanged(IssueKey.BACK_LOGOUT);
    }
}
