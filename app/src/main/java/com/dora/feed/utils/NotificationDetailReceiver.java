package com.dora.feed.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dora.feed.view.DetailsX5Activity;
import com.dora.feed.view.DrawLayoutActivity;
import com.famlink.frame.util.SysoutUtil;

/**
 * Created by liangzili on 15/8/3.
 */
public class NotificationDetailReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        SysoutUtil.out("收到了Notification的广播");
        //判断app进程是否存活
        if(SystemUtils.isAppAlive(context, "com.dora.feed")){
            android.util.Log.i("NotificationReceiver", "the app process is alive");
            Intent mainIntent = new Intent(context, DrawLayoutActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Intent detailIntent = new Intent(context, DetailsX5Activity.class);
            intent.putExtra("intentPosition", -2);
            SysoutUtil.out("aid----" + intent.getStringExtra("intentArticleId") + "---title---" + intent.getExtras().getString("intentArticleId"));
            detailIntent.putExtra("intentArticleId", intent.getStringExtra("intentArticleId"));
            detailIntent.putExtra("intentTitle", intent.getStringExtra("intentTitle"));
            detailIntent.putExtra("intentIcon", intent.getStringExtra("intentIcon"));
            detailIntent.putExtra("intentReadCount", intent.getStringExtra("look_num"));
            detailIntent.putExtra("intentTime", intent.getStringExtra("intentTime"));
            detailIntent.putExtra("intentCommentCount", intent.getStringExtra("intentCommentCount"));
            detailIntent.putExtra("intentPublicUrl", intent.getStringExtra("intentPublicUrl"));
            detailIntent.putExtra("intentTraceId", intent.getStringExtra("intentTraceId"));
            detailIntent.putExtra("intentIsNotification", intent.getStringExtra("intentIsNotification"));

            Intent[] intents = {mainIntent, detailIntent};
            context.startActivities(intents);
        }else {
            android.util.Log.i("NotificationReceiver", "the app process is dead");
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.dora.feed");
            launchIntent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            Bundle args = new Bundle();
            launchIntent.putExtra("launchBundle", intent.getExtras());
            context.startActivity(launchIntent);
        }
    }
}