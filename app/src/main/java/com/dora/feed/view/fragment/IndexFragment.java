package com.dora.feed.view.fragment;

/**
 * Created by wangkai on 16/7/11.
 */

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dora.feed.mvp.bean.IndexTitleBean;
import com.dora.feed.mvp.view.BaseView;
import com.dora.feed.net.RequestUtils;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.IssueKey;
import com.famlink.frame.util.notify.OnDataChangeObserver;
import com.famlink.frame.view.fragment.BaseFragment;
import com.dora.feed.R;
import com.dora.feed.databinding.LayoutFragmentIndexVpagerBinding;
import com.dora.feed.mvp.presenter.TabIndexTitlePresenterImpl;
import com.dora.feed.view.adapter.IndexTitleAdapter;

import org.xutils.view.annotation.ViewInject;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * 首页
 * by 12406 on 2016/5/14.
 */
public class IndexFragment extends BaseFragment<LayoutFragmentIndexVpagerBinding> implements BaseView.TabIndexTitleView, OnDataChangeObserver {

    @ViewInject(R.id.tab_layout)
    TabLayout tabLayout;
    @ViewInject(R.id.viewPager)
    ViewPager viewPager;

    private List<IndexTitleBean> data = new LinkedList<IndexTitleBean>();
    private IndexTitleAdapter tabNewsAdapter;

    @Override
    public void onBaseCreateView(View view, Bundle savedInstanceState) {
        viewPager.setOffscreenPageLimit(10);
        DataChangeNotification.getInstance().addObserver(IssueKey.DETAIL_LOGOUT, this);
        TabIndexTitlePresenterImpl tabNewsPresenter = new TabIndexTitlePresenterImpl(this);
        tabNewsPresenter.requestNetWork();


    }

    @Override
    public int setLayout() {
        return R.layout.layout_fragment_index_vpager;
    }

    @Override
    public void setInterfaceView() {

    }

    @Override
    public void setGenericNodataOrNonetwork() {

    }

    @Override
    protected void dataCallback(int result, Object obj) {

    }

    @Override
    public void setDatas(List<IndexTitleBean> datas) {
        tabNewsAdapter = new IndexTitleAdapter(getChildFragmentManager(), data);

        if (!datas.isEmpty()) {
            data.addAll(datas);
            viewPager.setAdapter(tabNewsAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public void setData(IndexTitleBean data) {

    }

    @Override
    public void netWorkError(IndexTitleBean data) {

    }

    @Override
    public void onDataChanged(IssueKey issue, Object o) {
       if(issue.equals(IssueKey.DETAIL_LOGOUT)){  //退出详情页界面
            String[] arr = (String[]) o;
////            String articlerId = (String)o;
//            long bhv_amt_2 = System.currentTimeMillis();
//            RequestUtils.logoutDetail(arr[0], String.valueOf((Long) (Long) (bhv_amt_2 - Long.parseLong(arr[1])) / 1000));

           long bhv_amt_2 = System.currentTimeMillis();
           String sub =  subtract(bhv_amt_2+"",arr[1]+"");
           String s = divide(sub, "" + 1000, 2, BigDecimal.ROUND_HALF_EVEN);
           RequestUtils.logoutDetail(arr[0], s);
       }
    }

    /**
     * 提供精确的减法运算
     * @param v1
     * @param v2
     * @return 两个参数数学差，以字符串格式返回
     */
    public static String subtract(String v1, String v2)
    {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).toString();
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。舍入模式采用用户指定舍入模式
     * @param v1
     * @param v2
     * @param scale 表示需要精确到小数点以后几位
     * @param round_mode 表示用户指定的舍入模式
     * @return 两个参数的商，以字符串格式返回
     */
    public static String divide(String v1, String v2, int scale, int round_mode)
    {
        if(scale < 0)
        {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, round_mode).toString();
    }
}
