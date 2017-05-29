package com.famlink.frame.view.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.famlink.frame.R;
import com.famlink.frame.util.ActivityManager;
import com.famlink.frame.util.NetUtils;
import com.famlink.frame.util.SysoutUtil;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.ObserverGroup;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.xutils.x;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by wangkai on 16/6/15.
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static Activity activity;
    public static Context context;

    public static final int TYPE_ITEM_VERTICAL = 1;   //横向的Card布局
    public static final int TYPE_ITEM_HORIZONTAL = TYPE_ITEM_VERTICAL*2; //竖向的Card布局


    private View generic_no_data; //没有数据界面
    private View generic_no_network;  //没有网络界面
    private View generic_no_data_refresh;
    private ImageView no_data_img;
    private View generic_no_network_refresh;
    private View generic_no_network_setting;

    private  TextView textTitle, textContent;

    private AnimationDrawable animationDrawable;
    protected ObserverGroup mObserverGroup;  //通知的消息队列

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        long start = System.currentTimeMillis();
        context = getApplicationContext();
        setContentView(setLayout());
//        long end = System.currentTimeMillis();
//        SysoutUtil.out(end - start);

        activity = this;

        x.view().inject(this);
        ActivityManager.instance().onCreate(this);
        // 默认全屏显示
        
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        // 不全屏显示
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        // 全屏显示
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        setInterfaceView();
        setGenericNodataOrNonetwork();

    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

    /**
     * 设置界面XML 也就是Layout
     * @return
     */
    public abstract int setLayout();

    /**
     * 实例化界面的各种自定义接口
     */
    public abstract void setInterfaceView();

    /**
     * 无数据或者是无网络时的空白界面或者是无网络界面的实现
     */
    public abstract void setGenericNodataOrNonetwork();

    public static Activity getActivity() {
        return activity;
    }

    public static Context getContext() {
        return context;
    }



    public void instantiationNoDataNetWork(){
        instantiationNoData();
        instantiationNoNetWork();
    };
    /**
     * 没有数据的界面实例化
     */
    private void instantiationNoData(){
        generic_no_data = (View) findViewById(R.id.no_data);
        generic_no_data_refresh = (View) findViewById(R.id.no_data_refresh);
        no_data_img = (ImageView) findViewById(R.id.no_data_img);
        textTitle = ((TextView) findViewById(R.id.textTitle));
        textContent = ((TextView) findViewById(R.id.textContent));

        //初始化动画
        no_data_img.setImageResource(R.drawable.loading);
        animationDrawable = (AnimationDrawable) no_data_img.getDrawable();
        animationDrawable.start();
    }

    /**
     * 没有数据的界面实例化
     */
    private void instantiationNoNetWork(){
        generic_no_network = (View) findViewById(R.id.no_network);
        generic_no_network_refresh = (View) findViewById(R.id.no_network_refresh);
        generic_no_network_setting = (View) findViewById(R.id.generic_no_network_setting);
    }

    /**
     * 无数据界面显示
     */
    public void showGenericNodata(){
        generic_no_data.setVisibility(View.VISIBLE);
    }

    /**
     * 无数据界面隐藏
     */
    public void hideGenericNodata(){
        if(animationDrawable != null){
            animationDrawable.stop();
        }
        generic_no_data.setVisibility(View.GONE);
    }
    /**
     *
     * @param isShow  是否显示刷新按钮
     * @param msg    更改Tips
     * @param resourdId   更改图片
     */
    public void ChangeNoData(boolean isShow, String msg, int resourdId){
        if(animationDrawable != null){
            animationDrawable.stop();
        }
        if(!TextUtils.isEmpty(msg)){
            textTitle.setText(msg);
            no_data_img.setImageResource(resourdId);
        }
        noDataRefresh(isShow);
    }



    /**
     * 无网络界面显示
     */
    public void showGenericNoNetwork(){
        generic_no_network.setVisibility(View.VISIBLE);
    }

    /**
     * 无网络界面隐藏
     */
    public void hideGenericNoNetwork(){
        generic_no_network.setVisibility(View.GONE);
    }

    /**
     * 通用的无数据的界面
     * @param isShow true 重新刷新按钮显示   //   false   重新刷新按钮不显示
     *
     * @param msg      界面中没有数据的提示语
     * @param content  界面中没有数据的内容提示语
     */
    public View genericNoData(boolean isShow, String msg, String content){
        generic_no_data = (View) findViewById(R.id.no_data);
        if(!TextUtils.isEmpty(msg)){
            ((TextView)findViewById(R.id.textTitle)).setText(msg);
            ((TextView)findViewById(R.id.textContent)).setText(content);
        }
        noDataRefresh(isShow);
        return generic_no_data;
    }

    private View noDataRefresh(boolean isShow){
        if(isShow){
            generic_no_data_refresh.setVisibility(View.VISIBLE);
            animationDrawable = (AnimationDrawable) no_data_img.getDrawable();
            animationDrawable.start();
            generic_no_data_refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeRefresh.onChangeRefresh();
                }
            });

        }else{
            generic_no_data_refresh.setVisibility(View.GONE);
        }

        return generic_no_data;
    }
    /**
     * 没有网络的界面
     * @return  isShow true 重新刷新按钮显示   //   false   重新刷新按钮不显示
     */
    public View genericNoNetwork(boolean isShow){
        noNetworkRefresh(isShow);
        return generic_no_network;
    }

    private View noNetworkRefresh(boolean isShow){
        if(isShow){
            generic_no_network_refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeRefresh.onChangeRefresh();
                }
            });

        }else{
            generic_no_network_refresh.setVisibility(View.GONE);
        }
        return generic_no_network;
    }

    private NoDataNetWorkChangeRefreshListener changeRefresh;

    public void setChangeRefresh(NoDataNetWorkChangeRefreshListener changeRefresh) {
        this.changeRefresh = changeRefresh;
    }

    public interface NoDataNetWorkChangeRefreshListener{
        void onChangeRefresh();
    }


    /**
     * 设置顶部无网络条是否显示
     * @param isHide  true 显示   ///   false  不显示
     *              如果显示的话点击右侧进入setting界面设置网络
     */
    private boolean isClickSetting = false;
    public void genericNoNetworkSetting(boolean isHide){
        if(isHide) {
            generic_no_network_setting.setVisibility(View.VISIBLE);
            generic_no_network_setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("ssss", "ss");
                    isClickSetting = true;
                    Intent wifiSettingsIntent = new Intent("android.settings.WIFI_SETTINGS");
                    startActivity(wifiSettingsIntent);
                }
            });
        }else {
            generic_no_network_setting.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(NetUtils.isNetworkConnected()){     //如果设置过网络返回过来的时候会重新刷新界面隐藏条形设置网络
            if(isClickSetting){
                if(generic_no_network_setting != null){
                    if(generic_no_network_setting.getVisibility() == View.VISIBLE){
                        generic_no_network_setting.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.instance().onDestroy(this);
        removeObserverGroup();
    }
    protected void removeObserverGroup() {
        if (mObserverGroup != null) {
            DataChangeNotification.getInstance().removeObserver(mObserverGroup);
            mObserverGroup = null;
        }
    }
}
