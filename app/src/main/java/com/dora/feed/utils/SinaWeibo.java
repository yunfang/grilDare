package com.dora.feed.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import com.famlink.frame.util.AccessTokenKeeper;
import com.famlink.frame.util.CacheUtils;
import com.famlink.frame.util.Content;
import com.famlink.frame.util.LocalContents;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.util.Utils;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.IssueKey;
import com.famlink.frame.widget.dialog.PromptUtils;
import com.sina.weibo.sdk.api.MusicObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.connect.common.Constants;
import com.dora.feed.R;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * 新浪微博的登录和分享
 * Created by admin on 2016/7/15.
 */
public class SinaWeibo {


    ImageOptions options=new ImageOptions.Builder()
//设置加载失败后的图片
            .setFailureDrawableId(R.mipmap.ic_launcher)
//设置使用缓存
            .setUseMemCache(true)
            .build();

    /** 微博实例 */
    private AuthInfo mAuthInfo;

    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;

    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;

    /** 是否安装微博*/
    private boolean isInstalledWeibo;

    /** 支持Api级别*/
    private int supportApiLevel;

    /** 微博分享的接口实例 */
    private IWeiboShareAPI mWeiboShareAPI;

    private String shareTile = "";    //分享的tite标题
    private String shareDescription = "";   //分享的描述
    private String shareActionUrl = "";    //点击跳转的链接
    private String shareDefaultText = "";   //Webpage默认的文案
    private String picHead;//标题图片
    private int id_drawbale;   //图片资源id
    private boolean isTuijian = false;//是否是测拉推荐好友

    private Context context;
    private int type = 0;//0:分享   1:登录
    public SinaWeibo(Context context){
        this.context = context;
//        this.type = type;
    }

//    public void requestSinaWeibo(IWeiboHandler.Response response){
//
//        mAuthInfo = new AuthInfo(context, Content.SINA_APP_KEY,
//                Content.SINA_REDIRECT_URL, Content.SINA_SCOPE);
//        mSsoHandler = new SsoHandler((Activity) context, mAuthInfo);
//
//
//        mAccessToken = AccessTokenKeeper.readAccessToken(context);
//
//        // 创建微博 SDK 接口实例
//        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context,
//                Content.SINA_APP_KEY);
//        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
//        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
//        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
//        mWeiboShareAPI.registerApp();
//        ;
//        mWeiboShareAPI.handleWeiboResponse(((Activity)context).getIntent(), response);
//
//        // 获取微博客户端是否安装
//        isInstalledWeibo = mWeiboShareAPI.isWeiboAppInstalled();
//        // 支持 SDK 的版本
//        supportApiLevel = mWeiboShareAPI.getWeiboAppSupportAPI();
//        if(mAccessToken != null && mAccessToken.isSessionValid()){
//            if(type == 0){
//                shareToWeiBo(context);
//            }else{
//                // SSO 授权, 仅客户端
//                mSsoHandler.authorizeClientSso(new AuthListener(context));
//            }
//        }else{
//            // SSO 授权, 仅客户端
//            mSsoHandler.authorizeClientSso(new AuthListener(context));
//        }
//
//    }

    public void requestLoginWeibo(IWeiboHandler.Response response){
        type = 1;  //1：为登录
        mAuthInfo = new AuthInfo(context, Content.SINA_APP_KEY, Content.SINA_REDIRECT_URL, Content.SINA_SCOPE);
        mSsoHandler = new SsoHandler((Activity) context, mAuthInfo);
        mAccessToken = AccessTokenKeeper.readAccessToken(context);
        // 创建微博 SDK 接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, Content.SINA_APP_KEY);
        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
        mWeiboShareAPI.registerApp();
        mWeiboShareAPI.handleWeiboResponse(((Activity)context).getIntent(), response);

        // 获取微博客户端是否安装
        isInstalledWeibo = mWeiboShareAPI.isWeiboAppInstalled();
        // 支持 SDK 的版本
        supportApiLevel = mWeiboShareAPI.getWeiboAppSupportAPI();

        if(mSsoHandler != null){
            mSsoHandler.authorize(new AuthListener(context));
        }
        // SSO 授权, 仅客户端
//        mSsoHandler.authorizeClientSso(new AuthListener(context));
    }

//    mediaObject.title = "titleText";
//    mediaObject.description = "描述信息附";
//
//    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
//    // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
//    mediaObject.setThumbImage(bitmap);
//    mediaObject.actionUrl = "https://www.baidu.com/";
//    mediaObject.defaultText = "Webpage 默认文案";




