package com.dora.feed.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.famlink.frame.util.CacheUtils;
import com.famlink.frame.util.Content;
import com.famlink.frame.util.LocalContents;
import com.famlink.frame.util.Utils;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.dora.feed.R;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.ByteArrayOutputStream;

/**
 * 微信登陆和分享
 * Created by admin on 2016/7/18.
 */
public class WXUtils {

    static ImageOptions options=new ImageOptions.Builder()
//设置加载失败后的图片
            .setFailureDrawableId(R.drawable.app_logo)
//设置使用缓存
            .setUseMemCache(true)
            .build();


    /**
     *
     * @param flag  //flag:0 微信好友       flag:1 微信朋友圈
     * @param title   //title
     * @param description //描述
     * @param res //Resources
     * @param picHead   //显示的图片地址
     * @param isTuijian //是否是侧边栏的推荐,是侧边栏为：true ,其他文章分享为:false
     * @param drawable  //侧边栏推荐给好友app的图标,其他为null
     */
    public static void wechatShare(Context context, final int flag, final String url, final String title, final String description, final Resources res, final String picHead,boolean isTuijian,int drawable){//flag:0 微信好友       flag:1 微信朋友圈
        final IWXAPI api = WXAPIFactory.createWXAPI(context, Content.APP_ID, true);
        api.registerApp(Content.APP_ID);
        if(isTuijian){//侧边栏推荐给好友app
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = url;
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = title;
            msg.description = description;
            Bitmap bmp = BitmapFactory.decodeResource(res, drawable);
//                msg.thumbData = bmpToByteArray(thumb, true);
            Bitmap thumb = Bitmap.createScaledBitmap(bmp, 120, 120, true);//压缩Bitmap
            msg.thumbData = bmpToByteArray(thumb, true);

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = flag==0?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
            CacheUtils.getInstance().putBoolean(LocalContents.LOGIN_WEIXIN_CODE,false);
            api.sendReq(req);
        }else{//分享文章
            /**
             * loadDrawable()方法加载图片
             */
            Callback.Cancelable cancelable = x.image().loadDrawable(picHead, options, new Callback.CommonCallback<Drawable>() {
                @Override
                public void onSuccess(Drawable result) {
                    WXWebpageObject webpage = new WXWebpageObject();
                    webpage.webpageUrl = url;
                    WXMediaMessage msg = new WXMediaMessage(webpage);
                    msg.title = title;
                    msg.description = description;
//                Bitmap thumb = BitmapFactory.decodeResource(res, result);
//                msg.thumbData = bmpToByteArray(thumb, true);
                    Bitmap thumb = Bitmap.createScaledBitmap(Utils.drawableToBitamp(result), 120, 120, true);//压缩Bitmap
                    msg.thumbData = bmpToByteArray(thumb, true);

                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = String.valueOf(System.currentTimeMillis());
                    req.message = msg;
                    req.scene = flag==0?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
                    CacheUtils.getInstance().putBoolean(LocalContents.LOGIN_WEIXIN_CODE,false);
                    api.sendReq(req);
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
    //微信登陆
    public static void loginWithWeiXin(Context context){
        IWXAPI api = WXAPIFactory.createWXAPI(context, Content.APP_ID, true);
        api.registerApp(Content.APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = Content.WEIXIN_SCOPE;
        req.state = Content.WEIXIN_STATE;
        CacheUtils.getInstance().putBoolean(LocalContents.LOGIN_WEIXIN_CODE,true);
        api.sendReq(req);

    }

    private static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
