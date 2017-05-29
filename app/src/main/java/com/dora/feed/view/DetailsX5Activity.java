package com.dora.feed.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dora.feed.R;
import com.dora.feed.mvp.bean.CommentDetailBean;
import com.dora.feed.mvp.bean.IndexItemBean;
import com.dora.feed.mvp.presenter.CommentDetailOtherPersenterImpl;
import com.dora.feed.mvp.presenter.CommentDetailPersenterImpl;
import com.dora.feed.mvp.view.BaseView;
import com.dora.feed.mvp.view.CommentDetalOtherView;
import com.dora.feed.net.Api;
import com.dora.feed.net.ParamsApi;
import com.dora.feed.utils.LoginUtils;
import com.dora.feed.utils.SinaWeibo;
import com.dora.feed.utils.TencentUtils;
import com.dora.feed.utils.WXUtils;
import com.dora.feed.view.adapter.DetailTuijianListAdapter;
import com.dora.feed.view.adapter.DetailWebViewListAdapter;
import com.dora.feed.widget.CusRecyclerView;
import com.dora.feed.widget.MyLinearLayoutManager;
import com.dora.feed.widget.dialog.RecommentDialog;
import com.dora.feed.widget.sqlite.SQLiteData;
import com.dora.feed.widget.x5webview.X5WebView;
import com.famlink.frame.mvp.bean.BaseResult;
import com.famlink.frame.net.biz.BaseRequestCallback;
import com.famlink.frame.util.CacheUtils;
import com.famlink.frame.util.LocalContents;
import com.famlink.frame.util.NetUtils;
import com.famlink.frame.util.SysoutUtil;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.util.Utils;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.IssueKey;
import com.famlink.frame.util.notify.OnDataChangeObserver;
import com.famlink.frame.view.activity.BaseActivity;
import com.famlink.frame.widget.SceenMannage;
import com.famlink.frame.widget.dialog.PromptUtils;
import com.famlink.frame.widget.pullrecycleview.BaseDataBindingAdapter;
import com.famlink.frame.widget.pullrecycleview.DividerItemDecoration;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.constant.WBConstants;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangkai on 16/7/14.
 */
