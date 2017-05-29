package com.famlink.frame.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.famlink.frame.view.activity.Card2Activity;
import com.famlink.frame.view.activity.Student;

import java.util.List;

/**
 * Created by wangkai on 16/7/1.
 */
public class MyHandler {
    Context context;
    List<Student> mList;
    public MyHandler(Context context, List<Student> mList) {
        this.context = context;
        this.mList = mList;
    }

    public void onMyClick(View v){
        System.out.println("mList:" + mList.size());
        Intent intent = new Intent(context, Card2Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
