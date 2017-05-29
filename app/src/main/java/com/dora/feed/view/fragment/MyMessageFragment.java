package com.dora.feed.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dora.feed.mvp.view.BaseView;
import com.famlink.frame.util.CacheUtils;
import com.famlink.frame.util.LocalContents;
import com.famlink.frame.util.NetUtils;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.IssueKey;
import com.famlink.frame.view.fragment.BaseFragment;
import com.famlink.frame.widget.pullrecycleview.BaseDataBindingAdapter;
import com.famlink.frame.widget.pullrecycleview.DividerItemDecoration;
import com.famlink.frame.widget.pullrecycleview.PullRecycler;
import com.famlink.frame.widget.pullrecycleview.layoutmanager.ILayoutManager;
import com.famlink.frame.widget.pullrecycleview.layoutmanager.MyLinearLayoutManager;
import com.dora.feed.R;
import com.dora.feed.databinding.LayoutFragmentMessageBinding;
import com.dora.feed.mvp.bean.MessageBean;
import com.dora.feed.mvp.presenter.MessagePersenterImpl;
import com.dora.feed.view.DetailDialog;
import com.dora.feed.view.adapter.Messagedapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息
 * Created by admin on 2016/7/11.
 */
public class MyMessageFragment extends BaseFragment<LayoutFragmentMessageBinding> implements PullRecycler.OnRecyclerRefreshListener, BaseFragment.NoDataNetWorkChangeRefreshListener, BaseDataBindingAdapter.OnItemBaseClickListener, BaseView.MessageView {
    private View view;
    private PullRecycler pullRecycler;
    private ArrayList<MessageBean.Data> list = new ArrayList<MessageBean.Data>();
    private int page = 1;
    private Messagedapter myAdapter;


    @Override
    public void onBaseCreateView(View view, Bundle savedInstanceState) {
        this.view = view;
    }

    @Override
    public int setLayout() {
        return R.layout.layout_fragment_message;
    }

    @Override
    public void setInterfaceView() {
        pullRecycler = (PullRecycler) view.findViewById(R.id.pullRecycler);
        myAdapter = new Messagedapter(context,list);
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
        genericNoData(false, getResources().getString(R.string.layout_loading_data), "");
        genericNoNetwork(true);
        genericNoNetworkSetting(true, view);
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
            MessagePersenterImpl commentPersenter = new MessagePersenterImpl(this);
            commentPersenter.requestNetWork(page,CacheUtils.getInstance().getString(LocalContents.USER_ID));

        }else if(action== PullRecycler.ACTION_LOAD_MORE_REFRESH){
            System.out.println("ACTION_LOAD_MORE_REFRESH--------------------");
            MessagePersenterImpl commentPersenter = new MessagePersenterImpl(this);
            page++;
            commentPersenter.requestNetWork(page, CacheUtils.getInstance().getString(LocalContents.USER_ID));
        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                pullRecycler.enableLoadMore(true);
////        list.addAll(response.body().results);
//                myAdapter.notifyDataSetChanged();
//                pullRecycler.onRefreshCompleted();
//            }
//        },2000);
    }

    @Override
    public void onItemClick(View view, int position, Object info) {
//        ToastUtils.showToast("onItemClick" + position);
//        setDetailDialog(list.get(position).getAid()+"");
        setDetailDialog(list.get(position).getAid()+"",list.get(position).getDid(),list.get(position).getUid(),list.get(position).getNick());
    }


    @Override
    public void setDatas(List<MessageBean> datas) {

    }

    @Override
    public void setData(MessageBean data) {
        if(data.getData().size() == 0){
            ChangeNoData(true, getResources().getString(com.famlink.frame.R.string.layout_no_data), R.drawable.loading_no_data);
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
        if(data.getPage_count() != page){
            pullRecycler.enableLoadMore(true);
        }else {
            pullRecycler.enableLoadMore(false);
        }

        myAdapter.setmDatas(list);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void netWorkError(MessageBean data) {

    }

    /**
     * 弹出输入框
     */
    DetailDialog detailDialog;

    public void setDetailDialog(String aid,String did,String to_user_id,String to_user_name) {
        detailDialog = new DetailDialog(getActivity());
        detailDialog.setDetailMessage(aid, did,to_user_id , to_user_name);
        detailDialog.show();
        detailDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (detailDialog != null && detailDialog.isShowing()) {
                    detailDialog.dismiss();
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
    public void onChangeRefresh() {
        page = 1;
        System.out.println("ACTION_PULL_TO_REFRESH--------------------");
        MessagePersenterImpl commentPersenter = new MessagePersenterImpl(this);
        commentPersenter.requestNetWork(page,CacheUtils.getInstance().getString(LocalContents.USER_ID));
    }
}
