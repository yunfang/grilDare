package com.dora.feed.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.dora.feed.mvp.bean.FaoriteBean;
import com.dora.feed.mvp.view.BaseView;
import com.dora.feed.view.ChangeNetWorkDialog;
import com.dora.feed.view.DetailVideoActivity;
import com.dora.feed.view.DetailsX5Activity;
import com.dora.feed.view.adapter.FavoriteAdapter;
import com.famlink.frame.util.CacheUtils;
import com.famlink.frame.util.LocalContents;
import com.famlink.frame.util.NetUtils;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.IssueKey;
import com.famlink.frame.util.notify.OnDataChangeObserver;
import com.famlink.frame.view.fragment.BaseFragment;
import com.famlink.frame.widget.pullrecycleview.BaseDataBindingAdapter;
import com.famlink.frame.widget.pullrecycleview.DividerItemDecoration;
import com.famlink.frame.widget.pullrecycleview.PullRecycler;
import com.famlink.frame.widget.pullrecycleview.layoutmanager.ILayoutManager;
import com.famlink.frame.widget.pullrecycleview.layoutmanager.MyLinearLayoutManager;
import com.dora.feed.R;
import com.dora.feed.databinding.LayoutFragmentFavoritBinding;
import com.dora.feed.mvp.presenter.FaoritePersenterImpl;
//import com.dora.feed.view.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的收藏
 * Created by admin on 2016/7/11.
 */
public class FavoritesFragment extends BaseFragment<LayoutFragmentFavoritBinding> implements PullRecycler.OnRecyclerRefreshListener, BaseFragment.NoDataNetWorkChangeRefreshListener,  BaseDataBindingAdapter.OnItemBaseClickListener, BaseView.FavoriteView, OnDataChangeObserver {
    private View view;
    private PullRecycler pullRecycler;
    private ArrayList<FaoriteBean.Data> list = new ArrayList<FaoriteBean.Data>();
    private int page = 1;
    private FavoriteAdapter myAdapter;
    private int position;

    @Override
    public void onBaseCreateView(View view, Bundle savedInstanceState) {
        this.view = view;
    }

    @Override
    public int setLayout() {
        return R.layout.layout_fragment_favorit;
    }

    @Override
    public void setInterfaceView() {

        DataChangeNotification.getInstance().addObserver(IssueKey.FAVIRATE_REFRESH, this);

        pullRecycler = (PullRecycler) view.findViewById(R.id.pullRecycler);
        myAdapter = new FavoriteAdapter(context,list);
        pullRecycler.setOnRefreshListener(this);
        pullRecycler.setLayoutManager(getLayoutManager());
        pullRecycler.addItemDecoration(getItemDecoration());
        pullRecycler.setAdapter(myAdapter);
        pullRecycler.setRefreshing();
        myAdapter.setOnItemClickListener(this);

    }
    protected ILayoutManager getLayoutManager() {
        return new MyLinearLayoutManager(context);
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(context, R.drawable.comment_pullrecycle_list_divider);
    }

    @Override
    public void setGenericNodataOrNonetwork() {
        setChangeRefresh(this);
        instantiationNoDataNetWork(view);
        genericNoData(false, getResources().getString(com.famlink.frame.R.string.layout_loading_data), "");
        genericNoNetwork(true);
//        genericNoNetworkSetting(true, view);
        if(!NetUtils.isNetworkConnected()){
            showGenericNoNetwork();
        }else{
            showGenericNodata();
        }

    }

    @Override
    protected void dataCallback(int result, Object obj) {

    }

