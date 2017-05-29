package com.dora.feed.widget.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangkai on 16/8/2.
 */

public class SQLiteData {

    private RecordSQLiteOpenHelper helper;
    private SQLiteDatabase db;

    public SQLiteData(Context context) {
        if (helper == null) {
            helper = new RecordSQLiteOpenHelper(context);
        }
    }

    /**
     * 插入数据-------------------------------------------------------是否被点赞过的增删改查
     */
    public void insertLikeData(String articleId, String isLike) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into "+helper.DATA_BASE_NAME_L+"(" + helper.ARTICLE_ID + ", " + helper.IS_LIKE + ") values ('" + articleId + "', '" + isLike + "')");
        db.close();
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    public String hasLikeData(String articleId) {
        List<Map<String, String>> mList = new ArrayList<Map<String, String>>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "select * from " + helper.DATA_BASE_NAME_L + " where " + helper.ARTICLE_ID + " =?", new String[]{articleId});
        String isLike = null;
        while (cursor.moveToNext()) {
//            Map<String, String> map = new HashMap<String, String>();
//            map.put(helper.ARTICLE_ID, cursor.getString(0)); //获取第一列的值,第一列的索引从0开始
//            map.put(helper.IS_LIKE, cursor.getString(1));//获取第二列的值

            isLike = cursor.getString(2);
//            mList.add(map);
        }
        cursor.close();
        db.close();
        return isLike;
    }

    /**
     * 修改数据表中的信息
     */
    public int updateLikeData(String articleId, String isLike) {
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(helper.IS_LIKE, isLike);
        String[] args = {String.valueOf(articleId)};
        return db.update(helper.DATA_BASE_NAME_L, cv, "articleId=?", args);
    }

    /**
     * 清空数据
     */
    public void deleteLikeData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from " + helper.DATA_BASE_NAME_L + "");
        db.close();
    }
    /**
     * 插入数据-------------------------------------------------------是否被收藏过的增删改查
     */
    public void insertFavirateData(String articleId, String isFavirate) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into "+helper.DATA_BASE_NAME_F+"("+helper.ARTICLE_ID+", " + helper.IS_FAVIRATE + ") values ('" + articleId + "', '" + isFavirate + "')");
        db.close();
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    public String hasFavirateData(String articleId) {
        List<Map<String, String>> mList = new ArrayList<Map<String, String>>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "select * from " + helper.DATA_BASE_NAME_F+ " where " + helper.ARTICLE_ID + " =?", new String[]{articleId});
        String isFavirate = null;
        while (cursor.moveToNext()) {
//            Map<String, String> map = new HashMap<String, String>();
//            map.put(helper.ARTICLE_ID, cursor.getString(0)); //获取第一列的值,第一列的索引从0开始
//            map.put(helper.IS_LIKE, cursor.getString(1));//获取第二列的值

            isFavirate = cursor.getString(2);
//            mList.add(map);
        }
        cursor.close();
        db.close();
        return isFavirate;
    }

    /**
     * 修改数据表中的信息
     */
    public int updateFavirateData(String articleId, String isFavirate) {
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(helper.IS_FAVIRATE, isFavirate);
        String[] args = {String.valueOf(articleId)};
        return db.update(helper.DATA_BASE_NAME_F, cv, "articleId=?", args);
    }

    /**
     * 清空数据
     */
    public void deleteFavirateData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from " + helper.DATA_BASE_NAME_F + "");
        db.close();
    }





    /**
     * 插入数据-------------------------------------------------------点击某个Item的数据表
     */
    public void insertClickIdData(String bid) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into "+helper.DATA_BASE_NAME_ID+"("+helper.B_ID+") values ('" + bid+ "')");
        db.close();
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    public String hasClickIdData() {
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select distinct "+helper.B_ID+" from " + helper.DATA_BASE_NAME_ID, new String[]{});
        String str="";
        for (int i=0;i<cursor.getCount();i++){
            cursor.moveToNext();
            if (i==0){
                str=cursor.getString(cursor.getColumnIndex(helper.B_ID));
            }else {
                str+=','+cursor.getString(cursor.getColumnIndex(helper.B_ID));
            }

        }

        cursor.close();
        db.close();
        return str;
    }

    /**
     * 清空数据
     */
    public void deleteClickIdData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from " + helper.DATA_BASE_NAME_ID + "");
        db.close();
    }



    /**
     * 插入数据-------------------------------------------------------某篇文章是否被访问过
     */
    public void insertIsshowData(String aid) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into "+helper.DATA_BASE_SHOW+"("+helper.ARTICLE_ID+", "+helper.IS_SHOW+") values ('" + aid+ "', '" +1+ "')");
        db.close();
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    public boolean hasIsshowData(String aid) {
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + helper.DATA_BASE_SHOW + " where " + helper.ARTICLE_ID + " =?", new String[]{aid});
        while (cursor.moveToNext()) {
            if(cursor.getCount() != 0){
                return true;
            }else{
                return false;
            }
        }
        cursor.close();
        db.close();
        return false;
    }

    /**
     * 清空数据
     */
    public void deleteIsshowData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from " + helper.DATA_BASE_SHOW + "");
        db.close();
    }



}
