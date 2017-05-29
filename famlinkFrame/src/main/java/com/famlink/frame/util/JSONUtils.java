package com.famlink.frame.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/6/23.
 */
public class JSONUtils {

    private static Gson mGson = new GsonBuilder().serializeNulls().create();

    /**
     * 获取GSON
     * @return GSON实例
     */
    public static Gson gsonInstance() {
        return mGson;
    }

    /**
     * 从json字符串构造 clazz 的实例
     *
     * @param jsonString json字符串
     * @param clazz      目标转换对象的class类型
     * @param <T>        转换完成的类型实例
     * @return <p>json字符串解析成功，返回SplashScreenItemsResult</p>
     *         <p>json字符串解析失败时，返回null</p>
     */
    public static <T> T fromJsonString(String jsonString, Class<T> clazz) {
        try {
            return gsonInstance().fromJson(jsonString, clazz);
        } catch (com.google.gson.JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从json字符串构造 List 的实例
     *
     * @param jsonString json字符串
     * @param typeOfT      目标转换对象的typeOfT类型
     * @param <T>        转换完成的类型实例
     * @return <p>json字符串解析成功，T</p>
     *         <p>json字符串解析失败时，返回null</p>
     */
    public static <T> T fromJsonString(String jsonString, Type typeOfT) {
        try {
            return (T)gsonInstance().fromJson(jsonString, typeOfT);
        } catch (com.google.gson.JsonSyntaxException e) {
            e.printStackTrace();
        }
        return (T)null;
    }
    /**
     * 从对象转换为json字符串
     * @param object    对象实例
     * @return          json字符串
     */
    public static String toJsonString(Object object) {
        try {
            return gsonInstance().toJson(object);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return "";
    }
}
