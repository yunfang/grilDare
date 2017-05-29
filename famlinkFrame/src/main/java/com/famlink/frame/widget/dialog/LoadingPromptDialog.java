package com.famlink.frame.widget.dialog;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.famlink.frame.R;

/**
 * Created by Administrator on 2016/6/21.
 */
public class LoadingPromptDialog extends  BaseDialog {
    /**
     * LoadingPromptDialog
     * @param context context
     */
    public LoadingPromptDialog(Context context) {
        super(context, R.layout.loading_dialog_view);
//        findViewById(R.id.id_loading_img).startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.unlimited_rotate));

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ImageView imageView = (ImageView)findViewById(R.id.id_loading_img);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }

    /**
     * 设置loading中的提示语, 默认无提示语
     * @param content content
     */
    public void setLoadingText(String content) {
        if (!TextUtils.isEmpty(content)) {
            ((TextView) findViewById(R.id.id_content_text)).setText(content);
        }
    }

    @Override
    public void dismiss() {
        findViewById(R.id.id_loading_img).clearAnimation();
        super.dismiss();
    }
}
