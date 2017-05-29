package com.dora.feed.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dora.feed.mvp.bean.IndexItemBean;
import com.dora.feed.mvp.presenter.TabIndexItemPresenterImpl;
import com.dora.feed.mvp.view.BaseView;
import com.dora.feed.net.ParamsApi;
import com.dora.feed.net.RequestUtils;
import com.dora.feed.view.ChangeNetWorkDialog;
import com.dora.feed.view.DetailVideoActivity;
import com.dora.feed.view.DetailsX5Activity;
import com.dora.feed.view.StarDetailActivity;
import com.dora.feed.view.StarSettingActivity;
import com.dora.feed.view.adapter.IndexItemAdapter;
import com.dora.feed.widget.sqlite.SQLiteData;
import com.famlink.frame.util.CacheUtils;
import com.famlink.frame.util.LocalContents;
import com.famlink.frame.util.NetUtils;
import com.famlink.frame.util.SysoutUtil;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.util.Utils;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.IssueKey;
import com.famlink.frame.util.notify.OnDataChangeObserver;
import com.famlink.frame.view.fragment.BaseFragment;
import com.famlink.frame.widget.dialog.LogoutDialog;
import com.famlink.frame.widget.pullrecycleview.BaseDataBindingAdapter;
import com.famlink.frame.widget.pullrecycleview.DividerItemDecoration;
import com.famlink.frame.widget.pullrecycleview.PullRecycler;
import com.famlink.frame.widget.pullrecycleview.layoutmanager.ILayoutManager;
import com.famlink.frame.widget.pullrecycleview.layoutmanager.MyGridLayoutManager;
import com.dora.feed.R;
import com.dora.feed.databinding.LayoutFragmentIndexMainBinding;
//import com.dora.feed.view.DetailsActivity;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangkai on 16/7/11.
 */
public class IndexMainFragment extends BaseFragment<LayoutFragmentIndexMainBinding> implements BaseView.TabIndexItemView, PullRecycler.OnRecyclerRefreshListener, BaseDataBindingAdapter.OnItemBaseClickListener, OnDataChangeObserver, BaseFragment.NoDataNetWorkChangeRefreshListener {
    static final String FRAGMENT_INDEX = "fragment_index";
    private int index = 0;  //切换ViewPager时传入的参数
    private int openStar = 0;
    @ViewInject(R.id.pullRecycler)
    private PullRecycler recyclerView;

    @ViewInject(R.id.notice_tip)
    RelativeLayout notice_tip;
    @ViewInject(R.id.notice_title)
    TextView notice_title;

    private MyGridLayoutManager manager;

    private IndexItemAdapter indexItemAdapter;

    private int type = -1;

    private boolean isLoad;
    private boolean isSettingStar;
    private View view;

    private TabIndexItemPresenterImpl tabNewsPresenter;

    private long bhv_amt_1;
    private long bhv_amt_2;

    private SQLiteData mSQL;

