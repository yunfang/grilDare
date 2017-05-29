package com.famlink.frame.net.biz;

import com.famlink.frame.configure.Config;
import com.famlink.frame.mvp.bean.BaseResult;
import com.famlink.frame.util.JSONUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/6/23.
 */
public  class Request<R extends BaseResult> {
    private HashMap<String, Object> mArguments = new HashMap<String, Object>();
    private Class<R> mResultClass;
    private String mUrl;
    private int mMethodType = 0;//0为GET的方式请求  ，1为POST的方式请求
    /**
     * 异步执行API
     *
     * @param requestCallback 执行完毕后的回调
     */
    public void execute(RequestCallback<R> requestCallback) {
        performRequest(requestCallback);
    }



    public Request(int methodType, Class<R> resultClass, String url) {
        if (!BaseResult.class.isAssignableFrom(resultClass)) {
            throw new IllegalArgumentException("resultClass must be subClass of BaseResult!");
        }
        mResultClass = resultClass;
        mUrl = url;
        mMethodType = methodType;
        // reset();
    }

    private void performRequest(RequestCallback<R> requestCallback) {
        StringBuilder sb = new StringBuilder();
        sb.append(mUrl+"?");
        switch (mMethodType){

            case 0://0为GET的方式请求
                RequestParams entity = new RequestParams(mUrl);
                for(String key : mArguments.keySet()){
                    entity.addParameter(key, mArguments.get(key));
                    sb.append(key).append("=").append((String)mArguments.get(key)).append("&");
                }
                String sbMsg = sb.substring(0, sb.length() - 1);
                if(Config.is_debug){
                    System.out.println("debug_url=============" + sbMsg.toString());
                }
                gethttp(requestCallback, entity);
                break;

            case 1://1为POST的方式请求
                RequestParams postentity = new RequestParams(mUrl);
                for(String key : mArguments.keySet()){
                    postentity.addParameter(key, mArguments.get(key));
                    sb.append(key).append("=").append((String)mArguments.get(key)).append("&");
                }
                String sbMsg1 = sb.substring(0, sb.length() - 1);
                if(Config.is_debug){
                    System.out.println("debug_url=============" + sbMsg1.toString());
                }
                posthttp(requestCallback, postentity);
                break;

            case 2: //2为文件上传
                RequestParams uploadentity = new RequestParams(mUrl);
                for(String key : mArguments.keySet()){

                    if(key.contains("***")){

                        File mFile = new File((String)mArguments.get(key));
                        if (mFile != null && mFile.exists()) {
                            uploadentity.addBodyParameter("headIcon", mFile);

                        }else{
                            System.out.println("file_error============文件路径异常");
                            return;
                        }
                    }else{
                        uploadentity.addParameter(key, (String)mArguments.get(key));
                    }
                    sb.append(key).append("=").append((String)mArguments.get(key)).append("&");
                }
                uploadentity.setMultipart(true);
                String sbMsg2 = sb.substring(0, sb.length() - 1);
                if(Config.is_debug){
                    System.out.println("debug_url=============" + sbMsg2.toString());
                }
                posthttp(requestCallback,uploadentity);
                break;
        }
    }

    private void posthttp(final RequestCallback<R> mRequestCallback, RequestParams entity) {
        x.http().post(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                R r = JSONUtils.fromJsonString(result, mResultClass);
                if(mRequestCallback != null){
                    if(r != null && r.getmCode().equals(BaseResult.OK)){//r != null && r.getCode() == BaseResult.OK
                        if(mResultClass != null){
                            invokeResultCheckMethod(mResultClass,r);
                        }
                        mRequestCallback.onRequestSuccess(r);
                    }else{
                        if(r == null){
                            r = newResultInstance();
                            if(r == null){
                                return;
                            }
                            r.setmCode(BaseResult.ERROR);
                            r.setmMessage("error");
                            mRequestCallback.onRequestFailure(r);
                        }else{
                            r.setmCode(r.getmCode());
                            r.setmMessage(r.getmMessage());
                            mRequestCallback.onRequestSuccess(r);
                        }
                    }
                }
                System.out.println(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("onError");
                R r =  newResultInstance();
                r.setmCode(BaseResult.ERROR);
                r.setmMessage("error");
                mRequestCallback.onRequestFailure(r);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }
    private void gethttp(final RequestCallback<R> mRequestCallback, RequestParams entity) {
        x.http().get(entity, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                R r = JSONUtils.fromJsonString(result, mResultClass);
                if (mRequestCallback != null) {
                    if (r != null && r.getmCode().equals(BaseResult.OK)) {//r != null && r.getCode() == BaseResult.OK
                        if (mResultClass != null) {
                            invokeResultCheckMethod(mResultClass, r);
                        }
                        mRequestCallback.onRequestSuccess(r);
                    } else {
                        if (r == null) {
                            r = newResultInstance();
                            if (r == null) {
                                return;
                            }
                            r.setmCode(BaseResult.ERROR);
                            r.setmMessage("error");
                            mRequestCallback.onRequestFailure(r);
                        } else {
                            r.setmCode(r.getmCode());
                            r.setmMessage(r.getmMessage());
                            mRequestCallback.onRequestSuccess(r);
                        }
                    }
                }
                System.out.println(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("onError");
                R r = newResultInstance();
                r.setmCode(BaseResult.ERROR);
                r.setmMessage(ex.getMessage());
                mRequestCallback.onRequestFailure(r);

            }

            @Override
            public void onCancelled(CancelledException cex) {
                System.out.println("onCancelled");
            }

            @Override
            public void onFinished() {
                System.out.println("onFinished");
            }
        });
        
    }

    /**
     * 增加请求参数, 会以URL?key1=value1&key2=value2的形式组合到URL中
     *
     * @param key      请求参数key
     * @param argument 请求参数value
     * @return 对象自身
     */
    public Request<R> addArgument(String key, Object argument) {
        if (!isObjectToStringNull(argument)) {
            mArguments.put(key, argument.toString());
        }
        return this;
    }
    private boolean isObjectToStringNull(Object object) {
//        return object == null || TextUtils.isEmpty(object.toString());
        return object == null;
    }


    protected R newResultInstance() {
        R data = null;
        try {
            data = mResultClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // 为了保证上层调用安全，这里需要保证data永远不可能为null，result class必须要有默认的构造方法
        if (data == null) {
        }
        return data;
    }

    private void invokeResultCheckMethod(Class<?> clazz, R r) {
        try {
                    java.lang.reflect.Method[] methods = clazz.getDeclaredMethods();
                    for (java.lang.reflect.Method method : methods) {
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        if (parameterTypes.length == 1 && parameterTypes[0] == BaseResult.class) {
                            method.setAccessible(true);
                            method.invoke(null, r);
                            break;
                        }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
