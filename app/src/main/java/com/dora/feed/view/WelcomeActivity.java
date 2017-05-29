package com.dora.feed.view;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dora.feed.net.ParamsApi;
import com.dora.feed.utils.MyPushIntentService;
import com.famlink.frame.util.CacheUtils;
import com.famlink.frame.util.LocalContents;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.IssueKey;
import com.famlink.frame.view.activity.BaseActivity;
import com.dora.feed.R;
import com.dora.feed.view.adapter.BasePagerAdapter;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by admin on 2016/7/11.
 */
public class WelcomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @ViewInject(R.id.viewpage)
    ViewPager viewPager;
    @ViewInject(R.id.start_Button)
    Button start_button;

    private int[] images;
    private PagerAdapter pagerAdapter;
    private ArrayList<View> views;
    private ImageView[] indicators = null;
    private PushAgent mPushAgent;
    @Override
    public int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void setInterfaceView() {
        initPush();
        images = new int[] { R.drawable.welcome_1, R.drawable.welcome_2,
                R.drawable.welcome_3,R.drawable.welcome_4};

        views = new ArrayList<View>();
        indicators = new ImageView[images.length]; // 定义指示器数组大小
        for (int i = 0; i < images.length; i++) {
            // 循环加入图片
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(images[i]);
            views.add(imageView);
            // 循环加入指示器
//            indicators[i] = new ImageView(context);
//            indicators[i].setBackgroundResource(R.drawable.indicators_default);
//            if (i == 0) {
//                indicators[i].setBackgroundResource(R.drawable.indicators_now);
//                indicatorLayout.addView(indicators[i]);
//                indicators[i].setLayoutParams(setParams(12,45));
//                continue;
//            }
//            indicatorLayout.addView(indicators[i]);
//            indicators[i].setLayoutParams(setParams(12,12));
        }

        pagerAdapter = new BasePagerAdapter(views);
        viewPager.setAdapter(pagerAdapter); // 设置适配器
        viewPager.setOnPageChangeListener(this);

    }
    @Event(value = R.id.start_Button, type = View.OnClickListener.class)
    private void onClick(View view) {
        if(view.getId() == R.id.start_Button){

//            SharedPreferences shared = new SharedConfig(this).GetConfig();
//            Editor editor = shared.edit();
//            editor.putBoolean("First", false);
//            editor.commit();
            startActivity(new Intent(this, DrawLayoutActivity.class));
            finish();
        }

    }

    @Override
    public void setGenericNodataOrNonetwork() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == indicators.length - 1) {
            start_button.setVisibility(View.VISIBLE);
            //indicatorLayout.setVisibility(View.GONE);
        } else {
            start_button.setVisibility(View.INVISIBLE);
            //indicatorLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void initPush(){
        mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        mPushAgent.onAppStart();
        mPushAgent.enable(mRegisterCallback);
        mPushAgent.setDebugMode(false);
        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
        mPushAgent.getMessageHandler();
    }
    IUmengRegisterCallback mRegisterCallback = new IUmengRegisterCallback() {
        @Override
        public void onRegistered(String registrationId) {
            //onRegistered方法的参数registrationId即是device_token
            System.out.println("device_token:--" + registrationId);
            CacheUtils.getInstance().putString(LocalContents.UMENG_DEVICE_TOKEN, registrationId);
            CacheUtils.getInstance().putBoolean(LocalContents.LOCAL_UMENG_STATUS,true);
            ParamsApi.initApi();//设备注册
            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.UMENG_PUSH_OPEN);
        }
    };

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(images!=null)
            images=null;
        if(views!=null)
            views.clear();
    }
}
