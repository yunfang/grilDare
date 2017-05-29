package com.dora.feed.mvp.model;


import android.content.Context;
import android.content.SharedPreferences;

import com.famlink.frame.net.biz.BaseRequestCallback;
import com.famlink.frame.util.SysoutUtil;
import com.famlink.frame.util.Utils;
import com.dora.feed.mvp.bean.IndexItemBean;
import com.dora.feed.net.ParamsApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * by 12406 on 2016/5/14.
 */
public class TabIndexItemModelImpl implements BaseModel.TabIndexItemModel {
    private String ids;
    private String category;
    private int type;
    private int openStar;
    private String starName;
    private String click_ids;
    private boolean isSettingStar;
    //String ids,  String category, String type, String openStar, String starName, String click_ids
    public TabIndexItemModelImpl(String ids, String category, int type, int openStar, String starName, String click_ids){
        this.ids = ids;
        this.category = category;
        this.type = type;
        this.openStar = openStar;
        this.starName = starName;
        this.click_ids = click_ids;
        if(starName.contains("#")){
            this.starName = starName.split("#")[0];
            isSettingStar = false;
        }else{
            this.starName = starName;
            isSettingStar = true;
        }

    }
    @Override
    public void netWorkTabIndexItemList(final BaseBridge.TabIndexItemsData tabIndexItemsData) {
        ParamsApi.getIndexItemUrl(ids, category, type+"", openStar+"", starName, click_ids).execute(new BaseRequestCallback<IndexItemBean>() {
            @Override
            public void onRequestSucceed(IndexItemBean result) {
                List<IndexItemBean.IndexDataBean> mList = result.getData();
                IndexItemBean.IndexDataBean mBean = new IndexItemBean().new IndexDataBean();

                if  (type != 1 && openStar == 1){
                    if(isSettingStar){
                        if(mList != null){
                            mList.remove(1);
                        }
                        mBean.setTitle(result.getSdata().getStar_name()+"今日运势");
                        mBean.setAtime(result.getSdata().getCreate_time());
                        mBean.setLook_num(result.getSdata().getAll_point());
                        mList.add(1, mBean);
                    }else{
                        mBean.setTitle("查看每日星座运势");
                        mBean.setAtime("快来看看吧");
                        mBean.setLook_num(" ");
                        mList.add(1, mBean);
                    }
                }
                tabIndexItemsData.addData(result);
            }

            @Override
            public void onRequestFailed(IndexItemBean result) {
                SysoutUtil.out(result);
                tabIndexItemsData.error(result);
            }
        });
    }
//
//    /**
//     * 存储最后一条数据
//     * @param datas
//     * @throws Exception
//     */
//    public void saveLastData(List<IndexItemBean.IndexDataBean> datas) throws Exception{
//        JSONArray mJsonArray = new JSONArray();
//        for (int i = 0; i < datas.size(); i++) {
//            if(i == datas.size()){
//                IndexItemBean.IndexDataBean mBean = datas.get(i);
//                JSONObject object = new JSONObject();
//                object.put("id", mBean.getAid());
//                object.put("title", mBean.getTitle());
//                object.put("author", mBean.getAuthor());
//                object.put("publish_time", mBean.getCreate_time());
//                object.put("public_url", mBean.getPublic_url());
//                object.put("master_img", mBean.getMaster_img());
//                object.put("status", mBean.getStatus());
//                object.put("look_num", mBean.getLook_num());
//
//                mJsonArray.put(object);
//            }
//        }
//
//        SharedPreferences sp = Utils.getContext().getSharedPreferences("last_data", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("last_data", mJsonArray.toString());
//        editor.commit();
//    }
//
//    /**
//     * 获取上一页的最后一条数据
//     * @return
//     * @throws Exception
//     */
//    public List<IndexItemBean.IndexDataBean> getLastData() throws Exception{
//        List<IndexItemBean.IndexDataBean> datas = new ArrayList<IndexItemBean.IndexDataBean>();
//        SharedPreferences sp = Utils.getContext().getSharedPreferences("last_data", Context.MODE_PRIVATE);
//        String result = sp.getString("last_data", "");
//        JSONArray array = new JSONArray(result);
//        for (int i = 0; i < array.length(); i++) {
//            JSONObject itemObject = array.getJSONObject(i);
//
//            JSONArray names = itemObject.names();
//            IndexItemBean.IndexDataBean mBean = null;
//            if (names != null) {
//                for (int j = 0; j < names.length(); j++) {
//
//                    mBean = new IndexItemBean(). new IndexDataBean();
//                    mBean.setAid(itemObject.getString("id"));
//                    mBean.setTitle(itemObject.getString("title"));
//                    mBean.setAuthor(itemObject.getString("author"));
//                    mBean.setCreate_time(itemObject.getString("publish_time"));
//                    mBean.setPublic_url(itemObject.getString("public_url"));
//                    mBean.setMaster_img(itemObject.getString("master_img"));
//                    mBean.setStatus(itemObject.getInt("status"));
//                    mBean.setLook_num(itemObject.getString("look_num"));
//                }
//                datas.add(mBean);
//            }
//        }
//        return datas;
//    }
}
