package com.dora.feed.view.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dora.feed.net.ParamsApi;
import com.famlink.frame.mvp.bean.BaseResult;
import com.famlink.frame.net.biz.BaseRequestCallback;
import com.famlink.frame.net.biz.RequestCallback;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.util.Utils;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.IssueKey;
import com.famlink.frame.view.fragment.BaseFragment;
import com.dora.feed.R;
import com.dora.feed.databinding.LayoutFragmentFeedbackBinding;

import org.xutils.view.annotation.ViewInject;

/**
 * 意见反馈
 * Created by admin on 2016/7/11.
 */
public class FeedBackFragment extends BaseFragment<LayoutFragmentFeedbackBinding> implements View.OnClickListener {
    protected static final int MAX_CONTENT_LENGTH = 100;
    private static final int MIN_CONTENT_LENGTH = 5;

    private View view;

    @ViewInject(R.id.send_bu)
    Button login_bu;

    @ViewInject(R.id.content)
    EditText editText;

    @Override
    public void onBaseCreateView(View view, Bundle savedInstanceState) {
        this.view = view;
    }

    @Override
    public int setLayout() {
        return R.layout.layout_fragment_feedback;
    }

    @Override
    public void setInterfaceView() {
        login_bu.setOnClickListener(this);

        editText.addTextChangedListener(new TextWatcher() {
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
                        editText.setText(s);
                        editText.setSelection(s.length());
                        ToastUtils.showToast(Utils.getString(context,R.string.feed_length_too_long));
                    }
                } catch (NumberFormatException e) {
                    ToastUtils.showToast(Utils.getString(context,R.string.feed_fail));
                    editText.setText("");
                }
            }
        });
    }

    @Override
    public void setGenericNodataOrNonetwork() {

    }

    @Override
    protected void dataCallback(int result, Object obj) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.send_bu:
                if(TextUtils.isEmpty(editText.getText().toString())){
                    ToastUtils.showToast(Utils.getString(context, com.famlink.frame.R.string.hint_feedback_input));
                    return;
                }
                com.dora.feed.net.ParamsApi.toRetroaction(editText.getText().toString(),"").execute(new BaseRequestCallback<BaseResult>() {
                    @Override
                    public void onRequestSucceed(BaseResult result) {
                        ToastUtils.showToast(result.getmMessage());
                        editText.setText("");
                        DataChangeNotification.getInstance().notifyDataChanged(IssueKey.FEEDBACK_SUCCESS);
                    }

                    @Override
                    public void onRequestFailed(BaseResult result) {
                        ToastUtils.showToast(result.getmMessage());
                    }
                });
                break;
        }

//        switch (v.getId()){
//            case R.id.setting_retroaction_rl:
//                startActivity(new Intent(getActivity(), RetroactionActivity.class));
//                break;
//            case R.id.setting_evaluate_rl:
//                try {
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse("market://details?id="+context.getPackageName()));
//                    startActivity(intent);
//                }catch (Exception e){
//                    ToastUtils.showToast(Utils.getString(getActivity(),R.string.no_market));
//                }
//                break;
//            case R.id.setting_update_rl:
//                PromptUtils.showProgressDialog(getContext(),Utils.getString(getActivity(),R.string.loding_check_update));
//                Utils.checkVersion(getActivity(), "", "", new Utils.OnUpdateVersionListener() {
//                    @Override
//                    public void NoNeedUpdateVersion() {
//                        PromptUtils.dismissProgressDialog();
//                    }
//                });
//                break;
//            case R.id.setting_about_rl:
//                startActivity(new Intent(getActivity(), AboutActivity.class));
//
//                break;
//            case R.id.logout_bu:
//                Utils.logout(getContext(), new Utils.onLogoutListener() {
//                    @Override
//                    public void onConfirm() {
//
//
//                        startActivity(new Intent(getActivity(), LoginActivity.class));
//                    }
//                });
//                break;
//        }
    }
}