    private List<IndexItemBean.IndexDataBean> categoryAllList = new ArrayList<IndexItemBean.IndexDataBean>();   //存储除了第一个分类的别的所有分类下拉的时候的数据存储
    private IndexItemBean.IndexStarDataBean starList = new IndexItemBean().new IndexStarDataBean();
    public static Fragment newInstance(int index) {
        Bundle bundle = new Bundle();
        IndexMainFragment fragment = new IndexMainFragment();
        bundle.putInt(FRAGMENT_INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        SysoutUtil.out("onAttach");
    }

    @Override
    public void onStart() {
        super.onStart();
//        SysoutUtil.out("onStart");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            index = bundle.getInt(FRAGMENT_INDEX);
        }
    }
    @Override
    public void onBaseCreateView(View view, Bundle savedInstanceState) {
        this.view = view;

        if (!isPrepared || !isVisible || isLoad) {
            return;
        }
        if(view == null){
            if(!NetUtils.isNetworkConnected()){
                showGenericNoNetwork();
                hideGenericNodata();
                return;
            }else{
                hideGenericNoNetwork();
                showGenericNodata();
            }
        }
        DataChangeNotification.getInstance().addObserver(IssueKey.STAR_UPDATE, this);
        DataChangeNotification.getInstance().addObserver(IssueKey.ARTICLE_IS_SHOW, this);

        indexItemAdapter = new IndexItemAdapter(context, (ArrayList<IndexItemBean.IndexDataBean>) categoryAllList);
        manager = new MyGridLayoutManager(context, 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
//                int size = itemBeanList.size();
//                switch (indexItemAdapter.getItemViewType(position)){
//                    case 1:
//                    case 2:
//                    case 4:
//                        return 2;
//                    case 3:
//                        return 1;
//                }
//                return 2;
                switch (indexItemAdapter.getItemViewType(position)){
                    case 0:
                    case 1:
                    case 3:
                    case 5:
                    case 100:
//                    case 8:
                        return 2;

                    default:
                        return 1;
                }
            }
        });
        recyclerView.setOnRefreshListener(this);
        recyclerView.setLayoutManager((ILayoutManager) manager);
