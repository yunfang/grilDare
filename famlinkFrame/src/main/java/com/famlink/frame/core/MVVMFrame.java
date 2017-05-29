package com.famlink.frame.core;//package com.famlink.frame.core;
//
//import android.app.ActivityManager;
//import android.content.Context;
//
//
//import com.famlink.frame.util.show.FL;
//
//import java.util.Stack;
//
///**
// * Created by lyy on 2015/11/4.
// * APP生命周期管理类管理
// */
//public class MVVMFrame {
//    private static final    String    TAG      = "MVVMFrame";
//    private static final    Object    LOCK     = new Object();
//    private volatile static MVVMFrame mManager = null;
//    private Context mContext;
//    private Stack<AbsActivity> mActivityStack = new Stack<>();
//
//    private MVVMFrame() {
//
//    }
//
//    private MVVMFrame(Context context) {
//        mContext = context;
//    }
//
//    /**
//     * 初始化框架
//     *
//     * @param applicationContext
//     * @return
//     */
//    public static MVVMFrame init(Context applicationContext) {
//        if (mManager == null) {
//            synchronized (LOCK) {
//                if (mManager == null) {
//                    mManager = new MVVMFrame(applicationContext);
//                }
//            }
//        }
//        return mManager;
//    }
//
//    /**
//     * 获取AppManager管流程实例
//     *
//     * @return
//     */
//    public static MVVMFrame getInstance() {
//        if (mManager == null) {
//            throw new NullPointerException("请在application 的 onCreate 方法里面使用MVVMFrame.init()方法进行初始化操作");
//        }
//        return mManager;
//    }
//
//    public Stack<AbsActivity> getActivityStack() {
//        return mActivityStack;
//    }
//
//    /**
//     * 开启异常捕获
//     * 日志文件位于/data/data/Package Name/cache//crash/AbsExceptionFile.crash
//     */
//    public void openCrashHandler() {
//        openCrashHandler("", "");
//    }
//
//    /**
//     * 开启异常捕获
//     * 需要网络权限，get请求，异常参数，需要下面两个网络权限
//     * android:name="android.permission.INTERNET"
//     * android:name="android.permission.ACCESS_NETWORK_STATE"
//     *
//     * @param serverHost 服务器地址
//     * @param key        数据传输键值
//     */
//    public MVVMFrame openCrashHandler(String serverHost, String key) {
//        CrashHandler handler = CrashHandler.getInstance(mContext);
//        handler.setServerHost(serverHost, key);
//        Thread.setDefaultUncaughtExceptionHandler(handler);
//        return this;
//    }
//
//    /**
//     * 堆栈大小
//     */
//    public int getActivitySize() {
//        return mActivityStack.size();
//    }
//
//    /**
//     * 获取指定的Activity
//     */
//    public AbsActivity getActivity(int location) {
//        return mActivityStack.get(location);
//    }
//
//    /**
//     * 添加Activity到堆栈
//     */
//    public void addActivity(AbsActivity activity) {
//        if (mActivityStack == null) {
//            mActivityStack = new Stack<>();
//        }
//        mActivityStack.add(activity);
//    }
//
//    /**
//     * 获取当前Activity（堆栈中最后一个压入的）
//     */
//    public AbsActivity getCurrentActivity() {
//        return mActivityStack.lastElement();
//    }
//
//    /**
//     * 结束当前Activity（堆栈中最后一个压入的）
//     */
//    public void finishActivity() {
//        finishActivity(mActivityStack.lastElement());
//    }
//
//    /**
//     * 结束指定的Activity
//     */
//    public void finishActivity(AbsActivity activity) {
//        if (activity != null) {
//            mActivityStack.remove(activity);
//            activity.finish();
//        }
//    }
//
//    /**
//     * 移除指定的Activity
//     */
//    public void removeActivity(AbsActivity activity) {
//        if (activity != null) {
//            mActivityStack.remove(activity);
//        }
//    }
//
//    /**
//     * 结束指定类名的Activity
//     */
//    public void finishActivity(Class<?> cls) {
//        for (AbsActivity activity : mActivityStack) {
//            if (activity.getClass().equals(cls)) {
//                finishActivity(activity);
//            }
//        }
//    }
//
//    /**
//     * 结束所有Activity
//     */
//    public void finishAllActivity() {
//        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
//            if (mActivityStack.get(i) != null && mActivityStack.size() > 0) {
//                mActivityStack.get(i).finish();
//            }
//        }
//        mActivityStack.clear();
//    }
//
//    /**
//     * 退出应用程序
//     *
//     * @param isBackground 是否开开启后台运行
//     */
//    public void exitApp(Boolean isBackground) {
//        try {
//            finishAllActivity();
//            ActivityManager activityMgr = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
//            activityMgr.restartPackage(mContext.getPackageName());
//        } catch (Exception e) {
//            FL.e(TAG, FL.getExceptionString(e));
//        } finally {
//            // 注意，如果您有后台程序运行，请不要支持此句子
//            if (!isBackground) {
//                System.exit(0);
//            }
//        }
//    }
//
//
//}
