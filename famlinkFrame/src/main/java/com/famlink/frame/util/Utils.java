package com.famlink.frame.util;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.famlink.frame.R;
import com.famlink.frame.mvp.bean.UpdateVersionBean;
import com.famlink.frame.net.ParamsApi;
import com.famlink.frame.net.biz.RequestCallback;
import com.famlink.frame.view.activity.BaseActivity;
import com.famlink.frame.widget.BadgeView;
import com.famlink.frame.widget.UpdateVersion;
import com.famlink.frame.widget.dialog.LogoutDialog;
//import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
//import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
//import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
//import com.tencent.mm.sdk.openapi.IWXAPI;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {

	public interface OnUpdateVersionListener{
		/**
		 * 不需要升级
		 */
		void NoNeedUpdateVersion();
	}

	/**
	 * 退出登录确认按钮
	 */
	public interface onLogoutListener{
		void onConfirm();
	}

	/**
	 * 设置添加完购物车的小红点
	 * @param number   //显示的小红点数字
	 * @param view     //具体要显示在哪个view上
	 * @param textSize  //小红点数字的大小
	 */
	public static  void ShowBadgeView(Context context, int number,View view,int textSize){
		BadgeView badgeView=new BadgeView(context);
		badgeView.setTargetView(view);
		badgeView.setTextSize(textSize);
		badgeView.setTextColor(Color.WHITE);
		badgeView.setAlpha(1f);
		if(number > 99){
			number = 99;
		}
		badgeView.setBadgeCount(number);
	}

	/**
	 * 获取Context上下文
	 * @return
	 */
	public static Context getContext() {
		return BaseActivity.getContext();
	}

	/**
	 * 获取Activity上下文
	 * @return
	 */
	public static Activity getActivity() {
		return BaseActivity.getActivity();
	}

	/**
	 * 从res的Drawable文件中通过id获取资源
	 * @param id
	 * @return
	 */
	public static Drawable getDrawable(Context context, int id) {
		return context.getResources().getDrawable(id);
	}

	/**
	 * 从res的color文件中通过id获取资源
	 * @param id
	 * @return
	 */
	public static int getColor(Context context, int id) {
		return context.getResources().getColor(id);
	}

	/**
	 * 从res的getString文件中通过id获取资源
	 * @param id
	 * @return
	 */
	public static String getString(Context context, int id) {
		return context.getResources().getString(id);
	}

	/**
	 *  从res的getStringArray文件中通过id获取资源
	 * @param id
	 * @return
	 */
	public static String[] getStringArray(Context context, int id) {
		return context.getResources().getStringArray(id);
	}


	public static void logout(final Context context, final onLogoutListener listener){
		LogoutDialog logoutDialog = new LogoutDialog(context, new LogoutDialog.onClickListener() {
			@Override
			public void onClickCancel() {

			}

			@Override
			public void onClickConfirm() {
				if(listener != null){
					listener.onConfirm();
				}
			}
		});
		logoutDialog.show();
	}

//	public static void checkVersion(final Context context,final String nitificationTitle,final String nitificationDescription, final OnUpdateVersionListener listener){
//		ParamsApi.updateVersion().execute(new RequestCallback<UpdateVersionBean>() {
//			@Override
//			public void onRequestSuccess(UpdateVersionBean dataResult) {
//				if (listener != null) {
//					if (Integer.parseInt(dataResult.getData().get(0).getVersionCode()) > AppUtils.getVersionNumber(context)) {
//						ShowOnkeyDowDialog(context, Integer.parseInt(dataResult.getData().get(0).getIsForce()), dataResult.getData().get(0).getMsg(), dataResult.getData().get(0).getDownloadUrl(), nitificationTitle, nitificationDescription, listener);
//					} else {
//						listener.NoNeedUpdateVersion();
//					}
//				}
//
//			}
//
//			@Override
//			public void onRequestFailure(UpdateVersionBean dataResult) {
//				if (listener != null) {
//					listener.NoNeedUpdateVersion();
//				}
//			}
//		});
//	}

//	// 退出直播间Dialog
//	private static void ShowOnkeyDowDialog(Context context,final int isForce, String msg, final String url,final String nitificationTitle,final String nitificationDescription, final OnUpdateVersionListener listener) {
//		final Dialog dialog = new Dialog(context, R.style.dialog);
//		dialog.setContentView(R.layout.update_version);
//		dialog.setOnKeyListener(keylistener);
//		dialog.setCancelable(false);
//		dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
//		((TextView) dialog.findViewById(R.id.title_content)).setText(msg);
//		if(isForce == 1){
//			((Button) dialog.findViewById(R.id.cancel)).setVisibility(View.GONE);
//		}else{
//			((Button) dialog.findViewById(R.id.cancel)).setVisibility(View.VISIBLE);
//		}
//		((Button) dialog.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				dialog.dismiss();
//				if (isForce != 1) {
//					if (listener != null) {
//						listener.NoNeedUpdateVersion();
//					}
//				}
//				UpdateVersion updateVersion = new UpdateVersion(url, nitificationTitle, nitificationDescription);
//				updateVersion.execute();
////				new ApkDownLoad(getApplicationContext(), url, "XiMi版本升级", "XiMi正在下载新版本").execute();
//			}
//		});
//		((Button) dialog.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				dialog.dismiss();
//				if (listener != null) {
//					listener.NoNeedUpdateVersion();
//				}
//			}
//		});
//		dialog.setCanceledOnTouchOutside(false);
//		dialog.show();
//	}

//	private static DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
//		public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//			if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//				return true;
//			} else {
//				return false;
//			}
//		}
//	};

	/**
	 * Drawable转换Bitmap
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitamp(Drawable drawable){
		BitmapDrawable bd = (BitmapDrawable) drawable;
		return bd.getBitmap();
	}



}