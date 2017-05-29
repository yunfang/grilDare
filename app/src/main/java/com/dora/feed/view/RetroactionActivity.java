package com.dora.feed.view;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dora.feed.net.ParamsApi;
import com.famlink.frame.R;
import com.famlink.frame.mvp.bean.BaseResult;
import com.famlink.frame.net.biz.RequestCallback;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.util.Utils;
import com.famlink.frame.view.activity.BaseActivity;

/**
 * Created by admin on 2016/7/6.
 * 用户反馈二级页面
 */
public class RetroactionActivity extends BaseActivity implements View.OnClickListener {
    private static final int MAX_CONTENT_LENGTH = 100;
    private static final int MIN_CONTENT_LENGTH = 5;

    private EditText mEditText;
    private Toolbar toolbar;

    @Override
    public int setLayout() {
        return R.layout.activity_retroaction;
    }

    @Override
    public void setInterfaceView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back); //更改菜单图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button commit_btn = (Button) findViewById(R.id.send_bu);
        mEditText = (EditText) findViewById(R.id.content);
        commit_btn.setOnClickListener(this);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int delta = s.length() - MAX_CONTENT_LENGTH;
                    if (delta > 0) {
                        s.delete(s.length() - delta, s.length());
                        mEditText.setText(s);
                        mEditText.setSelection(s.length());
                        ToastUtils.showToast(Utils.getString(context, R.string.feed_length_too_long));
                    }
                } catch (NumberFormatException e) {
                    ToastUtils.showToast(Utils.getString(context, R.string.feed_fail));
                    mEditText.setText("");
                }
            }
        });
    }

    @Override
    public void setGenericNodataOrNonetwork() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.send_bu){
            if(TextUtils.isEmpty(mEditText.getText().toString())){
                ToastUtils.showToast(Utils.getString(context,R.string.hint_feedback_input));
                return;
            }
            ParamsApi.toRetroaction(mEditText.getText().toString(),"").execute(new RequestCallback<BaseResult>() {
                @Override
                public void onRequestSuccess(BaseResult dataResult) {
                    ToastUtils.showToast(dataResult.getmMessage());
                    finish();
                }

                @Override
                public void onRequestFailure(BaseResult dataResult) {
                    ToastUtils.showToast(dataResult.getmMessage());
                }
            });
        }
    }
}
