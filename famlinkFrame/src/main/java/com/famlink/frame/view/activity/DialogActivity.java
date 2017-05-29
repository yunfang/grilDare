package com.famlink.frame.view.activity;

import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.famlink.frame.R;
import com.famlink.frame.util.NetUtils;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.util.Utils;
import com.famlink.frame.util.notify.DataChangeNotification;
import com.famlink.frame.util.notify.IssueKey;
import com.famlink.frame.util.notify.ObserverGroup;
import com.famlink.frame.util.notify.OnDataChangeObserver;
import com.famlink.frame.widget.dialog.ButtomDialog;
import com.famlink.frame.widget.dialog.LogoutDialog;
import com.famlink.frame.widget.dialog.PromptUtils;


/**
 * Created by Administrator on 2016/6/21.
 */
public class DialogActivity extends BaseActivity implements OnDataChangeObserver, View.OnClickListener {
//    @ViewInject(value = R.id.id_dialog_one)
//    Button dialog_one;
//    @ViewInject(R.id.id_dialog_two)
//    Button dialog_two;
//    @ViewInject(R.id.id_dialog_three)
//    Button dialog_three;
//    @ViewInject(R.id.id_dialog_net)
//    Button dialog_net;
//    @ViewInject(R.id.id_notify)
//    Button notify;


    ButtomDialog buttomDialog;//底部dialog
    private Button dialog_one;
    private Button dialog_two;
    private Button dialog_three;
    private Button dialog_net;
    private Button notify;

    @Override
    public int setLayout() {
        mObserverGroup = ObserverGroup.createLiveGroup();
        DataChangeNotification.getInstance().addObserver(IssueKey.ISLIVINGNOW,this,mObserverGroup);
        return R.layout.activity_dialog;
    }

    @Override
    public void setInterfaceView() {
        dialog_one = (Button) findViewById(R.id.id_dialog_one);
        dialog_two = (Button) findViewById(R.id.id_dialog_two);
        dialog_three = (Button) findViewById(R.id.id_dialog_three);
        dialog_net = (Button) findViewById(R.id.id_dialog_net);
        notify = (Button) findViewById(R.id.id_notify);

        dialog_one.setOnClickListener(this);
        dialog_two.setOnClickListener(this);
        dialog_three.setOnClickListener(this);
        dialog_net.setOnClickListener(this);
        notify.setOnClickListener(this);

        dialog_net.setText("sss");
    }

    @Override
    public void setGenericNodataOrNonetwork() {

    }
//    @Event(value = {R.id.id_dialog_one,R.id.id_dialog_two,R.id.id_dialog_three,R.id.id_dialog_net,R.id.id_notify}, type = View.OnClickListener.class)
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.id_dialog_one){
            buttomDialog = new ButtomDialog(Utils.getActivity(), new ButtomDialog.DialogOnClickListener() {
                @Override
                public void OnConfirm() {
                    ToastUtils.showToast("OnConfirm");
                }

                @Override
                public void OnCancel() {
                    ToastUtils.showToast("OnCancel");
                }
            });
            buttomDialog.show();

        }else if(view.getId() == R.id.id_dialog_two){
            PromptUtils.showProgressDialog(Utils.getActivity(),"正在加载中..");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    PromptUtils.dismissProgressDialog();
                }
            }, 2000);
        }else if(view.getId() == R.id.id_dialog_three){
            LogoutDialog logoutDialog = new LogoutDialog(Utils.getActivity(), new LogoutDialog.onClickListener() {
                @Override
                public void onClickCancel() {

                }

                @Override
                public void onClickConfirm() {

                }
            });
            logoutDialog.show();
        }else if(view.getId() == R.id.id_dialog_net){
            if(NetUtils.isNetworkConnected()){
                ToastUtils.showToast("网络有效");
            }else{
                ToastUtils.showToast("网络无效");
            }
        }else if(view.getId() == R.id.id_notify){
            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.ISLIVINGNOW);
        }
    }


    @Override
    public void onDataChanged(IssueKey issue, Object o) {
        if(issue.equals(IssueKey.ISLIVINGNOW)){
            ToastUtils.showToast("通知");
        }
    }


}
