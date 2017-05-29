package com.dora.feed.view;//package com.ximi.gread.view;
//
//import android.annotation.SuppressLint;
//import android.annotation.TargetApi;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.PixelFormat;
//import android.os.Build;
//import android.support.design.widget.AppBarLayout;
//import android.support.design.widget.CollapsingToolbarLayout;
//import android.support.v4.widget.NestedScrollView;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.text.TextUtils;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.famlink.frame.mvp.bean.BaseResult;
//import com.famlink.frame.util.CacheUtils;
//import com.famlink.frame.util.LocalContents;
//import com.famlink.frame.util.SysoutUtil;
//import com.famlink.frame.util.ToastUtils;
//import com.famlink.frame.util.notify.DataChangeNotification;
//import com.famlink.frame.util.notify.IssueKey;
//import com.famlink.frame.util.notify.ObserverGroup;
//import com.famlink.frame.util.notify.OnDataChangeObserver;
//import com.famlink.frame.view.activity.BaseActivity;
//import com.famlink.frame.widget.pullrecycleview.DividerItemDecoration;
//import com.tencent.smtt.sdk.WebSettings;
//import com.ximi.gread.R;
//import Constants;
//import CommentDetailBean;
//import CommentDetailOtherPersenterImpl;
//import CommentDetailPersenterImpl;
//import BaseView;
//import CommentDetalOtherView;
//import Api;
//import LoginUtils;
//import TencentUtils;
//import WXUtils;
//import DetailWebViewListAdapter;
//import CusNestedScrollView;
//import CusRecyclerView;
//import MyLinearLayoutManager;
//import RecommentDialog;
//import SQLiteData;
//import X5WebView;
//
//import org.xutils.view.annotation.Event;
//import org.xutils.view.annotation.ViewInject;
//import org.xutils.x;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by wangkai on 16/7/14.
// */
//public class DetailsActivity extends BaseActivity implements BaseView.CommentDetalView, CommentDetalOtherView, OnDataChangeObserver {
//    @ViewInject(R.id.appbar)
//    AppBarLayout appbar;
////    @ViewInject(R.id.webView)
////    WebView webView;
//    @ViewInject(R.id.collapsing_toolbar_layout)
//    CollapsingToolbarLayout collapsing_toolbar_layout;
//    @ViewInject(R.id.collapsing_image_icon)
//    ImageView collapsing_image_icon;
//    @ViewInject(R.id.head_layout)
//    RelativeLayout head_layout;
//    @ViewInject(R.id.collapsing_read_num)
//    TextView collapsing_read_num;
//    @ViewInject(R.id.collapsing_title)
//    TextView collapsing_title;
//    @ViewInject(R.id.collapsing_time)
//    TextView collapsing_time;
//    @ViewInject(R.id.toolbar)
//    Toolbar toolbar;
//
//    @ViewInject(R.id.detail_edit)
//    TextView detail_edit;  //输入框
//
//    @ViewInject(R.id.detail_text_count)
//    TextView detail_text_count; //评论条数
//
//    @ViewInject(R.id.detail_text_favirate)
//    TextView detail_text_favirate;  //我要收藏
//
//    @ViewInject(R.id.detail_text_share)
//    TextView detail_text_share;  //我要分享
//
//    @ViewInject(R.id.nested_scrollview)
//    NestedScrollView nested_scrollview;
//
//    //点赞和阅读全文
//    @ViewInject(R.id.comment_icon)
//    ImageView comment_icon;  //点赞按钮
//    @ViewInject(R.id.comment_count)
//    TextView comment_count; //点赞条数
//
//    @ViewInject(R.id.source_from_text)
//    TextView source_from_text;  //阅读全文
//
//    @ViewInject(R.id.bottom_loadmore)
//    LinearLayout bottom_loadmore;
//    @ViewInject(R.id.bottom_loadmore_pro)
//    ProgressBar bottom_loadmore_pro;
//    @ViewInject(R.id.footer)
//    TextView footer;
//
//    //评论列表
//    @ViewInject(R.id.comment_pullrecycler)
//    CusRecyclerView comment_pullrecycler;
//
//
//    private TencentUtils instance;
//
//    private int isFalg;  //是否是收藏或者是点赞
//
//
//    private boolean isClick = true; //评论是否被点击过
//    private boolean isLoadingComment = false; //是否加载过评论数据
//    private boolean isClickDianzan = true;  //文章是否被点赞过
//    private boolean isClickFavirate = true;  //文章是否被收藏过
//
//    private String intentArticleId;
//    private String intentTitle;
//    private String intentIcon;
//    private String intentReadCount;
//    private String intentTime;
//    private String intentCommentCount;  //文章点赞数
//    private String intentPublicUrl; //阅读全文的URL
//    private String intentPinlunNum; //评论的条数
//
//    private ArrayList<CommentDetailBean.Data> commentList = new ArrayList<CommentDetailBean.Data>();
//    private DetailWebViewListAdapter commentWebViewListAdapter;
//
//    private int offset;
//    private SQLiteData mSQL;
//    @Override
//    public int setLayout() {
//
//        return R.layout.layout_activity_detail;
//    }
//
//    @SuppressLint("NewApi")
//    @Override
//    public void setInterfaceView() {
//        getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）
//
//        intentArticleId = getIntent().getStringExtra("intentArticleId");
//        intentTitle = getIntent().getStringExtra("intentTitle");
//        intentIcon = getIntent().getStringExtra("intentIcon");
//        intentReadCount = getIntent().getStringExtra("intentReadCount");
//        intentTime = getIntent().getStringExtra("intentTime");
//        intentCommentCount = getIntent().getStringExtra("intentCommentCount");
//        intentPublicUrl = getIntent().getStringExtra("intentPublicUrl");
//        intentPinlunNum = getIntent().getStringExtra("intentPinlunNum");
//
//
//        setLikeOrFavirate();
//
//        DataChangeNotification.getInstance().addObserver(IssueKey.LOGIN_ADD_BIND_SCCESS,this);
//        DataChangeNotification.getInstance().addObserver(IssueKey.RECOMMEND_REFRESH,this);
//
//
//
//
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {   //toolbar监听返回键
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//                if(webView.canGoBack()){
//                    webView.goBack();// 关闭Flash播放器
//                }
//            }
//        });
////        collapsing_toolbar_layout.setTitle(intentTitle);
//        toolbar.setNavigationIcon(R.drawable.app_back); //更改菜单图标
////        toolbar.setTitle(intentTitle);
//
//        collapsing_title.setText(intentTitle);
//        collapsing_read_num.setText(intentReadCount);
//        collapsing_time.setText(intentTime);
//        if(intentIcon.contains("_animated")){
//            intentIcon = Api.IMAGE_HEADER_URL + intentIcon.substring(0, intentIcon.lastIndexOf(".")) + Api.IMAGE_BIG_FOOTER_W;
//
//        }else{
//            intentIcon = Api.IMAGE_HEADER_URL + intentIcon.substring(0, intentIcon.lastIndexOf(".")) + Api.IMAGE_BIG_FOOTER_C;
//        }
//
//        x.image().bind(collapsing_image_icon, intentIcon);
////        x.image().bind(collapsing_image_icon, "http://g.hiphotos.baidu.com/image/pic/item/241f95cad1c8a7866f726fe06309c93d71cf5087.jpg");
//        collapsing_toolbar_layout.setTitle(intentTitle);
//
//
//
////
//        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (verticalOffset <= -head_layout.getHeight() / 3) {
////                    collapsing_toolbar_layout.setTitle(intentTitle);
//
////                    collapsing_title.setVisibility(View.INVISIBLE);
//                    collapsing_read_num.setVisibility(View.INVISIBLE);
//                    collapsing_time.setVisibility(View.INVISIBLE);
//                } else {
////                    collapsing_toolbar_layout.setTitle(" ");
////                    collapsing_title.setVisibility(View.VISIBLE);
//                    collapsing_read_num.setVisibility(View.VISIBLE);
//                    collapsing_time.setVisibility(View.VISIBLE);
//
//                }
//                SysoutUtil.out("vertacalOffset:" + verticalOffset);
//                if(verticalOffset < 0){
//                    collapsing_toolbar_layout.setTitle(intentTitle);
//                    collapsing_title.setVisibility(View.INVISIBLE);
//                }else if(verticalOffset == 0){
//                    collapsing_toolbar_layout.setTitle(" ");
//                    collapsing_title.setVisibility(View.VISIBLE);
//                }
////                if(verticalOffset < -444){
////                    offset = 0;
////                }else if(verticalOffset == 0){
////                    offset = 255;
////                }else{
////                    offset--;
////                }
////                collapsing_image_icon.setAlpha(offset);
//            }
//        });
//
//        setWebView();
//
//        commentWebViewListAdapter = new DetailWebViewListAdapter(context, activity, intentArticleId, commentList);
////        comment_pullrecycler.setOnRefreshListener(this);
////        comment_pullrecycler.setNestedScrollingEnabled(false);
//        comment_pullrecycler.setLayoutManager(new MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        comment_pullrecycler.addItemDecoration(getItemDecoration());
//        comment_pullrecycler.setAdapter(commentWebViewListAdapter);
//
//        comment_pullrecycler.setNestedScrollingEnabled(false);
//
//        bottom_loadmore.setVisibility(View.GONE);
//
//        collapsing_toolbar_layout.setExpandedTitleColor(getResources().getColor(R.color.white));//设置还没收缩时状态下字体颜色
//        collapsing_toolbar_layout.setCollapsedTitleTextColor(getResources().getColor(R.color.color_fb82a9));//设置收缩后Toolbar上字体的颜色
////        collapsing_toolbar_layout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));//设置还没收缩时状态下字体颜色
////        collapsing_toolbar_layout.setCollapsedTitleTextColor(getResources().getColor(R.color.color_fb82a9));//设置收缩后Toolbar上字体的颜色
////        nested_scrollview.setNestedScrollingEnabled(false);
//        nested_scrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (scrollY >= (webView.getBottom() - webView.getBottom() / 3)) {
//                    if (!isLoadingComment) {
//                        ToastUtils.showToast("滚动到了底部");
//                        page = 1;
//                        getCommentMessage(page);
//                    }
//                }
//            }
//        });
//    }
//
//    /**
//     * 设置Footview底部和列表的线
//     *
//     * @return
//     */
//    protected RecyclerView.ItemDecoration getItemDecoration() {
//        return new DividerItemDecoration(context, R.drawable.comment_pullrecycle_list_divider);
//    }
//
//    @TargetApi(Build.VERSION_CODES.M)
//    public void setWebView() {
////        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//        // 开启 DOM storage API 功能
////        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setJavaScriptEnabled(true);
//        //!!设置跳转的页面始终在当前WebView打开
//        webView.loadUrl(Api.WEBVIEW_HEADER_URL + intentArticleId + ".html");
////        webView.loadUrl("https://mp.weixin.qq.com/s?__biz=MjM5MTgzNzg0MA==&mid=2652023512&idx=8&sn=fa143dc33e45dfbaf6f049bf99a42e22&scene=2&srcid=0722cJqxB6CdkNmdL6XF8Mmc&from=timeline&isappinstalled=0&key=77421cf58af4a6535a2439fc841a448706018baa0c104307268287a508403ac193f626b7130dc85a8fe2d9abd7cb722a&ascene=2&uin=MTUxNDM3OTEyOA%3D%3D&devicetype=iPhone+OS9.3.2&version=16031610&nettype=WIFI&fontScale=100&pass_ticket=%2BQsjP1pfjofm2KfHnTzNRg%2B9KIKDkreGkDPd%2FGTBLtYwIMDUrr2oiMgSY20i%2BeCr");
//        webView.getSettings().setBlockNetworkImage(true);
////        hideGenericNodata();
//        webView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                SysoutUtil.out("加载资源onPageFinished--"+url);
//                super.onPageFinished(view, url);
//                hideGenericNodata();
//                webView.getSettings().setBlockNetworkImage(false);
//                if(!webView.getSettings().getLoadsImagesAutomatically()) {
//                    webView.getSettings().setLoadsImagesAutomatically(true);
//                }
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
////                view.loadUrl(url);
//
//                SysoutUtil.out("加载资源shouldOverrideUrlLoading--"+url);
//                return true;
//            }
//            @Override
//            public void onLoadResource(WebView view, String url) {
//                SysoutUtil.out("加载资源onLoadResource--"+url);
//
//                super.onLoadResource(view, url);
//            }
//        });
//    }
//
//    @Override
//    public void setGenericNodataOrNonetwork() {
//        genericNoData(false, "", "");
//        genericNoNetwork(true);
//        genericNoNetworkSetting(true);
//        showGenericNodata();
//    }
//
//    /**
//     * 弹出输入框
//     */
//    DetailDialog detailDialog;
//
//    public void setDetailDialog() {
//        detailDialog = new DetailDialog(this);
//        detailDialog.setDetailMessage(intentArticleId, null, null, null);
//        detailDialog.show();
//        detailDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    if (detailDialog != null && detailDialog.isShowing()) {
//                        detailDialog.dismiss();
//                    }
//                }
//                return false;
//            }
//        });
//        Window win = detailDialog.getWindow();
//        win.getDecorView().setPadding(0, 0, 0, 0);
//        win.getDecorView().setBackgroundColor(getResources().getColor(R.color.color_f2f2f2));
//        win.setGravity(Gravity.BOTTOM);  //此处可以设置dia
//        WindowManager.LayoutParams lp = win.getAttributes();
//        lp.width = WindowManager.LayoutParams.FILL_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        win.setAttributes(lp);
//
//        //显示键盘
//        detailDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//
//    }
//
//    private int page = 1;
//
//    public void getCommentMessage(int page) {
//        isLoadingComment = true;
//
//        CommentDetailPersenterImpl commentDetailPersenter = new CommentDetailPersenterImpl(this);
//        commentDetailPersenter.requestNetWork(page, Integer.valueOf(intentArticleId));
//    }
//
//    /**
//     * 详情页其他接口
//     */
//
//    public CommentDetailOtherPersenterImpl getCommentOtherMessage() {
//        return new CommentDetailOtherPersenterImpl(this);
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        //qq分享和登陆回调
//        if (instance != null) {
//            instance.onActivityResultData(requestCode, resultCode, data);
//        }
//    }
//
//    //XUtils3.0点击事件，onClick的方法必须是private(私有化)类型
//    @Event(value = {R.id.detail_edit, R.id.detail_text_count, R.id.detail_text_favirate, R.id.detail_text_share, R.id.comment_icon, R.id.source_from_text, R.id.bottom_loadmore, R.id.generic_back_click}, type = View.OnClickListener.class)
//    private void onClick(View view) {
//        if (view.getId() == R.id.detail_edit) {
//            if(LoginUtils.isAlreadyLogin()){
//
//                setDetailDialog();
//            }else {
//                startActivity(new Intent(activity, LoginActivity.class));
//            }
//        } else if (view.getId() == R.id.detail_text_count) {
//            if (isClick) {
//                appbar.setExpanded(false, true);  // AppBarLayout收起来，并且有动画
//                nested_scrollview.scrollTo(0, webView.getBottom());
//                isClick = false;
////                if(!isLoadingComment){
////                    getCommentMessage();
////                }
//            } else {
//                isClick = true;
//                appbar.setExpanded(true, true);  // AppBarLayout展开，并且有动画
//                nested_scrollview.scrollTo(0, webView.getTop());
//            }
//
//        } else if (view.getId() == R.id.detail_text_favirate) {
//            String user_id = CacheUtils.getInstance().getString(LocalContents.USER_ID,"");
//            if(isClickFavirate){//收藏
//                isFalg = 0;
//                getCommentOtherMessage().requestNetWorkFavirateClick(user_id, intentArticleId, 0);
//
//            }else{  //取消收藏
//                isFalg = 1;
//                getCommentOtherMessage().requestNetWorkFavirateClick(user_id, intentArticleId, 1);
//
//            }
//        } else if (view.getId() == R.id.detail_text_share) {
//            RecommentDialog recommentDialog = new RecommentDialog(this, new RecommentDialog.RecommentDialogOnClickListener() {
//                @Override
//                public void OnFriendCicleClick() {
//                    WXUtils.wechatShare(context, 1, "https://www.baidu.com/", "测试", "测试描述", getResources(), com.famlink.frame.R.mipmap.ic_launcher);
//                }
//
//                @Override
//                public void OnWeChatClick() {
//                    WXUtils.wechatShare(context, 0, "https://www.baidu.com/", "测试", "测试描述", getResources(), com.famlink.frame.R.mipmap.ic_launcher);
//                }
//
//                @Override
//                public void OnMicroBlogSina() {
//                    ToastUtils.showToast("OnMicroBlogSina");
//                }
//
//                @Override
//                public void OnQQFriend() {
//                    instance = new TencentUtils(DetailsActivity.this);
//                    instance.doShareToQQ("title测试", "http://www.baidu.com/", "描述信息", "http://img3.imgtn.bdimg.com/it/u=1924893621,661118346&fm=206&gp=0.jpg", "测试应用");
//                }
//
//                @Override
//                public void OnQQZone() {
//                    instance = new TencentUtils(DetailsActivity.this);
//                    instance.doShareToQzone("空间title", "空间描述信息", "http://www.baidu.com/", "http://img3.imgtn.bdimg.com/it/u=1924893621,661118346&fm=206&gp=0.jpg");
//                }
//            });
//            recommentDialog.show();
//        } else if (view.getId() == R.id.comment_icon) {
//            isFalg = 2;
//            ToastUtils.showToast("点击了点赞按钮");
//            if (!isClickDianzan) {
////                getCommentOtherMessage().requestNetWorkLikeForContent(intentArticleId, 0);
////                isClickDianzan = false;
////                comment_icon.setBackgroundResource(R.drawable.attention_icon_down);
//            } else {
//                getCommentOtherMessage().requestNetWorkLikeForContent(intentArticleId, 2);
//                comment_icon.setBackgroundResource(R.drawable.attention_icon_down);
//            }
//
//
//        } else if (view.getId() == R.id.source_from_text) {
//            ToastUtils.showToast("点击了阅读全文");
//            Intent intent = new Intent(this, ReadNewsActivity.class);
//            intent.putExtra("public_url", intentPublicUrl);
//            startActivity(intent);
//        } else if (view.getId() == R.id.bottom_loadmore) {
//            ToastUtils.showToast("加载更多");
//            page++;
//            getCommentMessage(page);
//            bottom_loadmore_pro.setVisibility(View.VISIBLE);
//            footer.setText("正在加载中...");
//        }else if(view.getId() == R.id.generic_back_click){
//            onBackPressed();
//            if(webView.canGoBack()){
//                webView.goBack();// 关闭Flash播放器
//            }
//        }
//    }
//
//    @Override
//    public void netWorkError(CommentDetailBean data) {
//
//    }
//
//    @Override
//    public void setDatas(List<CommentDetailBean> datas) {
//
//    }
//
//    @Override
//    public void setData(CommentDetailBean data) {
//        if(page == 1){
//            commentList.clear();
//        }
//        commentList.addAll(data.getData());
//        commentWebViewListAdapter.setmDatas(commentList);
//        commentWebViewListAdapter.notifyDataSetChanged();
//        bottom_loadmore.setVisibility(View.VISIBLE);
//        bottom_loadmore_pro.setVisibility(View.GONE);
//        if(data.getData().size() < app_10){
//            footer.setVisibility(View.GONE);
//        }else{
//            footer.setVisibility(View.VISIBLE);
//            footer.setText("查看更多");
//        }
//
//    }
//
//    //详情页其他接口返回
//    @Override
//    public void setOtherData(BaseResult data) {
//        ToastUtils.showToast("data:"+data.getmMessage());
//        if("200".equals(data.getmCode())){
//            if(isFalg == 0){   //收藏成功
//                ToastUtils.showToast("收藏成功");
//                detail_text_favirate.setBackgroundResource(R.drawable.favirate_press_down);
//                mSQL.insertFavirateData(intentArticleId, "success");
//                isClickFavirate = false;
//            }else if(isFalg == 1){   //点赞成功
//                ToastUtils.showToast("收藏失败");
//                detail_text_favirate.setBackgroundResource(R.drawable.favirate_press_up);
//                mSQL.updateFavirateData(intentArticleId, "fail");
//                isClickFavirate = true;
//
//            }else if(isFalg == 2){   //点赞成功
//                ToastUtils.showToast("已经点赞过了");
//                mSQL.insertLikeData(intentArticleId, "success");
//                int commendCount = Integer.valueOf(intentCommentCount) + 1;
//                comment_count.setText(commendCount+"");
//                isClickDianzan = false;
//            }
//        }
//    }
//
//    //详情页其他接口返回
//    @Override
//    public void setOtherData(List<BaseResult> data) {
//
//    }
//
//    @Override
//    public void error(BaseResult data) {
//        ToastUtils.showToast("data:"+data.getmMessage());
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
////        if(mSQL != null){
////            mSQL.deleteFavirateData();
////            mSQL.deleteLikeData();
////        }
//        if (instance != null) {
//            instance.releaseResource();
//            instance = null;
//        }
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
//            webView.goBack();// 返回前一个页面
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    public void onDataChanged(IssueKey issue, Object o) {
//        if(issue.equals(IssueKey.LOGIN_ADD_BIND_SCCESS)){
//            setDetailDialog();
//        }else if(issue.equals(IssueKey.RECOMMEND_REFRESH)){
//            page = 1;
//            getCommentMessage(page);
//        }
//    }
//
//    /**
//     * 设置点赞和收藏的状态
//     */
//    private void setLikeOrFavirate(){
//        mSQL = new SQLiteData(context);
//        String isLike = mSQL.hasLikeData(intentArticleId);
//        String isFavirate = mSQL.hasFavirateData(intentArticleId);
//        comment_count.setText(intentCommentCount);
//        if(TextUtils.isEmpty(isLike)){
//            isClickDianzan = true;
//            comment_icon.setBackgroundResource(R.drawable.attention_icon_up);
//        }else if("success".equals(isLike)){
//            isClickDianzan = false;
//            comment_icon.setBackgroundResource(R.drawable.attention_icon_down);
//        }
//        if(TextUtils.isEmpty(isFavirate)){
//            isClickFavirate = true;
//            detail_text_favirate.setBackgroundResource(R.drawable.favirate_press_up);
//        }else if("success".equals(isFavirate)){
//            isClickFavirate = false;
//            detail_text_favirate.setBackgroundResource(R.drawable.favirate_press_down);
//        }else if("fail".equals(isFavirate)){
//            isClickFavirate = true;
//            detail_text_favirate.setBackgroundResource(R.drawable.favirate_press_up);
//        }
//
//    }
//
//}
