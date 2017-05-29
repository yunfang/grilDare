package com.dora.feed.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.widget.ImageView;

import com.dora.feed.config.Constants;
import com.dora.feed.net.RequestUtils;
import com.dora.feed.utils.CrashHandler;
import com.famlink.frame.util.AndroidUtils;
import com.famlink.frame.util.CacheUtils;
import com.famlink.frame.util.LocalContents;
import com.famlink.frame.util.NetUtils;
import com.famlink.frame.util.SysoutUtil;
import com.famlink.frame.util.Utils;
import com.famlink.frame.view.activity.BaseActivity;
import com.dora.feed.R;
import com.dora.feed.net.ParamsApi;
import com.famlink.frame.widget.dialog.PromptUtils;

/**
 * Created by admin on 2016/7/7.
 */
public class SplashActivity extends BaseActivity {
    private ImageView title;
    @Override
    public int setLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void setInterfaceView() {
        title = (ImageView) findViewById(R.id.title);
        NetUtils.init(activity);
        CacheUtils.getInstance().putString(LocalContents.SCREEM_W_H, AndroidUtils.getMetrics(activity));
//        initApi();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }

    /**
     * 设备注册
     */
    private void initApi() {
        ParamsApi.initApi();
    }

    @Override
    public void setGenericNodataOrNonetwork() {

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == NetUtils.REQUEST_READ_PHONE_STATE){
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                //TODO
                TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
                NetUtils.getOperators(telephonyManager);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(NetUtils.isNetworkConnected()){
                    PromptUtils.showProgressDialog(getContext(), Utils.getString(getActivity(),R.string.loding_check_update));
                    RequestUtils.checkVersion(false, getActivity(),"","",new RequestUtils.OnUpdateVersionListener(){

                        @Override
                        public void NoNeedUpdateVersion() {
                            PromptUtils.dismissProgressDialog();
                            IntentAct();
                        }
                    });
                }else{
                    IntentAct();
                }
            }
        }, 2000);
    }


    public void IntentAct(){
        if(CacheUtils.getInstance().getBoolean("isFirst",true)){//是否是第一次启动应用
            startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
            CacheUtils.getInstance().putBoolean("isFirst",false);
        }else{
            Intent intent = new Intent(SplashActivity.this, DrawLayoutActivity.class);
            if(getIntent().getBundleExtra(Constants.EXTRA_BUNDLE) != null){
                SysoutUtil.out("-------------------------------Constants.EXTRA_BUNDLE---------------------------------");
                intent.putExtra(Constants.EXTRA_BUNDLE,  getIntent().getBundleExtra(Constants.EXTRA_BUNDLE));
            }
            startActivity(intent);
        }
        finish();
    }
}
