package com.famlink.frame.view.adapter;

/**
 * Created by wangkai on 16/6/14.
 */

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.famlink.frame.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@SuppressWarnings("ALL")
public abstract class BaseDataBindingRecyAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<T> mDatas = new LinkedList<>();
    public Context context;
    private boolean showFoot = true;

    public static int TYPE_ITEM;

    static final int TYPE_FOOTER = 0;

//    public static int[] type_layout_items;
//
//    public static int[] getType_layout_items() {
//        return type_layout_items;
//    }
//
//    public static void setType_layout_items(int[] type_layout_items) {
//        BaseDataBindingRecyAdapter.type_layout_items = type_layout_items;
//    }


    protected static final int VIEW_TYPE_LOAD_MORE_FOOTER = 100;
    protected boolean isLoadMoreFooterShown;


    public BaseDataBindingRecyAdapter(Context context, ArrayList<T> datas) {
        if (datas != null) {
            this.context = context;
            mDatas = datas;
        }
    }

    public List<T> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    /**
     * 设置Binding值
     * @param holder
     * @param position
     * @param data
     */
    protected abstract void onBindView(RecyclerView.ViewHolder holder, int position, T data);
    /**
     * 设置XML
     * @return
     */
    protected abstract int setResourId(int viewType);

    /**
     * 设置列表的各种类型
     * @param position   //当前多少条的position
     * @return
     */
    protected abstract int setTypeItem(int position);
    public interface OnItemBaseClickListener<T> {

        void onItemClick(View view, int position, T info);

    }
    private OnItemBaseClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemBaseClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if(setResourId(viewType) != 0){
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), setResourId(viewType), parent, false);
            holder = new ViewHolder(binding.getRoot());
            holder.setBinding(binding);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_FOOTER) {
            return;
        }
        final int pos = getRealPosition(holder);
        final T data = mDatas.get(position);
        if (data == null) {
            return;
        }
        onBindView(holder, position, data);
    }

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return position;
    }

    @Override
    public int getItemCount() {
        int type = showFoot ? 1 : 0;
        if (mDatas == null) {
            return type;
        }
        return mDatas.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (!showFoot) {
            return setTypeItem(position);
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return setTypeItem(position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            if (mOnItemClickListener != null) {
                 itemView.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         final T data = mDatas.get(getAdapterPosition());
                         mOnItemClickListener.onItemClick(v,  getAdapterPosition(), data);
                     }
                 });
            }
        }
        protected void setData(T data) {
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return this.binding;
        }
    }
//    public void setTYPE_ITEM(int TYPE_ITEM) {
//        this.TYPE_ITEM = TYPE_ITEM;
//    }
//
//    /**
//     * 获取设置的是那种布局
//     * @return
//     */
//    public int GET_TYPE_ITEM(){
//        if(TYPE_ITEM != 0){
//            if(TYPE_ITEM == type_layout_items[0]){
//                TYPE_ITEM = type_layout_items[0];
//            }else if(TYPE_ITEM == type_layout_items[1]){
//                TYPE_ITEM = type_layout_items[1];
//            }else if(TYPE_ITEM == type_layout_items[2]){
//                TYPE_ITEM = type_layout_items[2];
//            }else if(TYPE_ITEM == type_layout_items[3]){
//                TYPE_ITEM = type_layout_items[3];
//            }
//        }else{
//            TYPE_ITEM = TYPE_ITEM = type_layout_items[0];
//        }
//        return TYPE_ITEM;
//    }
}

