package com.famlink.frame.configure;

import android.app.Application;

import com.famlink.frame.util.NetUtils;
import com.tencent.smtt.sdk.TbsDownloader;

import org.xutils.x;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by wangkai on 16/6/21.
 */
public class GenericApplication extends Application
{

    @Override
    public void onCreate() {
        super.onCreate();
//        NetUtils.init(this);
        x.Ext.init(this);
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        TbsDownloader.needDownload(getApplicationContext(), false);
//        //更改系统字体
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/appleW3.otf").setFontAttrId(R.attr.fontPath).build());

    }
}
