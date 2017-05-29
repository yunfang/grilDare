package com.famlink.frame.util;

/**
 * 本地缓存用的key
 * Created by admin on 2016/7/27.
 */
public class LocalContents {

    public final static String ACCESS_TOKEN = "thrid_access_token";//第三方登录的token   ,不同的设备，同一个第三方登录同一个应用，返回唯一相同的标示
    public final static String LOGIN_USER_NAME = "login_user_name";
    public final static String LOGIN_USER_HEAD = "login_user_head";
    public final static String UMENG_DEVICE_TOKEN = "umeng_device_token";
    public final static String USER_ID = "user_id";
    public final static String STAR = "star";  //设置的星座名称
    public final static String STAR_POSITION = "star_position";//设置星座的下标

    public final static String SCREEM_W_H = "screem_w_h";//存储分辨率

    public final static String LOCAL_UMENG_STATUS = "local_umeng_status";//umeng推送开关状态

    public final static String CHANGE_NETWORK = "change_network";//存储是否用移动网络播放视频

    public final static String LOGIN_WEIXIN_CODE = "login_weixin_code";//微信登录的表示

    public final static String UMENG_NOTIFICATION_NUMBER = "login_weixin_code";//存储通知消息的个数


    public final static String IMEI = "imei";//存储网络类型是移动, 典型, 联通
    public final static String IMSI = "imsi";//国际移动用户识别码
    public final static String PHONE_NUMBER = "phonenumber";//获取SIM卡手机号,有的手机能获取到有的获取不到,如果运营商没有把卡号写入SIM中,那么就无法获取到手机号码


}
