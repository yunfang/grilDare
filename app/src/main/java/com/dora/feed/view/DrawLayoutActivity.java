package com.dora.feed.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dora.feed.config.Constants;
import com.dora.feed.mvp.bean.IndexItemBean;
import com.dora.feed.mvp.presenter.drawlayout.DrawViewInterface;
import com.dora.feed.mvp.presenter.drawlayout.DrawtViewPersenterImpl;
import com.dora.feed.net.Api;
import com.dora.feed.net.ParamsApi;
import com.dora.feed.utils.LoginUtils;
import com.dora.feed.utils.MyPushIntentService;
import com.dora.feed.utils.TencentUtils;
import com.dora.feed.view.fragment.CommentFragment;
import com.dora.feed.view.fragment.FavoritesFragment;
import com.dora.feed.view.fragment.FeedBackFragment;
import com.dora.feed.view.fragment.IndexFragment;
import com.dora.feed.view.fragment.MyMessageFragment;
import com.dora.feed.view.fragment.RecommendFragment;
import com.dora.feed.view.fragment.SettingFragment;
import com.dora.feed.widget.dialog.RecommentDialog;
import com.famlink.frame.R;
import com.famlink.frame.util.ActivityManager;
import com.famlink.frame.util.AndroidUtils;
import com.famlink.frame.util.CacheUtils;
import com.famlink.frame.util.LocalContents;
import com.famlink.frame.util.SysoutUtil;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.util.Utils;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.IssueKey;
import com.famlink.frame.util.notify.ObserverGroup;
import com.famlink.frame.util.notify.OnDataChangeObserver;
import com.famlink.frame.view.activity.BaseActivity;
import com.famlink.frame.widget.circleview.CircularImageView;
import com.famlink.frame.widget.circleview.HeadRoundImageView;
import com.famlink.frame.widget.dialog.PromptUtils;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.constant.WBConstants;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.IUmengUnregisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.dora.feed.net.RequestUtils;
import com.dora.feed.utils.SinaWeibo;
import com.dora.feed.utils.WXUtils;
import com.umeng.message.UmengRegistrar;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import static com.dora.feed.R.id.textView;

//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;


/**
 * Created by wangkai on 16/6/15.
 */
public class DrawLayoutActivity extends BaseActivity implements DrawViewInterface, IWeiboHandler.Response, OnDataChangeObserver, View.OnClickListener {
    @ViewInject(R.id.appbar)
    AppBarLayout appar;
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @ViewInject(R.id.navigation_view) NavigationView navigation_view;
    @ViewInject(R.id.navigateBar_title)
    TextView navigateBar_title;


    AppBarLayout.LayoutParams mParams;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawtViewPersenterImpl mDrawViewImpl;
    private FragmentManager supportFragmentManager;
    private IndexFragment indexFragment;
    private FavoritesFragment favoritesFragment;
    private CommentFragment commentFragment;
    private RecommendFragment recommendFragment;
    private SettingFragment settingFragment;
    private RecommentDialog recommentDialog;
    //    private Toolbar toolbar;
//    private DrawerLayout drawer_layout;
//    private NavigationView navigation_vaiew;
//    private IWXAPI api;
    private int mBackKeyPressedTimes = 0;
    private SinaWeibo shareUtils;
    //    public Tencent mTencent;
    private TencentUtils instance;
    private FeedBackFragment feedBackFragment;
    private PushAgent mPushAgent;
    private HeadRoundImageView head_image;//头像
    private TextView name;
    private MyMessageFragment myMessageFragment;
    private LinearLayout navigation_header_ll;


    private int enterTag;

    private int selectMenu;  //获取当前选中了哪个navigateMenu

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        Bundle bundle = getIntent().getBundleExtra(Constants.EXTRA_BUNDLE);
        if(bundle != null){
            Intent intent = new Intent(activity, DetailsX5Activity.class);
            intent.putExtra("intentArticleId", bundle.getString("intentArticleId"));
            intent.putExtra("intentPosition", -2);
            intent.putExtra("intentTitle", bundle.getString("intentTitle"));
            intent.putExtra("intentIcon", bundle.getString("intentIcon"));
            intent.putExtra("intentReadCount", bundle.getString("intentReadCount"));
            intent.putExtra("intentTime", bundle.getString("intentTime"));
            intent.putExtra("intentCommentCount", bundle.getString("intentCommentCount"));
            intent.putExtra("intentPublicUrl", bundle.getString("intentPublicUrl"));
            intent.putExtra("intentTraceId", bundle.getString("intentTraceId"));
            startActivity(intent);

        }




