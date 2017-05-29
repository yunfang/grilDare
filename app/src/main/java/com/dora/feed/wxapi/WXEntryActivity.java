package com.dora.feed.wxapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.famlink.frame.util.CacheUtils;
import com.famlink.frame.util.Content;
import com.famlink.frame.util.LocalContents;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.util.Utils;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.IssueKey;
import com.famlink.frame.widget.dialog.PromptUtils;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.dora.feed.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


/**
 * Created by admin on 2016/7/14.
 */
public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Content.APP_ID, true);
        api.handleIntent(getIntent(),this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        PromptUtils.dismissProgressDialog();
        Bundle bundle = new Bundle();
        switch (baseResp.errCode){
            case BaseResp.ErrCode.ERR_OK:
//                baseResp.toBundle(bundle);
//                SendAuth.Resp sp =  new SendAuth.Resp();
//                String code = sp.code;
                String code = null;
                if(CacheUtils.getInstance().getBoolean(LocalContents.LOGIN_WEIXIN_CODE,false)){
                    CacheUtils.getInstance().putBoolean(LocalContents.LOGIN_WEIXIN_CODE,false);
                    code = ((SendAuth.Resp) baseResp).code;
                    //微信登陆
                    if(!TextUtils.isEmpty(code)){
                        loginForWechat(code);
                        CacheUtils.getInstance().putBoolean(LocalContents.LOGIN_WEIXIN_CODE,false);
                        ToastUtils.showToast(Utils.getString(this, R.string.login_success));
                    }
                }else{
                    //分享成功
                    ToastUtils.showToast(Utils.getString(this,R.string.share_complete));
                    finish();
                }

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //分享取消
                CacheUtils.getInstance().putBoolean(LocalContents.LOGIN_WEIXIN_CODE,false);
                ToastUtils.showToast(Utils.getString(this,R.string.share_cancel));
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //分享拒绝
                CacheUtils.getInstance().putBoolean(LocalContents.LOGIN_WEIXIN_CODE,false);
                ToastUtils.showToast(Utils.getString(this,R.string.authentication_error));
                finish();
                break;
            case BaseResp.ErrCode.ERR_SENT_FAILED:
                CacheUtils.getInstance().putBoolean(LocalContents.LOGIN_WEIXIN_CODE,false);
                ToastUtils.showToast(Utils.getString(this,R.string.login_error));
                finish();
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                CacheUtils.getInstance().putBoolean(LocalContents.LOGIN_WEIXIN_CODE,false);
                ToastUtils.showToast(Utils.getString(this,R.string.sdk_no_support));
                finish();
                break;
        }

    }

    private void loginForWechat(String code) {
        RequestParams entity = new RequestParams("https://api.weixin.qq.com/sns/oauth2/access_token");
        entity.addParameter("appid",  Content.APP_ID);
        entity.addParameter("secret",  Content.APP_SECRET);
        entity.addParameter("code",  code);
        entity.addParameter("grant_type",  "authorization_code");


        x.http().get(entity, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);
                    String openid = jsonObject.getString("openid").toString().trim();
                    String access_token = jsonObject .getString("access_token").toString().trim();
                    System.out.println("access_token----"+result);
                    getUID(openid, access_token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("onError");

            }

            @Override
            public void onCancelled(CancelledException cex) {
                System.out.println("onCancelled");
            }

            @Override
            public void onFinished() {
                System.out.println("onFinished");
            }
        });
    }

    private void getUID(final String openid, String access_token) {
        RequestParams entity = new RequestParams("https://api.weixin.qq.com/sns/userinfo");
        entity.addParameter("access_token",  access_token);
        entity.addParameter("openid", openid);

        x.http().get(entity, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);
                    String nickname = jsonObject.getString("nickname");
                    String headimgurl = jsonObject.getString("headimgurl");
                    String[] arr  = {nickname,headimgurl};
                    CacheUtils.getInstance().putString(LocalContents.LOGIN_USER_NAME, nickname);
                    CacheUtils.getInstance().putString(LocalContents.LOGIN_USER_HEAD, headimgurl);
                    CacheUtils.getInstance().putString(LocalContents.ACCESS_TOKEN, "WEIXIN" + openid);

                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.CLOSE_LOGIN_ACTIVITY);
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.UPDATEHEAD, arr);
                    finish();

//                    String unionid = jsonObject.getString("unionid");
//                    System.out.println("nickname----" + result);
//                    ToastUtils.showToast("nickname----"+result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("onError");

            }

            @Override
            public void onCancelled(CancelledException cex) {
                System.out.println("onCancelled");
            }

            @Override
            public void onFinished() {
                System.out.println("onFinished");
            }
        });
    }
}
