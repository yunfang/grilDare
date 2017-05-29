package com.dora.feed.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.famlink.frame.util.CacheUtils;
import com.famlink.frame.util.LocalContents;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.IssueKey;
import com.famlink.frame.view.activity.BaseActivity;
import com.dora.feed.R;
import com.dora.feed.view.adapter.SagittariusAdapter;

import org.xutils.view.annotation.ViewInject;

/**
 * 星座设定页面
 * Created by admin on 2016/7/13.
 */
public class StarSettingActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @ViewInject(R.id.gridView_id)
    GridView mgridView;

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    private SagittariusAdapter sagittariusAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //    int[] img = {R.drawable.star_chu,R.drawable.star_jin_niu,R.drawable.star_ju_xie,R.drawable.star_mo_xie
//            ,R.drawable.star_she_shou,R.drawable.star_shi,R.drawable.star_shuang_yu,R.drawable.star_shuang_zhi,R.drawable.star_shui_ping
//            ,R.drawable.star_tian_ping,R.drawable.star_tian_xie,R.drawable.star_yang};
    int[] img = {R.drawable.star_yang,R.drawable.star_jin_niu,R.drawable.star_shuang_zhi,R.drawable.star_ju_xie
                    ,R.drawable.star_shi,R.drawable.star_chu,R.drawable.star_tian_ping,R.drawable.star_tian_xie
                    ,R.drawable.star_she_shou,R.drawable.star_mo_xie,R.drawable.star_shui_ping,R.drawable.star_shuang_yu};


    String[] star_name = {"白羊座","金牛座","双子座","巨蟹座","狮子座","处女座","天平座","天蝎座","射手座","摩羯座","水瓶座","双鱼座"};
    String[] star_data = {"03.21-04.20","04.21-05.20","05.21-06.21",
                            "06.22-07.22","07.23-08.22","08.23-09.22",
                            "09.23-app_10.23","app_10.24-11.22","11.23-12.21",
                            "12.22-01.19","01.20-02.19","02.20-03.20"};
    @Override
    public int setLayout() {
        return R.layout.activity_star_setting;

    }

    @Override
    public void setInterfaceView() {
        toolbar.setNavigationIcon(R.drawable.back); //更改菜单图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String start_position = CacheUtils.getInstance().getString(LocalContents.STAR_POSITION);
        sagittariusAdapter = new SagittariusAdapter(this, img, star_name, star_data);
        mgridView.setOnItemClickListener(this);
        if(TextUtils.isEmpty(start_position)) {
            mgridView.setAdapter(sagittariusAdapter);
        }else{
            sagittariusAdapter.setSeclection(Integer.parseInt(start_position));
            mgridView.setAdapter(sagittariusAdapter);
        }

    }

    @Override
    public void setGenericNodataOrNonetwork() {

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            finish();
        }
    };
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        handler.removeCallbacksAndMessages(null);
        sagittariusAdapter.setSeclection(position);
        sagittariusAdapter.notifyDataSetChanged();
        CacheUtils.getInstance().putString(LocalContents.STAR, star_name[position]);
        CacheUtils.getInstance().putString(LocalContents.STAR_POSITION, position + "");
//        ToastUtils.showToast(star_name[position]);
        handler.sendMessageDelayed( new Message(),1500);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!TextUtils.isEmpty(CacheUtils.getInstance().getString(LocalContents.STAR))){
            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.STAR_UPDATE);
        }
    }
}
