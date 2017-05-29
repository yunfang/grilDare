package com.dora.feed.mvp.bean;

/**
 * Created by wangkai on 16/6/14.
 */
public class CardBean {

    private String imgUrl;
    private String title;
    private String content;


    public static final int TYPE_ITEM_VERTICAL = 100;   //横向的Card布局
    public static final int TYPE_ITEM_HORIZONTAL = TYPE_ITEM_VERTICAL*10; //竖向的Card布局

    public static final int TYPE_ITEM_ = TYPE_ITEM_VERTICAL;    //默认设置列表类型为横向布局

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
