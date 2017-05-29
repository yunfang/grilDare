package com.famlink.frame.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Created by wangkai on 16/6/21.
 */
public class NetUtils{
    private static String mIMEI = "";
    private static String mIMSI = "";
    private static String mWifiMac = "";

    private static NetworkInfo mNetworkInfo;
    private static int mDefaultNetworkType;
    private static ConnectivityManager mConnectManager;
    private static TelephonyManager telephonyManager;
    /**
     * 无网络
     */
    public static final int NETWORK_INVALID = -1;
    /**
     * 2G网络
     */
    public static final int NETWORK_2G = 0;
    /**
     * wap网络
     */
    public static final int NETWORK_WAP = 1;
    /**
     * wifi网络
     */
    public static final int NETWORK_WIFI = 2;
    /**
     * 3G和3G以上网络，或统称为快速网络
     */
    public static final int NETWORK_3G = 3;

    /**
     * 4G以上网络，或统称为快速网络
     */
    public static final int NETWORK_4G = 4;


    /**
     * 运营商的网络类型 ，1=wifi,2=移动网络，3=联通网络，4=电信网络
     */
    public static final int NET_TYPE = 1;






    private static final int[] NETWORK_MATCH_TABLE = {NETWORK_2G // NETWORK_TYPE_UNKNOWN
            , NETWORK_2G // NETWORK_TYPE_GPRS
            , NETWORK_2G // NETWORK_TYPE_EDGE
            , NETWORK_3G // NETWORK_TYPE_UMTS
            , NETWORK_2G // NETWORK_TYPE_CDMA
            , NETWORK_3G // NETWORK_TYPE_EVDO_O
            , NETWORK_3G // NETWORK_TYPE_EVDO_A
            , NETWORK_2G // NETWORK_TYPE_1xRTT
            , NETWORK_3G // NETWORK_TYPE_HSDPA
            , NETWORK_3G // NETWORK_TYPE_HSUPA
            , NETWORK_3G // NETWORK_TYPE_HSPA
            , NETWORK_2G // NETWORK_TYPE_IDEN
            , NETWORK_3G // NETWORK_TYPE_EVDO_B
            , NETWORK_4G // NETWORK_TYPE_LTE
            , NETWORK_3G // NETWORK_TYPE_EHRPD
            , NETWORK_3G // NETWORK_TYPE_HSPAP
    };
    public static int REQUEST_READ_PHONE_STATE = 1*10000;
    /**
     * 网络类型判断的实例化
     */
    public static void init(Activity activity){

        if (Build.VERSION.SDK_INT >= 23) {

            int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
            } else {
                //TODO
            }
        }else{
            telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
            getOperators(telephonyManager);
        }
    }


    /**
     * 检测网络是否可用
     * @return
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) Utils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }
    //    /**
//     * 获取网络类型
//     *  1=wifi,2=2G，3=3G，4=4G
//     * @return 网络类型
//     */
    public static int getNetworkType() {
        int strNetworkType = 0;//其他网络
        NetworkInfo networkInfo = ((ConnectivityManager) Utils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = 1;//wifi网络返回1
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();

                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = 2;//2G网络返回2
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = 3;//3G网络返回2
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = 4;//4G网络返回2
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = 3;//3G网络返回2
                        }
//                        else {
//                            strNetworkType = _strSubTypeName;
//                        }
                        break;
                }
            }
        }
        return strNetworkType;
    }

    /**
     * 获取手机运营商信息
     * 0:中国移动,1:中国联通,2:中国电信,3:其他
     * @return
     */
    public static int getOperators(TelephonyManager telephonyManager){
        // 返回唯一的用户ID;就是这张卡的编号神马的
        int imsi;
        String IMSI = telephonyManager.getSubscriberId();//国际移动用户识别码
        String phoneNumber = telephonyManager.getLine1Number();  //获取SIM卡手机号,有的手机能获取到有的获取不到,如果运营商没有把卡号写入SIM中,那么就无法获取到手机号码
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if(TextUtils.isEmpty(IMSI)){
            imsi = 3;
            CacheUtils.getInstance().putString(LocalContents.IMEI, imsi+"");
            return imsi;
        }
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
//                ProvidersName = "中国移动";
            imsi = 0;
        } else if (IMSI.startsWith("46001")) {
//                ProvidersName = "中国联通";
            imsi = 1;
        } else if (IMSI.startsWith("46003")) {
//                ProvidersName = "中国电信";
            imsi = 2;
        }else{
            imsi = 3;

        }
