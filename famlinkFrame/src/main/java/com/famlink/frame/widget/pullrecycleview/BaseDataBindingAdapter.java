package com.famlink.frame.widget.pullrecycleview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.famlink.frame.R;
import com.famlink.frame.util.SysoutUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Stay on 7/3/16.
 * Powered by www.stay4it.com
 */
public abstract class BaseDataBindingAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected static final int VIEW_TYPE_LOAD_MORE_FOOTER = 100;
    protected boolean isLoadMoreFooterShown;

    private List<T> mDatas = new LinkedList<>();
    public Context context;
    public BaseDataBindingAdapter(Context context, ArrayList<T> datas) {
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


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOAD_MORE_FOOTER) {
            return onCreateLoadMoreFooterViewHolder(parent);
        }
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
        if (isLoadMoreFooterShown && getItemViewType(position) == VIEW_TYPE_LOAD_MORE_FOOTER) {
            if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                params.setFullSpan(true);
            }
        }else{
            final int pos = getRealPosition(holder);
            final T data = mDatas.get(position);
            if (data == null) {
                return;
            }
            onBindView(holder, position, data);
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(v, pos, data);
//                    mOnItemClickListener.onItemLongClick(v, position, data);
                    }
                });
            }
        }

//        holder.onBindViewHolder(position);
    }
    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return position;
    }

    @Override
    public int getItemCount() {

        return mDatas.size() + (isLoadMoreFooterShown ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (!isLoadMoreFooterShown) {
            return setTypeItem(position);
        }
        if (isLoadMoreFooterShown && position == getItemCount() - 1) {
            return VIEW_TYPE_LOAD_MORE_FOOTER;
        }else{
            return setTypeItem(position);
        }
    }


//    protected abstract BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType);

    protected ViewHolder onCreateLoadMoreFooterViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_pull_to_refresh_footer, parent, false);
        return new LoadMoreFooterViewHolder(view);
    }

    public void onLoadMoreStateChanged(boolean isShown) {
        this.isLoadMoreFooterShown = isShown;
        if (isShown) {
            notifyItemInserted(getItemCount());
        } else {
            notifyItemRemoved(getItemCount());
        }
    }

    public boolean isLoadMoreFooter(int position) {
        return isLoadMoreFooterShown && position == getItemCount() - 1;
    }

    public boolean isSectionHeader(int position) {
        return false;
    }

    private class LoadMoreFooterViewHolder extends ViewHolder {
        public LoadMoreFooterViewHolder(View view) {
            super(view);
        }
    }
    public interface OnItemBaseClickListener<T> {

        void onItemClick(View view, int position, T info);

    }
    private OnItemBaseClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemBaseClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
//            SysoutUtil.out("getAdapterPosition():" + getAdapterPosition());
//            if (mOnItemClickListener != null) {
//                if(!isLoadMoreFooterShown){
//                    itemView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            final T data = mDatas.get(getAdapterPosition());
//                            mOnItemClickListener.onItemClick(v,  getAdapterPosition(), data);
//                        }
//                    });
//                }
//            }
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
}