    @Override
    public void onRefresh(int action) {
//        pullRecycler.enableLoadMore(true);
        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            page = 1;
            System.out.println("ACTION_PULL_TO_REFRESH--------------------");
            FaoritePersenterImpl faoritePersenter = new FaoritePersenterImpl(this);
            faoritePersenter.requestNetWork(page, CacheUtils.getInstance().getString(LocalContents.USER_ID));

        }else if(action== PullRecycler.ACTION_LOAD_MORE_REFRESH){
            System.out.println("ACTION_LOAD_MORE_REFRESH--------------------");
            page ++;
            FaoritePersenterImpl faoritePersenter = new FaoritePersenterImpl(this);
            faoritePersenter.requestNetWork(page,  CacheUtils.getInstance().getString(LocalContents.USER_ID));
        }

    }



    @Override
    public void onItemClick(View view, int position, Object info) {
        this.position = position;
        if(TextUtils.isEmpty(list.get(position).getPrivate_url())){
            Intent intent = new Intent(context, DetailsX5Activity.class);
            intent.putExtra("intentArticleId",list.get(position).getAid());
            intent.putExtra("intentPosition", position);
            intent.putExtra("intentTitle",list.get(position).getTitle());
            intent.putExtra("intentIcon",list.get(position).getMaster_img());
            intent.putExtra("intentReadCount",list.get(position).getLook_num());
            intent.putExtra("intentTime",list.get(position).getAtime());
            intent.putExtra("intentPublicUrl",list.get(position).getPublic_url());
            intent.putExtra("intentCommentCount", list.get(position).getCommend_num());
            context.startActivity(intent);
        }else {
            changeNetworkDialog();
        }
    }

    ChangeNetWorkDialog changeNetWorkDialog;
    private void changeNetworkDialog(){
        if(!NetUtils.isNetworkConnected()){
            ToastUtils.showToast(context.getResources().getString(R.string.toast_net_work_error));
            return;
        }
        if(NetUtils.getNetworkType() == 1){
            intentVideo();
        }else{
            int change_network = CacheUtils.getInstance().getInt(LocalContents.CHANGE_NETWORK, 0);
            if(change_network == 1){
                intentVideo();

            }else {
                changeNetWorkDialog = new ChangeNetWorkDialog(context, new ChangeNetWorkDialog.onClickListener() {
                    @Override
                    public void onClickConfirm() {
                        CacheUtils.getInstance().putInt(LocalContents.CHANGE_NETWORK, 1);
                        intentVideo();
                    }
                });
                changeNetWorkDialog.show();
            }
        }
    }
    private void intentVideo(){
        Intent intent = new Intent(context, DetailVideoActivity.class);
        intent.putExtra("intentArticleId",list.get(position).getAid());
        intent.putExtra("intentTitle",list.get(position).getTitle());
        intent.putExtra("intentIcon",list.get(position).getMaster_img());
        intent.putExtra("intentReadCount",list.get(position).getLook_num());
        intent.putExtra("intentTime",list.get(position).getAtime());
        intent.putExtra("intentPrivateUrl", list.get(position).getPrivate_url());
//        intent.putExtra("intentTraceId", mList.get(position).getTrace_id());
        context.startActivity(intent);
    }

    @Override
    public void setDatas(List<FaoriteBean> datas) {

    }

    @Override
    public void setData(FaoriteBean data) {
        if(data.getData().size() == 0){
            ChangeNoData(true, getResources().getString(R.string.layout_no_data), R.drawable.loading_no_data);
            return;
        }else{
            hideGenericNodata();
            hideGenericNoNetwork();
        }
        pullRecycler.onRefreshCompleted();

        if(page == 1){
            list.clear();
        }
        list.addAll(data.getData());
        if(Integer.parseInt(data.getPage_count()) != page){
            pullRecycler.enableLoadMore(true);
        }else {
            pullRecycler.enableLoadMore(false);
        }


        myAdapter.setmDatas(list);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void netWorkError(FaoriteBean data) {

    }
    //刷新数据
    public void RefreshData() {
        pullRecycler.setRefreshing();
    }

    @Override
    public void onDataChanged(IssueKey issue, Object o) {
        if(issue.equals(IssueKey.USER_LOGOUT)){
            page = 1;
            System.out.println("ACTION_PULL_TO_REFRESH--------------------");
            FaoritePersenterImpl faoritePersenter = new FaoritePersenterImpl(this);
            faoritePersenter.requestNetWork(page, CacheUtils.getInstance().getString(LocalContents.USER_ID));
        }
    }

    @Override
    public void onChangeRefresh() {
        page = 1;
        System.out.println("ACTION_PULL_TO_REFRESH--------------------");
        FaoritePersenterImpl faoritePersenter = new FaoritePersenterImpl(this);
        faoritePersenter.requestNetWork(page, CacheUtils.getInstance().getString(LocalContents.USER_ID));

    }
}
