package com.famlink.frame.widget.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.famlink.frame.R;
import com.famlink.frame.util.AccessTokenKeeper;
import com.famlink.frame.util.Content;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.LogoutAPI;
//import com.sina.weibo.sdk.openapi.LogoutAPI;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 屏幕中间弹出的dialog
 * Created by Administrator on 2016/6/21.
 */
public class LogoutDialog extends  BaseDialog implements View.OnClickListener {
    private Context context;
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.dialog_cancel){
            if(listener != null){
                listener.onClickCancel();
                dismiss();
            }

        }else if(v.getId() == R.id.dialog_comfirm){
            if(listener != null){
                listener.onClickConfirm();
                dismiss();
            }
        }
    }
    private onClickListener listener;
    public interface onClickListener{
        void onClickCancel();
        void onClickConfirm();
    }
    public LogoutDialog(Context context, onClickListener listener) {
        super(context, R.layout.logout_dialog);
        this.listener = listener;
        this.context = context;

        findViewById(R.id.dialog_cancel).setOnClickListener(this);
        findViewById(R.id.dialog_comfirm).setOnClickListener(this);
    }

}