public class DetailsX5Activity extends BaseActivity implements BaseView.CommentDetalView, CommentDetalOtherView, OnDataChangeObserver, IWeiboHandler.Response, BaseDataBindingAdapter.OnItemBaseClickListener {
    @ViewInject(R.id.appbar)
    AppBarLayout appbar;
    @ViewInject(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsing_toolbar_layout;
    @ViewInject(R.id.collapsing_image_icon)
    ImageView collapsing_image_icon;
    @ViewInject(R.id.head_layout)
    RelativeLayout head_layout;
    @ViewInject(R.id.collapsing_read_num)
    TextView collapsing_read_num;
    @ViewInject(R.id.collapsing_title)
    TextView collapsing_title;
    @ViewInject(R.id.collapsing_time)
    TextView collapsing_time;
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;

    @ViewInject(R.id.detail_edit)
    TextView detail_edit;  //输入框

    @ViewInject(R.id.detail_linear_count)
    LinearLayout detail_linear_count; //评论条数
    @ViewInject(R.id.detail_text_count)
    TextView detail_text_count; //评论条数



    @ViewInject(R.id.detail_linear_favirate)
    LinearLayout detail_linear_favirate; //评论条数
    @ViewInject(R.id.detail_text_favirate)
    TextView detail_text_favirate;  //我要收藏


    @ViewInject(R.id.detail_linear_share)
    LinearLayout detail_linear_share; //评论条数
    @ViewInject(R.id.detail_text_share)
    TextView detail_text_share;  //我要分享

    @ViewInject(R.id.nested_scrollview)
    NestedScrollView nested_scrollview;

    //点赞和阅读全文
    @ViewInject(R.id.comment_icon)
    ImageView comment_icon;  //点赞按钮
    @ViewInject(R.id.comment_count)
    TextView comment_count; //点赞条数

    @ViewInject(R.id.source_from_text)
    TextView source_from_text;  //阅读全文

    @ViewInject(R.id.bottom_loadmore)
    LinearLayout bottom_loadmore;
    @ViewInject(R.id.bottom_loadmore_pro)
    ProgressBar bottom_loadmore_pro;
    @ViewInject(R.id.footer)
    TextView footer;

    @ViewInject(R.id.source_from_rela)
    RelativeLayout source_from_rela;

    //评论列表
    @ViewInject(R.id.comment_pullrecycler)
    CusRecyclerView comment_pullrecycler;
    //相关推荐列表
    @ViewInject(R.id.comment_pullrectuijian)
    CusRecyclerView comment_pullrectuijian;

    @ViewInject(R.id.no_comment_data)
    LinearLayout no_comment_data;

    @ViewInject(R.id.about_recommend)
    LinearLayout about_recommend;

    //    @ViewInject(R.id.webView)
//    X5WebView mWebView;
    @ViewInject(R.id.webView)
    ViewGroup mViewParent;
    private String public_url;
    private URL url;
    private X5WebView mWebView;
    private ProgressBar mPageLoadingProgressBar;


    private TencentUtils instance;

    private int isFalg;  //是否是收藏或者是点赞


    private boolean isClick = true; //评论是否被点击过
    private boolean isLoadingComment = false; //是否加载过评论数据
    private boolean isClickDianzan = true;  //文章是否被点赞过
    private boolean isClickFavirate = true;  //文章是否被收藏过

    private String intentArticleId;
    private int intentPosition;
    private String intentTitle;
    private String intentIcon;
    private String intentReadCount;
    private String intentTime;
    private String intentCommentCount;
    private String intentPublicUrl; //阅读全文的URL
//    private String intentPinlunNum; //评论的条数
    private String intentTraceId; //trace_id

    private ArrayList<CommentDetailBean.Data> commentList = new ArrayList<CommentDetailBean.Data>();
    private ArrayList<IndexItemBean.IndexDataBean> tuijianList = new ArrayList<IndexItemBean.IndexDataBean>();
    private DetailWebViewListAdapter commentWebViewListAdapter;
    private DetailTuijianListAdapter mTuijianListAdapter;

    private long bhv_amt_1;
    private long bhv_amt_2;

    private int offset;
    private SQLiteData mSQL;
    SinaWeibo shareUtils;//微博

    private boolean noDataIsShow = true;

    private int[] loading_default = {R.drawable.loading_detail_default_1,R.drawable.loading_detail_default_2,
                                     R.drawable.loading_detail_default_3,R.drawable.loading_detail_default_4,
                                     R.drawable.loading_detail_default_5,R.drawable.loading_detail_default_6};

    @Override
    public int setLayout() {

        return R.layout.layout_activity_detail;
    }

    private long isOpenAcitivty_currentTime;

    @SuppressLint("NewApi")
    @Override
    public void setInterfaceView() {
        isOpenAcitivty_currentTime = System.currentTimeMillis();

        intentArticleId = getIntent().getStringExtra("intentArticleId");
        intentPosition = getIntent().getIntExtra("intentPosition", -1);
        intentTitle = getIntent().getStringExtra("intentTitle");
        intentIcon = getIntent().getStringExtra("intentIcon");
        intentReadCount = getIntent().getStringExtra("intentReadCount");
        intentTime = getIntent().getStringExtra("intentTime");
        intentCommentCount = getIntent().getStringExtra("intentCommentCount");
        intentPublicUrl = getIntent().getStringExtra("intentPublicUrl");
//        intentPinlunNum = getIntent().getStringExtra("intentPinlunNum");
        intentTraceId = getIntent().getStringExtra("intentTraceId");
        init();
        setLikeOrFavirate();
        articleIsShow();
        page = 1;
        getCommentMessage(page);

        DataChangeNotification.getInstance().addObserver(IssueKey.LOGIN_ADD_BIND_SCCESS, this);
        DataChangeNotification.getInstance().addObserver(IssueKey.RECOMMEND_REFRESH, this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {   //toolbar监听返回键
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
//        collapsing_toolbar_layout.setTitle(intentTitle);
        toolbar.setVisibility(View.GONE);
//        toolbar.setTitle(intentTitle);

        collapsing_title.setText(intentTitle);
        collapsing_read_num.setText(intentReadCount);
        collapsing_time.setText(intentTime);
        if (!TextUtils.isEmpty(intentIcon)) {
            if (intentIcon.contains("_animated")) {
                intentIcon = Api.IMAGE_HEADER_URL + intentIcon.substring(0, intentIcon.lastIndexOf(".")) + Api.IMAGE_BIG_FOOTER_W;

            } else {
                intentIcon = Api.IMAGE_HEADER_URL + intentIcon.substring(0, intentIcon.lastIndexOf(".")) + Api.IMAGE_BIG_FOOTER_C;
            }
            ImageOptions imageOptions= new ImageOptions.Builder().setSize(-1,-1).build();
            x.image().bind(collapsing_image_icon, intentIcon, imageOptions);
        }else{
            int i=(int)(Math.random()* loading_default.length);
            collapsing_image_icon.setBackgroundResource(loading_default[i]);
        }
        collapsing_toolbar_layout.setTitle(intentTitle);


        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -head_layout.getHeight() / 3) {
//                    collapsing_toolbar_layout.setTitle(intentTitle);

//                    collapsing_title.setVisibility(View.INVISIBLE);
                    collapsing_read_num.setVisibility(View.INVISIBLE);
                    collapsing_time.setVisibility(View.INVISIBLE);
                } else {
//                    collapsing_toolbar_layout.setTitle(" ");
//                    collapsing_title.setVisibility(View.VISIBLE);
                    collapsing_read_num.setVisibility(View.VISIBLE);
                    collapsing_time.setVisibility(View.VISIBLE);

                }
//                SysoutUtil.out("vertacalOffset:" + verticalOffset);
                if (verticalOffset < 0) {
                    collapsing_toolbar_layout.setTitle(intentTitle);
                    collapsing_title.setVisibility(View.INVISIBLE);
                } else if (verticalOffset == 0) {
                    collapsing_toolbar_layout.setTitle(" ");
                    collapsing_title.setVisibility(View.VISIBLE);
                }
            }
        });


