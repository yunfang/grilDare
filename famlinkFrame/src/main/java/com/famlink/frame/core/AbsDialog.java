package com.famlink.frame.core;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.famlink.frame.core.module.AbsModule;
import com.famlink.frame.core.module.IOCProxy;
import com.famlink.frame.util.StringUtil;

import org.xutils.x;


/**
 * Created by lyy on 2015/11/4.
 * 继承Dialog
 */
public abstract class AbsDialog extends Dialog {
    protected String TAG = "";
    private Object             mObj;    //被观察者
    private IOCProxy mProxy;
    private DialogSimpleModule mSimpleModule;
    private ModuleFactory mModuleF;

    public AbsDialog(Activity activity) {
        this(activity, null);
    }
    public AbsDialog(Activity activity, int theme) {
        super(activity, theme);
    }
    /**
     * @param activity
     * @param obj     Dialog的寄主
     */
    public AbsDialog(Activity activity, Object obj) {
        super(activity);
        mObj = obj;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(setLayoutId());
//        ButterKnife.inject(this);
        x.view().inject(activity);
        initDialog();
    }

    public abstract void setInitView();

    private void initDialog() {
        TAG = StringUtil.getClassName(this);
        mProxy = IOCProxy.newInstance(this);
        mModuleF = ModuleFactory.newInstance();
        if (mObj != null) {
            mSimpleModule = new DialogSimpleModule(getContext());
            IOCProxy.newInstance(mObj, mSimpleModule);
        }
    }

    /**
     * 获取简单打Moduel回调，这个一般用于回调数据给寄主
     */
    protected DialogSimpleModule getSimplerModule() {
        if (mObj == null) {
            throw new NullPointerException("必须设置寄主对象");
        }
        return mSimpleModule;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        System.gc();
    }

    /**
     * 获取Module
     *
     * @param clazz {@link AbsModule}
     */
    protected <M extends AbsModule> M getModule(Class<M> clazz) {
        M module = mModuleF.getModule(getContext(), clazz);
        mProxy.changeModule(module);
        return module;
    }

    /**
     * 设置资源布局
     *
     * @return
     */
    protected abstract int setLayoutId();

    /**
     * 数据回调
     */
    protected abstract void dataCallback(int result, Object obj);

}
