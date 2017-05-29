package com.dora.feed.config;


import org.xutils.image.ImageOptions;

/**
 * Created by wangkai on 16/7/12.
 */
public class Constants {

    /**\
     *
     * @param drawableLoading   //正在加载的默认图
     * @param drawableFail      //加载失败的默认图
     * @param isCircular        //是否是圆形图片  isCircular true:是圆形图   false:非
     * @return
     */
    public static ImageOptions setImageUtils(int drawableLoading, int drawableFail, boolean isCircular){
        ImageOptions options=new ImageOptions.Builder()
//设置加载过程中的图片
                .setLoadingDrawableId(drawableLoading)
//设置加载失败后的图片
                .setFailureDrawableId(drawableFail)
//设置使用缓存
                .setUseMemCache(true)
//设置显示圆形图片
                .setCircular(isCircular)
//设置支持gif
                .setIgnoreGif(false)
                .setFadeIn(true)

                .build();
        return options;
    }

    public static ImageOptions setImageUtils(int drawableLoading, int drawableFail){
        ImageOptions options=new ImageOptions.Builder()
//设置加载过程中的图片
                .setLoadingDrawableId(drawableLoading)
//设置加载失败后的图片
                .setFailureDrawableId(drawableFail)
//设置使用缓存
                .setUseMemCache(true)
//设置显示圆形图片
                .setCircular(true)
//设置支持gif
                .setIgnoreGif(false)
                .build();
        return options;
    }

    /**
     * 通知消息的传递
     */
    public static final String EXTRA_BUNDLE = "launchBundle";
    /**
     * 界面打开时通知UI界面
     */
    public static final String SEND_BROADCAST_DRAW = "sendNotifi";
}