        CacheUtils.getInstance().putString(LocalContents.SCREEM_W_H, AndroidUtils.getMetrics(activity));

        mDrawerToggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        toolbar.setNavigationIcon(com.dora.feed.R.drawable.xml_click_menu_list_selector); //更改菜单图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED); //打开手势滑动
                drawer_layout.openDrawer(navigation_view);
            }
        });
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); //关闭手势滑动
//        drawer_layout.setDrawerListener(mDrawerToggle);
        drawer_layout.setDrawerListener(new MyDrawerListener());
        setupDrawerContent(navigation_view);
//        toolbar.setBackgroundResource(R.drawable.ximi);
//        navigateBar_title.setVisibility(View.VISIBLE);
//        navigateBar_title.setText(R.string.navigation_1);
//        toolbar.setTitle(R.string.navigation_1);
        mDrawViewImpl = new DrawtViewPersenterImpl(this);
        mParams = (AppBarLayout.LayoutParams) appar.getChildAt(0).getLayoutParams();
        switchIndexs(false);
        View headerView = navigation_view.getHeaderView(0);
        name = (TextView) headerView.findViewById(R.id.check_login_tv);
        head_image = (HeadRoundImageView) headerView.findViewById(R.id.profile_image);
        navigation_header_ll = (LinearLayout) headerView.findViewById(R.id.navigation_header_ll);
        navigation_header_ll.setOnClickListener(this);
