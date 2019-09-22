package com.schn.camera2019.util;

import android.util.Log;

public class LogUtils {
    private static String LOG_TAG = "TAG";
    private static char LOG_TYPE = 'v';

    public static void init() {}

    /****************************
     * Warn
     *********************************/
    public static void w(Object msg) {
        w(LOG_TAG, msg);
    }

    public static void w(String tag, Object msg) {
        w(tag, msg, null);
    }

    public static void w(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'w');
    }

    /***************************
     * Error
     ********************************/
    public static void e(Object msg) {
        e(LOG_TAG, msg);
    }

    public static void e(String tag, Object msg) {
        e(tag, msg, null);
    }

    public static void e(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'e');
    }

    /***************************
     * Debug
     ********************************/
    public static void d(Object msg) {
        d(LOG_TAG, msg);
    }

    public static void d(String tag, Object msg) {// 调试信息
        d(tag, msg, null);
    }

    public static void d(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'd');
    }

    /****************************
     * Info
     *********************************/
    public static void i(Object msg) {
        i(LOG_TAG, msg);
    }

    public static void i(String tag, Object msg) {
        i(tag, msg, null);
    }

    public static void i(String tag, Object msg, Throwable tr) {
        log(tag, msg == null ? null : msg.toString(), tr, 'i');
    }

    /**************************
     * Verbose
     ********************************/
    public static void v(Object msg) {
        v(LOG_TAG, msg);
    }

    public static void v(String tag, Object msg) {
        v(tag, msg, null);
    }

    public static void v(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'v');
    }

    /**
     * @param tag
     * @param msg
     * @param level
     */
    private static void log(String tag, String msg, Throwable tr, char level) {
        if ('e' == level && ('e' == LOG_TYPE || 'v' == LOG_TYPE)) {
            Log.e(tag, msg, tr);
        } else if ('w' == level && ('w' == LOG_TYPE || 'v' == LOG_TYPE)) {
            Log.w(tag, msg, tr);
        } else if ('d' == level && ('d' == LOG_TYPE || 'v' == LOG_TYPE)) {
            Log.d(tag, msg, tr);
        } else if ('i' == level && ('d' == LOG_TYPE || 'v' == LOG_TYPE)) {
            Log.i(tag, msg, tr);
        } else {
            Log.v(tag, msg, tr);
        }
    }

}