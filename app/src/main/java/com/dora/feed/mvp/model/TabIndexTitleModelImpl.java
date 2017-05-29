package com.dora.feed.mvp.model;


import com.dora.feed.mvp.bean.IndexTitleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * by 12406 on 2016/5/14.
 */
public class TabIndexTitleModelImpl implements BaseModel.TabNewsModel {
//    0 推荐
//    2	八卦
//    3	爱情
//    4	美容
//    5	视频
//    6	时尚
//    7	美发
//    8	生活
//    9	星座
    private String[] recommendTitle = {"推荐", "美容", "时尚", "发型", "美甲", "爱情", "星座", "八卦", "视频", "生活"};
    private int[] recommendId = {0, 4, 6, 7, 10, 3, 9, 2, 5, 8};
    @Override
    public void netWorkTabIndexList(final BaseBridge.TabIndexsData tabIndexsData) {
//        ParamsApi.requestTitleViewpager().execute(new BaseRequestCallback<TabIndexTitleBean>() {
//            @Override
//            public void onRequestSucceed(TabIndexTitleBean result) {
////                tabIndexsData.addData(result);
//                List<TabIndexTitleBean> mList = new ArrayList<TabIndexTitleBean>();
//                for (int i = 0; i < 5 ; i++) {
//                    TabIndexTitleBean mBean = new TabIndexTitleBean();
//                    mBean.setName("测试" + i);
//                    mList.add(mBean);
//
//                }
//                tabIndexsData.addData(mList);
//            }
//
//            @Override
//            public void onRequestFailed(TabIndexTitleBean result) {
//
//            }
//        });
        List<IndexTitleBean> mList = new ArrayList<IndexTitleBean>() ;
        for (int i = 0; i < recommendTitle.length ; i++) {
            IndexTitleBean mBean = new IndexTitleBean();
            mBean.setName(recommendTitle[i]);
            mBean.setId(recommendId[i]);
            mList.add(mBean);

        }
        tabIndexsData.addData(mList);
    }
}
