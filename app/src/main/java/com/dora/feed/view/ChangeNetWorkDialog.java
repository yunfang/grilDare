package com.dora.feed.view;

import android.content.Context;
import android.view.View;

import com.dora.feed.R;
import com.famlink.frame.widget.dialog.BaseDialog;

/**
 * Created by wangkai on 16/8/15.
 * 判断网络是3G还是WIFI
 * Created by Administrator on 2016/6/21.
 */
public class ChangeNetWorkDialog extends BaseDialog implements View.OnClickListener {
    private Context context;



    @Override
    public void onClick(View v) {
        if (v.getId() == com.famlink.frame.R.id.dialog_cancel) {
            if (listener != null) {
//                listener.onClickCancel();
                dismiss();
            }

        } else if (v.getId() == com.famlink.frame.R.id.dialog_comfirm) {
            if (listener != null) {
                listener.onClickConfirm();
                dismiss();
            }
        }
    }

    private onClickListener listener;

    public interface onClickListener {
//        void onClickCancel();

        void onClickConfirm();
    }

    public ChangeNetWorkDialog(Context context, onClickListener listener) {
        super(context, R.layout.change_network_dialog);
        this.listener = listener;
        this.context = context;

        findViewById(com.famlink.frame.R.id.dialog_cancel).setOnClickListener(this);
        findViewById(com.famlink.frame.R.id.dialog_comfirm).setOnClickListener(this);
    }
}