package com.famlink.frame.view.adapter;

/**
 * Created by wangkai on 16/6/14.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

import java.util.LinkedList;
import java.util.List;


/**
 * by y on 2016/4/28.
 */
@SuppressWarnings("ALL")
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private int TYPE_ITEM;
    public static final int TYPE_ITEM_VERTICAL = 1;   //横向的Card布局
    public static final int TYPE_ITEM_HORIZONTAL = TYPE_ITEM_VERTICAL*2; //竖向的Card布局
    static final int TYPE_FOOTER = 0;
    private List<T> mDatas = new LinkedList<>();
    private OnItemBaseClickListener mOnItemClickListener;
    private boolean showFoot = true;

    private Context context;
    public BaseRecyclerViewAdapter(Context context, List<T> datas) {
        if (datas != null) {
            this.context = context;
            mDatas = datas;
        }
    }


    public void addAll(List<T> datas) {
        mDatas.addAll(datas);
    }

    public void addItemTop(List<T> datas) {
        mDatas = datas;
    }

    public void remove(int position) {
        mDatas.remove(position);
        this.notifyDataSetChanged();
    }

    public void removeAll() {
        if (mDatas.size() != 0) {
            mDatas.clear();
        }
        this.notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        int type = showFoot ? 1 : 0;
        if (mDatas == null) {
            return type;
        }
        return mDatas.size() + type;
    }

    @Override
    public int getItemViewType(int position) {
        if (!showFoot) {
            return GET_TYPE_ITEM();
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return GET_TYPE_ITEM();
        }
    }

    public void isShowFooter(boolean showFoot) {
        this.showFoot = showFoot;
        this.notifyDataSetChanged();
    }

    public boolean isShowFooter() {
        return this.showFoot;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public interface OnItemBaseClickListener<T> {

        void onItemClick(View view, int position, T info);

        void onItemLongClick(View view, int position, T info);

    }

    public void setOnItemClickListener(OnItemBaseClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreate(parent, viewType);
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
        onBind(holder, position, data);

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, pos, data);
                    mOnItemClickListener.onItemLongClick(v, pos, data);
                }
            });
        }

    }


    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return position;
    }

    protected abstract void onBind(RecyclerView.ViewHolder holder, int position, T data);

    protected abstract BaseRecyclerViewHolder onCreate(ViewGroup parent, int viewType);


    class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

        public BaseRecyclerViewHolder(View itemView) {
            super(itemView);
            x.view().inject(this, itemView);
        }

        protected void setData(T data) {

        }
    }

    public void setTYPE_ITEM(int TYPE_ITEM) {
        this.TYPE_ITEM = TYPE_ITEM;
    }

    /**
     * 获取设置的是那种布局
     * @return
     */
    public int GET_TYPE_ITEM(){
        if(TYPE_ITEM != 0){
            if(TYPE_ITEM == TYPE_ITEM_VERTICAL){
                TYPE_ITEM = TYPE_ITEM_VERTICAL;
            }else if(TYPE_ITEM == TYPE_ITEM_HORIZONTAL){
                TYPE_ITEM = TYPE_ITEM_HORIZONTAL;
            }
        }else{
            TYPE_ITEM = TYPE_ITEM_VERTICAL;
        }
        return TYPE_ITEM;
    }

}

