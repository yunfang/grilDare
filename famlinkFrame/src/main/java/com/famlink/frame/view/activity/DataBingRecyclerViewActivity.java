package com.famlink.frame.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.famlink.frame.R;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.view.adapter.BaseDataBindingRecyAdapter;
import com.famlink.frame.view.adapter.DataBingRecyAdapter;

import java.util.ArrayList;

/**
 * Created by wangkai on 16/6/29.
 */
public class DataBingRecyclerViewActivity extends BaseActivity implements BaseDataBindingRecyAdapter.OnItemBaseClickListener{
    @Override
    public int setLayout() {
        return R.layout.activity_data_bingrecyclerview;
    }

    @Override
    public void setInterfaceView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        DataBingRecyAdapter adapter = new DataBingRecyAdapter(context, mData);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setGenericNodataOrNonetwork() {

    }


    private RecyclerView mRecyclerView;
    private ArrayList<Student> mData = new ArrayList<Student>() {
        {
            for (int i=0;i<100;i++) {
                add(new Student("loader" + i, 18 + i));
            }
        }
    };

    @Override
    public void onItemClick(View view, int position, Object info) {
        ToastUtils.showToast("设置了点击事件" + position + ((Student)info).getName());
    }
    public void click(View view) {
        ToastUtils.showToast("设置了点击事件");
    }
}
