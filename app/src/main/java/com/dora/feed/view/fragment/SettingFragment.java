package com.dora.feed.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dora.feed.view.RetroactionActivity;
import com.famlink.frame.util.CacheUtils;
import com.famlink.frame.util.LocalContents;
import com.famlink.frame.util.StringUtil;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.util.Utils;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.IssueKey;
import com.famlink.frame.util.notify.OnDataChangeObserver;
import com.famlink.frame.view.activity.AboutActivity;
import com.famlink.frame.view.fragment.BaseFragment;
import com.famlink.frame.widget.dialog.PromptUtils;
import com.dora.feed.R;
import com.dora.feed.databinding.LayoutFragmentSettingBinding;
import com.dora.feed.net.RequestUtils;
import com.dora.feed.utils.LoginUtils;
import com.dora.feed.view.CommentedDetailActivity;
import com.dora.feed.view.LoginActivity;
import com.dora.feed.view.StarSettingActivity;
import com.umeng.message.IUmengUnregisterCallback;
import com.umeng.message.UmengRegistrar;

import org.xutils.view.annotation.ViewInject;

/**
 * 设置
 * Created by admin on 2016/7/11.
 */
public class SettingFragment extends BaseFragment<LayoutFragmentSettingBinding> implements View.OnClickListener, OnDataChangeObserver {
    private View view;
    @ViewInject(R.id.setting_sagittarius_rl)
    RelativeLayout setting_sagittarius_rl;  //星座设定

    @ViewInject(R.id.setting_update_rl)
    RelativeLayout setting_update_rl;//检查更新

    @ViewInject(R.id.setting_feed_rl)
    RelativeLayout setting_feed_rl; //用户反馈

    @ViewInject(R.id.setting_about_rl)
    RelativeLayout setting_about_rl;  //关于我们

    @ViewInject(R.id.setting_evaluate_rl)
    RelativeLayout setting_evaluate_rl;  //评价

    @ViewInject(R.id.setting_logout_rl)
    RelativeLayout setting_logout_rl;   //退出登录

    @ViewInject(R.id.pingl_id)
    Button button;

    @ViewInject(R.id.btnEnable)
    ImageView btnEnable;


    @Override
    public void onBaseCreateView(View view, Bundle savedInstanceState) {
        getBinding().setStr2("设置");
        this.view = view;
    }

    @Override
    public int setLayout() {
        DataChangeNotification.getInstance().addObserver(IssueKey.USER_LOGOUT, this);
        DataChangeNotification.getInstance().addObserver(IssueKey.UPDATEHEAD, this);
        DataChangeNotification.getInstance().addObserver(IssueKey.UMENG_PUSH_CLOSE, this);
        DataChangeNotification.getInstance().addObserver(IssueKey.UMENG_PUSH_OPEN, this);
        return R.layout.layout_fragment_setting;
    }

    @Override
    public void setInterfaceView() {
        setting_sagittarius_rl.setOnClickListener(this);
        setting_update_rl.setOnClickListener(this);
        setting_feed_rl.setOnClickListener(this);
        setting_about_rl.setOnClickListener(this);
        setting_evaluate_rl.setOnClickListener(this);
        setting_logout_rl.setOnClickListener(this);
        btnEnable.setOnClickListener(this);
        button.setOnClickListener(this);
        if(!LoginUtils.isAlreadyLogin()){
            setting_logout_rl.setVisibility(View.GONE);
        }
        updateStatus();
    }

    @Override
    public void setGenericNodataOrNonetwork() {

    }

