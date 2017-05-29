package com.famlink.frame.util;

import android.widget.Toast;

/**
 * Toast处理类
 * Created by wangkai on 16/6/21.
 */
public class ToastUtils{

    private static Toast mToast;
    public static void showToast(String text) {
        if(mToast == null) {
            mToast = Toast.makeText(Utils.getContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    /**
     * 如何按返回键想让Toast立即不显示，执行该方法
     */
    public static void onBackPressed() {
        cancelToast();
    }
}
