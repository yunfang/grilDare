package com.famlink.frame.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.famlink.frame.R;
import com.famlink.frame.core.ModuleFactory;
import com.famlink.frame.core.module.AbsModule;
import com.famlink.frame.core.module.IOCProxy;
import com.famlink.frame.util.ActivityManager;
import com.famlink.frame.util.NetUtils;
import com.famlink.frame.util.StringUtil;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.ObserverGroup;

import org.xutils.x;

/**
 * Created by wangkai on 16/6/15.
 */
public abstract class BaseDataBindingActivity<VB extends ViewDataBinding>  extends AppCompatActivity {
    public static Activity activity;
    public static Context context;

    public static final int TYPE_ITEM_VERTICAL = 1;   //横向的Card布局
    public static final int TYPE_ITEM_HORIZONTAL = TYPE_ITEM_VERTICAL*2; //竖向的Card布局

    private View generic_loading_dialog; //正在加载界面‘
    private View generic_no_data; //没有数据界面
    private View generic_no_network;  //没有网络界面
    protected ObserverGroup mObserverGroup;  //通知的消息队列

    private View generic_no_data_refresh;
    private View generic_no_network_refresh;
    private View generic_no_network_setting;



    protected String TAG = "";
    private VB       mBind;
    private IOCProxy mProxy;
    private ModuleFactory mModuleF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBind = DataBindingUtil.setContentView(this, setLayout());
        mProxy = IOCProxy.newInstance(this);
        TAG = StringUtil.getClassName(this);
        mModuleF = ModuleFactory.newInstance();
        activity = this;
        context = getApplicationContext();
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
        generic_no_data.setVisibility(View.GONE);
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
        generic_no_data_refresh = (View) findViewById(R.id.no_data_refresh);
        if(isShow){
            generic_no_data_refresh.setVisibility(View.VISIBLE);
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
        generic_no_network = (View) findViewById(R.id.no_network);
        noNetworkRefresh(isShow);
        return generic_no_network;
    }

    private View noNetworkRefresh(boolean isShow){
        generic_no_network_refresh = (View) findViewById(R.id.no_network_refresh);
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
        generic_no_network_setting = (View) findViewById(R.id.generic_no_network_setting);
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






    /**
     * 获取binding对象
     */
    protected VB getBinding() {
        return mBind;
    }

    /**
     * 获取Module
     *
     * @param clazz {@link AbsModule}
     */
    protected <M extends AbsModule> M getModule(Class<M> clazz) {
        M module = mModuleF.getModule(this, clazz);
        mProxy.changeModule(module);
        return module;
    }

    /**
     * 数据回调
     *
     * @param result
     * @param data
     */
    protected abstract void dataCallback(int result, Object data);

}
