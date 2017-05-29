package com.famlink.frame.view.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.famlink.frame.R;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by admin on 2016/7/6.
 * 关于页面
 */
public class AboutActivity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    public int setLayout() {
        return R.layout.activity_about;
    }

    @Override
    public void setInterfaceView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back); //更改菜单图标
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
