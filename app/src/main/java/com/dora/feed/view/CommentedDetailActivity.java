package com.dora.feed.view;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.famlink.frame.view.activity.BaseActivity;
import com.dora.feed.R;

import org.xutils.view.annotation.ViewInject;

/**
 * 评论详情页
 * Created by admin on 2016/7/22.
 */
public class CommentedDetailActivity extends BaseActivity{

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int setLayout() {
        return R.layout.activity_commentdetail;
    }

    @Override
    public void setInterfaceView() {
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public void setGenericNodataOrNonetwork() {

    }
}
