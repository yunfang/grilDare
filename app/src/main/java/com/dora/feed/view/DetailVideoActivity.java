package com.dora.feed.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dora.feed.mvp.bean.CommentDetailBean;
import com.dora.feed.mvp.bean.IndexItemBean;
import com.dora.feed.mvp.view.BaseView;
import com.dora.feed.net.Api;
import com.dora.feed.net.ParamsApi;
import com.dora.feed.utils.TencentUtils;
import com.dora.feed.view.adapter.DetailTuijianListAdapter;
import com.dora.feed.view.adapter.DetailWebViewListAdapter;
import com.dora.feed.widget.CusRecyclerView;
import com.dora.feed.widget.sqlite.SQLiteData;
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
import com.famlink.frame.widget.dialog.PromptUtils;
import com.famlink.frame.widget.pullrecycleview.BaseDataBindingAdapter;
import com.famlink.frame.widget.pullrecycleview.DividerItemDecoration;
import com.famlink.frame.widget.pullrecycleview.PullRecycler;
import com.famlink.frame.widget.pullrecycleview.layoutmanager.ILayoutManager;
import com.famlink.frame.widget.pullrecycleview.layoutmanager.MyLinearLayoutManager;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.constant.WBConstants;
import com.dora.feed.R;
import com.dora.feed.mvp.presenter.CommentDetailOtherPersenterImpl;
import com.dora.feed.mvp.presenter.CommentDetailPersenterImpl;
import com.dora.feed.mvp.view.CommentDetalOtherView;
import com.dora.feed.utils.LoginUtils;
import com.dora.feed.utils.SinaWeibo;
import com.dora.feed.utils.WXUtils;
import com.dora.feed.view.adapter.DetailVideoAdapter;
import com.dora.feed.widget.cusvideoview.VideoPlayView;
import com.dora.feed.widget.dialog.RecommentDialog;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangkai on 16/7/25.
 */
public class DetailVideoActivity extends BaseActivity implements PullRecycler.OnRecyclerRefreshListener, BaseView.CommentDetalView, CommentDetalOtherView, OnDataChangeObserver, IWeiboHandler.Response, BaseDataBindingAdapter.OnItemBaseClickListener  {

    private String url = "http://101.28.255.23/154/42/43/acloud/151672/letv.v.yinyuetai.com/hc.yinyuetai.com/uploads/videos/common/9AC60129C8012ECB2E35F9609D947813.flv?crypt=87aa7f2e98550&b=1314&nlh=4096&nlt=60&bf=6000&p2p=1&video_type=flv&termid=0&tss=no&platid=0&splatid=0&its=0&qos=5&fcheck=0&proxy=2016684118,2091241095,467484302&uid=3732937437.rp&keyitem=GOw_33YJAAbXYE-cnQwpfLlv_b2zAkYctFVqe5bsXQpaGNn3T1-vhw..&ntm=1469537400&nkey=3a8b28fa485cea974078acc46a549eb3&nkey2=62bf4e55c7cfc3b54d037736aaf35402&geo=CN-1-9-2&errc=0&gn=2119&vrtmcd=103&buss=106551&cips=222.128.26.221&lersrc=MTI1Ljg5Ljc0LjE3MQ==&tag=yinyuetai&cuhost=letv.v.yinyuetai.com&cuid=151672&fext=.flv";

    @ViewInject(R.id.detail_recyclerview)
    private CusRecyclerView detail_recyclerview;

    @ViewInject(R.id.detail_video_layout)
    FrameLayout detail_video_layout;

    @ViewInject(R.id.layout_detail_relate)
    RelativeLayout layout_detail_relate;

    @ViewInject(R.id.full_screen)
    FrameLayout fullScreen;  //全屏的布局


    @ViewInject(R.id.start)
    ImageButton start;

    @ViewInject(R.id.detail_back)
    TextView detail_back;

    @ViewInject(R.id.about_tuijian)
    LinearLayout about_tuijian;

    @ViewInject(R.id.detail_edit)
    TextView detail_edit;  //输入框

    @ViewInject(R.id.detail_linear_count)
    LinearLayout detail_linear_count; //评论条数
    @ViewInject(R.id.detail_text_count)
    TextView detail_text_count; //评论条数

    @ViewInject(R.id.detail_text_favirate)
    TextView detail_text_favirate;  //我要收藏