//        navigation_view.setItemTextColor((ColorStateList) getResources().getColorStateList(R.color.navigation_menu_item_color));
        navigation_view.setItemIconTintList(null);
        ColorStateList csl=(ColorStateList)getResources().getColorStateList(R.color.navigation_menu_item_color);    //设置选中的时候图标和文字是同一种颜色
        navigation_view.setItemTextColor(csl);
        navigation_view.setBackgroundColor(getResources().getColor(R.color.white));




        initPush();
        initheadView();
        getUmengNumber();

    }
    public void initPush(){
        mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        mPushAgent.setDebugMode(false);
        mPushAgent.onAppStart();
        if(CacheUtils.getInstance().getBoolean(LocalContents.LOCAL_UMENG_STATUS,true)){
            mPushAgent.enable(mRegisterCallback);
        }
        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
        mPushAgent.getMessageHandler();
    }


    @Override
    public int setLayout() {

        CacheUtils.getInstance().putInt(LocalContents.CHANGE_NETWORK, 0);
        mObserverGroup = ObserverGroup.createDrawLayoutGroup();
        DataChangeNotification.getInstance().addObserver(IssueKey.UPDATEHEAD,this,mObserverGroup);
        DataChangeNotification.getInstance().addObserver(IssueKey.USER_LOGOUT,this,mObserverGroup);
        DataChangeNotification.getInstance().addObserver(IssueKey.SWITCHPUSH,this,mObserverGroup);
        DataChangeNotification.getInstance().addObserver(IssueKey.BACK_LOGOUT,this,mObserverGroup);
        DataChangeNotification.getInstance().addObserver(IssueKey.FEEDBACK_SUCCESS,this,mObserverGroup);
        DataChangeNotification.getInstance().addObserver(IssueKey.UMENG_BEDGE_NUMBER_CLEAR,this,mObserverGroup);
        DataChangeNotification.getInstance().addObserver(IssueKey.UMENG_BEDGE_NUMBER,this,mObserverGroup);


        // 动态注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.SEND_BROADCAST_DRAW);
        UpdateUIBroadcastReceiver broadcastReceiver = new UpdateUIBroadcastReceiver();
        registerReceiver(broadcastReceiver, filter);


        return R.layout.v7_activity_drawerlayout;
    }



    private class MyDrawerListener implements DrawerLayout.DrawerListener {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {// drawer滑动的回调
            mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {//开关状态改为opened
            mDrawerToggle.onDrawerOpened(drawerView);//开关状态改为opened
        }

        @Override
        public void onDrawerClosed(View drawerView) {// 关闭drawer
            mDrawerToggle.onDrawerClosed(drawerView);//开关状态改为closed
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); //关闭手势滑动
        }

        @Override
        public void onDrawerStateChanged(int newState) {// drawer状态改变的回调
            mDrawerToggle.onDrawerStateChanged(newState);
        }
    }

    @Override
    public void setInterfaceView() {

    }

    @Override
    public void setGenericNodataOrNonetwork() {

    }
    Menu searchMenu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.search) {
//            ToastUtils.showToast("Search");
            startActivity(new Intent(this,SearchActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDrawViewImpl.switchNavigation(menuItem.getItemId());
                        menuItem.setChecked(true);
                        if (menuItem.getItemId() != R.id.navigation_group_5) {
                            drawer_layout.closeDrawers();
                        }
                        return true;
                    }
                });
    }

    @Override
    public void switchIndexs(boolean isShow) {
        selectMenu = 1;
        if(isShow){
            (toolbar.getMenu().findItem(R.id.search)).setVisible(true);   //切换到别的界面时search按钮应该隐藏
        }
        mParams.setScrollFlags(5);//AppBarLayout下的toolbar会随着滚动条折叠
//        toolbar.setTitle(R.string.navigation_1);
        toolbar.setTitle("");
        toolbar.setLogo(com.dora.feed.R.drawable.navi_icon);
//        toolbar.setBackgroundResource(com.dora.feed.R.drawable.ximi);
//        navigateBar_title.setText(R.string.navigation_1);

        navigation_view.getMenu().getItem(0).setChecked(true);   //设置默认选中某个

        mParams = (AppBarLayout.LayoutParams) appar.getChildAt(0).getLayoutParams();
        supportFragmentManager = getSupportFragmentManager();
        if(indexFragment == null){
            indexFragment = new IndexFragment();
        }
        switchFragment(indexFragment);
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new IndexFragment()).commit();
    }

    @Override
    public void switchFavorites() {
        selectMenu = 2;
        (toolbar.getMenu().findItem(R.id.search)).setVisible(false);   //切换到别的界面时search按钮应该隐藏
        mParams.setScrollFlags(0); //AppBarLayout下的toolbar不会随着滚动条折叠
        toolbar.setTitle(R.string.navigation_2);
        toolbar.setLogo(R.color.transparent);
//        navigateBar_title.setText(R.string.navigation_2);
        if(favoritesFragment == null){
            favoritesFragment = new FavoritesFragment();
        }else{
            favoritesFragment.RefreshData();
        }
        switchFragment(favoritesFragment);
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FavoritesFragment(), TAG_THEME).commit();

    }

    @Override
    public void switchComment() {
//        selectMenu = 3;
        if(LoginUtils.isAlreadyLogin()){
            (toolbar.getMenu().findItem(R.id.search)).setVisible(false);   //切换到别的界面时search按钮应该隐藏
            mParams.setScrollFlags(0); //AppBarLayout下的toolbar不会随着滚动条折叠
            toolbar.setTitle(R.string.navigation_3);
            toolbar.setLogo(R.color.transparent);
//        navigateBar_title.setText(R.string.navigation_3);
            if(commentFragment == null){
                commentFragment = new CommentFragment();
            }
            switchFragment(commentFragment);
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new CommentFragment(), TAG_THEME).commit();
        }else{
            enterTag = 1;
//            navigation_view.getMenu().getItem(2).setCheckable(false);  //设置不要选中状态
            navigation_view.getMenu().getItem(2).setChecked(false);  //设置不要选中状态
            startActivity(new Intent(this,LoginActivity.class));
        }

    }

    @Override
    public void switchMessage() {
//        selectMenu = 4;
        if(LoginUtils.isAlreadyLogin()){
            (toolbar.getMenu().findItem(R.id.search)).setVisible(false);   //切换到别的界面时search按钮应该隐藏
            mParams.setScrollFlags(0); //AppBarLayout下的toolbar不会随着滚动条折叠
            toolbar.setTitle(R.string.navigation_4);
            toolbar.setLogo(R.color.transparent);
//        navigateBar_title.setText(R.string.navigation_3);
            if(myMessageFragment == null){
                myMessageFragment = new MyMessageFragment();
            }else{
                myMessageFragment.onChangeRefresh();
            }
            switchFragment(myMessageFragment);

//            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.UMENG_BEDGE_NUMBER_CLEAR, 0);
//            CacheUtils.getInstance().putInt(LocalContents.UMENG_NOTIFICATION_NUMBER, 0);
//            SharedPreferences mSettings = context.getSharedPreferences(CacheUtils.PREF_NAME, Context.MODE_PRIVATE);
//            SharedPreferences.Editor edit = mSettings.edit();
//            edit.putInt(LocalContents.UMENG_NOTIFICATION_NUMBER, 0);
//            edit.commit();
            umengNotification(0);
        }else{
            enterTag = 2;
//            navigation_view.getMenu().getItem(3).setCheckable(false);  //设置不要选中状态
            navigation_view.getMenu().getItem(3).setChecked(false);  //设置不要选中状态
            startActivity(new Intent(this,LoginActivity.class));
        }
    }

    @Override
    public void switchShareFriend() {
//        toolbar.setTitle(R.string.navigation_1);
//        navigateBar_title.setText(R.string.navigation_4);


        recommentDialog = new RecommentDialog(this, new RecommentDialog.RecommentDialogOnClickListener() {
            @Override
            public void OnFriendCicleClick() {
                WXUtils.wechatShare(activity, 1, Api.SHARE_LEFT_URL, getResources().getString(R.string.share_tips_tips), getResources().getString(R.string.share_tips_tips), getResources(), "",true,com.dora.feed.R.drawable.app_logo);
            }

            @Override
            public void OnWeChatClick() {
                WXUtils.wechatShare(activity,0, Api.SHARE_LEFT_URL, getResources().getString(R.string.share_tips_tips), getResources().getString(R.string.share_from), getResources(),"",true,com.dora.feed.R.drawable.app_logo);
            }

            @Override
            public void OnMicroBlogSina() {
                shareWeibo();

            }

            @Override
            public void OnQQFriend() {
                instance = new TencentUtils(DrawLayoutActivity.this);
                instance.doShareToQQ(getResources().getString(R.string.app_name), Api.SHARE_LEFT_URL, getResources().getString(R.string.share_tips_1), "", null,true);
            }

            @Override
            public void OnQQZone() {
                instance = new TencentUtils(DrawLayoutActivity.this);
                instance.doShareToQzone(getResources().getString(R.string.share_tips_tips), Api.SHARE_LEFT_URL, null,"",true);
            }
        });
        recommentDialog.show();

    }

    private void shareWeibo() {
//        shareUtils = new SinaWeibo(this);
//        shareUtils.requestShareWeibo(this,"","","","",R.mipmap.ic_launcher);
        shareUtils = new SinaWeibo(this);
        shareUtils.requestShareWeibo(this, getResources().getString(R.string.share_tips_tips), getResources().getString(R.string.share_tips_tips), Api.SHARE_LEFT_URL, "", "",true,com.dora.feed.R.drawable.app_logo);
    }

    @Override
    public void switchSetting() {
        selectMenu = 5;
        (toolbar.getMenu().findItem(R.id.search)).setVisible(false);   //切换到别的界面时search按钮应该隐藏
        mParams.setScrollFlags(0); //AppBarLayout下的toolbar不会随着滚动条折叠
        toolbar.setTitle(R.string.navigation_6);
        toolbar.setLogo(R.color.transparent);
//        navigateBar_title.setText(R.string.navigation_5);
        if(settingFragment == null){
            settingFragment = new SettingFragment();
        }
        switchFragment(settingFragment);
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new SettingFragment(), TAG_THEME).commit();
    }

    @Override
    public void switchFeedback() {
        selectMenu = 6;
        (toolbar.getMenu().findItem(R.id.search)).setVisible(false);   //切换到别的界面时search按钮应该隐藏
        mParams.setScrollFlags(0); //AppBarLayout下的toolbar不会随着滚动条折叠
        toolbar.setTitle(R.string.setting_feedback);
        toolbar.setLogo(R.color.transparent);
        if(feedBackFragment == null){
            feedBackFragment = new FeedBackFragment();
        }
        switchFragment(feedBackFragment);
    }

    public void switchFragment(Fragment fragment){
        List<Fragment> fragmentslist = supportFragmentManager.getFragments();
        if(fragmentslist != null){
            for(int i = 0;i<fragmentslist.size();i++){
                supportFragmentManager.beginTransaction().hide(fragmentslist.get(i)).commitAllowingStateLoss();
            }
        }

        if(fragment.isAdded()){
            if(fragment instanceof IndexFragment){
                TAG_THEME = TAG_OUT;
            }else{
                TAG_THEME = TAG_BACK;
            }
            supportFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
        }else {
            if(fragment instanceof IndexFragment){
                TAG_THEME = TAG_OUT;
            }else{
                TAG_THEME = TAG_BACK;
            }
            supportFragmentManager.beginTransaction().add(R.id.frame_content, fragment).commitAllowingStateLoss();
        }
    }
    private String TAG_THEME;
    private String TAG_BACK = "app_back";
    private String TAG_OUT = "out";
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
    //新浪微博的回调    注意:暂定微博分享和登陆不给提示。在主配置文件中不设置回调页面的intent-filter，intent-filter只能在一个页面配置回调
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

    @Override
    public void onBackPressed() {
        if(drawer_layout.isDrawerOpen(navigation_view)){
            drawer_layout.closeDrawers();
        }else{
            if(TAG_BACK.equals(TAG_THEME)){
                switchIndexs(true);
            }else if(TAG_OUT.equals(TAG_THEME)){
//                super.onBackPressed();
                exit();
            }
        }
    }
    private long clickTime = 0; //记录第一次点击的时间
    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
            this.finish();
            ActivityManager.instance().finishActivities();
            System.exit(0);
