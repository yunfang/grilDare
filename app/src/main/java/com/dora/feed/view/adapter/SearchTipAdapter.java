package com.dora.feed.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dora.feed.mvp.bean.SearchBeanTip;
import com.dora.feed.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/7/28.
 */
public class SearchTipAdapter extends BaseAdapter {
    private Context context;
    private List<SearchBeanTip.Data> list= new ArrayList<SearchBeanTip.Data>();
    public SearchTipAdapter(Context context){
        this.context  =  context;
    }

    public void setList(List<SearchBeanTip.Data> list){
        this.list = list;
    }

    @Override
    public int getCount() {
        if(list == null){
            return 0;
        }else{
            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.search_listview_item, parent,false);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.search_listview_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(list.get(position).getSuggestion());
        return convertView;
    }
    public class ViewHolder {
        private TextView textView;
    }


}