    /**
     *
     * @param response  IWeiboHandler
     * @param shareTile  分享的tite标题
     * @param shareDescription  分享的描述
     * @param shareActionUrl  点击跳转的链接
     * @param shareDefaultText   Webpage默认的文案
     */
    public void requestShareWeibo(IWeiboHandler.Response response,String shareTile,String shareDescription,String shareActionUrl,String shareDefaultText,String picHead,boolean isTuijian, int id_drawbale){
        this.shareTile = shareTile;
        this.shareDescription = shareDescription;
        this.shareActionUrl = shareActionUrl;
        this.shareDefaultText = shareDefaultText;
        this.picHead = picHead;
        this.id_drawbale = id_drawbale;
        this.isTuijian = isTuijian;
        type = 0;  //0：为分享
        mAuthInfo = new AuthInfo(context, Content.SINA_APP_KEY,
                Content.SINA_REDIRECT_URL, Content.SINA_SCOPE);
        mSsoHandler = new SsoHandler((Activity) context, mAuthInfo);
        mAccessToken = AccessTokenKeeper.readAccessToken(context);
        // 创建微博 SDK 接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, Content.SINA_APP_KEY);
        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
        mWeiboShareAPI.registerApp();
        mWeiboShareAPI.handleWeiboResponse(((Activity) context).getIntent(), response);

        // 获取微博客户端是否安装
        isInstalledWeibo = mWeiboShareAPI.isWeiboAppInstalled();
        // 支持 SDK 的版本
        supportApiLevel = mWeiboShareAPI.getWeiboAppSupportAPI();
        if(mAccessToken != null && mAccessToken.isSessionValid()){
            shareToWeiBo(context);
        }else{
            // SSO 授权, 仅客户端
//            mSsoHandler.authorizeClientSso(new AuthListener(context));
            shareToWeiBo(context);

        }
    }

    public IWeiboShareAPI getWeiboShare(){
        return  mWeiboShareAPI;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResult 分享
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    class AuthListener implements WeiboAuthListener {
        Context context;
        public AuthListener(Context context){
            this.context = context;
        }

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            // 判断AccessToken是否有效
            if (mAccessToken.isSessionValid()) {
                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(context, mAccessToken);
//                Toast.makeText(context, "获取token成功", Toast.LENGTH_SHORT).show();
                if(type == 0){//0为微博分享
                    shareToWeiBo(context);
                }else if(type == 1){//1为微博登录
                    //获取登录信息
                    UsersAPI usersAPI = new UsersAPI(context,Content.SINA_APP_KEY,mAccessToken);
                    usersAPI.show(Long.parseLong(mAccessToken.getUid()), requestListener);
                }
            } else {
                ToastUtils.showToast(Utils.getString(context,R.string.authentication_error));
            }
        }

        @Override
        public void onCancel() {
            PromptUtils.dismissProgressDialog();
            ToastUtils.showToast(Utils.getString(context, R.string.login_cancel));
        }

        @Override
        public void onWeiboException(WeiboException e) {
            PromptUtils.dismissProgressDialog();
            ToastUtils.showToast("Auth exception : " + e.getMessage());
        }
    }


