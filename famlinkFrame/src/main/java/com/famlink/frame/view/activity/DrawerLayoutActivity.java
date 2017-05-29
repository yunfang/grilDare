package com.famlink.frame.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.famlink.frame.R;
import com.famlink.frame.mvp.presenter.drawlayout.DrawViewInterface;
import com.famlink.frame.mvp.presenter.drawlayout.DrawtViewPersenterImpl;

import org.xutils.view.annotation.ViewInject;


/**
 * Created by wangkai on 16/6/15.
 */
public class DrawerLayoutActivity extends BaseActivity implements DrawViewInterface {
//    @ViewInject(R.id.toolbar)
//    Toolbar toolbar;
//    @ViewInject(R.id.drawer_layout)
//    DrawerLayout drawer_layout;
//    @ViewInject(R.id.navigation_view) NavigationView navigation_view;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawtViewPersenterImpl mDrawViewImpl;
    private Toolbar toolbar;
    private DrawerLayout drawer_layout;
    private NavigationView navigation_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();

        drawer_layout.setDrawerListener(mDrawerToggle);
        setupDrawerContent(navigation_view);

        mDrawViewImpl = new DrawtViewPersenterImpl(this);
    }

    @Override
    public int setLayout() {
        return R.layout.v7_activity_drawerlayout;
    }

    @Override
    public void setInterfaceView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);


    }

    @Override
    public void setGenericNodataOrNonetwork() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDrawViewImpl.switchNavigation(menuItem.getItemId());
                        menuItem.setChecked(true);
                        drawer_layout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public void switchNews() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new NewsFragment()).commit();
        toolbar.setTitle(R.string.navigation_1);
    }

    @Override
    public void switchImages() {
        toolbar.setTitle(R.string.navigation_2);
    }

    @Override
    public void switchWeather() {
        toolbar.setTitle(R.string.navigation_3);
    }

    @Override
    public void switchAbout() {
        toolbar.setTitle(R.string.navigation_4);
    }

    @Override
    public void switchsetting() {

    }

}
