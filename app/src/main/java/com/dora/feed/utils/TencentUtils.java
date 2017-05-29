package com.dora.feed.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;

import com.dora.feed.R;
import com.famlink.frame.util.CacheUtils;
import com.famlink.frame.util.Content;
import com.famlink.frame.util.LocalContents;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.util.Utils;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.IssueKey;
import com.famlink.frame.widget.dialog.PromptUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 腾讯的好友分享和空间分享，并且qq登陆的封装的工具类
 * Created by admin on 2016/7/15.
 */
public class TencentUtils {
    private Tencent mTencent;
    private UserInfo mInfo;
    private  Activity mactivity;
    private Context mcontext;
    private int shareType = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
    private String app_logo_path = "/sdcard/app_logo.png";//应用的图标保存的地址

    //QZone分享， SHARE_TO_QQ_TYPE_DEFAULT 图文，SHARE_TO_QQ_TYPE_IMAGE 纯图
    private int sharezoneType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;

    public TencentUtils(Activity activity){
        this.mactivity = activity;
        this.mcontext = mactivity.getApplicationContext();
//        if (mTencent == null) {
            mTencent = Tencent.createInstance(Content.Tencent_APP_ID, mactivity);
//        }
    }
    public void onClickLoginQQ(){
        if (!mTencent.isSessionValid()) {
            mTencent.login(mactivity, "all", loginListener);
//            isServerSideLogin = false;
//                    Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
        } else {
//                    if (isServerSideLogin) { // Server-Side 模式的登陆, 先退出，再进行SSO登陆
//                        mTencent.logout(this);
//                        mTencent.login(this, "all", loginListener);
//                        isServerSideLogin = false;
//                        Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
//                        return;
//                    }
            mTencent.logout(mactivity);
            updateUserInfo();
//            updateLoginButton();
        }
    }
    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {

                }

                @Override
                public void onComplete(final Object response) {
                    JSONObject json = (JSONObject)response;
                    if (json.has("nickname")) {
                        try {
//                            Toast.makeText(MainActivity.this,json.getString("nickname"),0).show();
//                            Toast.makeText(mcontext, "---------------" + json.toString(), 0).show();
                            String[] str  = {json.getString("nickname"),json.getString("figureurl_qq_2")};

                            CacheUtils.getInstance().putString(LocalContents.LOGIN_USER_NAME, str[0]);
                            CacheUtils.getInstance().putString(LocalContents.LOGIN_USER_HEAD, str[1]);
                            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.CLOSE_LOGIN_ACTIVITY);
                            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.UPDATEHEAD, str);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

//                    Message msg = new Message();
//                    msg.obj = response;
//                    msg.what = 0;
//                    mHandler.sendMessage(msg);
//                    new Thread(){
//
//                        @Override
//                        public void run() {
//                            JSONObject json = (JSONObject)response;
//                            if(json.has("figureurl")){
//                                Bitmap bitmap = null;
//                                try {
//                                    bitmap = Util.getbitmap(json.getString("figureurl_qq_2"));
//                                } catch (JSONException e) {
//
//                                }
//                                Message msg = new Message();
//                                msg.obj = bitmap;
//                                msg.what = 1;
//                                mHandler.sendMessage(msg);
//                            }
//                        }
//
//                    }.start();
                }

                @Override
                public void onCancel() {

                }
            };
            mInfo = new UserInfo(mcontext, mTencent.getQQToken());
            mInfo.getUserInfo(listener);

        } else {
//            mUserInfo.setText("");
//            mUserInfo.setVisibility(android.view.View.GONE);
//            mUserLogo.setVisibility(android.view.View.GONE);
        }
    }

    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
//            Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
            initOpenidAndToken(values);
            //登录成功之后保存ACCESS_TOKEN
            try {
                CacheUtils.getInstance().putString(LocalContents.ACCESS_TOKEN, "QQ"+values.getString(Constants.PARAM_OPEN_ID));  //不同的设备，同一个qq登录同一个应用，返回唯一相同的标示openid
            } catch (JSONException e) {
                e.printStackTrace();
            }
            updateUserInfo();
//            updateLoginButton();
        }
    };
    public void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }



    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                ToastUtils.showToast(Utils.getString(mcontext, com.dora.feed.R.string.login_error));
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                ToastUtils.showToast(Utils.getString(mcontext, com.dora.feed.R.string.login_error));
                return;
            }
            ToastUtils.showToast(Utils.getString(mcontext, com.dora.feed.R.string.login_success));
            // 有奖分享处理
