package com.famlink.frame.net;



import android.text.TextUtils;

import com.famlink.frame.mvp.bean.BaseResult;
import com.famlink.frame.mvp.bean.RoomBean;
import com.famlink.frame.mvp.bean.UpdateVersionBean;
import com.famlink.frame.mvp.bean.UploadBean;
import com.famlink.frame.net.biz.GetMethodRequest;
import com.famlink.frame.net.biz.PostMethodRequest;
import com.famlink.frame.net.biz.Request;
import com.famlink.frame.net.biz.UploadMethodRequest;
import com.famlink.frame.util.AndroidUtils;
import com.famlink.frame.util.AppUtils;
import com.famlink.frame.util.CacheUtils;
import com.famlink.frame.util.LocalContents;
import com.famlink.frame.util.NetUtils;
import com.famlink.frame.util.Utils;

/**
 * Created by wangkai on 16/6/24.
 */
public class ParamsApi {
    /**
     * 获取房间视频流地址
     *
     * @param roomId 房间id
     * @return Request
     */
    public final static Request<RoomBean> videoStreamUrl(long roomId) {

        return new GetMethodRequest<RoomBean>(RoomBean.class, Api.API)
                .addArgument("userId", "18501134764")
                .addArgument("pageNo", 0)
                .addArgument("Resolution", "0*0")
                .addArgument("token", "jAJB6saEB2t63+ub6xfeYA==")
                .addArgument("version", 1)
                .addArgument("device", "862845028391634")
                .addArgument("pageSize", 20)
                .addArgument("deviceToken", "AicOSpDZDFQGmkfxi6frGjJzEZkiL0D2yiRQN8zbD1hp")
                .addArgument("language", 0)
                .addArgument("ip", "")
                .addArgument("Type", 1);
    }


    /**
     * 文件上传
     *
     * @param path 文件路径
     * @return Request
     */
    public final static Request<UploadBean> uploadStreamUrl(String path) {

        return new UploadMethodRequest<UploadBean>(UploadBean.class, Api.API)
                .addArgument("***headIcon", path)
                .addArgument("deviceId", "862845028391634")// 设备序列号
                .addArgument("country", "ssss")// 城市
                .addArgument("language", "0")// 语言
                .addArgument("device", "iphone5")// 设备ex：iphone4，iphone5，小米，魅族
                .addArgument("resolution", "1920*1080")// 分辨率
                .addArgument("osVerison", "os")// 操作系统os版本
                .addArgument("ip", "")// ip
                .addArgument("simCard", "sss")// sim_card
                .addArgument("uid", "")// 第三方唯一标识
                .addArgument("expires", "")// 失效时间
                .addArgument("userId", "18502134988")// 手机号或者email
                .addArgument("password", "123456")// password
                .addArgument("nick", "peter8")// nickName
                .addArgument("version", "1.0.1");
    }

//    /**
//     *反馈信息接口
//     * @param string
//     * @return
//     */
//    public final static Request<BaseResult> toRetroaction(String string){
//        return new GetMethodRequest<BaseResult>(BaseResult.class, Api.API)
//                .addArgument("userId", "18501134764")
//                .addArgument("msg", string)
//                .addArgument("token", "");
//
//    }


//    /**
//     * 检查版本更新
//     * @return
//     */
//    public final static Request<UpdateVersionBean> updateVersion(){
//        return new GetMethodRequest<UpdateVersionBean>(UpdateVersionBean.class, Api.API+Api.UPDATE_URL)
//                .addArgument("platom",0);
//    }




//    /**
//     * 公共参数
//     * @return
//     */
//    private final static String[] getPublicParam() {
//        String deviceId = AndroidUtils.getAndroidId(Utils.getContext());                            //获取设备的唯一ID
//        String userId = CacheUtils.getInstance().getString(LocalContents.USER_ID);                  //用户ID
//        String deviceModel = AndroidUtils.getDeviceModel();                                         //获取设备型号(Nexus5)
//        String getScreemesolution = AndroidUtils.getMetrics(Utils.getContext());                    //获取屏幕分辨率
//        int netWork = NetUtils.getNetworkType();                                                    //获取网络类型
//        String platom = "0";                                                                        //系统平台   0=android,1=ios
//        String phoneSystem = android.os.Build.VERSION.RELEASE;                                      //获取手机系统
//        int versonCode = AndroidUtils.getVersionCode(Utils.getContext());                           //获取版本号
//        String token = "token";                                                                     //内部校验
//        String deviceToken = CacheUtils.getInstance().getString(LocalContents.UMENG_DEVICE_TOKEN);  //友盟的设备token
//        int chanel_id = AppUtils.getChanel_id(Utils.getContext());                                  //设备的唯一id
//
//        userId =  CacheUtils.getInstance().getString(LocalContents.USER_ID,"");
//        if(TextUtils.isEmpty(userId)){
//            initApi();
//        }
//        String[] stringMsg = {deviceId, userId, deviceModel, getScreemesolution, netWork+"",
//                platom, phoneSystem, versonCode+"", token, deviceToken,chanel_id+""};
//
//        return stringMsg;
//    }


}
