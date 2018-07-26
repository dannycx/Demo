package com.danny.service;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreference保存状态数据
 * Created by danny on 2018/5/7.
 */

public class SpUtil {
    private static final String sName="config";

    /**
     * sp存数据
     * @param context 上下文
     * @param key key
     * @param value 值
     */
    public static void put(Context context, String key, Object value){
        SharedPreferences preferences=context.getSharedPreferences(sName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        if (value instanceof String){
            editor.putString(key,(String) value);
        }else if (value instanceof Integer){
            editor.putInt(key,(int)value);
        }else if (value instanceof Boolean){
            editor.putBoolean(key,(boolean)value);
        }else if (value instanceof Float){
            editor.putFloat(key,(float)value);
        }else if (value instanceof Long){
            editor.putLong(key,(long)value);
        }
        editor.apply();
    }

    /**
     * sp取数据
     * @param context 上下文
     * @param key key
     * @param def 默认值
     * @return sp返回结果
     */
    public static Object get(Context context, String key, Object def){
        SharedPreferences preferences=context.getSharedPreferences(sName,Context.MODE_PRIVATE);
        if (def instanceof String){
            return preferences.getString(key,(String) def);
        }else if (def instanceof Integer){
            return preferences.getInt(key,(Integer) def);
        }else if (def instanceof Boolean){
            return preferences.getBoolean(key,(Boolean) def);
        }else if (def instanceof Float){
            return preferences.getFloat(key,(Float) def);
        }else if (def instanceof Long){
            return preferences.getLong(key,(Long) def);
        }else {
            return null;
        }
    }

    /**
     * 移除sp数据
     * @param context 上下文
     * @param key key
     */
    public static void remove(Context context,String key){
        SharedPreferences preferences=context.getSharedPreferences(sName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