    /**
     * 分享到微博
     */
    private void shareToWeiBo(Context context) {
        if (isInstalledWeibo) {
            if (mWeiboShareAPI.isWeiboAppSupportAPI()) {
                if (supportApiLevel >= 10351 /* ApiUtils.BUILD_INT_VER_2_2 */) {
                    sendMultiMessage(context,1);
                }else{
                    sendSingleMessage(context);
                }
            } else {
                PromptUtils.dismissProgressDialog();
                ToastUtils.showToast(Utils.getString(context, R.string.sdk_no_support));
            }
        } else {
            sendMultiMessage(context,2);
            PromptUtils.dismissProgressDialog();
//            ToastUtils.showToast(Utils.getString(context, R.string.sina_not_install));
        }


    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * 当{@link IWeiboShareAPI#getWeiboAppSupportAPI()} < 10351 时，只支持分享单条消息，即
     * 文本、图片、网页、音乐、视频中的一种，不支持Voice消息。
     *
     */
    private void sendSingleMessage(final Context context) {

        if(isTuijian) {//侧边栏推荐给好友app
// 1. 初始化微博的分享消息
            // 用户可以分享文本、图片、网页、音乐、视频中的一种
            WeiboMessage weiboMessage = new WeiboMessage();
//        if (hasText) {
//            weiboMessage.mediaObject = getTextObj();
//        }
//        if (hasImage) {
//            weiboMessage.mediaObject = getImageObj();
//        }
//        if (hasWebpage) {
//            weiboMessage.mediaObject = getWebpageObj();
//        }
//        if (hasMusic) {
//            weiboMessage.mediaObject = getMusicObj();
//        }
//        if (hasVideo) {
//            weiboMessage.mediaObject = getVideoObj();
//        }
//        if (hasVoice) {
            weiboMessage.mediaObject = getWebpageObj(context,null);
//        }

            // 2. 初始化从第三方到微博的消息请求
            SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
            // 用transaction唯一标识一个请求
            request.transaction = String.valueOf(System.currentTimeMillis());
            request.message = weiboMessage;

            // 3. 发送请求消息到微博，唤起微博分享界面
            mWeiboShareAPI.sendRequest((Activity) context, request);
        }else{
/**
 * loadDrawable()方法加载图片
 */
            Callback.Cancelable cancelable = x.image().loadDrawable(picHead, options, new Callback.CommonCallback<Drawable>() {
                @Override
                public void onSuccess(Drawable result) {
                    // 1. 初始化微博的分享消息
                    // 用户可以分享文本、图片、网页、音乐、视频中的一种
                    WeiboMessage weiboMessage = new WeiboMessage();
                    weiboMessage.mediaObject = getWebpageObj(context,result);

                    // 2. 初始化从第三方到微博的消息请求
                    SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
                    // 用transaction唯一标识一个请求
                    request.transaction = String.valueOf(System.currentTimeMillis());
                    request.message = weiboMessage;

                    // 3. 发送请求消息到微博，唤起微博分享界面
                    mWeiboShareAPI.sendRequest((Activity) context, request);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                }

                @Override
                public void onCancelled(CancelledException cex) {
                }

                @Override
                public void onFinished() {
                }
            });
        }



    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * 注意：当 {@link IWeiboShareAPI#getWeiboAppSupportAPI()} >= 10351 时，支持同时分享多条消息，
     * 同时可以分享文本、图片以及其它媒体资源（网页、音乐、视频、声音中的一种）。
     */
    private void sendMultiMessage(final Context context, final int type) {

        if(isTuijian){//侧边栏推荐给好友app
            // 1. 初始化微博的分享消息
            WeiboMultiMessage weiboMessage = new WeiboMultiMessage();

            //这里指实现了分享文本內容 还可以分享 图片 视频 音乐 声音 网页 详见WeiboSDKDemo
//                    TextObject textObject = new TextObject();
//                    textObject.text = "分享消息";
//                    weiboMessage.textObject = textObject;

            //分享网页
//                    weiboMessage.mediaObject= getWebpageObj(context);
            //分享多媒体（视频）消息对象。
//                    weiboMessage.mediaObject = getVideoObj(context);
//                    weiboMessage.mediaObject = getMusicObj(context);
            weiboMessage.mediaObject = getWebpageObj(context, null);


            // 2. 初始化从第三方到微博的消息请求
            SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
            // 用transaction唯一标识一个请求
            request.transaction = String.valueOf(System
                    .currentTimeMillis());
            request.multiMessage = weiboMessage;
            if(type == 1){
                // 3. 发送请求消息到微博，唤起微博分享界面
                mWeiboShareAPI.sendRequest((Activity) context, request);
            }else if(type ==2){
                mAuthInfo = new AuthInfo(context, Content.SINA_APP_KEY,
                        Content.SINA_REDIRECT_URL, Content.SINA_SCOPE);
//            mSsoHandler = new SsoHandler((Activity) context, mAuthInfo);
                mAccessToken = AccessTokenKeeper.readAccessToken(context);
                String token = "";
                if(mAccessToken != null){
                    token = mAccessToken.getToken();
                }
                mWeiboShareAPI.sendRequest((Activity) context, request, mAuthInfo, token, new WeiboAuthListener() {
                    @Override
                    public void onComplete(Bundle bundle) {
                        Oauth2AccessToken  newToken = Oauth2AccessToken.parseAccessToken(bundle);
                        AccessTokenKeeper.writeAccessToken(context,newToken);
                    }

                    @Override
                    public void onWeiboException(WeiboException e) {

                    }

                    @Override
                    public void onCancel() {

                    }
                });
//                // 创建微博 SDK 接口实例
//                mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, Content.SINA_APP_KEY);



            }

        }else{//分享文章
            /**
             * loadDrawable()方法加载图片
             */
            Callback.Cancelable cancelable = x.image().loadDrawable(picHead, options, new Callback.CommonCallback<Drawable>() {
                @Override
                public void onSuccess(Drawable result) {
                    // 1. 初始化微博的分享消息
                    WeiboMultiMessage weiboMessage = new WeiboMultiMessage();

                    //这里指实现了分享文本內容 还可以分享 图片 视频 音乐 声音 网页 详见WeiboSDKDemo
//                    TextObject textObject = new TextObject();
//                    textObject.text = "分享消息";
//                    weiboMessage.textObject = textObject;

                    //分享网页
//                    weiboMessage.mediaObject= getWebpageObj(context);
                    //分享多媒体（视频）消息对象。
//                    weiboMessage.mediaObject = getVideoObj(context);
//                    weiboMessage.mediaObject = getMusicObj(context);
                    weiboMessage.mediaObject = getWebpageObj(context,result);


                    // 2. 初始化从第三方到微博的消息请求
                    SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
                    // 用transaction唯一标识一个请求
                    request.transaction = String.valueOf(System
                            .currentTimeMillis());
                    request.multiMessage = weiboMessage;

                    if(type == 1){
                        // 3. 发送请求消息到微博，唤起微博分享界面
                        mWeiboShareAPI.sendRequest((Activity) context, request);
                    }else if(type ==2){
                        mAuthInfo = new AuthInfo(context, Content.SINA_APP_KEY,
                                Content.SINA_REDIRECT_URL, Content.SINA_SCOPE);
//            mSsoHandler = new SsoHandler((Activity) context, mAuthInfo);
                        mAccessToken = AccessTokenKeeper.readAccessToken(context);
                        // 创建微博 SDK 接口实例
                        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, Content.SINA_APP_KEY);
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                }

                @Override
                public void onCancelled(CancelledException cex) {
                }

                @Override
                public void onFinished() {
                }
            });
        }



    }


    /**
     * request监听，用以获取返回的用户资料
     */
    RequestListener requestListener = new RequestListener() {

        @Override
        public void onComplete(String str) {
            try {
                JSONObject object = new JSONObject(str);
                String[] arr  = {object.getString("name"),object.getString("avatar_hd")};
                CacheUtils.getInstance().putString(LocalContents.LOGIN_USER_NAME, arr[0]);
                CacheUtils.getInstance().putString(LocalContents.LOGIN_USER_HEAD, arr[1]);
//                CacheUtils.getInstance().putString(LocalContents.ACCESS_TOKEN, "SINA" + mAccessToken.getToken());
                CacheUtils.getInstance().putString(LocalContents.ACCESS_TOKEN, "SINA" + object.getString("id"));  //不同的设备，同一个qq登录同一个应用，返回唯一相同的标示uid  微博的唯一标示待定,

                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.CLOSE_LOGIN_ACTIVITY);
                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.UPDATEHEAD, arr);

            } catch (Exception e) {
                e.printStackTrace();
            }

//            if (false == StringUtil.isBlankString(str)) {
//                SinaWeiboJsonTemplate sinaWeiboJsonTemplate = new SinaWeiboJsonTemplate();
//                try {
//                    JSONObject jsonObject = new JSONObject(str);
//                    Map&lt;String, String&gt; userInfoMap = sinaWeiboJsonTemplate.analyzeUserInfo(jsonObject);
//                    if (userInfoMap != null) {
//                        // 将用户资料保存到SharedPreference
//                        saveSinaUserInfo(userInfoMap);
//                    }
//                    if (null != dialog) {
//                        dialog.dismiss();
//                    }
//                    Looper.prepare();
//                    CommonTips.showToast(context, &quot;绑定成功&quot;, CommonTips.LENGTH_LONG);
//                    Looper.loop();
//                }
//                catch (JSONException e) {
//                    e.printStackTrace();
//                }
        }

        @Override
        public void onWeiboException(WeiboException e) {

        }
    };
    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj(Context context,Drawable drawable) {

        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = shareTile;
        mediaObject.description = shareDescription;

        if(null != drawable){//加载的网络图片
            Bitmap bitmap = Bitmap.createScaledBitmap(Utils.drawableToBitamp(drawable), 120, 120, true);//压缩Bitmap
            mediaObject.setThumbImage(bitmap);
        }else{//加载的本地图片
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id_drawbale);
            // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
            mediaObject.setThumbImage(bitmap);
        }

        mediaObject.actionUrl = shareActionUrl;
        mediaObject.defaultText = shareDefaultText;
        return mediaObject;
    }

    /**
     * 创建多媒体（视频）消息对象。
     *
     * @return 多媒体（视频）消息对象。
     */
    private VideoObject getVideoObj(Context context) {

        // 创建媒体消息
        VideoObject videoObject = new VideoObject();
        videoObject.identify = Utility.generateGUID();
        videoObject.title = shareTile;
        videoObject.description = shareDescription;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id_drawbale);
        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。

        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, os);
            System.out.println("kkkkkkk    size  "+ os.toByteArray().length );
        } catch (Exception e) {
            e.printStackTrace();
//            LogUtil.e("Weibo.BaseMediaObject", "put thumb failed");
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        videoObject.setThumbImage(bitmap);
        videoObject.actionUrl = shareActionUrl;
        videoObject.dataUrl = "www.weibo.com";
        videoObject.dataHdUrl = "www.weibo.com";
        videoObject.duration = 10;
        videoObject.defaultText = shareDefaultText;
        return videoObject;
    }


    /**
     * 创建多媒体（音乐）消息对象。
     *
     * @return 多媒体（音乐）消息对象。
     */
    private MusicObject getMusicObj(Context context) {
        // 创建媒体消息
        MusicObject musicObject = new MusicObject();
        musicObject.identify = Utility.generateGUID();
        musicObject.title = "音乐";
        musicObject.description = "不错的音乐";

        Bitmap  bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);

        // 设置 Bitmap 类型的图片到视频对象里        设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        musicObject.setThumbImage(bitmap);
        musicObject.actionUrl = "http://sina.cn/";
        musicObject.dataUrl = "www.weibo.com";
        musicObject.dataHdUrl = "www.weibo.com";
        musicObject.duration = 10;
        musicObject.defaultText = "Music 默认文案";
        return musicObject;
    }

    /**
     * 创建多媒体（音频）消息对象。
     *
     * @return 多媒体（音乐）消息对象。
     */
    private VoiceObject getVoiceObj(Context context) {
        // 创建媒体消息
        VoiceObject voiceObject = new VoiceObject();
        voiceObject.identify = Utility.generateGUID();
        voiceObject.title = "音乐";
        voiceObject.description = "音乐不错哟";
        Bitmap  bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        // 设置 Bitmap 类型的图片到视频对象里      设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        voiceObject.setThumbImage(bitmap);
        voiceObject.actionUrl = "http://sina.cn/";
        voiceObject.dataUrl = "www.weibo.com";
        voiceObject.dataHdUrl = "www.weibo.com";
        voiceObject.duration = 10;
        voiceObject.defaultText = "Voice 默认文案";
        return voiceObject;
    }

    public static void saveFile(String str) {
        String filePath = null;
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) {
            filePath = Environment.getExternalStorageDirectory().toString() + File.separator + "a_text_weibo.txt";
        } else
            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + "a_text_weibo.txt";

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(str.getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