        commentWebViewListAdapter = new DetailWebViewListAdapter(context, activity, intentArticleId, commentList);
        mTuijianListAdapter = new DetailTuijianListAdapter(context, tuijianList);
//        comment_pullrecycler.setOnRefreshListener(this);
//        comment_pullrecycler.setNestedScrollingEnabled(false);
        comment_pullrecycler.setLayoutManager(new MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        comment_pullrectuijian.setLayoutManager(new MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        comment_pullrecycler.addItemDecoration(getItemDecoration());
        comment_pullrecycler.setAdapter(commentWebViewListAdapter);
        comment_pullrectuijian.setAdapter(mTuijianListAdapter);
        mTuijianListAdapter.setOnItemClickListener(this);

        comment_pullrecycler.setNestedScrollingEnabled(false);
        comment_pullrectuijian.setNestedScrollingEnabled(false);

        bottom_loadmore.setVisibility(View.GONE);

        collapsing_toolbar_layout.setExpandedTitleColor(getResources().getColor(R.color.white));//设置还没收缩时状态下字体颜色
        collapsing_toolbar_layout.setCollapsedTitleTextColor(getResources().getColor(R.color.color_333333));//设置收缩后Toolbar上字体的颜色
//        collapsing_toolbar_layout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));//设置还没收缩时状态下字体颜色
//        collapsing_toolbar_layout.setCollapsedTitleTextColor(getResources().getColor(R.color.color_fb82a9));//设置收缩后Toolbar上字体的颜色
//        nested_scrollview.setNestedScrollingEnabled(false);
//        nested_scrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                SysoutUtil.out("scrollY---" + scrollY + "---oldScrollY---" + oldScrollY + "---height----" + mWebView.getHeight() + "---getTop----" + comment_pullrecycler.getTop()+ "---mWebViewY---" + mWebView.getY());
//                if (scrollY >= mWebView.getHeight() * 0.8500752) {
//                    if (!isLoadingComment) {
//                        ToastUtils.showToast("滚动到了底部");
//                        page = 1;
//                        getCommentMessage(page);
//                    }
//                }
//            }
//        });
    }

    /**
     * 设置Footview底部和列表的线
     *
     * @return
     */
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(context, R.drawable.comment_pullrecycle_list_divider);
    }