    @ViewInject(R.id.detail_text_share)
    TextView detail_text_share;  //我要分享

    //相关推荐列表
    @ViewInject(R.id.comment_pullrectuijian)
    CusRecyclerView comment_pullrectuijian;

    @ViewInject(R.id.bottom_loadmore)
    LinearLayout bottom_loadmore;
    @ViewInject(R.id.bottom_loadmore_pro)
    ProgressBar bottom_loadmore_pro;
    @ViewInject(R.id.footer)
    TextView footer;


    @ViewInject(R.id.detail_read_title)
    TextView detail_read_title;
    @ViewInject(R.id.detail_read_count)
    TextView detail_read_count;

    private VideoPlayView videoItemView;
    private int page = 1;
    private String intentArticleId;
    private String intentTitle;
    private String intentIcon;
    private String intentReadCount;
    private String intentTime;
    private String intentPrivateUrl;
    private String intentTraceId;
    private int intentPosition;
    private String intentCommentCount;

    private ArrayList<CommentDetailBean.Data> commentList = new ArrayList<CommentDetailBean.Data>();
    private DetailVideoAdapter detailVideoAdapter;
    private ArrayList<IndexItemBean.IndexDataBean> tuijianList = new ArrayList<IndexItemBean.IndexDataBean>();
    private DetailTuijianListAdapter mTuijianListAdapter;
    private MyLinearLayoutManager manager;
    private TencentUtils instance;

    private boolean isClick = true; //评论是否被点击过
    private boolean isLoadingComment = false; //是否加载过评论数据
    private boolean isClickDianzan = true;  //文章是否被点赞过
    private boolean isClickFavirate = true;  //文章是否被收藏过
    SinaWeibo shareUtils;//微博
    private SQLiteData mSQL;
    @Override
    public int setLayout() {

        return R.layout.layout_detail_video_activity;
    }

    @Override
    public void setInterfaceView() {

        intentArticleId = getIntent().getStringExtra("intentArticleId");
        intentTitle = getIntent().getStringExtra("intentTitle");
        intentIcon = getIntent().getStringExtra("intentIcon");
        intentReadCount = getIntent().getStringExtra("intentReadCount");
        intentTime = getIntent().getStringExtra("intentTime");
        intentPrivateUrl = getIntent().getStringExtra("intentPrivateUrl");
        intentTraceId = getIntent().getStringExtra("intentTraceId");
        intentPosition = getIntent().getIntExtra("intentPosition", -1);
        intentCommentCount = getIntent().getStringExtra("intentCommentCount");

        detail_read_title.setText(intentTitle);
        detail_read_count.setText(intentReadCount);


        DataChangeNotification.getInstance().addObserver(IssueKey.LOGIN_ADD_BIND_SCCESS,this);
        DataChangeNotification.getInstance().addObserver(IssueKey.RECOMMEND_REFRESH,this);

        videoItemView = new VideoPlayView(activity);
        initActions();
        setLikeOrFavirate();
        articleIsShow();
        setInit();


        getCommentMessage(page);
    }