//            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(instance != null){
            instance.releaseResource();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navigation_header_ll:
                if(LoginUtils.isAlreadyLogin()){
                    Utils.logout(this, new Utils.onLogoutListener() {
                        @Override
                        public void onConfirm() {
                            LoginUtils.logout(DrawLayoutActivity.this, LoginActivity.class);
                        }
                    });
                }else{
                    startActivity(new Intent(DrawLayoutActivity.this,LoginActivity.class));
                }
            break;

        }
    }


    //初始化头像和昵称
    public void initheadView(){
        if(LoginUtils.isAlreadyLogin()){
            name.setText(CacheUtils.getInstance().getString(LocalContents.LOGIN_USER_NAME));
            x.image().bind(head_image, CacheUtils.getInstance().getString(LocalContents.LOGIN_USER_HEAD));
        }
//        SysoutUtil.out("-----------umengNumber-----------" + CacheUtils.getInstance().getInt(LocalContents.UMENG_NOTIFICATION_NUMBER, 0));
    }
    //此处是注销的回调处理
    //参考集成文档的1.7.10
    //http://dev.umeng.com/push/android/integration#1_7_10
    public IUmengUnregisterCallback mUnregisterCallback = new IUmengUnregisterCallback() {

        @Override
        public void onUnregistered(String registrationId) {
            // TODO Auto-generated method stub
            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.UMENG_PUSH_CLOSE);
            CacheUtils.getInstance().putBoolean(LocalContents.LOCAL_UMENG_STATUS, false);
        }
    };

    IUmengRegisterCallback mRegisterCallback = new IUmengRegisterCallback() {
        @Override
        public void onRegistered(String registrationId) {
            //onRegistered方法的参数registrationId即是device_token
            System.out.println("device_token:--" + registrationId);
            CacheUtils.getInstance().putString(LocalContents.UMENG_DEVICE_TOKEN, registrationId);
            CacheUtils.getInstance().putBoolean(LocalContents.LOCAL_UMENG_STATUS,true);
            ParamsApi.initApi();//设备注册
            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.UMENG_PUSH_OPEN);
        }
    };


    private void switchPush(){
        if (mPushAgent.isEnabled() || UmengRegistrar.isRegistered(this)) {
            //开启推送并设置注册的回调处理
            mPushAgent.disable(mUnregisterCallback);
        } else {
            //关闭推送并设置注销的回调处理
            mPushAgent.enable(mRegisterCallback);
        }
    }

    @Override
    public void onDataChanged(IssueKey issue, Object o) {
        if(issue.equals(IssueKey.UPDATEHEAD)){
            String[] arr = (String[]) o;
            name.setText(arr[0]);
            x.image().bind(head_image, arr[1]);
            String from = "";
            String thrid_token = CacheUtils.getInstance().getString(LocalContents.ACCESS_TOKEN);
            if(thrid_token.startsWith("QQ")){
                from = "qq";
                thrid_token = thrid_token.substring(2,thrid_token.length());
            }else if(thrid_token.startsWith("SINA")){
                from = "weibo";
                thrid_token = thrid_token.substring(5,thrid_token.length());
            }else if(thrid_token.startsWith("WEIXIN")){
                from = "wechat";
                thrid_token = thrid_token.substring(6,thrid_token.length());
            }
            if(enterTag == 1){
                navigation_view.getMenu().getItem(2).setChecked(true);  //设置我的评论为选中
                switchComment();
            }else if(enterTag == 2){
                navigation_view.getMenu().getItem(3).setChecked(true);  //设置我的消息为选中
                switchMessage();
            }
//            umengNotification();
            getUmengNumber();
            RequestUtils.requestLogin(thrid_token,arr[1],arr[0],from);

        }else if(issue.equals(IssueKey.USER_LOGOUT)){
            name.setText(Utils.getString(context, R.string.check_login));
            head_image.setImageResource(R.drawable.app_icon_left);
        }else if(issue.equals(IssueKey.SWITCHPUSH)){
            switchPush();
        }else if(issue.equals(IssueKey.BACK_LOGOUT)){
            switch (selectMenu){
                case 1:
                    navigation_view.getMenu().getItem(0).setChecked(true);  //设置第一个选中
                    break;
                case 2:
                    navigation_view.getMenu().getItem(1).setChecked(true);  //设置第二个选中
                    break;
                case 5:
                    navigation_view.getMenu().getItem(5).setChecked(true);  //设置第六个选中
                    break;
                case 6:
                    navigation_view.getMenu().getItem(6).setChecked(true);  //设置第七个选中
                    break;
                default:
                    navigation_view.getMenu().getItem(1).setChecked(true);  //设置第一个选中
            }

        }else if(issue.equals(IssueKey.FEEDBACK_SUCCESS)){
            navigation_view.getMenu().getItem(0).setChecked(true);  //设置我的评论为选中
            switchIndexs(true);
        }else if(issue.equals(IssueKey. UMENG_BEDGE_NUMBER)){
            umengNotification((int)o);
        }
    }
    /**
     * 定义广播接收器（内部类）
     * @author lenovo
     *
     */
    private class UpdateUIBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            umengOpenActivityNotification(intent.getIntExtra("umengNumber", 0));

        }
    }
    /**
     * 页面没有打开的时候的通知红点个数
     */
    public void umengNotification(int umengNumber){
        TextView msg = null;
        LinearLayout gallery = (LinearLayout) navigation_view.getMenu().findItem(R.id.navigation_group_4).getActionView();  //设置消息右侧的小红点
        msg= (TextView) gallery.findViewById(R.id.msg);
        if(LoginUtils.isAlreadyLogin()){
//            SharedPreferences mSettings = context.getSharedPreferences(CacheUtils.PREF_NAME, Context.MODE_PRIVATE);
//            int umengNumber = mSettings.getInt(LocalContents.UMENG_NOTIFICATION_NUMBER, 0);
            if(umengNumber > 0){
                navigateBar_title.setVisibility(View.VISIBLE);
                Utils.ShowBadgeView(context, umengNumber, navigateBar_title, 8);   //设置菜单按钮的小红点
                msg.setVisibility(View.VISIBLE);
                msg.setText(umengNumber+"");
            }else{
                msg.setVisibility(View.GONE);
                Utils.ShowBadgeView(context, 0, navigateBar_title, 8);   //设置菜单按钮的小红点
                navigateBar_title.setVisibility(View.GONE);
            }
        }else{
            msg.setVisibility(View.GONE);
        }
    }
    /**
     * 页面打开的时候的通知红点个数
     */
    private void umengOpenActivityNotification(int umengNumber){
        TextView msg = null;
        LinearLayout gallery = (LinearLayout) navigation_view.getMenu().findItem(R.id.navigation_group_4).getActionView();  //设置消息右侧的小红点
        msg= (TextView) gallery.findViewById(R.id.msg);
        if(LoginUtils.isAlreadyLogin()){
            if(umengNumber > 0){
                navigateBar_title.setVisibility(View.VISIBLE);
                Utils.ShowBadgeView(context, umengNumber, navigateBar_title, 8);   //设置菜单按钮的小红点
                msg.setVisibility(View.VISIBLE);
                msg.setText(umengNumber+"");
            }else{
                msg.setVisibility(View.GONE);
                Utils.ShowBadgeView(context, 0, navigateBar_title, 8);   //设置菜单按钮的小红点
                navigateBar_title.setVisibility(View.GONE);
            }
        }
    }
    private void getUmengNumber (){
        RequestUtils.checkVersion(true, getActivity(),"","",new RequestUtils.OnUpdateVersionListener(){
            @Override
            public void NoNeedUpdateVersion() {
            }
        });
    }
}
