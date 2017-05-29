package com.dora.feed.net;//package com.famlink.frame.net;


/**
 * Created by wangkai on 16/6/23.
 */
public class Api extends com.famlink.frame.net.Api{


    //图片分类
    public static final String REGISTER = "appRegister/register";

    //获取房间视频流地址
    public static final String NEW = "appRoom/recent_new";

    //检查版本升级
    public static final String UPDATEVERSION = "http://api.yimigongchang.com/update.php";

    //图片分类
    public static final String TAB_NAME = "tnfs/api/classify";
    //    public static final String API = "http://222.128.26.221/";

    //我的评论
    public static final String MYCOMMENTS = "api/Index/mycomments";

    //消息
    public static final String MYMESSAGE = "api/Index/message";

    //我的收藏
    public static final String MYFAVART = "api/Index/myfavart";

    //登录同步信息
    public static final String LOGIN = "/api/Index/login";

    public static final String ADDCOMM = "api/Index/addcomm";

    //点赞
    public static final String COMMEND = "api/Index/commend";

//    //推荐接口
//    public static final String RECOMMEND_URL = "api/Index/recommend";
    //推荐接口
    public static final String RECOMMEND_URL = "api/Index/tuijian";

    //相关推荐接口
    public static final String ABOUT_RECOMMEND = "api/Index/relevant";

    //设备注册
    public static final String INIT_API = "api/Index/init";

    //详情页评论接口
    public static final String DETAIL_COMMENT = "api/Index/comments";

    //点击收藏的接口
    public static final String FAVIRATE_CLICK = "api/Index/addfav";

    //tip搜索
    public static final String SEARCH_TIP= "api/Search/tips";

    //搜索
    public static final String SEARCH_INDEX= "api/Search/index";


    //图片的前缀
    public static final String IMAGE_HEADER_URL = "http://doraimg.farmlink.cn/thumb/d/";
    //有_animated的图片后缀
    public static final String IMAGE_SMALL_FOOTER_W = ",w_305.jpg";
    //无_animated的图片后缀
    public static final String IMAGE_SMALL_FOOTER_C = ",c_fill,w_305,h_305.jpg";

    //有_animated的图片后缀
    public static final String IMAGE_BIG_FOOTER_W = ",w_540.jpg";
    //无_animated的图片后缀
    public static final String IMAGE_BIG_FOOTER_C = ",c_fill,w_540,h_540.jpg";


    //详情页WebView加载网页的接口前缀
    public static final String WEBVIEW_HEADER_URL = "http://dorainfo.farmlink.cn/index/Read/app";

    //第三方分享跳转的详情页面
    public static final String SHARE_ACTIONG_URL = "http://dora.farmlink.cn/index/Read/index";

    //侧栏分享的接口
    public static final String SHARE_LEFT_URL = "http://dora.farmlink.cn/download";


    //根据ids上传trace_id
    public static final String SHARE_IDS_TRACE_ID = "api/Index/show";

    //退出详情页时间戳
    public static final String LOGOUT_TIME= "api/Index/used";

    //意见反馈
    public static final String FEED_BACK= "api/Index/feedback";



}
