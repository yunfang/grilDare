package com.dora.feed.view;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dora.feed.mvp.bean.IndexItemBean;
import com.famlink.frame.net.biz.BaseRequestCallback;
import com.famlink.frame.util.CacheUtils;
import com.famlink.frame.util.LocalContents;
import com.famlink.frame.util.NetUtils;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.view.activity.BaseActivity;
import com.famlink.frame.widget.pullrecycleview.BaseDataBindingAdapter;
import com.famlink.frame.widget.pullrecycleview.DividerItemDecoration;
import com.famlink.frame.widget.pullrecycleview.PullRecycler;
import com.famlink.frame.widget.pullrecycleview.layoutmanager.ILayoutManager;
import com.famlink.frame.widget.pullrecycleview.layoutmanager.MyLinearLayoutManager;
import com.dora.feed.R;
import com.dora.feed.mvp.bean.SearchBeanTip;
import com.dora.feed.mvp.bean.SearchIndexBean;
import com.dora.feed.net.ParamsApi;
import com.dora.feed.view.adapter.SearchHistoryAdapter;
import com.dora.feed.view.adapter.SearchIndexAdapter;
import com.dora.feed.view.adapter.SearchTipAdapter;
import com.dora.feed.widget.MyListView;
import com.dora.feed.widget.sqlite.RecordSQLiteOpenHelper;

import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by admin on 2016/7/6.
 * 搜索的页面
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener, PullRecycler.OnRecyclerRefreshListener, BaseDataBindingAdapter.OnItemBaseClickListener {

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;

    @ViewInject(R.id.id_edit)
    EditText id_edit;

    @ViewInject(R.id.tv_tip)
    TextView tv_tip;

    @ViewInject(R.id.listView)
    MyListView listView;

    @ViewInject(R.id.tv_clear)
    TextView tv_clear;

    @ViewInject(R.id.id_scroll_view)
    ScrollView id_scroll_view;

    @ViewInject(R.id.pullRecycler)
    PullRecycler pullRecycler;

    SearchIndexAdapter myAdapter;


    ArrayList<SearchIndexBean.Data> list = new ArrayList<SearchIndexBean.Data>();


    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);
    private SQLiteDatabase db;
    private BaseAdapter adapter;
    private SearchTipAdapter searchTipAdapter;

    private int page = 1;
    private String name;//搜索的keyword

    @Override
    public int setLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void setInterfaceView() {
        searchTipAdapter = new SearchTipAdapter(this);
        toolbar.setNavigationIcon(R.drawable.back); //更改菜单图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        id_edit.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent event) {

                Drawable drawable = id_edit.getCompoundDrawables()[2]; //如果右边没有图片，不再处理
                if (drawable == null) return false;
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > id_edit.getWidth() - id_edit.getPaddingRight() - drawable.getIntrinsicWidth()) {
                    id_edit.setText("");
//                    Toast.makeText(context, "szdlfkjsljdfs", 0).show();
//			           InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//			           imm.hideSoftInputFromWindow(mid_type_edit.getWindowToken(),0);
                    queryData("");
                }

                return false;
            }
        });

        // 搜索框的键盘搜索键点击回调
        id_edit.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
//                    boolean hasData = hasData(id_edit.getText().toString().trim());
//                    if (!hasData) {
//                        insertData(id_edit.getText().toString().trim());
//                        queryData("");
//                    }
//                     TODO 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
//                    Toast.makeText(MainActivity.this, "clicked!", Toast.LENGTH_SHORT).show();
                    if(TextUtils.isEmpty(id_edit.getText().toString().trim())){
                        return false;
                    }else{
                        requestSearchIndex(true,id_edit.getText().toString().trim(),1);
                    }
                }
                return false;
            }
        });

        // 搜索框的文本变化实时监听
        id_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    tv_tip.setText("搜索历史");
                } else {
                    tv_tip.setText("搜索结果");
                }
                String tempName = id_edit.getText().toString();
                // 根据tempName去模糊查询数据库中有没有数据
//                queryData(tempName);
                if(s.toString().trim().length() == 0){
                    return;
                }
                name = tempName;
                queryDataNetTip(tempName);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.search_listview_tv);
                name = textView.getText().toString();
                id_edit.setText(name);
//                Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
                // TODO 获取到item上面的文字，根据该关键字跳转到另一个页面查询，由你自己去实现
                if(name.length() == 0){
                    return;
                }

                requestSearchIndex(true,name,1);

            }
        });

        tv_clear.setOnClickListener(this);

        // 插入数据，便于测试
