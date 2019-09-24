package com.schn.camera2019.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.schn.camera2019.app.App;

public final class PreferenceUtil {

    public static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(App.context());
    }

    public static String getString(String key, String defValue) {
        return PreferenceManager.getDefaultSharedPreferences(App.context()).getString(key, defValue);
    }

    public static Boolean getBoolean(String key, Boolean defValue) {
        return PreferenceManager.getDefaultSharedPreferences(App.context()).getBoolean(key, defValue);
    }

    public static long getLong(String key, long defValue) {
        return PreferenceManager.getDefaultSharedPreferences(App.context()).getLong(key, defValue);
    }

    public static float getFloat(String key, float defValue) {
        return PreferenceManager.getDefaultSharedPreferences(App.context()).getFloat(key, defValue);
    }

    public static void put(String key, String value) {
        putString(key, value);
    }

    public static void put(String key, int value) {
        putInt(key, value);
    }

    public static void put(String key, float value) {
        putFloat(key, value);
    }

    public static void put(String key, long value) {
        putLong(key, value);
    }

    public static void put(String key, boolean value) {
        putBoolean(key, value);
    }

    private static void putString(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.context());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void clear() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.context());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public static void putLong(String key, long value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.context());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static void putBoolean(String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.context());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private static void putInt(String key, int value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.context());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private static void putFloat(String key, float value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.context());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

}