//        recyclerView.addItemDecoration(getItemDecoration());   //RecyclerView Item中间的分割线
        recyclerView.setAdapter(indexItemAdapter);

        recyclerView.setRefreshing();
        indexItemAdapter.setOnItemClickListener(this);
        isLoad = true;


    }

    /**
     * 设置Footview底部和列表的线
     * @return
     */
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(context, R.drawable.comment_pullrecycle_list_divider);
    }


    @Override
    public int setLayout() {
        return R.layout.layout_fragment_index_main;
    }

    @Override
    public void setInterfaceView() {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        SysoutUtil.out(18 / fontScale + 0.5f);
    }

    @Override
    public void setGenericNodataOrNonetwork() {
        setChangeRefresh(this);
        instantiationNoDataNetWork(view);
        genericNoData(false, getResources().getString(com.famlink.frame.R.string.layout_loading_data), "");
        genericNoNetwork(true);
        genericNoNetworkSetting(false, view);
        if(!NetUtils.isNetworkConnected()){
            showGenericNoNetwork();
        }else{
            showGenericNodata();
        }
    }

    @Override
    protected void dataCallback(int result, Object obj) {

    }

    /**
     * 上传IDS的日志
     * @param ids
     * @param trace_id
     */
    private StringBuffer ids;
    private StringBuffer trace_id;
    private void UpdateIds(String ids, String trace_id){
//        ParamsApi.updateShow(ids, trace_id);
        RequestUtils.updateShow(ids,trace_id);
    }


    @Override
    public void netWorkError(IndexItemBean data) {
        recyclerView.onRefreshCompleted();
        if("0".equals(data.getmCode())){
            recyclerView.enableLoadMore(false);
        }else{
            ToastUtils.showToast(data.getmMessage());
            recyclerView.enableLoadMore(true);
        }
    }

    @Override
    public void onRefresh(int action) {
        SysoutUtil.out("action:" + action);
        if(!NetUtils.isNetworkConnected()){
            topTips(getResources().getString(R.string.toast_net_work_error));
            recyclerView.onRefreshCompleted();
            return;
        }
        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            if(type == -1){
                getDataPresenterImpl(type);
                return;
            }
            type = 0;
            getDataPresenterImpl(type);
            SysoutUtil.out("ACTION_PULL_TO_REFRESH--------------------");
        }else if(action== PullRecycler.ACTION_LOAD_MORE_REFRESH){
            SysoutUtil.out("ACTION_LOAD_MORE_REFRESH--------------------");
            type = 1;
            getDataPresenterImpl(type);
        }
    }
    @Override
    public void setDatas(List<IndexItemBean> datas) {
        SysoutUtil.out("data:"+ datas.size());
    }
    @Override
    public void setData(IndexItemBean data) {
        List<IndexItemBean.IndexDataBean> mData = data.getData();
        recyclerView.onRefreshCompleted();
        mSQL.deleteClickIdData();
        if(-1 == type){
            type = 0;
        }
        if(0 == type){
            if(0 == index){  //第一个分类
                if(mData.size() == 0 || mData.size() < 11){
                    ChangeNoData(true, getResources().getString(R.string.layout_no_data), R.drawable.loading_no_data);
                    topTips(getResources().getString(R.string.tips_msg0));
                    return;
                }
                categoryAllList.clear();
                if(openStar == 1){
                    indexItemAdapter.setOpenStar(openStar);
                    starList = null;
                    starList = data.getSdata();
                }
            }else{
                if(mData.size() == 0){
                    ChangeNoData(true, getResources().getString(R.string.layout_no_data), R.drawable.loading_no_data);
                    topTips(getResources().getString(R.string.tips_msg0));
                    return;
                }
            }
            hideGenericNodata();
            hideGenericNoNetwork();
            topTips(String.format(getResources().getString(R.string.tips_msg1), mData.size()));

        }
        if(1 == type && mData.size() == 0){
            ToastUtils.showToast(getResources().getString(R.string.load_more_all));
            recyclerView.enableLoadMore(false);
        }else {
            recyclerView.enableLoadMore(true);
        }
        if(0 != index && 1 != type){
            if(-1 == type){
                categoryAllList.addAll(mData);
            }else if(0 == type){
                mData.addAll(categoryAllList);
                categoryAllList = mData;
            }
            indexItemAdapter.setmDatas(categoryAllList);
        }else{
            categoryAllList.addAll(data.getData());
            indexItemAdapter.setmDatas(categoryAllList);
        }
        indexItemAdapter.setSettingStar(isSettingStar);
        indexItemAdapter.setCategoryType(index);
        indexItemAdapter.notifyDataSetChanged();

        UpdateIds(data.getData());
    }
    public void getDataPresenterImpl(int type){
//        SysoutUtil.out("切换了第几个Viewpager：" + index + "openStar:" + openStar);
        if(index == 0){
            openStar = 1;
        }else{
            openStar = 0;
        }
       String starString = CacheUtils.getInstance().getString("star");
        if(TextUtils.isEmpty(starString)){
            starString="狮子座#";
            isSettingStar = false;
        }else{
            isSettingStar = true;
        }
        mSQL = new SQLiteData(context);
        tabNewsPresenter = new TabIndexItemPresenterImpl(this, 0+"", String.valueOf(index), type, openStar, starString, mSQL.hasClickIdData());
        tabNewsPresenter.requestNetWork();
    }

    @Override
    public void onItemClick(View view, int position, Object info) {

        IndexItemBean.IndexDataBean mData = (IndexItemBean.IndexDataBean)info;



        mSQL.insertClickIdData(mData.getAid());

        bhv_amt_1 = System.currentTimeMillis();
        if(!TextUtils.isEmpty(mData.getPrivate_url())){

            changeNetworkDialog((IndexItemBean.IndexDataBean)info, position);

        }else if(position == 1 && openStar == 1){
            if(isSettingStar){
                Intent intent = new Intent(activity, StarDetailActivity.class);
                if(starList != null){
                    intent.putExtra("starModel", starList);
                }
                startActivity(intent);
            }else{
                Intent intent = new Intent(activity, StarSettingActivity.class);
                startActivity(intent);
            }
        }else{
            Intent intent = new Intent(activity, DetailsX5Activity.class);
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
        }
    }

    @Override
    public void onDataChanged(IssueKey issue, Object o) {
        if(issue.equals(IssueKey.STAR_UPDATE)){  //星座更改完之后刷新
            type = 0;
            getDataPresenterImpl(type);
        }else if(issue.equals(IssueKey.ARTICLE_IS_SHOW)){
                int position = (int)o;
                categoryAllList = (List<IndexItemBean.IndexDataBean>)indexItemAdapter.getmDatas();
                if(categoryAllList.size() >= position){
                    if(categoryAllList.size() != 0){
                        categoryAllList.get(position).setShow(true);
                        indexItemAdapter.notifyItemChanged(position);
                    }
               }
        }
    }

    @Override
    public void onChangeRefresh() {
        type = 0;
        getDataPresenterImpl(type);
        SysoutUtil.out("ACTION_PULL_TO_REFRESH--------------------");
    }
    /*******************************************Tips提示*************************************************/
    public void topTips(final String tip){
        new Thread(){
            @Override
            public void run() {
                super.run();
                Message msg = new Message();
                msg.what = 0;
                msg.obj = tip;
                mHandler.sendMessage(msg);
                try {
                    sleep(2200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                notice_tip.setVisibility(View.VISIBLE);
                notice_title.setText((String)msg.obj);
            }else if(msg.what == 1){
                notice_tip.setVisibility(View.GONE);
            }
        }
    };

    /*******************************************点击视频弹出是否是Wifi网络判断*************************************************/
    ChangeNetWorkDialog changeNetWorkDialog;
    private void changeNetworkDialog(final IndexItemBean.IndexDataBean info, final int position){
        if(!NetUtils.isNetworkConnected()){
           ToastUtils.showToast(getResources().getString(R.string.toast_net_work_error));
            return;
        }
        if(NetUtils.getNetworkType() == 1){
            intentVideo(info, position);
        }else{
            int change_network = CacheUtils.getInstance().getInt(LocalContents.CHANGE_NETWORK, 0);
            if(change_network == 1){
                intentVideo(info, position);

            }else {
                changeNetWorkDialog = new ChangeNetWorkDialog(context, new ChangeNetWorkDialog.onClickListener() {
                    @Override
                    public void onClickConfirm() {
                        CacheUtils.getInstance().putInt(LocalContents.CHANGE_NETWORK, 0);

                        CacheUtils.getInstance().putInt(LocalContents.CHANGE_NETWORK, 1);
                        intentVideo(info, position);
                    }
                });
                changeNetWorkDialog.show();
            }
        }
    }
    private void intentVideo(IndexItemBean.IndexDataBean info, int position){
        Intent intent = new Intent(activity, DetailVideoActivity.class);
        intent.putExtra("intentArticleId", ((IndexItemBean.IndexDataBean)info).getAid());
        intent.putExtra("intentPosition", position);
        intent.putExtra("intentTitle", ((IndexItemBean.IndexDataBean)info).getTitle());
        intent.putExtra("intentIcon", ((IndexItemBean.IndexDataBean)info).getMaster_img());
        intent.putExtra("intentReadCount", ((IndexItemBean.IndexDataBean)info).getLook_num());
        intent.putExtra("intentTime", ((IndexItemBean.IndexDataBean)info).getAtime());
        intent.putExtra("intentPrivateUrl", ((IndexItemBean.IndexDataBean)info).getPrivate_url());
        intent.putExtra("intentTraceId", ((IndexItemBean.IndexDataBean)info).getTrace_id());
        startActivity(intent);
    }
    /*******************************************上传Ids日志*************************************************/
    private void UpdateIds(List<IndexItemBean.IndexDataBean> data){
        ids  = new StringBuffer();
        trace_id  = new StringBuffer();
        for (int i = 0; i < data.size(); i++) {
            if(!TextUtils.isEmpty(data.get(i).getAid())){
                ids.append(data.get(i).getAid()).append(",");
                trace_id.append((data.get(i).getTrace_id() == null ? "" : data.get(i).getTrace_id())).append(",");
            }
        }
        UpdateIds(ids.toString().substring(0, ids.length()-1), trace_id.toString().substring(0, trace_id.length()-1));
    }
}



















