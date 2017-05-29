package com.famlink.frame.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.famlink.frame.R;
import com.famlink.frame.view.tab.tabbar.SellerBottomMenuView;
import com.famlink.frame.view.tab.tabbar.SellerNavigationBar;
import com.famlink.frame.view.tab.tabfragment.FragmentFive;
import com.famlink.frame.view.tab.tabfragment.FragmentFour;
import com.famlink.frame.view.tab.tabfragment.FragmentOne;
import com.famlink.frame.view.tab.tabfragment.FragmentThree;
import com.famlink.frame.view.tab.tabfragment.FragmentTwo;


public class TabFragmentActivity extends AppCompatActivity implements SellerNavigationBar.NavigationBarClickListener {
    private SellerBottomMenuView mBottomMenuView;
    private SellerNavigationBar mSellerNavigationBar;
    private Fragment[] fragments;

    private static final int TAB_INDEX_ONE= 0;
    private static final int TAB_INDEX_TWO = 1;
    private static final int TAB_INDEX_THREE = 2;
    private static final int TAB_INDEX_FOUR = 3;
    private static final int TAB_INDEX_FIVE = 4;
    private boolean IS_TAB_INDEX_ONE = true;
    private static final int FragmentCount = 4;//动态设置tab的个数


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_fragment);


        setinitView();
        initFragment();
        switchFragment(TAB_INDEX_ONE);
    }

    private void setinitView() {
        mBottomMenuView = (SellerBottomMenuView) findViewById(R.id.bottom_menu);
        mBottomMenuView.setFragmentCount(FragmentCount);
        mBottomMenuView.setmTitleState(View.VISIBLE);
        mBottomMenuView.setmImageState(View.VISIBLE);
        mSellerNavigationBar = (SellerNavigationBar) findViewById(R.id.tb_navigationBar);
        mSellerNavigationBar.selectFristTab();
        mSellerNavigationBar.setNavigationBarClickListener(this);
    }
    private void initFragment(){
        FragmentOne mOneFragment = (FragmentOne) getSupportFragmentManager().findFragmentById(R.id.fragment_one);
        FragmentTwo mTwoFragment = (FragmentTwo) getSupportFragmentManager().findFragmentById(R.id.fragment_two);
        FragmentThree mThreeFragment = (FragmentThree) getSupportFragmentManager().findFragmentById(R.id.fragment_three);
        FragmentFour mFourFragment = (FragmentFour) getSupportFragmentManager().findFragmentById(R.id.fragment_four);
        FragmentFive mFiveFragment = (FragmentFive) getSupportFragmentManager().findFragmentById(R.id.fragment_five);
        fragments = new Fragment[]{mOneFragment,mTwoFragment,mThreeFragment,mFourFragment,mFiveFragment};
    }

    @Override
    public void onNavigationBarClickListener(int itemId) {
        switch (itemId) {
            case TAB_INDEX_ONE:
                switchFragment(TAB_INDEX_ONE);
                break;
            case TAB_INDEX_TWO:
                switchFragment(TAB_INDEX_TWO);
                break;
            case TAB_INDEX_THREE:
                switchFragment(TAB_INDEX_THREE);
                break;
            case TAB_INDEX_FOUR:
                switchFragment(TAB_INDEX_FOUR);
                break;
            case TAB_INDEX_FIVE:
                switchFragment(TAB_INDEX_FIVE);
                break;
            default:
                break;
        }
    }
    private void switchFragment(int which) {
        getSupportFragmentManager().beginTransaction().hide(fragments[0]).hide(fragments[1]).hide(fragments[2]).hide(fragments[3]).hide(fragments[4]).show(fragments[which]).commitAllowingStateLoss();

    }
}