    @Override
    public void setGenericNodataOrNonetwork() {
        instantiationNoDataNetWork();
        genericNoData(false, "", "");
        genericNoNetwork(false);
//        genericNoNetworkSetting(true);
        if(!NetUtils.isNetworkConnected()){
            showGenericNoNetwork();
        }else{
            showGenericNodata();
        }
        detail_linear_count.setEnabled(false);
    }

    /**
     * 弹出输入框
     */
    DetailDialog detailDialog;

    public void setDetailDialog() {
        detailDialog = new DetailDialog(this);
        detailDialog.setDetailMessage(intentArticleId, null, null, null);
        detailDialog.show();
        detailDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                SysoutUtil.out("关闭Dialog和键盘");
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (detailDialog != null && detailDialog.isShowing()) {
                        detailDialog.dismiss();
                    }
                }
                return false;
            }
        });
        Window win = detailDialog.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        win.getDecorView().setBackgroundColor(getResources().getColor(R.color.color_f2f2f2));
        win.setGravity(Gravity.BOTTOM);  //此处可以设置dia
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);

        //显示键盘
        detailDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    }

    private int page = 1;

    public void getCommentMessage(int page) {
        isLoadingComment = true;
        CommentDetailPersenterImpl commentDetailPersenter = new CommentDetailPersenterImpl(this);
        commentDetailPersenter.requestNetWork(page, Integer.valueOf(intentArticleId), intentTraceId, 0);
        ParamsApi.getAboutRecommendUrl(intentArticleId).execute(new BaseRequestCallback<IndexItemBean>() {
            @Override
            public void onRequestSucceed(IndexItemBean result) {
                List<IndexItemBean.IndexDataBean> mData = result.getData();
                if(result.getmCode().equals("500")){
                    about_recommend.setVisibility(View.GONE);
                }else{
                    if(mData.size() != 0){
                        about_recommend.setVisibility(View.VISIBLE);
                        tuijianList.clear();
                        tuijianList = (ArrayList<IndexItemBean.IndexDataBean>)mData;
                        mTuijianListAdapter.setmDatas(tuijianList);
                        mTuijianListAdapter.notifyDataSetChanged();
                    }else{
                        about_recommend.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onRequestFailed(IndexItemBean result) {
                about_recommend.setVisibility(View.GONE);
                SysoutUtil.out(result);
            }
        });
    }

    /**
     * 详情页其他接口
     */
    public CommentDetailOtherPersenterImpl getCommentOtherMessage() {
        return new CommentDetailOtherPersenterImpl(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (shareUtils != null) {
            IWeiboShareAPI weiboShare = shareUtils.getWeiboShare();
            if (weiboShare != null) {
                weiboShare.handleWeiboResponse(intent, this);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PromptUtils.dismissProgressDialog();
        //新浪微博的回调
        if (shareUtils != null) {
            shareUtils.onActivityResult(requestCode, resultCode, data);
        }

        //qq分享和登陆回调
        if (instance != null) {
            instance.onActivityResultData(requestCode, resultCode, data);
        }
    }

    //XUtils3.0点击事件，onClick的方法必须是private(私有化)类型
    @Event(value = {R.id.detail_edit, R.id.detail_linear_count, R.id.detail_linear_favirate, R.id.detail_linear_share, R.id.comment_icon, R.id.source_from_text, R.id.bottom_loadmore, R.id.generic_back_click, R.id.generic_back_click_network}, type = View.OnClickListener.class)
    private void onClick(View view) {
        if (view.getId() == R.id.detail_edit) {
            if(!NetUtils.isNetworkConnected()){
                ToastUtils.showToast(getResources().getString(R.string.toast_net_work_error));
                return;
            }
            if (LoginUtils.isAlreadyLogin()) {

                setDetailDialog();
            } else {
                startActivity(new Intent(activity, LoginActivity.class));
            }
        } else if (view.getId() == R.id.detail_linear_count) {
//            if(!noDataIsShow){
                if(!NetUtils.isNetworkConnected()){
                    ToastUtils.showToast(getResources().getString(R.string.toast_net_work_error));
                    return;
                }
                if (isClick) {
                    appbar.setExpanded(false, true);  // AppBarLayout收起来，并且有动画
                    nested_scrollview.scrollTo(0, mWebView.getBottom() + source_from_rela.getHeight() + 64);
                    isClick = false;
//                if(!isLoadingComment){
//                    getCommentMessage();
//                }
                } else {
                    isClick = true;
                    appbar.setExpanded(true, true);  // AppBarLayout展开，并且有动画
                    nested_scrollview.scrollTo(0, mWebView.getTop());
                }
//            }

        } else if (view.getId() == R.id.detail_linear_favirate) {
            if(!NetUtils.isNetworkConnected()){
                ToastUtils.showToast(getResources().getString(R.string.toast_net_work_error));
                return;
            }
            String user_id = CacheUtils.getInstance().getString(LocalContents.USER_ID, "");
            if (isClickFavirate) {//收藏
                isFalg = 0;
                getCommentOtherMessage().requestNetWorkFavirateClick(user_id, intentArticleId, 0, intentTraceId);

            } else {  //取消收藏
                isFalg = 1;
                getCommentOtherMessage().requestNetWorkFavirateClick(user_id, intentArticleId, 1, intentTraceId);
            }
        } else if (view.getId() == R.id.detail_linear_share) {
            if(!NetUtils.isNetworkConnected()){
                ToastUtils.showToast(getResources().getString(R.string.toast_net_work_error));
                return;
            }
            RecommentDialog recommentDialog = new RecommentDialog(this, new RecommentDialog.RecommentDialogOnClickListener() {
                @Override
                public void OnFriendCicleClick() {
                    String shareWechatFriend = Api.SHARE_ACTIONG_URL + "?id=" + intentArticleId + "&trace_id=" + intentTraceId + "&media_type=2";

                    WXUtils.wechatShare(context, 1, ParamsApi.webViewUrlAndShareUrl(shareWechatFriend), intentTitle, intentTitle, getResources(), intentIcon,false,R.drawable.app_logo);
                }

                @Override
                public void OnWeChatClick() {
                    String shareWeChat = Api.SHARE_ACTIONG_URL + "?id=" + intentArticleId + "&trace_id=" + intentTraceId + "&media_type=1";
                    WXUtils.wechatShare(context, 0, ParamsApi.webViewUrlAndShareUrl(shareWeChat), intentTitle, getResources().getString(com.famlink.frame.R.string.share_from), getResources(), intentIcon,false,R.drawable.app_logo);
                }

                @Override
                public void OnMicroBlogSina() {
                    shareWeibo();
                }

                @Override
                public void OnQQFriend() {
                    String shareQQFriend = Api.SHARE_ACTIONG_URL + "?id=" + intentArticleId + "&trace_id=" + intentTraceId + "&media_type=3";
                    instance = new TencentUtils(DetailsX5Activity.this);
                    instance.doShareToQQ(intentTitle, ParamsApi.webViewUrlAndShareUrl(shareQQFriend), intentTitle, intentIcon, intentTitle,false);
                }

                @Override
                public void OnQQZone() {
                    String shareQQZone = Api.SHARE_ACTIONG_URL + "?id=" + intentArticleId + "&trace_id=" + intentTraceId + "&media_type=4";
                    instance = new TencentUtils(DetailsX5Activity.this);
                    instance.doShareToQzone(intentTitle, ParamsApi.webViewUrlAndShareUrl(shareQQZone), intentTitle, intentIcon,false);
                }
            });
            recommentDialog.show();
        } else if (view.getId() == R.id.comment_icon) {
            isFalg = 2;
//            ToastUtils.showToast("点击了点赞按钮");
            if (!isClickDianzan) {
//                getCommentOtherMessage().requestNetWorkLikeForContent(intentArticleId, 0);
//                isClickDianzan = false;
//                comment_icon.setBackgroundResource(R.drawable.attention_icon_down);
            } else {
                getCommentOtherMessage().requestNetWorkLikeForContent(intentArticleId, 2, intentTraceId);
                comment_icon.setBackgroundResource(R.drawable.attention_icon_down);
            }


        } else if (view.getId() == R.id.source_from_text) {
//            ToastUtils.showToast("点击了阅读全文");
            if(!TextUtils.isEmpty(intentPublicUrl)){
                Intent intent = new Intent(this, ReadNewsActivity.class);
                intent.putExtra("public_url", intentPublicUrl);
                startActivity(intent);
            }else{
                ToastUtils.showToast("interface not obtained");
            }

        } else if (view.getId() == R.id.bottom_loadmore) {
//            ToastUtils.showToast("加载更多");
            page++;
            getCommentMessage(page);
            bottom_loadmore_pro.setVisibility(View.VISIBLE);
            footer.setText("正在加载中...");
        } else if (view.getId() == R.id.generic_back_click) {
            Logout();
        } else if (view.getId() == R.id.generic_back_click_network) {
            Logout();
        }
    }

    private void shareWeibo() {
        String shareWeibo = Api.SHARE_ACTIONG_URL + "?id=" + intentArticleId + "&trace_id=" + intentTraceId + "&media_type=5";
        shareUtils = new SinaWeibo(this);
        shareUtils.requestShareWeibo(this, intentTitle, intentTitle, ParamsApi.webViewUrlAndShareUrl(shareWeibo), "",  intentIcon,false, R.drawable.app_logo);
    }

    @Override
    public void netWorkError(CommentDetailBean data) {

    }

    @Override
    public void setDatas(List<CommentDetailBean> datas) {

    }

    @Override
    public void setData(CommentDetailBean data) {
        if(page ==1 && data.getData().size() == 0){
            no_comment_data.setVisibility(View.VISIBLE);
        }else{
            no_comment_data.setVisibility(View.GONE);
        }
        if (page == 1) {
            commentList.clear();
        }
        commentList.addAll(data.getData());
        commentWebViewListAdapter.setmDatas(commentList);
        commentWebViewListAdapter.notifyDataSetChanged();
        bottom_loadmore.setVisibility(View.VISIBLE);
        bottom_loadmore_pro.setVisibility(View.GONE);
        if (data.getData().size() < 5) {
            footer.setVisibility(View.GONE);
        } else {
            footer.setVisibility(View.VISIBLE);
            footer.setText(getResources().getString(R.string.toast_load_more));
        }

        Utils.ShowBadgeView(context, data.getTotal(), detail_linear_count, 8);   //设置小红点

    }

    //详情页其他接口返回
    @Override
    public void setOtherData(BaseResult data) {
        ToastUtils.showToast("data:" + data.getmMessage());
        if ("200".equals(data.getmCode())) {
            if (isFalg == 0) {   //收藏成功
                ToastUtils.showToast("收藏成功");
                detail_text_favirate.setBackgroundResource(R.drawable.my_favorites_icon_down);
                mSQL.insertFavirateData(intentArticleId, "success");
                isClickFavirate = false;
            } else if (isFalg == 1) {   //点赞成功
                ToastUtils.showToast("取消收藏");
                detail_text_favirate.setBackgroundResource(R.drawable.my_favorites_icon_up);
                mSQL.updateFavirateData(intentArticleId, "fail");
                isClickFavirate = true;
                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.FAVIRATE_REFRESH);

            } else if (isFalg == 2) {   //点赞成功
                ToastUtils.showToast("已经点赞过了");
                mSQL.insertLikeData(intentArticleId, "success");
                int commendCount = Integer.valueOf(intentCommentCount) + 1;
                comment_count.setText(commendCount + "");
                isClickDianzan = false;
            }
        }
    }

    //详情页其他接口返回
    @Override
    public void setOtherData(List<BaseResult> data) {

    }

    @Override
    public void error(BaseResult data) {
        ToastUtils.showToast("data:" + data.getmMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (instance != null) {
            instance.releaseResource();
            instance = null;
        }
        if(mWebView != null ){
            mWebView.setVisibility(View.GONE);
            mWebView.destroy();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Logout();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDataChanged(IssueKey issue, Object o) {
        if (issue.equals(IssueKey.LOGIN_ADD_BIND_SCCESS)) {
            setDetailDialog();
        } else if (issue.equals(IssueKey.RECOMMEND_REFRESH)) {
            page = 1;
            getCommentMessage(page);
        }
    }

    /**
     * 设置点赞和收藏的状态
     */
    private void setLikeOrFavirate() {

        bhv_amt_1 = System.currentTimeMillis();

        mSQL = new SQLiteData(context);
        String isLike = mSQL.hasLikeData(intentArticleId);
        String isFavirate = mSQL.hasFavirateData(intentArticleId);
        comment_count.setText(intentCommentCount);
        if (TextUtils.isEmpty(isLike)) {
            isClickDianzan = true;
            comment_icon.setBackgroundResource(R.drawable.attention_icon_up);
        } else if ("success".equals(isLike)) {
            isClickDianzan = false;
            comment_icon.setBackgroundResource(R.drawable.attention_icon_down);
        }
        if (TextUtils.isEmpty(isFavirate)) {
            isClickFavirate = true;
            detail_text_favirate.setBackgroundResource(R.drawable.my_favorites_icon_up);
        } else if ("success".equals(isFavirate)) {
            isClickFavirate = false;
            detail_text_favirate.setBackgroundResource(R.drawable.my_favorites_icon_down);
        } else if ("fail".equals(isFavirate)) {
            isClickFavirate = true;
            detail_text_favirate.setBackgroundResource(R.drawable.my_favorites_icon_up);
        }
    }

    private void init() {
        X5WebView.setSmallWebViewEnabled(true);

        mWebView = new X5WebView(this);
        mWebView.getSettings().setBlockNetworkImage(true);
        mViewParent.addView(mWebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.FILL_PARENT));

        initProgressBar();
        mWebView.addJavascriptInterface(this, "MyApp");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                // TODO Auto-generated method stub
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mWebView.loadUrl("javascript:MyApp.resize(document.body.getBoundingClientRect().height)");
                hideGenericNodata();
                toolbar.setVisibility(View.VISIBLE);
                toolbar.setNavigationIcon(R.drawable.back); //更改菜单图标
                mWebView.getSettings().setBlockNetworkImage(false);
                if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
                    mWebView.getSettings().setLoadsImagesAutomatically(true);
                }
                detail_linear_count.setEnabled(true);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                mPageLoadingProgressBar.setProgress(newProgress);
                if (mPageLoadingProgressBar != null && newProgress != 100) {
                    mPageLoadingProgressBar.setVisibility(View.VISIBLE);
                } else if (mPageLoadingProgressBar != null) {
                    mPageLoadingProgressBar.setVisibility(View.GONE);
                }
            }
        });

        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0).getPath());

        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);


        mWebView.reload();
        nested_scrollview.requestLayout();



        mWebView.loadUrl(ParamsApi.webViewUrlAndShareUrl(Api.WEBVIEW_HEADER_URL + "?id=" + intentArticleId + "&trace_id=" + intentTraceId));
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
        comment_pullrecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                SysoutUtil.out("onScrollStateChanged");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                SysoutUtil.out("onScrolled----" + dy);
            }
        });
    }
    private void initProgressBar() {
        mPageLoadingProgressBar = (ProgressBar) findViewById(R.id.progressBar1);// new
        mPageLoadingProgressBar.setMax(100);
        mPageLoadingProgressBar.setProgressDrawable(this.getResources().getDrawable(R.drawable.color_progressbar));
    }

    @Override
    public void onResponse(BaseResponse baseResponse) {
        switch (baseResponse.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                Toast.makeText(this, "成功", Toast.LENGTH_LONG).show();
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                Toast.makeText(this, "取消", Toast.LENGTH_LONG).show();
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                Toast.makeText(this, "失败" + baseResponse.errMsg, Toast.LENGTH_LONG)
                        .show();
                break;
        }
    }

    /**
     * 退出详情页
     */
    public void Logout() {

        DataChangeNotification.getInstance().notifyDataChanged(IssueKey.DETAIL_LOGOUT, new String[] {intentArticleId,isOpenAcitivty_currentTime+""});
        onBackPressed();
        if (mWebView.canGoBack()) {
            mWebView.goBack();// 关闭Flash播放器
        }
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView.canGoBack()) {
            mWebView.goBack();// 关闭Flash播放器
        }
    }

    /**
     * 该文章是否被查看过,外部列表文字要变色
     */
    private void articleIsShow(){
        mSQL.insertIsshowData(intentArticleId);
        if(intentPosition !=-1 && intentPosition != -2){
            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.ARTICLE_IS_SHOW, intentPosition);
        }
    }
