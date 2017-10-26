package com.airong.core.utils;

import android.content.Context;
import android.support.compat.BuildConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : Utils初始化相关
 * </pre>
 */
public class Utils {

    private static Context context;

    private static Boolean sDebug;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }


    public static boolean isDebugBuild() {
        if (sDebug == null) {
            try {
                final Class<?> activityThread = Class.forName("android.app.ActivityThread");
                final Method currentPackage = activityThread.getMethod("currentPackageName");
                final String packageName = (String) currentPackage.invoke(null, (Object[]) null);
                final Class<?> buildConfig = Class.forName(packageName + ".BuildConfig");
                final Field DEBUG = buildConfig.getField("DEBUG");
                DEBUG.setAccessible(true);
                sDebug = DEBUG.getBoolean(null);
            } catch (final Throwable t) {
                final String message = t.getMessage();
                if (message != null && message.contains("BuildConfig")) {
                    // Proguard obfuscated build. Most likely a production build.
                    sDebug = false;
                } else {
                    sDebug = BuildConfig.DEBUG;
                }
            }
        }
        return sDebug;
    }
}