//        SysoutUtil.out("IMEI:" + imsi);

        CacheUtils.getInstance().putString(LocalContents.PHONE_NUMBER, phoneNumber);
        CacheUtils.getInstance().putString(LocalContents.IMSI, IMSI);
        CacheUtils.getInstance().putString(LocalContents.IMEI, imsi+"");
        return imsi;
    }
//    /**
//     * 获取网络类型
//     *  // 1=wifi,2=移动网络，3=联通网络，4=电信网络
//     * @return 网络类型
//     */
//    public static int getNetworkType() {
//        int networkType = mDefaultNetworkType;
//        if (mConnectManager == null) {
//            //当还未来得及初始化时，另一线程请求网络时通用参数中取此值时先运行到这儿，那么如不做处理将崩溃
//            return NETWORK_WAP;
//        }
//        mNetworkInfo = mConnectManager.getActiveNetworkInfo();
////        if (!networkConnected(mNetworkInfo)) {
////            networkType = NETWORK_INVALID;
////        } else
//        if (isWifiNetwork(mNetworkInfo)) {
//            networkType = 1;
//            return networkType;
//        }else{
//            // 返回唯一的用户ID;就是这张卡的编号神马的
//            String IMSI = telephonyManager.getSubscriberId();//国际移动用户识别码
//            // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
//            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
////                ProvidersName = "中国移动";
//                return 2;
//            } else if (IMSI.startsWith("46001")) {
////                ProvidersName = "中国联通";
//                return 3;
//            } else if (IMSI.startsWith("46003")) {
////                ProvidersName = "中国电信";
//                return 4;
//            }else{
//                return 0;
//            }
//
//        }
////        else if (isWapNetwork(mNetworkInfo)) {
////            networkType = NETWORK_WAP;
////        }
//
////        return networkType;
//    }

    /**
     * 获取IMEI串号
     *
     * @return IMEI串号。<b>有可能为空值</b>
     */
    public static String imei() {
        return mIMEI;
    }

    /**
     * 获取IMSI移动用户识别码
     *
     * @return IMSI移动用户识别码。<b>有可能为空值</b>
     */
    public static String imsi() {
        return mIMSI;
    }

    /**
     * 获取Wifi Mac地址
     *
     * @return Wifi Mac地址
     */
    public static String wifiMac() {
        WifiManager wifimanage=(WifiManager)Utils.getContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiinfo= wifimanage.getConnectionInfo();
        return wifiinfo.getMacAddress();
    }

    /**
     * 直接从系统函数里得到的network type
     *
     * @return net type
     */
    private static int telephonyNetworkType() {
        int networkType = telephonyManager.getNetworkType();
        if (networkType < 0 || networkType >= NETWORK_MATCH_TABLE.length) {
            networkType = TelephonyManager.NETWORK_TYPE_UNKNOWN;
        }
        return networkType;
    }

    private static boolean networkConnected(NetworkInfo networkInfo) {
        return networkInfo != null && networkInfo.isConnected();
    }

    private static boolean isMobileNetwork(NetworkInfo networkInfo) {
        return networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    @SuppressWarnings("deprecation")
    private static boolean isWapNetwork(NetworkInfo networkInfo) {
        return isMobileNetwork(networkInfo) && !TextUtils.isEmpty(android.net.Proxy.getDefaultHost());
    }

    private static boolean isWifiNetwork(NetworkInfo networkInfo) {
        return networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

}