//    webView.loadUrl("javascript:MyApp.resize(document.body.getBoundingClientRect().height)");
    private void setupWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                mWebView.loadUrl("javascript:MyApp.resize(document.body.getBoundingClientRect().height)");
                super.onPageFinished(view, url);
            }
        });
        mWebView.addJavascriptInterface(this, "MyApp");
    }

    @JavascriptInterface
    public void resize(final float height) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SceenMannage sceenMannage = new SceenMannage(context);
                SysoutUtil.out("height----------------:" + height + "---------------------" +(int) (sceenMannage.changeHight(height)));
//                mWebView.setLayoutParams(new FrameLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (sceenMannage.changeHight(height))));
//                nested_scrollview.setLayoutParams(new CoordinatorLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
            }
        });
    }

    @Override
    public void onItemClick(View view, int position, Object info) {
        SysoutUtil.out("position------------------:" + position);
        Intent intent = new Intent(activity, DetailsX5Activity1.class);
        intent.putExtra("intentArticleId", ((IndexItemBean.IndexDataBean)info).getAid());
        intent.putExtra("intentPosition", position);
        intent.putExtra("intentTitle", ((IndexItemBean.IndexDataBean)info).getTitle());
        intent.putExtra("intentIcon", ((IndexItemBean.IndexDataBean)info).getMaster_img());
        intent.putExtra("intentReadCount", ((IndexItemBean.IndexDataBean)info).getLook_num());
        intent.putExtra("intentTime", ((IndexItemBean.IndexDataBean)info).getAtime());
        intent.putExtra("intentCommentCount", ((IndexItemBean.IndexDataBean)info).getCommend_num());
        intent.putExtra("intentPublicUrl", ((IndexItemBean.IndexDataBean)info).getPublic_url());
        intent.putExtra("intentTraceId", ((IndexItemBean.IndexDataBean)info).getTrace_id());
        startActivity(intent);
        ((IndexItemBean.IndexDataBean)info).setShow(true);
        mTuijianListAdapter.notifyDataSetChanged();
    }
}
