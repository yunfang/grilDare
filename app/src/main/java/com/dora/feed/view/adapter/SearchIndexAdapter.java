package com.dora.feed.view.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.dora.feed.config.Constants;
import com.dora.feed.net.Api;
import com.famlink.frame.BR;
import com.famlink.frame.widget.pullrecycleview.BaseDataBindingAdapter;
import com.dora.feed.R;
import com.dora.feed.mvp.bean.SearchIndexBean;
import com.dora.feed.mvp.bean.SearchIndexItmeHandler;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/30.
 */
public class SearchIndexAdapter extends BaseDataBindingAdapter {
    private List<SearchIndexBean.Data> list;

    public SearchIndexAdapter(Context context, ArrayList<SearchIndexBean.Data> datas) {
        super(context, datas);
        list = datas;
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, int position, Object data) {
        ((ViewHolder) holder).getBinding().setVariable(BR.click, new SearchIndexItmeHandler(context, list, position));
        ((ViewHolder) holder).getBinding().setVariable(BR.bean, ((SearchIndexBean.Data) data));
        ((ViewHolder) holder).getBinding().executePendingBindings();
    }

    @Override
    protected int setResourId(int viewType) {
        return R.layout.layout_search_index_list_item;
    }

    @Override
    protected int setTypeItem(int position) {
        return getItemType(position);
    }

    @BindingAdapter("app:imgSearchIndex")
    public static void setImageUrl(ImageView view, String url) {
        if(!TextUtils.isEmpty(url)){
            url = Api.IMAGE_HEADER_URL + url;
        }
        x.image().bind(view, url, Constants.setImageUtils(R.drawable.loading_icon_small, R.drawable.loading_icon_small, false));
    }

//    @Override
//    protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
//        return null;
//    }

//    @Override
//    protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
//        return getViewHolder(parent, viewType);
//    }

    //
//
//    @Override
//    protected int getDataCount() {
//        return list.size();
//    }
//
//    @Override
//    protected int getDataViewType(int position) {
//        return getItemType(position);
//    }
//
    private int getItemType(int position) {
        return 0;
    }

    //
    @Override
    public boolean isSectionHeader(int position) {
        return super.isSectionHeader(position);
    }
//
//    private BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false);
//        return new SampleViewHolder(view);
//    }
//
//    class SampleViewHolder extends BaseViewHolder {
//        ImageView mSampleListItemImg;
//        TextView mSampleListItemLabel;
//        Button button;
//        LinearLayout linearLayout;
//
//        public SampleViewHolder(View itemView) {
//            super(itemView);
//            linearLayout = (LinearLayout) itemView.findViewById(R.id.id_ll);
////            mSampleListItemLabel = (TextView) itemView.findViewById(R.id.mSampleListItemLabel);
////            mSampleListItemImg = (ImageView) itemView.findViewById(R.id.mSampleListItemImg);
////            button = (Button) itemView.findViewById(R.id.bu);
//        }
//
//        @Override
//        public void onBindViewHolder(final int position) {
////            mSampleListItemImg.setBackgroundColor(Color.parseColor("#125456"));
//            linearLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ToastUtils.showToast("linearLayout");
//                }
//            });
////            mSampleListItemImg.setBackgroundResource(R.mipmap.ic_launcher);
////            mSampleListItemLabel.setText("sdfsdfsdfsdsdfdsf");
////            button.setText("button"+position);
////            button.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    System.out.println("onClick"+position);
////                }
////            });
//        }
//
//        @Override
//        public void onItemClick(View view, int position) {
//            System.out.println("onItemClick"+position);
//            ToastUtils.showToast("onItemClick"+position);
//        }
//    }

}