    @Override
    protected void dataCallback(int result, Object obj) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.setting_sagittarius_rl:  //星座设定
                startActivity(new Intent(getActivity(),StarSettingActivity.class));
                break;
            case R.id.setting_update_rl:   //检查更新
                PromptUtils.showProgressDialog(getContext(),Utils.getString(getActivity(),R.string.loding_check_update));
                RequestUtils.checkVersion(false, getActivity(),"","",new RequestUtils.OnUpdateVersionListener(){

                    @Override
                    public void NoNeedUpdateVersion() {
                        ToastUtils.showToast(getResources().getString(R.string.update_is_new));
                        PromptUtils.dismissProgressDialog();
                    }
                });
                break;
            case R.id.setting_feed_rl:  //用户反馈
                startActivity(new Intent(getActivity(), RetroactionActivity.class));
                break;
            case R.id.setting_about_rl:   //关于我们
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.setting_evaluate_rl://应用评价
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id="+context.getPackageName()));
                    startActivity(intent);
                }catch (Exception e){
                    ToastUtils.showToast(Utils.getString(getActivity(),R.string.no_market));
                }
                break;
            case R.id.setting_logout_rl:   //退出登录
                Utils.logout(getContext(), new Utils.onLogoutListener() {
                    @Override
                    public void onConfirm() {

                        LoginUtils.logout(getActivity(), LoginActivity.class);
//                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                });
                break;
            case R.id.pingl_id:
                startActivity(new Intent(getActivity(), CommentedDetailActivity.class));
                break;
            case R.id.btnEnable:
//                switchPush();
                PromptUtils.showProgressDialog(getContext(),R.string.three_loading);
                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.SWITCHPUSH);
                break;
        }

//        switch (v.getId()){
//            case R.id.setting_retroaction_rl:
//                startActivity(new Intent(getActivity(), RetroactionActivity.class));
//                break;
//            case R.id.setting_evaluate_rl:
//                try {
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse("market://details?id="+context.getPackageName()));
//                    startActivity(intent);
//                }catch (Exception e){
//                    ToastUtils.showToast(Utils.getString(getActivity(),R.string.no_market));
//                }
//                break;
//            case R.id.setting_update_rl:
//                PromptUtils.showProgressDialog(getContext(),Utils.getString(getActivity(),R.string.loding_check_update));
//                Utils.checkVersion(getActivity(), "", "", new Utils.OnUpdateVersionListener() {
//                    @Override
//                    public void NoNeedUpdateVersion() {
//                        PromptUtils.dismissProgressDialog();
//                    }
//                });
//                break;
//            case R.id.setting_about_rl:
//                startActivity(new Intent(getActivity(), AboutActivity.class));
//
//                break;
//            case R.id.logout_bu:
//                Utils.logout(getContext(), new Utils.onLogoutListener() {
//                    @Override
//                    public void onConfirm() {
//
//
//                        startActivity(new Intent(getActivity(), LoginActivity.class));
//                    }
//                });
//                break;
//        }
    }



    private void updateStatus() {
//        btnEnable.setImageResource(CacheUtils.getInstance().getBoolean(LocalContents.LOCAL_UMENG_STATUS, true)? R.drawable.open_button : R.drawable.close_button);
        btnEnable.setBackgroundResource(CacheUtils.getInstance().getBoolean(LocalContents.LOCAL_UMENG_STATUS, true)? R.drawable.open_button : R.drawable.close_button);
    }

    @Override
    public void onDataChanged(IssueKey issue, Object o) {
        if(issue.equals(IssueKey.USER_LOGOUT)){
            setting_logout_rl.setVisibility(View.GONE);
        }else if(issue.equals(IssueKey.UPDATEHEAD)){
            setting_logout_rl.setVisibility(View.VISIBLE);
        }else if(issue.equals(IssueKey.UMENG_PUSH_CLOSE)){
//            btnEnable.setImageResource( R.drawable.close_button);
            PromptUtils.dismissProgressDialog();
            btnEnable.setBackgroundResource(R.drawable.close_button);
            ToastUtils.showToast(Utils.getString(context,R.string.close_push));
        }else if(issue.equals(IssueKey.UMENG_PUSH_OPEN)){
//            btnEnable.setImageResource( R.drawable.open_button);
            PromptUtils.dismissProgressDialog();
            btnEnable.setBackgroundResource(R.drawable.open_button);
            ToastUtils.showToast(Utils.getString(context,R.string.open_push));;
        }
    }
}
