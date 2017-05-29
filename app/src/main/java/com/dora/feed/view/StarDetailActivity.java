package com.dora.feed.view;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dora.feed.widget.CircleProgressView;
import com.famlink.frame.view.activity.BaseActivity;
import com.dora.feed.R;
import com.dora.feed.mvp.bean.IndexItemBean;

import org.xutils.view.annotation.ViewInject;

/**
 * 星座
 * Created by wangkai on 16/7/29.
 */
public class StarDetailActivity extends BaseActivity{
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;

    @ViewInject(R.id.star_time)
    TextView star_time; //星座时间

    @ViewInject(R.id.circle_id)
    CircleProgressView circle_id;

    @ViewInject(R.id.star_title)
    TextView star_title;  //内容

    @ViewInject(R.id.star_star_icon)
    ImageView star_star_icon;  //icon
    @ViewInject(R.id.star_star_number)
    TextView star_star_number;
    @ViewInject(R.id.star_star_text)
    TextView star_star_text;
    @ViewInject(R.id.love_id)
    RatingBar love_id;
    @ViewInject(R.id.work_id)
    RatingBar work_id;

    @ViewInject(R.id.money_id)
    RatingBar money_id;

    @ViewInject(R.id.person_num)
    TextView person_num;
    @ViewInject(R.id.lucky_color)
    TextView lucky_color;
    @ViewInject(R.id.lucky_num)
    TextView lucky_num;

    @ViewInject(R.id.star_love_txt)
    TextView star_love_txt;
    @ViewInject(R.id.star_money_txt)
    TextView star_money_txt;
    @ViewInject(R.id.star_work_txt)
    TextView star_work_txt;
    @ViewInject(R.id.star_day_notice)
    TextView star_day_notice;




    private IndexItemBean.IndexStarDataBean starModel;

    @Override
    public int setLayout() {
        return R.layout.activity_star;
    }

    @Override
    public void setInterfaceView() {
        toolbar.setNavigationIcon(R.drawable.back); //更改菜单图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        starModel = (IndexItemBean.IndexStarDataBean) getIntent().getSerializableExtra("starModel");
        if(starModel != null){
            star_time.setText(starModel.getCreate_time());
            star_title.setText(starModel.getSummary());
//            star_star_number.setText(make_point(starModel.getAll_point())+"");
//            circle_id.setProgress(make_point(starModel.getAll_point()));
//            star_star_text.setText(starModel.getStar_name().split(getResources().getString(R.string.today_star))[0]);
//            love_id.setNumStars(make_point(starModel.getLove_point())/20);
//            heathly_id.setNumStars(make_point(starModel.getHealth_point())/20);
//            work_id.setNumStars(make_point(starModel.getWork_point())/20);
//            money_id.setNumStars(make_point(starModel.getMoney_point())/20);
//            person_num.setText(starModel.getQfriend());
//            lucky_color.setText(starModel.getLucky_color());
//            lucky_num.setText(starModel.getLuncky_number());
            star_star_number.setText(starModel.getAll_point()+"");
            circle_id.setProgress(Integer.valueOf(starModel.getAll_point())*20);
            star_star_text.setText(starModel.getStar_name().split(getResources().getString(R.string.today_star))[0]);
            love_id.setNumStars(Integer.valueOf(starModel.getLove_point()));
//            heathly_id.setNumStars(Integer.valueOf(starModel.getHealth_point()));
            work_id.setNumStars(Integer.valueOf(starModel.getWork_point()));
            money_id.setNumStars(Integer.valueOf(starModel.getMoney_point()));
            person_num.setText(starModel.getQfriend());
            lucky_color.setText(starModel.getLucky_color());
            lucky_num.setText(starModel.getLuncky_number());
            star_love_txt.setText(starModel.getLove_txt());
            star_money_txt.setText(starModel.getMoney_txt());
            star_work_txt.setText(starModel.getWork_txt());
            star_day_notice.setText(starModel.getDay_notice());

        }
    }

    private Integer make_point(String str){
        if(!TextUtils.isEmpty(str)){
            String[] arr=str.split("%");
            return Integer.valueOf(arr[0]);
        }

        return 0;
    }

    @Override
    public void setGenericNodataOrNonetwork() {

    }
}