    private void setInit(){

        detailVideoAdapter = new DetailVideoAdapter(context, activity, intentArticleId, commentList);
        mTuijianListAdapter = new DetailTuijianListAdapter(context, tuijianList);
        manager = new MyLinearLayoutManager(context);
//        recyclerView.setLayoutManager(manager);
//        detail_recyclerview.setOnRefreshListener(this);
//        detail_recyclerview.setRefreshing();
        detail_recyclerview.setLayoutManager(new com.dora.feed.widget.MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        detail_recyclerview.setLayoutManager((ILayoutManager) manager);
        comment_pullrectuijian.setLayoutManager(new com.dora.feed.widget.MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        detail_recyclerview.addItemDecoration(getItemDecoration());
        detail_recyclerview.setAdapter(detailVideoAdapter);
        comment_pullrectuijian.setAdapter(mTuijianListAdapter);
        mTuijianListAdapter.setOnItemClickListener(this);

        bottom_loadmore.setVisibility(View.INVISIBLE);
    }
    public void getCommentMessage(int page) {
        CommentDetailPersenterImpl commentDetailPersenter = new CommentDetailPersenterImpl(this);
        commentDetailPersenter.requestNetWork(page, Integer.valueOf(intentArticleId), intentTraceId, 5);
        ParamsApi.getAboutRecommendUrl(intentArticleId).execute(new BaseRequestCallback<IndexItemBean>() {
            @Override
            public void onRequestSucceed(IndexItemBean result) {
                List<IndexItemBean.IndexDataBean> mData = result.getData();
                if(result.getmCode().equals("500")){
                    about_tuijian.setVisibility(View.GONE);
                }else{
                    if(mData.size() != 0){
                        about_tuijian.setVisibility(View.VISIBLE);
                        tuijianList.clear();
                        tuijianList = (ArrayList<IndexItemBean.IndexDataBean>)mData;
                        mTuijianListAdapter.setmDatas(tuijianList);
                        mTuijianListAdapter.notifyDataSetChanged();
                    }else{
                        about_tuijian.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onRequestFailed(IndexItemBean result) {
                about_tuijian.setVisibility(View.GONE);
                SysoutUtil.out(result);
            }
        });
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
        if(!NetUtils.isNetworkConnected()){
            showGenericNoNetwork();
        }else{
            showGenericNodata();
        }
    }
    private void initActions(){
        if(!TextUtils.isEmpty(intentPrivateUrl)){
            detail_video_layout.addView(videoItemView);
            videoItemView.start(intentPrivateUrl);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (videoItemView!=null){
            videoItemView.stop();
        }
    }

    @Override
    public void onRefresh(int action) {
        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            SysoutUtil.out("ACTION_PULL_TO_REFRESH--------------------");
            page = 1;
            getCommentMessage(page);

        }else if(action== PullRecycler.ACTION_LOAD_MORE_REFRESH){
            SysoutUtil.out("ACTION_LOAD_MORE_REFRESH--------------------");
            page++;
            getCommentMessage(page);
        }
    }

    @Event(value = {R.id.detail_edit, R.id.detail_back, R.id.start, R.id.detail_linear_count, R.id.detail_linear_favirate, R.id.detail_linear_share, R.id.bottom_loadmore, R.id.generic_back_click, R.id.generic_back_click_network}, type = View.OnClickListener.class)
    private void onClick(View view) {
        if (view.getId() == R.id.detail_edit) {
            if(!NetUtils.isNetworkConnected()){
                ToastUtils.showToast(getResources().getString(R.string.toast_net_work_error));
                return;
            }
            if(LoginUtils.isAlreadyLogin()){

                setDetailDialog();
            }else {
                startActivity(new Intent(activity, LoginActivity.class));
            }
        }else if(view.getId() == R.id.detail_back){
            if (videoItemView!=null){
                videoItemView.stop();
            }
            finish();
        }else if (view.getId() == R.id.detail_linear_count) {
            if(!NetUtils.isNetworkConnected()){
                ToastUtils.showToast(getResources().getString(R.string.toast_net_work_error));
                return;
            }
        } else if (view.getId() == R.id.detail_linear_favirate) {
            String user_id = CacheUtils.getInstance().getString(LocalContents.USER_ID,"");
            if(isClickFavirate){
                getCommentOtherMessage().requestNetWorkFavirateClick(user_id, intentArticleId, 0, intentTraceId);
                isClickFavirate = false;
//                ToastUtils.showToast("收藏成功");
                detail_text_favirate.setBackgroundResource(R.drawable.my_favorites_icon_down);
            }else{
                getCommentOtherMessage().requestNetWorkFavirateClick(user_id, intentArticleId, 1, intentTraceId);
                isClickFavirate = true;
//                ToastUtils.showToast("取消收藏");
                detail_text_favirate.setBackgroundResource(R.drawable.my_favorites_icon_up);
            }
        } else if (view.getId() == R.id.detail_linear_share) {
            if(!NetUtils.isNetworkConnected()){
                ToastUtils.showToast(getResources().getString(R.string.toast_net_work_error));
                return;
            }
            RecommentDialog recommentDialog = new RecommentDialog(this, new RecommentDialog.RecommentDialogOnClickListener() {
                @Override
                public void OnFriendCicleClick() {
                    String shareWechatFriend = Api.SHARE_ACTIONG_URL +"?id="+ intentArticleId + "&trace_id="+intentTraceId + "&media_type=2";
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
                    String shareQQFriend = Api.SHARE_ACTIONG_URL +"?id="+ intentArticleId + "&trace_id="+intentTraceId + "&media_type=3";
                    instance = new TencentUtils(DetailVideoActivity.this);
                    instance.doShareToQQ(intentTitle, ParamsApi.webViewUrlAndShareUrl(shareQQFriend), intentTitle, intentIcon, intentTitle,false);
                }

                @Override
                public void OnQQZone() {
                    String shareQQZone = Api.SHARE_ACTIONG_URL +"?id="+ intentArticleId + "&trace_id="+intentTraceId + "&media_type=4";
                    instance = new TencentUtils(DetailVideoActivity.this);
                    instance.doShareToQzone(intentTitle, ParamsApi.webViewUrlAndShareUrl(shareQQZone), intentTitle, intentIcon,false);
                }
            });
            recommentDialog.show();
        }else if (view.getId() == R.id.generic_back_click) {
            if (videoItemView!=null){
                videoItemView.stop();
            }
            finish();
        } else if (view.getId() == R.id.generic_back_click_network) {
            if (videoItemView!=null){
                videoItemView.stop();
            }
            finish();
        } else if (view.getId() == R.id.bottom_loadmore) {
//            ToastUtils.showToast("加载更多");
            page++;
            getCommentMessage(page);
            bottom_loadmore_pro.setVisibility(View.VISIBLE);
            footer.setText("正在加载中...");
        }
    }

    private void shareWeibo() {
        String shareWeibo = Api.SHARE_ACTIONG_URL +"?id="+ intentArticleId + "&trace_id="+intentTraceId + "&media_type=5";
        shareUtils = new SinaWeibo(this);
        shareUtils.requestShareWeibo(this, intentTitle, intentTitle,  ParamsApi.webViewUrlAndShareUrl(shareWeibo), "",intentIcon,false, R.drawable.app_logo);
    }

    /**
     * 详情页其他接口
     */

    public CommentDetailOtherPersenterImpl getCommentOtherMessage() {
        return new CommentDetailOtherPersenterImpl(this);
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

    @Override
    public void setDatas(List<CommentDetailBean> datas) {

    }

    @Override
    public void setData(CommentDetailBean data) {
//        detail_recyclerview.onRefreshCompleted();

        if(page ==1 && data.getData().size() == 0){
            ChangeNoData(false, getResources().getString(R.string.layout_no_data), R.drawable.loading_no_data);
        }else{
            hideGenericNodata();
            hideGenericNoNetwork();
        }
        commentList.addAll(data.getData());

        if (data.getData().size() < 5) {
            footer.setVisibility(View.GONE);
        } else {
            footer.setVisibility(View.VISIBLE);
            footer.setText(getResources().getString(R.string.toast_load_more));
        }
        bottom_loadmore.setVisibility(View.VISIBLE);
        bottom_loadmore_pro.setVisibility(View.GONE);
        detailVideoAdapter.setmDatas(commentList);
        detailVideoAdapter.notifyDataSetChanged();

        Utils.ShowBadgeView(context, data.getTotal(), detail_linear_count, 8);   //设置小红点
    }

    @Override
    public void netWorkError(CommentDetailBean data) {
//        detail_recyclerview.onRefreshCompleted();
        if("0".equals(data.getmCode())){
//            detail_recyclerview.enableLoadMore(false);
        }else{
            ToastUtils.showToast(data.getmMessage());
//            detail_recyclerview.enableLoadMore(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (instance != null) {
            instance.releaseResource();
            instance = null;
        }
        if (videoItemView!=null){
            videoItemView.stop();
        }
    }

    @Override
    public void setOtherData(BaseResult data) {
        ToastUtils.showToast("data:" + data.getmMessage());
        if ("200".equals(data.getmCode())) {
            if (isClickFavirate == false) {   //收藏成功
                ToastUtils.showToast("收藏成功");
                detail_text_favirate.setBackgroundResource(R.drawable.my_favorites_icon_down);
                mSQL.insertFavirateData(intentArticleId, "success");
            } else {   //点赞成功
                ToastUtils.showToast("取消收藏");
                detail_text_favirate.setBackgroundResource(R.drawable.my_favorites_icon_up);
                mSQL.updateFavirateData(intentArticleId, "fail");
                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.FAVIRATE_REFRESH);

            }
        }
    }

    @Override
    public void setOtherData(List<BaseResult> data) {

    }

    @Override
    public void error(BaseResult data) {

    }

    @Override
    public void onDataChanged(IssueKey issue, Object o) {
        if(issue.equals(IssueKey.LOGIN_ADD_BIND_SCCESS)){
            setDetailDialog();
        }else if(issue.equals(IssueKey.RECOMMEND_REFRESH)){
            page = 1;
            getCommentMessage(page);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (videoItemView!=null){
            videoItemView.onChanged(newConfig);
            if (newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
                fullScreen.setVisibility(View.GONE);
                detail_recyclerview.setVisibility(View.VISIBLE);
                layout_detail_relate.setVisibility(View.VISIBLE);
                fullScreen.removeAllViews();
//                if (postion<=mLayoutManager.findLastVisibleItemPosition()
//                        &&postion>=mLayoutManager.findFirstVisibleItemPosition()) {
//                    View view = videoList.findViewHolderForAdapterPosition(postion).itemView;
//                    FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.layout_video);
//                    frameLayout.removeAllViews();
//                    frameLayout.addView(videoItemView);
//                    videoItemView.setShowContoller(true);
//                }else {
                    detail_video_layout.removeAllViews();
                    detail_video_layout.addView(videoItemView);
                    videoItemView.setShowContoller(true);
//                }
                videoItemView.setContorllerVisiable();
            }else {
                ViewGroup viewGroup= (ViewGroup) videoItemView.getParent();
                if (viewGroup==null)
                    return;
                viewGroup.removeAllViews();
                fullScreen.addView(videoItemView);
                detail_recyclerview.setVisibility(View.GONE);
                layout_detail_relate.setVisibility(View.GONE);
                fullScreen.setVisibility(View.VISIBLE);
                detail_back.setVisibility(View.VISIBLE);
            }
        }else {
            detail_recyclerview.setVisibility(View.VISIBLE);
            layout_detail_relate.setVisibility(View.VISIBLE);
            fullScreen.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return true;
            }else{
                if (videoItemView!=null){
                    videoItemView.stop();
                }
                finish();
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(shareUtils != null){
            IWeiboShareAPI weiboShare = shareUtils.getWeiboShare();
            if(weiboShare != null){
                weiboShare.handleWeiboResponse(intent,this);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PromptUtils.dismissProgressDialog();
        //新浪微博的回调
        if(shareUtils != null){
            shareUtils.onActivityResult(requestCode,resultCode,data);
        }
        //qq分享和登陆回调
        if(instance != null){
            instance.onActivityResultData(requestCode, resultCode, data);
        }
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
     * 设置点赞和收藏的状态
     */
    private void setLikeOrFavirate() {

        mSQL = new SQLiteData(context);
        String isFavirate = mSQL.hasFavirateData(intentArticleId);
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
    /**
     * 该文章是否被查看过,外部列表文字要变色
     */
    private void articleIsShow(){
        mSQL.insertIsshowData(intentArticleId);
        if(intentPosition != -2){
            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.ARTICLE_IS_SHOW, intentPosition);
        }
    }

    @Override
    public void onItemClick(View view, int position, Object info) {
        SysoutUtil.out("position------------------:" + position);
        Intent intent;
        if(TextUtils.isEmpty(((IndexItemBean.IndexDataBean)info).getPrivate_url())){
            intent = new Intent(activity, DetailsX5Activity.class);
        }else{
            intent = new Intent(activity, DetailVideoActivity1.class);
        }
        intent.putExtra("intentArticleId", ((IndexItemBean.IndexDataBean)info).getAid());
        intent.putExtra("intentPosition", position);
        intent.putExtra("intentTitle", ((IndexItemBean.IndexDataBean)info).getTitle());
        intent.putExtra("intentIcon", ((IndexItemBean.IndexDataBean)info).getMaster_img());
        intent.putExtra("intentReadCount", ((IndexItemBean.IndexDataBean)info).getLook_num());
        intent.putExtra("intentTime", ((IndexItemBean.IndexDataBean)info).getAtime());
        intent.putExtra("intentCommentCount", ((IndexItemBean.IndexDataBean)info).getCommend_num());
        intent.putExtra("intentPublicUrl", ((IndexItemBean.IndexDataBean)info).getPublic_url());
        intent.putExtra("intentTraceId", ((IndexItemBean.IndexDataBean)info).getTrace_id());
        ((IndexItemBean.IndexDataBean)info).setShow(true);
        startActivity(intent);
        mTuijianListAdapter.notifyDataSetChanged();
    }
}
