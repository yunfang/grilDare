package com.famlink.frame.util;

/**
 * 第三方参数
 * Created by admin on 2016/7/14.
 */
public class Content {
    /************************************************以下是微信的参数*****************************************************************************/

    public static final String APP_ID = "xxxxxxx";//微信分享的app_id

    public static final String APP_SECRET = "xxxxxxxxxxxxxxxxx";//微信的APP_SECRET

    public static final String WEIXIN_SCOPE = "snsapi_userinfo";// 用于请求用户信息的作用域
    public static final String WEIXIN_STATE = "login_state"; // 自定义


    /*********************************************腾讯的appid******************************************************************************************/
    public static final String Tencent_APP_ID = "xxxxxxx";//腾讯app_id  1105545018

/****************************************以下是新浪微博的参数*********************************************************************/


    /** 当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY */
//    public static final String SINA_APP_KEY = "1552473704"; // 微博sina_app_key
    //正式版Key
    public static final String SINA_APP_KEY = "xxxxxxx"; // 微博sina_app_key

    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     *
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String SINA_REDIRECT_URL = "http://www.dorafeed.com/";


    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     *
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请。
     *
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     *
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SINA_SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
}
