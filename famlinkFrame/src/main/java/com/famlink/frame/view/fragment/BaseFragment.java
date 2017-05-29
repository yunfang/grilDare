package com.famlink.frame.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.famlink.frame.R;
import com.famlink.frame.core.ModuleFactory;
import com.famlink.frame.core.module.AbsModule;
import com.famlink.frame.core.module.IOCProxy;
import com.famlink.frame.util.NetUtils;
import com.famlink.frame.util.StringUtil;
import com.famlink.frame.util.SysoutUtil;
import com.famlink.frame.widget.pullrecycleview.DividerItemDecoration;

import org.xutils.x;
/**
 * Created by wangkai on 16/6/16.
 */
public abstract  class BaseFragment<VB extends ViewDataBinding> extends Fragment{

    public Activity activity;
    public Context context;

    private View generic_no_data; //没有数据界面
    private View generic_no_network;  //没有网络界面
    private View generic_no_data_refresh;
    private ImageView no_data_img;
    private View generic_no_network_refresh;
    private View generic_no_network_setting;

    private  TextView textTitle, textContent;

    private AnimationDrawable animationDrawable;


    private boolean injected = false;
    public boolean isVisible;
    public boolean isPrepared;


    protected String TAG = "";
    private   VB            mBind;
    private ModuleFactory mModuleF;
    private   IOCProxy      mProxy;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBind = DataBindingUtil.inflate(inflater, setLayout(), container, false);
        injected = true;
        x.view().inject(this, mBind.getRoot());
        SysoutUtil.out("onCreateView");
        isPrepared = true;
        return mBind.getRoot();
    }
    private void initFragment() {
        TAG = StringUtil.getClassName(this);
        mProxy = IOCProxy.newInstance(this);
        mModuleF = ModuleFactory.newInstance();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
    private void onVisible() {
        onBaseCreateView(null, null);
    }

    private void onInvisible() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        onBaseCreateView(null, savedInstanceState);
//        setInterfaceView();
//        setGenericNodataOrNonetwork();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        setInterfaceView();
        if(!injected){
            x.view().inject(this, this.getView());
        }
        SysoutUtil.out("onViewCreated");
        onBaseCreateView(view, savedInstanceState);
        setInterfaceView();
        setGenericNodataOrNonetwork();
    }
    public abstract void onBaseCreateView(View view, Bundle savedInstanceState);

    /**
     * 设置界面XML 也就是Layout
     * @return
     */
    public abstract int setLayout();

    /**
     * 实例化界面的各种自定义接口
     */
    public abstract void setInterfaceView();
    /**
     * 无数据或者是无网络时的空白界面或者是无网络界面的实现
     */
    public abstract void setGenericNodataOrNonetwork();
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 无数据界面显示
     */
    public void showGenericNodata(){
        generic_no_data.setVisibility(View.VISIBLE);
    }

    /**
     * 无数据界面隐藏
     */
    public void hideGenericNodata(){
        if(animationDrawable != null){
            animationDrawable.stop();
        }
        generic_no_data.setVisibility(View.GONE);
    }

    public void instantiationNoDataNetWork(View rootView){
        instantiationNoData(rootView);
        instantiationNoNetWork(rootView);
    };
    /**
     * 没有数据的界面实例化
     */
    private void instantiationNoData(View rootView){
        generic_no_data = (View) rootView.findViewById(R.id.no_data);
        generic_no_data_refresh = (View) rootView.findViewById(R.id.no_data_refresh);
        no_data_img = (ImageView) rootView.findViewById(R.id.no_data_img);
        textTitle = ((TextView)rootView.findViewById(R.id.textTitle));
        textContent = ((TextView)rootView.findViewById(R.id.textContent));

        //初始化动画
        no_data_img.setImageResource(R.drawable.loading);
        animationDrawable = (AnimationDrawable) no_data_img.getDrawable();
        animationDrawable.start();
    }

    /**
     * 没有数据的界面实例化
     */
    private void instantiationNoNetWork(View rootView){
        generic_no_network = (View) rootView.findViewById(R.id.no_network);
        generic_no_network_refresh = (View) rootView.findViewById(R.id.no_network_refresh);
    }


    /**
     * 无网络界面显示
     */
    public void showGenericNoNetwork(){
        generic_no_network.setVisibility(View.VISIBLE);
    }

    /**
     * 无网络界面隐藏
     */
    public void hideGenericNoNetwork(){
        generic_no_network.setVisibility(View.GONE);
    }
    /**
     * 没有数据的界面
     */
    public View genericNoData(boolean isShow, String msg, String content){

        if(!TextUtils.isEmpty(msg)){
            textTitle.setText(msg);
            textContent.setText(content);
        }
        NoDataRefresh(isShow);
        return generic_no_data;
    }

    private View NoDataRefresh(boolean isShow){
        if(isShow){
            generic_no_data_refresh.setVisibility(View.VISIBLE);
            generic_no_data_refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    genericNoData(false, getResources().getString(R.string.layout_loading_data), "");
                    no_data_img.setImageResource(R.drawable.loading);
                    animationDrawable = (AnimationDrawable) no_data_img.getDrawable();
                    animationDrawable.start();
                    changeRefresh.onChangeRefresh();
                }
            });

        }else{
            generic_no_data_refresh.setVisibility(View.INVISIBLE);
        }

        return generic_no_data;
    }

    /**
     *
     * @param isShow  是否显示刷新按钮
     * @param msg    更改Tips
     * @param resourdId   更改图片
     */
    public void ChangeNoData(boolean isShow, String msg, int resourdId){
        if(animationDrawable != null){
            animationDrawable.stop();
        }
        if(!TextUtils.isEmpty(msg)){
            textTitle.setText(msg);
            no_data_img.setImageResource(resourdId);
        }
        NoDataRefresh(isShow);
    }
    /**
     * 没有网络的界面
     * @return
     */
    public View genericNoNetwork(boolean isShow){

        NoNetworkRefresh(isShow);
        return generic_no_network;
    }

    private View NoNetworkRefresh(boolean isShow){

        if(isShow){
            generic_no_network_refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeRefresh.onChangeRefresh();
                }
            });

        }else{
            generic_no_network_refresh.setVisibility(View.GONE);
        }
        return generic_no_network;
    }

    private NoDataNetWorkChangeRefreshListener changeRefresh;

    public void setChangeRefresh(NoDataNetWorkChangeRefreshListener changeRefresh) {
        this.changeRefresh = changeRefresh;
    }

    public interface NoDataNetWorkChangeRefreshListener{
        void onChangeRefresh();
    }


    /**
     * 设置顶部无网络条是否显示
     * @param isHide  true 显示   ///   false  不显示
     *              如果显示的话点击右侧进入setting界面设置网络
     */
    private boolean isClickSetting = false;
    public void genericNoNetworkSetting(boolean isHide, View rootView){
        generic_no_network_setting = (View) rootView.findViewById(R.id.generic_no_network_setting);
        if(isHide) {
            generic_no_network_setting.setVisibility(View.VISIBLE);
            generic_no_network_setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isClickSetting = true;
                    Intent wifiSettingsIntent = new Intent("android.settings.WIFI_SETTINGS");
                    startActivity(wifiSettingsIntent);
                }
            });
        }else {
            generic_no_network_setting.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(NetUtils.isNetworkConnected()){     //如果设置过网络返回过来的时候会重新刷新界面隐藏条形设置网络
            if(isClickSetting){
                if(generic_no_network_setting != null){
                    if(generic_no_network_setting.getVisibility() == View.VISIBLE){
                        generic_no_network_setting.setVisibility(View.GONE);
                    }
                }
            }
        }
    }




    /**
     * 获取binding对象
     */
    protected VB getBinding() {
        return mBind;
    }
    /**
     * 获取Module
     *
     * @param clazz {@link AbsModule}
     */
    protected <M extends AbsModule> M getModule(Class<M> clazz) {
        M module = mModuleF.getModule(getContext(), clazz);
        mProxy.changeModule(module);
        return module;
    }
    /**
     * 数据回调
     */
    protected abstract void dataCallback(int result, Object obj);

}
