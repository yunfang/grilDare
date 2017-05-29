package com.dora.feed.net;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.famlink.frame.mvp.bean.BaseResult;
import com.famlink.frame.mvp.bean.UpdateVersionBean;
import com.famlink.frame.net.biz.BaseRequestCallback;
import com.famlink.frame.net.biz.RequestCallback;
import com.famlink.frame.util.AppUtils;
import com.famlink.frame.util.LocalContents;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.IssueKey;
import com.famlink.frame.widget.UpdateVersion;

/**
 * Created by admin on 2016/7/28.
 */
public class RequestUtils {

    public interface OnUpdateVersionListener{
        /**
         * 不需要升级
         */
        void NoNeedUpdateVersion();
    }

    /**
     * 第三方登录成功之后请求自己服务器
     * @param ids    第三方平台的id
     * @param head_icon  第三方头像
     * @param nick    第三方登录的昵称
     * @param from   wechat,qq,weibo
     */
    public static void requestLogin(String ids,String head_icon,String nick,String from){
        ParamsApi.requestLogin(ids,head_icon,nick,from).execute(new BaseRequestCallback<BaseResult>() {
            @Override
            public void onRequestSucceed(BaseResult result) {
//                ToastUtils.showToast("登录成功之后onRequestSucceed---"+result.getmMessage());
                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.LOGIN_ADD_BIND_SCCESS);
            }

            @Override
            public void onRequestFailed(BaseResult result) {
                ToastUtils.showToast(result.getmMessage());
            }
        });

    }

    public static void updateShow(String ids, String trace_id){
        ParamsApi.updateShow(ids,trace_id).execute(new BaseRequestCallback<UpdateVersionBean>() {
            @Override
            public void onRequestSucceed(UpdateVersionBean result) {

            }

            @Override
            public void onRequestFailed(UpdateVersionBean result) {

            }
        });



//        ParamsApi.requestLogin(ids,head_icon,nick,from).execute(new BaseRequestCallback<BaseResult>() {
//            @Override
//            public void onRequestSucceed(BaseResult result) {
////                ToastUtils.showToast("登录成功之后onRequestSucceed---"+result.getmMessage());
//                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.LOGIN_ADD_BIND_SCCESS);
//            }
//
//            @Override
//            public void onRequestFailed(BaseResult result) {
//                ToastUtils.showToast(result.getmMessage());
//            }
//        });

    }




    public static void checkVersion(final boolean isShow, final Context context,final String nitificationTitle,final String nitificationDescription, final OnUpdateVersionListener listener){
        ParamsApi.updateVersion().execute(new RequestCallback<UpdateVersionBean>() {
            @Override
            public void onRequestSuccess(UpdateVersionBean dataResult) {
                if(isShow){
                    int un_read = dataResult.getSdata().getUnread_num();
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.UMENG_BEDGE_NUMBER, un_read);
                }else {
                    if (listener != null) {
                        if (Integer.parseInt(dataResult.getData().get(0).getVersionCode()) > AppUtils.getVersionNumber(context)) {
                            ShowOnkeyDowDialog(context, Integer.parseInt(dataResult.getData().get(0).getIsForce()), dataResult.getData().get(0).getMsg(), dataResult.getData().get(0).getDownloadUrl(), nitificationTitle, nitificationDescription, listener);
                        } else {
                            listener.NoNeedUpdateVersion();
                        }
                    }
                }
            }

            @Override
            public void onRequestFailure(UpdateVersionBean dataResult) {
                if (listener != null) {
                    listener.NoNeedUpdateVersion();
                }
            }
        });
    }
    public static void logoutDetail(String articleId, String bhv) {
        ParamsApi.logoutDetail(articleId, bhv).execute(new RequestCallback<BaseResult>() {
            @Override
            public void onRequestSuccess(BaseResult dataResult) {

            }

            @Override
            public void onRequestFailure(BaseResult dataResult) {

            }
        });
    }

    // 退出直播间Dialog
    private static void ShowOnkeyDowDialog(Context context,final int isForce, String msg, final String url,final String nitificationTitle,final String nitificationDescription, final OnUpdateVersionListener listener) {
        final Dialog dialog = new Dialog(context, com.famlink.frame.R.style.dialog);
        dialog.setContentView(com.famlink.frame.R.layout.update_version);
        dialog.setOnKeyListener(keylistener);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        ((TextView) dialog.findViewById(com.famlink.frame.R.id.title_content)).setText(msg);
        if(isForce == 1){
            ((Button) dialog.findViewById(com.famlink.frame.R.id.cancel)).setVisibility(View.GONE);
        }else{
            ((Button) dialog.findViewById(com.famlink.frame.R.id.cancel)).setVisibility(View.VISIBLE);
        }
        ((Button) dialog.findViewById(com.famlink.frame.R.id.ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (isForce != 1) {
                    if (listener != null) {
                        listener.NoNeedUpdateVersion();
                    }
                }
                UpdateVersion updateVersion = new UpdateVersion(url, nitificationTitle, nitificationDescription);
                updateVersion.execute();
//				new ApkDownLoad(getApplicationContext(), url, "XiMi版本升级", "XiMi正在下载新版本").execute();
            }
        });
        ((Button) dialog.findViewById(com.famlink.frame.R.id.cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (listener != null) {
                    listener.NoNeedUpdateVersion();
                }
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    private static DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };
}
