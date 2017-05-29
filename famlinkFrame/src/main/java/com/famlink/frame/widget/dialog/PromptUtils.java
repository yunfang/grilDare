package com.famlink.frame.widget.dialog;

import android.content.Context;

/**
 * 正在加载的dialog
 * Created by Administrator on 2016/6/21.
 */
public class PromptUtils {
    private static LoadingPromptDialog sProgressDialog;



    /**
     * 显示一个进度对话框
     *
     * @param context 上下文对象
     * @param resId   提示信息资源ID
     */
    public static synchronized void showProgressDialog(Context context, int resId) {
        showProgressDialog(context, context.getResources().getString(resId), true);
    }

    /**
     * 显示一个进度对话框
     *
     * @param context 上下文对象
     * @param message 提示信息
     */
    public static synchronized void showProgressDialog(Context context, String message) {
        showProgressDialog(context, message, true, true);
    }

    /**
     * 显示一个进度对话框
     *
     * @param context                      上下文对象
     * @param resId                        提示信息资源ID
     * @param canceledOnTouchOutsideEnable 是否允许触摸对话框以外的地方，关闭对话框
     */
    public static synchronized void showProgressDialog(Context context, int resId, boolean canceledOnTouchOutsideEnable) {
        showProgressDialog(context, context.getResources().getString(resId), canceledOnTouchOutsideEnable, true);
    }

    /**
     * 显示一个进度对话框
     *
     * @param context                      上下文对象
     * @param message                      提示信息
     * @param canceledOnTouchOutsideEnable 是否允许触摸对话框以外的地方，关闭对话框
     */
    public static synchronized void showProgressDialog(Context context, String message, boolean canceledOnTouchOutsideEnable) {
        showProgressDialog(context, message, canceledOnTouchOutsideEnable, true);
    }

    /**
     * 显示一个进度对话框
     *
     * @param context                      上下文对象
     * @param resId                        提示信息资源ID
     * @param canceledOnTouchOutsideEnable 是否允许触摸对话框以外的地方，关闭对话框
     * @param cancel                       点击back键时，是否关闭对话框
     */
    public static synchronized void showProgressDialog(Context context, int resId, boolean canceledOnTouchOutsideEnable, boolean cancel) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null!!");
        }
        showProgressDialog(context, context.getString(resId), canceledOnTouchOutsideEnable, cancel);
    }

    /**
     * 显示一个进度对话框
     *
     * @param context                      上下文对象 此处的上下文对象必须使用对应调取Activity的上下文，不然报错
     * @param message                      提示信息
     * @param canceledOnTouchOutsideEnable 是否允许触摸对话框以外的地方，关闭对话框
     * @param cancel                       点击back键时，是否关闭对话框
     */
    public static synchronized void showProgressDialog(Context context, String message, boolean canceledOnTouchOutsideEnable, boolean cancel) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null!!");
        }

        try {
            if (sProgressDialog != null) {
                sProgressDialog.dismiss();
                sProgressDialog = null;
            }

            sProgressDialog = new LoadingPromptDialog(context);
            sProgressDialog.setCanceledOnTouchOutside(canceledOnTouchOutsideEnable);
            sProgressDialog.setCancelable(cancel);
            sProgressDialog.setLoadingText(message);
            sProgressDialog.show();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭对话框
     */
    public static void dismissProgressDialog() {
        try {
            if (sProgressDialog != null) {
                sProgressDialog.dismiss();
                sProgressDialog = null;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 进度对话框是否在显示中
     *
     * @return true - 显示中
     */
    public static boolean isProgressDialogShowing() {
        return sProgressDialog != null && sProgressDialog.isShowing();
    }

}
