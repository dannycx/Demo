package com.danny.file.sp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by danny on 5/31/18.
 */

public class SpUtils {
    private static final String sName = "config";

    /**
     * 往SharedPreferences中存储值
     * @param context 上下文
     * @param key key
     * @param value 值
     */
    public static void put(Context context, String key, Object value){
        SharedPreferences sp=context.getSharedPreferences(sName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        if (value instanceof String){
            editor.putString(key, (String) value);
        }else if (value instanceof Boolean){
            editor.putBoolean(key, (Boolean) value);
        }else if (value instanceof Long){
            editor.putLong(key, (Long) value);
        }else if (value instanceof Integer){
            editor.putInt(key, (Integer) value);
        }else if (value instanceof Float){
            editor.putFloat(key, (Float) value);
        }else if (value instanceof Set){
            editor.putStringSet(key, (Set<String>) value);
        }
        editor.apply();
    }

    /**
     * 获取SharedPreferences中存储值
     * @param context 上下文
     * @param key key
     * @param defValue 默认值
     * @return SharedPreferences存储值
     */
    public static Object get(Context context,String key,Object defValue){
        SharedPreferences sp=context.getSharedPreferences(sName,Context.MODE_PRIVATE);
        if (defValue instanceof String){
            return sp.getString(key, (String) defValue);
        }else if (defValue instanceof Boolean){
            return sp.getBoolean(key, (Boolean) defValue);
        }else if (defValue instanceof Long){
            return sp.getLong(key, (Long) defValue);
        }else if (defValue instanceof Integer){
            return sp.getInt(key, (Integer) defValue);
        }else if (defValue instanceof Float){
            return sp.getFloat(key, (Float) defValue);
        }else if (defValue instanceof Set){
            return sp.getStringSet(key, (Set<String>) defValue);
        }else {
            return null;
        }
    }

    /**
     * 移除key
     * @param context 上下文
     * @param key key
     */
    public static void remove(Context context,String key){
        SharedPreferences sp=context.getSharedPreferences(sName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.remove(key);
        editor.apply();
    }
}
