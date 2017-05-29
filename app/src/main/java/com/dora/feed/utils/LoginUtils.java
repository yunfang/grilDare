package com.dora.feed.utils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.famlink.frame.util.AccessTokenKeeper;
import com.famlink.frame.util.CacheUtils;
import com.famlink.frame.util.Content;
import com.famlink.frame.util.LocalContents;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.util.Utils;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.IssueKey;
import com.famlink.frame.widget.dialog.PromptUtils;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.LogoutAPI;
import com.tencent.tauth.Tencent;
import com.dora.feed.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2016/7/27.
 */
public class LoginUtils {

    /**
     * 判断是否登录
     * @return
     */
    public static boolean isAlreadyLogin(){
        String access_token = CacheUtils.getInstance().getString(LocalContents.ACCESS_TOKEN, "");
        if(TextUtils.isEmpty(access_token)){
            return false;
        }else{
            return true;
        }
    }
    /**
     * 退出登录
     * @param activity
     */
    public static void logout(final Activity activity, final Class cla) {
        PromptUtils.showProgressDialog(activity, Utils.getString(activity, R.string.logout_loding));
        String token = CacheUtils.getInstance().getString(LocalContents.ACCESS_TOKEN);
        if(token.startsWith("QQ")){    //qq退出
            Tencent.createInstance(Content.Tencent_APP_ID, activity).logout(activity);
            CacheUtils.getInstance().remove(LocalContents.LOGIN_USER_NAME);
            CacheUtils.getInstance().remove(LocalContents.LOGIN_USER_HEAD);
            CacheUtils.getInstance().remove(LocalContents.ACCESS_TOKEN);
            PromptUtils.dismissProgressDialog();

            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.USER_LOGOUT);
            activity.startActivity(new Intent(activity,cla));
        }else if (token.startsWith("SINA")){   //微博退出
            new LogoutAPI(activity, Content.SINA_APP_KEY,AccessTokenKeeper.readAccessToken(activity)).logout(new RequestListener() {
                @Override
                public void onComplete(String response) {
                    if (!TextUtils.isEmpty(response)) {
                        try {
                            PromptUtils.dismissProgressDialog();
                            JSONObject obj = new JSONObject(response);
                            String value = obj.getString("result");
                            if ("true".equalsIgnoreCase(value)) {
                                AccessTokenKeeper.clear(activity);
                                CacheUtils.getInstance().remove(LocalContents.LOGIN_USER_NAME);
                                CacheUtils.getInstance().remove(LocalContents.LOGIN_USER_HEAD);
                                CacheUtils.getInstance().remove(LocalContents.ACCESS_TOKEN);
                                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.USER_LOGOUT);
                                activity.startActivity(new Intent(activity, cla));
                            }else{
                                ToastUtils.showToast(Utils.getString(activity,R.string.logout_error));
                            }
                        } catch (JSONException e) {
                            ToastUtils.showToast(Utils.getString(activity,R.string.logout_error));
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onWeiboException(WeiboException e) {
                    PromptUtils.dismissProgressDialog();
                    ToastUtils.showToast(Utils.getString(activity,R.string.logout_error));
                }
            });
        }else if(token.startsWith("WEIXIN")){
            CacheUtils.getInstance().remove(LocalContents.LOGIN_USER_NAME);
            CacheUtils.getInstance().remove(LocalContents.LOGIN_USER_HEAD);
            CacheUtils.getInstance().remove(LocalContents.ACCESS_TOKEN);
            PromptUtils.dismissProgressDialog();
            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.USER_LOGOUT);
            activity.startActivity(new Intent(activity,cla));
        }



    }


}
