package com.dora.feed.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.dora.feed.R;
import com.dora.feed.config.Constants;
import com.famlink.frame.util.SysoutUtil;
import com.umeng.common.message.Log;
import com.umeng.message.UTrack;
import com.umeng.message.UmengBaseIntentService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.client.BaseConstants;
import org.json.JSONObject;

/**
 * Developer defined push intent service.
 * Remember to call {@link com.umeng.message.PushAgent#setPushIntentServiceClass(Class)}.
 *
 * @author lucas
 */
//完全自定义处理类
//参考文档的1.6.5
//http://dev.umeng.com/push/android/integration#1_6_5
public class MyPushIntentService extends UmengBaseIntentService {
    private static final String TAG = MyPushIntentService.class.getName();

    @Override
    protected void onMessage(Context context, Intent intent) {
        // 需要调用父类的函数，否则无法统计到消息送达
        SysoutUtil.out("------------MyPushIntentService------------");
        super.onMessage(context, intent);
        try {
            //可以通过MESSAGE_BODY取得消息体
            String message = intent.getStringExtra(BaseConstants.MESSAGE_BODY);
            SysoutUtil.out("messageWK-----------------------" + message);
            UMessage msg = new UMessage(new JSONObject(message));
            SysoutUtil.out("message=" + message);    //消息体
            SysoutUtil.out("custom=" + msg.custom);    //自定义消息的内容
            SysoutUtil.out("title=" + msg.title);    //通知标题
            SysoutUtil.out("text=" + msg.text);    //通知内容
            SysoutUtil.out("text=" + msg.extra);    //键值对
            // code  to handle message here
            // ...
            String activity = msg.activity;//点击通知栏需要跳转的页面


            Intent intentAct = new Intent();
            if(msg.extra.size() == 1){

                SysoutUtil.out("umengNumber-----------" + msg.extra.get("un_read"));
                intentAct.setClassName(this, "com.dora.feed.view.SplashActivity");
                intentAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //判断app进程是否存活
                if(SystemUtils.isAppAlive(context, "com.dora.feed")){

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Constants.SEND_BROADCAST_DRAW);
                    sendIntent.putExtra("umengNumber", Integer.valueOf(msg.extra.get("un_read")));
                    sendBroadcast(sendIntent);
                }else {
                    showNotification(context, msg, intentAct);//必须要有，不然收不到推送的消息
                }

            }else{
                showDetailNotification(context, msg);

            }
//            if(!message.contains("extra")){
//                SharedPreferences mSettings = context.getSharedPreferences(CacheUtils.PREF_NAME, Context.MODE_PRIVATE);
//                int umengNumber = mSettings.getInt(LocalContents.UMENG_NOTIFICATION_NUMBER, 0);
//                umengNumber = umengNumber+1;
//
//
//                SharedPreferences.Editor edit = mSettings.edit();
//                edit.putInt(LocalContents.UMENG_NOTIFICATION_NUMBER, umengNumber);
//                edit.commit();
////                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.UMENG_BEDGE_NUMBER, umengNumber);
//                SysoutUtil.out("umengNumber-----------" + umengNumber);
//                intentAct.setClassName(this, "com.dora.feed.view.SplashActivity");
//                intentAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                //判断app进程是否存活
//                if(SystemUtils.isAppAlive(context, "com.dora.feed")){
//
//                    Intent sendIntent = new Intent();
//                    sendIntent.setAction(Constants.SEND_BROADCAST_DRAW);
//                    sendIntent.putExtra("umengNumber", umengNumber);
//                    sendBroadcast(sendIntent);
//                }else {
//                    showNotification(context, msg, intentAct);//必须要有，不然收不到推送的消息
//                }
//
//            }else{
//                showDetailNotification(context, msg);
//
//            }
            // 对完全自定义消息的处理方式，点击或者忽略
            boolean isClickOrDismissed = true;
            if (isClickOrDismissed) {
                //完全自定义消息的点击统计
                UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
            } else {
                //完全自定义消息的忽略统计
                UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    // 通知栏显示当前播放信息，利用通知和 PendingIntent来启动对应的activity
    public void showNotification(Context context, UMessage msg, Intent intent) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        builder.setAutoCancel(true);
//        Notification mNotification = builder.build();
//        mNotification.icon = R.drawable.app_logo;//notification通知栏图标
//        mNotification.defaults |= Notification.DEFAULT_SOUND;
//        mNotification.defaults |= Notification.DEFAULT_VIBRATE;
//        mNotification.tickerText = msg.title;
//        //自定义布局
//        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.activity_umeng_push);
//        contentView.setImageViewResource(R.id.umeng_view, R.drawable.app_logo);
//        contentView.setTextViewText(R.id.push_title, msg.title);
//        contentView.setTextViewText(R.id.push_content, msg.text);
//        mNotification.contentView = contentView;
//        mNotification.bigContentView = contentView; // 防止显示不完全,需要添加apisupport

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);//不是Intent
        Notification mNotification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.app_logo)
                .setTicker("多拉阅读")
                .setContentTitle(msg.title)
                .setContentText(msg.text)
                .setDefaults(Notification.DEFAULT_ALL)
//                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent).build(); // 需要注意build()是在API