//            handlePrizeShare();
            doComplete((JSONObject)response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
            ToastUtils.showToast(Utils.getString(mcontext, com.dora.feed.R.string.login_error));
        }

        @Override
        public void onCancel() {
            ToastUtils.showToast(Utils.getString(mcontext, com.dora.feed.R.string.login_cancel));
        }
    }


    public void doShareToQzone(String title,String target_url,String description,String image_url,boolean isTuijian){
        final Bundle paramszone = new Bundle();
        paramszone.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        paramszone.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);//空间title
        paramszone.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, description);//空间描述信息
        paramszone.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, target_url);//http://www.baidu.com/
        // 支持传多个imageUrl
        ArrayList<String> imageUrls = new ArrayList<String>();
        if(isTuijian){
            saveBitmaptoSdcard();
            imageUrls.add(app_logo_path);
            paramszone.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
        }else{
            imageUrls.add(image_url);//http://img3.imgtn.bdimg.com/it/u=1924893621,661118346&fm=206&gp=0.jpg
            paramszone.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
        }

        // QZone分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                if (null != mTencent) {
                    mTencent.shareToQzone(mactivity,paramszone, qZoneShareListener);
                }
            }
        });
    }
    //qq空间的回调
    IUiListener qZoneShareListener = new IUiListener() {

        @Override
        public void onCancel() {
            PromptUtils.dismissProgressDialog();
            ToastUtils.showToast(Utils.getString(mcontext, com.dora.feed.R.string.login_cancel));
            releaseResource();
        }

        @Override
        public void onError(UiError e) {
            PromptUtils.dismissProgressDialog();
            ToastUtils.showToast(Utils.getString(mcontext, com.dora.feed.R.string.share_error));
            releaseResource();
        }

        @Override
        public void onComplete(Object response) {
            PromptUtils.dismissProgressDialog();
            ToastUtils.showToast(Utils.getString(mcontext, com.dora.feed.R.string.share_complete));
            releaseResource();
        }

    };

    /**
     *
     * @param title
     * @param tagget_url
     * @param decription
     * @param image_url
     * @param appName
     * @param isTuijian //是否是测拉推荐好友
     */
    public void doShareToQQ(String title, String tagget_url, String decription, String image_url, String appName,boolean isTuijian) {
        final Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);//title测试
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, tagget_url);//http://www.baidu.com/
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, decription);//描述信息
        if(isTuijian){
            saveBitmaptoSdcard();
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, app_logo_path);//图片地址  http://img3.imgtn.bdimg.com/it/u=1924893621,661118346&fm=206&gp=0.jpg
        }else{
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, image_url);//图片地址  http://img3.imgtn.bdimg.com/it/u=1924893621,661118346&fm=206&gp=0.jpg
        }

        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);//测试应用
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);

        // QQ分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                if (null != mTencent) {
                    mTencent.shareToQQ(mactivity, params, qqShareListener);
                }
            }
        });

    }

    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            PromptUtils.dismissProgressDialog();
            if (shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
                ToastUtils.showToast(Utils.getString(mcontext, com.dora.feed.R.string.login_cancel));
                releaseResource();
            }
        }

        @Override
        public void onComplete(Object response) {
            PromptUtils.dismissProgressDialog();
            ToastUtils.showToast(Utils.getString(mcontext, com.dora.feed.R.string.share_complete));
            releaseResource();
        }

        @Override
        public void onError(UiError e) {
            PromptUtils.dismissProgressDialog();
            ToastUtils.showToast(Utils.getString(mcontext, com.dora.feed.R.string.share_error));
            releaseResource();
        }
    };

    public void onActivityResultData(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
        }else if (requestCode == Constants.REQUEST_QZONE_SHARE) {
            Tencent.onActivityResultData(requestCode,resultCode,data,qZoneShareListener);
        }else if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
        }
    }
    public void releaseResource() {
        if (mTencent != null) {
            mTencent.releaseResource();
            mTencent = null;
        }
    }

    /**
     * Bitmap保存到sdcard中
     */
    private void saveBitmaptoSdcard(){
        Bitmap logoBitmap = BitmapFactory.decodeResource(mcontext.getResources(), R.drawable.app_logo);
        ByteArrayOutputStream logoStream = new ByteArrayOutputStream();
        boolean res = logoBitmap.compress(Bitmap.CompressFormat.PNG,100,logoStream);
        //将图像读取到logoStream中
        byte[] logoBuf = logoStream.toByteArray();
        //将图像保存到byte[]中
        Bitmap temp = BitmapFactory.decodeByteArray(logoBuf,0,logoBuf.length);
        //将图像从byte[]中读取生成Bitmap 对象 temp
        saveMyBitmap(temp);

    }

    //将图像保存到SD卡中
    private void saveMyBitmap(Bitmap mBitmap){
        File f = new File(app_logo_path);
        try {
            f.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
