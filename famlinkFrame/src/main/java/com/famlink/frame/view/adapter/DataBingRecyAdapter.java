package com.famlink.frame.view.adapter;

/**
 * Created by wangkai on 16/6/14.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;


import com.famlink.frame.BR;
import com.famlink.frame.R;
import com.famlink.frame.view.activity.Student;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class DataBingRecyAdapter extends BaseDataBindingRecyAdapter {
    private ArrayList<Student> mData = new ArrayList<Student>();

    public DataBingRecyAdapter(Context context, ArrayList datas) {
        super(context, datas);
        this.mData = datas;
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, int position, Object data) {
        ((ViewHolder) holder).getBinding().setVariable(BR.stu, ((Student)data));
        ((ViewHolder) holder).getBinding().setVariable(BR.click, new MyHandler(context, mData));
        ((ViewHolder) holder).getBinding().executePendingBindings();

    }

    @Override
    protected int setResourId(int viewType)  {
        return R.layout.demo_data_bing_item;
    }

    @Override
    protected int setTypeItem(int position) {
        return 0;
    }


}

