package com.famlink.frame.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.famlink.frame.util.show.T;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 缓存存储类
 */
public class CacheUtils {
    public static final String PREF_NAME = "appsys.local.dbfile";
    public static final String KEY_IS_LOGOUT = "is_logout";

    

    private static CacheUtils mPreferences;

    private SharedPreferences mSettings;
//    private SharedPreferences shared;
    private CacheUtils() {
        mSettings = Utils.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static CacheUtils getInstance()
    {
        if (mPreferences == null) {
            mPreferences = new CacheUtils();
        }
        return mPreferences;
    }

    public boolean isContains(String key) {
        return mSettings.contains(key);
    }

    public int getInt(String name, int def) {
        return mSettings.getInt(name, def);
    }

    public void putInt(String name, int value) {
        SharedPreferences.Editor edit = mSettings.edit();
        edit.putInt(name, value);
        edit.commit();
    }

    public String getString(String key) {
        return mSettings.getString(key, "");
    }

    public String getString(String key, String defaultValue) {
        return mSettings.getString(key, defaultValue);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor edit = mSettings.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public long getLong(String key, long defaultValue) {
        return mSettings.getLong(key, defaultValue);
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor edit = mSettings.edit();
        edit.putLong(key, value);
        edit.commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mSettings.getBoolean(key, defaultValue);
    }

    public void putBoolean(String key, boolean bool) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean(key, bool);
        editor.commit();
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.remove(key);
        editor.commit();
    }

    public void clear() {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * put long preferences
     * 
     * @param context
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putLong(Context context, String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * get long preferences
     * 
     * @param context
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     *         name that is not a long
     * @see #getLong(Context, String, long)
     */
    public static long getLong(Context context, String key) {
        return getLong(context, key, -1);
    }

    /**
     * get long preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     *         this name that is not a long
     */
    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }
    
    /**
     * remove obj in preferences 
     * @param context
     * @param key
     * @return
     */
    public static boolean removeSharedPreferenceByKey(Context context, String key){
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        return editor.commit();
    }

    public void logout(boolean forceLogout) {
        if (forceLogout) {
            clear();
            putBoolean(KEY_IS_LOGOUT, true);
        }
    }
    public static String PREFERENCE_NAME = "VersionUpdate";


}

