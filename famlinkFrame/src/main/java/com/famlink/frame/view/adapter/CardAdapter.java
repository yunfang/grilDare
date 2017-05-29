package com.famlink.frame.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.famlink.frame.R;
import com.famlink.frame.mvp.bean.CardBean;

import org.xutils.view.annotation.ViewInject;

import java.util.List;


/**
 * Created by wangkai on 16/6/14.
 */
public class CardAdapter extends BaseRecyclerViewAdapter<CardBean> {

    public CardAdapter(Context context, List<CardBean> mCardList){
        super(context, mCardList);
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position, CardBean data) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).setData(data);
        }
    }
    @Override
    protected BaseRecyclerViewHolder onCreate(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM_VERTICAL) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycle_item, parent, false);
            return new ViewHolder(view);

        } else if (viewType == TYPE_ITEM_HORIZONTAL) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycle_item2, parent, false);
            return new BaseRecyclerViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_footer, parent, false);
            return new BaseRecyclerViewHolder(view);
        }

        return null;
    }


    class ViewHolder extends BaseRecyclerViewHolder {
        private  ImageView card_img;
        private  TextView card_title;
        private  TextView card_content;

//        @ViewInject(R.id.card_img) ImageView card_img;
//
//        @ViewInject(R.id.card_title) TextView card_title;
//
//        @ViewInject(R.id.card_content) TextView card_content;

        public ViewHolder(View itemView) {
            super(itemView);
            card_img = (ImageView) itemView.findViewById(R.id.card_img);
            card_title = (TextView) itemView.findViewById(R.id.card_title);
            card_content = (TextView) itemView.findViewById(R.id.card_content);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void setData(@NonNull final CardBean data) {
            super.setData(data);
            card_content.setText(data.getContent() + "");
        }
    }
}