        //notifcation.flags = Notification.FLAG_NO_CLEAR;// 永久在通知栏里
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
        //使用自定义下拉视图时，不需要再调用setLatestEventInfo()方法，但是必须定义contentIntent
        mNotification.contentIntent = contentIntent;

        mNotificationManager.notify(103, mNotification);
    }
    // 通知栏显示当前播放信息，利用通知和 PendingIntent来启动对应的activity
    public void showDetailNotification(Context context, UMessage msg) {

        Intent broadcastIntent = new Intent(context, NotificationDetailReceiver.class);
        broadcastIntent.putExtra("intentArticleId", msg.extra.get("aid"));
        broadcastIntent.putExtra("intentTitle", msg.extra.get("title"));
        broadcastIntent.putExtra("intentIcon", msg.extra.get("master_img"));
        broadcastIntent.putExtra("intentTime", msg.extra.get("atime"));
        broadcastIntent.putExtra("intentCommentCount", msg.extra.get("commend_num"));
        broadcastIntent.putExtra("intentReadCount", msg.extra.get("look_num"));
        broadcastIntent.putExtra("intentPublicUrl", msg.extra.get("public_url"));
        broadcastIntent.putExtra("intentTraceId", msg.extra.get("trace_id"));
        broadcastIntent.putExtra("intentIsNotification", "true");



        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        builder.setAutoCancel(true);
//        Notification mNotification = builder.build();
//        mNotification.icon = R.drawable.app_logo;
//        mNotification.defaults |= Notification.DEFAULT_SOUND;
//        mNotification.defaults |= Notification.DEFAULT_VIBRATE;
//        mNotification.tickerText = msg.title;
//        //自定义布局
//        final RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.activity_umeng_push);
//        contentView.setImageViewResource(R.id.umeng_view, R.drawable.app_logo);
//        contentView.setTextViewText(R.id.push_title, msg.title);
//        contentView.setTextViewText(R.id.push_content, msg.text);
//        mNotification.contentView = contentView;
//        mNotification.bigContentView = contentView; // 防止显示不完全,需要添加apisupport

        PendingIntent contentIntent = PendingIntent.getBroadcast(context, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);//不是Intent
        Notification mNotification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.app_logo)
                .setTicker("多拉阅读")
                .setContentTitle(msg.title)
                .setContentText(msg.text)
                .setDefaults(Notification.DEFAULT_ALL)
//                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent).build(); // 需要注意build()是在API

        //notifcation.flags = Notification.FLAG_NO_CLEAR;// 永久在通知栏里
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
        //使用自定义下拉视图时，不需要再调用setLatestEventInfo()方法，但是必须定义contentIntent
        mNotification.contentIntent = contentIntent;

        mNotificationManager.notify(103, mNotification);
    }
}
