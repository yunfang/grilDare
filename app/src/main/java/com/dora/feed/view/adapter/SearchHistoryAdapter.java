package com.dora.feed.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.dora.feed.R;

/**
 * Created by admin on 2016/8/2.
 */
public class SearchHistoryAdapter extends SimpleCursorAdapter {
    private Cursor m_cursor;
    private Context m_context;
    private LayoutInflater miInflater;

    public SearchHistoryAdapter(Context context, int layout, Cursor c,
                                String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        m_context = context;
        m_cursor = c;
    }

    @Override
    public void bindView(View arg0, Context arg1, Cursor arg2) {
        View convertView = null;
        if (arg0 == null) {
            convertView = miInflater.inflate(R.layout.search_listview_item, null);
        } else {
            convertView = arg0;
        }
        TextView textView = (TextView) convertView
                .findViewById(R.id.search_listview_tv);

        textView.setText(arg2.getString(arg2
                .getColumnIndex("name")));

    }
}
