package com.famlink.frame.widget.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.famlink.frame.R;

/**
 * 底部弹出的dialog
 * Created by Administrator on 2016/6/21.
 */
public class ButtomDialog extends BaseDialog  {

    private DialogOnClickListener listener;

    public interface DialogOnClickListener{
        void OnConfirm();
        void OnCancel();
    }

    public ButtomDialog(Context context, DialogOnClickListener listener) {
        super(context, R.layout.bottom_dialog);
        this.listener = listener;
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setWindowAnimations(R.style.BottomDialogWindowAnim);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        findViewById(R.id.id_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.OnConfirm();
                    dismiss();
                }
            }
        });
        findViewById(R.id.id_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.OnCancel();
                    dismiss();
                }
            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        switch(v.getId()){
//            case R.id.id_confirm:
//                if(listener != null){
//                    listener.OnConfirm();
//                    dismiss();
//                }
//                break;
//            case R.id.id_cancel:
//                if(listener != null){
//                    listener.OnCancel();
//                    dismiss();
//                }
//                break;
//
//        }
//    }
}
