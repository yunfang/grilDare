package com.dora.feed.widget.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.famlink.frame.util.ToastUtils;

public class RecordSQLiteOpenHelper extends SQLiteOpenHelper {

    private static String name = "temp.db";
    private static Integer version = 2;

    public String ARTICLE_ID = "articleId";
    public String IS_LIKE = "isLike";
    public String IS_SHOW = "isShow";
    public String IS_FAVIRATE = "isFavirate";

    public String B_ID = "bid";

    public String DATA_BASE_NAME_L = "data_like";
    public String DATA_BASE_SHOW = "data_isShow";
    public String DATA_BASE_NAME_F = "data_favirate";
    public String DATA_BASE_NAME_ID = "data_click_id";

    /**
     * 存储该ID对应的文章是否被看过
     */
    private String DATABASE_CREATE_ISSHOW =
            "create table "+DATA_BASE_SHOW+"( _id integer primary key autoincrement,"+ARTICLE_ID+"  varchar(200), "+IS_SHOW+"  varchar(200));";
    /**
     * 点赞数据表
     */
    private String DATABASE_CREATE_LIKE =
            "create table "+DATA_BASE_NAME_L+"( _id integer primary key autoincrement,"+ARTICLE_ID+"  varchar(200), "+IS_LIKE+"  varchar(200));";
    /**
     * 收藏数据表
     */
    private String DATABASE_CREATE_FAVIRATE =
            "create table "+DATA_BASE_NAME_F+"( _id integer primary key autoincrement,"+ARTICLE_ID+"  varchar(200), "+IS_FAVIRATE+" varchar(200));";
    /**
     * 点击某个Item的数据表
     */
    private String DATABASE_CREATE_CLICK_ID =
            "create table "+DATA_BASE_NAME_ID+"( _id integer primary key autoincrement,"+B_ID+"  varchar(200));";





    public RecordSQLiteOpenHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table records(id integer primary key autoincrement,name varchar(200))");
        db.execSQL(DATABASE_CREATE_ISSHOW);
        db.execSQL(DATABASE_CREATE_LIKE);
        db.execSQL(DATABASE_CREATE_FAVIRATE);
        db.execSQL(DATABASE_CREATE_CLICK_ID);
//        ToastUtils.showToast("Oncreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//      ToastUtils.showToast("OnUpgrade-----------begin");
        db.execSQL("drop table records");
//        db.execSQL("drop table"+DATABASE_CREATE_ISSHOW);
        db.execSQL("drop table "+DATA_BASE_NAME_L);
        db.execSQL("drop table "+DATA_BASE_NAME_F);
        db.execSQL("drop table "+DATA_BASE_NAME_ID);
//        ToastUtils.showToast("OnUpgrade-----------zhongjian");
        onCreate(db);
//        ToastUtils.showToast("OnUpgrade-----------end");
//        db.execSQL("create table records(id integer primary key autoincrement,name varchar(200))");
//        db.execSQL(DATABASE_CREATE_ISSHOW);
//        db.execSQL(DATABASE_CREATE_LIKE);
//        db.execSQL(DATABASE_CREATE_FAVIRATE);
//        db.execSQL(DATABASE_CREATE_CLICK_ID);
    }


}