//        Date date = new Date();
//        long time = date.getTime();
//        insertData("Leo" + time);

        // 第一次进入查询所有的历史记录
        queryData("");





        myAdapter = new SearchIndexAdapter(context,list);
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
    //搜索
    private void requestSearchIndex(final boolean isinsert,final String name, final int page) {
        ParamsApi.requestSearchIndex(name,page).execute(new BaseRequestCallback<SearchIndexBean>() {
            @Override
            public void onRequestSucceed(SearchIndexBean result) {
                if(isinsert){
                    if(!hasData(name)){
                        insertData(name);
                    };
                }

                pullRecycler.setVisibility(View.VISIBLE);
                id_scroll_view.setVisibility(View.GONE);
                pullRecycler.onRefreshCompleted();
                if(page == 1){
                    list.clear();
                }
                list.addAll(result.getData());
                if(result.getPage_count() != page){
                    pullRecycler.enableLoadMore(true);
                }else {
                    pullRecycler.enableLoadMore(false);
                }
//                if(list.size() == 0){
//                    showGenericNodata();
//                }else{
//                    hideGenericNodata();
//                }
                myAdapter.setmDatas(list);
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onRequestFailed(SearchIndexBean result) {
                ToastUtils.showToast(getResources().getString(R.string.search_error));
                pullRecycler.onRefreshCompleted();
//                if(list.size() == 0){
//                    showGenericNodata();
//                }else{
//                    hideGenericNodata();
//                }
            }
        });
    }


    private void queryDataNetTip(final String tempName) {
//        hideGenericNodata();
        ParamsApi.requestSearchTip(tempName).execute(new BaseRequestCallback<SearchBeanTip>() {
            @Override
            public void onRequestSucceed(SearchBeanTip result) {
                pullRecycler.setVisibility(View.GONE);
                id_scroll_view.setVisibility(View.VISIBLE);
                listView.setAdapter(searchTipAdapter);
                searchTipAdapter.setList(result.getData());
                searchTipAdapter.notifyDataSetChanged();

            }

            @Override
            public void onRequestFailed(SearchBeanTip result) {
                ToastUtils.showToast(getResources().getString(R.string.search_error));
            }
        });
    }

    @Override
    public void setGenericNodataOrNonetwork() {
//        instantiationNoDataNetWork();
//        genericNoData(false, "", "");
//        genericNoNetwork(false);
//        genericNoNetworkSetting(false);
    }

    /**
     * 插入数据
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 创建adapter适配器对象
//        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[] { "name" },
//                new int[] { android.R.id.text1 }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        adapter = new SearchHistoryAdapter(this, R.layout.search_listview_item, cursor, new String[] { "name" },
                new int[] { R.id.search_listview_tv }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 清空数据
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_clear:
                deleteData();
                queryData("");
                break;
        }
    }

    @Override
    public void onRefresh(int action) {
        if(TextUtils.isEmpty(name)){
            return;
        }

        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            page = 1;
            System.out.println("ACTION_PULL_TO_REFRESH--------------------");
            requestSearchIndex(false,name, page);

        }else if(action== PullRecycler.ACTION_LOAD_MORE_REFRESH){
            System.out.println("ACTION_LOAD_MORE_REFRESH--------------------");
            page ++;
            requestSearchIndex(false,name,page);
        }
    }

    @Override
    public void onItemClick(View view, int position, Object info) {
        if(TextUtils.isEmpty(((SearchIndexBean.Data)info).getPrivate_url())){
            Intent intent = new Intent(activity, DetailsX5Activity.class);
            intent.putExtra("intentArticleId",((SearchIndexBean.Data)info).getId());
            intent.putExtra("intentPosition", position);
            intent.putExtra("intentTitle",((SearchIndexBean.Data)info).getTitle());
            intent.putExtra("intentIcon",((SearchIndexBean.Data)info).getMaster_img());
            intent.putExtra("intentReadCount",((SearchIndexBean.Data)info).getLook_num());
            intent.putExtra("intentTime",getDateToString(((SearchIndexBean.Data)info).getCreate_time()));
            intent.putExtra("intentCommentCount", ((SearchIndexBean.Data)info).getCommend_num());
            intent.putExtra("intentPublicUrl", ((SearchIndexBean.Data)info).getPublic_url());
            startActivity(intent);
        }else{
            changeNetworkDialog((SearchIndexBean.Data)info);
        }

    }
/*时间戳转换成字符窜*/
    public static String getDateToString(String time) {
        long timeStr = Long.valueOf(time);
        Date d = new Date(timeStr);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sf.format(d);
    }


    /*******************************************点击视频弹出是否是Wifi网络判断*************************************************/

    ChangeNetWorkDialog changeNetWorkDialog;
    private void changeNetworkDialog(final SearchIndexBean.Data data){
        if(!NetUtils.isNetworkConnected()){
            ToastUtils.showToast(context.getResources().getString(R.string.toast_net_work_error));
            return;
        }
        if(NetUtils.getNetworkType() == 1){
            intentVideo(data);
        }else{
            int change_network = CacheUtils.getInstance().getInt(LocalContents.CHANGE_NETWORK, 0);
            if(change_network == 1){
                intentVideo(data);

            }else {
                changeNetWorkDialog = new ChangeNetWorkDialog(context, new ChangeNetWorkDialog.onClickListener() {
                    @Override
                    public void onClickConfirm() {
                        CacheUtils.getInstance().putInt(LocalContents.CHANGE_NETWORK, 1);
                        intentVideo(data);
                    }
                });
                changeNetWorkDialog.show();
            }
        }
    }
    private void intentVideo(SearchIndexBean.Data data){
        Intent intent = new Intent(context, DetailVideoActivity.class);
        intent.putExtra("intentArticleId",data.getId());
        intent.putExtra("intentTitle",data.getTitle());
        intent.putExtra("intentIcon",data.getMaster_img());
        intent.putExtra("intentReadCount",data.getLook_num());
        intent.putExtra("intentTime",data.getCreate_time());
        intent.putExtra("intentPrivateUrl", data.getPrivate_url());
        intent.putExtra("intentPrivateUrl", data.getPublic_url());
//        intent.putExtra("intentTraceId", mList.get(position).getTrace_id());
        context.startActivity(intent);
    }
}
