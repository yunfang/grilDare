package com.dora.feed.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dora.feed.R;

/**
 * Created by admin on 2016/7/21.
 */
public class SagittariusAdapter extends BaseAdapter {
    private Context context;
    private int clickTemp = -1;

    int[] img;
    String[] star_name;
    String[] star_data;
    public SagittariusAdapter(Context context,int[] img,String[] star_name,String[] star_data){
        this.context = context;
        this.img = img;
        this.star_name = star_name;
        this.star_data = star_data;
    }
    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    //标识选择的Item
    public void setSeclection(int position) {
        clickTemp = position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler = null;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.image_view, null);
            viewHodler = new ViewHodler();
            viewHodler.star_image = (ImageView) convertView.findViewById(R.id.star_image);
            viewHodler.star_name = (TextView) convertView.findViewById(R.id.star_name);
            viewHodler.back_image = (ImageView) convertView.findViewById(R.id.back_image);
            viewHodler.id_data = (TextView) convertView.findViewById(R.id.id_data);
            convertView.setTag(viewHodler);
        }else{
            viewHodler = (ViewHodler) convertView.getTag();
        }

        // 点击改变选中listItem的背景色
        if (clickTemp == position) {
            viewHodler.back_image.setImageResource(R.drawable.start_checked_ground);
        } else {
            viewHodler.back_image.setImageResource(R.drawable.star_nomal_ground);
        }
        viewHodler.star_image.setImageResource(img[position]);
        viewHodler.star_name.setText(star_name[position]);
        viewHodler.id_data.setText(star_data[position]);


        return convertView;
    }

    class ViewHodler {
        ImageView star_image;
        ImageView back_image;
        TextView star_name;
        TextView id_data;
    }
}
