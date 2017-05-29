package com.dora.feed.view.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.dora.feed.net.Api;
import com.dora.feed.widget.sqlite.SQLiteData;
import com.famlink.frame.BR;
import com.famlink.frame.widget.pullrecycleview.BaseDataBindingAdapter;
import com.dora.feed.R;
import com.dora.feed.mvp.bean.IndexItemBean.IndexDataBean;
import com.dora.feed.mvp.bean.IndexItemHandler;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangkai on 16/7/12.
 */
public class IndexItemAdapter extends BaseDataBindingAdapter {
    private ArrayList<IndexDataBean> mData = new ArrayList<IndexDataBean>();
//    private int[] type_layout_items = {1, 2, 3, 4};
//    static final int TYPE_FOOTER = 0;

    private int openStar;  //星座状态判断
    private int categoryType;  //不同分类下XML的不同的状态
    private boolean isSettingStar;
    private static int categoryLoveType;
    private static int position=0;
    private static int loadingImage = 0;
    private SQLiteData mSQL;

    public IndexItemAdapter(Context context, ArrayList<IndexDataBean> datas) {
        super(context, datas);
        this.mData = datas;
    }

    public void setSettingStar(boolean settingStar) {
        isSettingStar = settingStar;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
        categoryLoveType = categoryType;
    }

    public void setOpenStar(int openStar) {
        this.openStar = openStar;
    }
    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, int position, Object data) {
        this.position = position;
        mSQL = new SQLiteData(context);
//        if(getmDatas().size() != 0){
//        (IndexDataBean)mBean = data;
            if(!TextUtils.isEmpty(((IndexDataBean)data).getAid())){
                if(mSQL.hasIsshowData(((IndexDataBean)data).getAid())){
                    ((IndexDataBean)data).setShow(true);
                }else{
                    ((IndexDataBean)data).setShow(false);
                }
            }
//        }
        ((ViewHolder) holder).getBinding().setVariable(BR.mBean, ((IndexDataBean) data));
        ((ViewHolder) holder).getBinding().setVariable(BR.isSettingStar, isSettingStar);
        ((ViewHolder) holder).getBinding().setVariable(BR.click, new IndexItemHandler(this, mData, position));
        ((ViewHolder) holder).getBinding().executePendingBindings();

    }

    @BindingAdapter("app:image")
    public static void imageLoader(ImageView view, IndexDataBean bean) {
        String url = bean.getMaster_img();
        if(!TextUtils.isEmpty(url)){

            if(position == 0){
                if(bean.getMaster_img().contains("_animated")){
                    url = Api.IMAGE_HEADER_URL + url.substring(0, url.lastIndexOf(".")) + Api.IMAGE_BIG_FOOTER_W;

                }else{
                    url = Api.IMAGE_HEADER_URL + url.substring(0, url.lastIndexOf(".")) + Api.IMAGE_BIG_FOOTER_C;
                }
            }else{
                if(url.contains("_animated")){
                    url = Api.IMAGE_HEADER_URL + url.substring(0, url.lastIndexOf(".")) + Api.IMAGE_SMALL_FOOTER_W;

                }else{
                    url  = Api.IMAGE_HEADER_URL + url.substring(0, url.lastIndexOf(".")) + Api.IMAGE_SMALL_FOOTER_C;
                }
            }
        }
        if(categoryLoveType == 3){
            loadingImage = R.drawable.loading_icon_small;
        }else if(categoryLoveType == 5){
            loadingImage = R.drawable.loading_icon_big;
        }else if(categoryLoveType == 0 && position == 0){
            loadingImage = R.drawable.loading_icon_big;
        }else{
            loadingImage = R.drawable.loading_icon_fix;
        }
        ImageOptions imageOptions= new ImageOptions.Builder().setSize(-1,-1).build();
        x.image().bind(view, url,imageOptions);
    }

    @Override
    protected int setResourId(int viewType) {
        if(viewType == 0){
            return R.layout.layout_fragment_index_main_item_1;
        }else if (viewType == 1){
            return R.layout.layout_fragment_index_main_item_4;
        }else if(viewType == 3){
            return R.layout.layout_fragment_index_main_item_2;
        }else if(viewType == 5){
            return R.layout.layout_fragment_index_main_item_5;
        }else {
            return R.layout.layout_fragment_index_main_item_3;
        }
    }

    @Override
    protected int setTypeItem(int position) {
        if(categoryType == 0 && position == 0){   //推荐(首页)分类并且是第一条数据的时候显示"竖向大图排列"
            return 0;  //状态为0时显示大图数据
        }else if (position == 1 && openStar == 1){  //推荐(首页)分类中得第二条数据并且星座状态为1时显示"星座的界面"
            return 1;  //状态为1时显示星座数据
        }else if(categoryType == 3){  //爱情分类的数据显示"横向图文排列"
            return 3;
        }else if(categoryType == 5){  //视频分类的数据显示"竖向大图排列的"
            return 5;
        }else {
            return 10;  //状态为1时显示2*2数据
        }
   }

    @Override
    public boolean isSectionHeader(int position) {
        return super.isSectionHeader(position);
    }
}

