package com.lzy.mywheelsthree.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * sharePreferences 工具类
 * <p/>
 * 缓存对象 字符串 boolean
 *
 * @author Leader 大侠是我
 */
public class SPUtils {

    public static String SP_NAME = "config";
    private static SharedPreferences sp;
    private static String serStr;
    private static Object deSerialization;
    private static final String USER_INFO = "user_info";//文件名
    private static final String USER_INFO_IS_FIRST = "user_info_is_first";//是否第一次启动
    private static SharedPreferences preferences;


    public static String getData(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
        //return null;
    }

    public static void saveData(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /*
         * 存储对象
         */
    public static void saveObject(Context context, Object object, String key) {
        SharedPreferences.Editor prefsEditor = getSharedPreferences(context).edit();
        Gson gson = new Gson();
        String json = gson.toJson(object);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    /*
     * 获取对象
     */
    public static Object getObject(Context context, Class<?> type, String key) {
        Gson gson = new Gson();
        String json = getSharedPreferences(context).getString(key, null);
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            return null;
        }
    }
    /*
	 * 获取SharedPreferences
	 */
    public static SharedPreferences getSharedPreferences(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return preferences;
    }
    /**
     * 保存 boolead 类型的数据
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveBoolean(Context context, String key, boolean value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 获取 boolead 类型的保存数据
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        //   Object sdf = sp.getBoolean(key, defValue);

        return sp.getBoolean(key, defValue);
    }

    /**
     * 保存 string 类型的数据
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveString(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    /**
     * 缓存int类型的数据
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveNum(Context context, String key, int value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }

    /**
     * 获取缓存的 int 类型的数据
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static int getNum(Context context, String key, int defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

    /**
     * 获取 保存的 string 数据
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(Context context, String key, String defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    /**
     * 清除所有缓存数据
     *
     * @param context
     */
    public static void clearAll(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().clear().commit();
    }

    /**
     * 序列化对象
     *
     * @param obj
     */
    private static String serialize(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return serStr;
    }

    /**
     * 反序列化对象
     *
     * @param str
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static Object deSerialization(String str) throws IOException,
            ClassNotFoundException {
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        Object person = (Object) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return person;
    }


}
