/*
 * Copyright (C) 2016 venshine.cn@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.airong.core.utils;

import android.util.Log;

import com.airong.core.BuildConfig;


/**
 * Log日志打印操作
 *
 * @author Weiss
 */
public class LogUtils {

    private static final boolean DEBUG = true;

    /**
     * 获取当前类名
     *
     * @return
     */
    private static String getClassName() {
        // 这里的数组的index，即2，是根据你工具类的层级取的值，可根据需求改变
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        String result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        result = result.substring(lastIndex + 1, result.length());
        return result;
    }


    public static void w(String msg) {
        if (DEBUG) {
            if (null == msg) {
                msg = "数据为null";
            }
            Log.w(getClassName(), msg);
        }
    }

    /**
     * debug log
     *
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (DEBUG) {
            if (null == msg) {
                msg = "数据为null";
            }
            Log.d(tag, msg);
        }
    }

    /**
     * error log
     *
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (DEBUG) {
            if (null == msg) {
                msg = "数据为null";
            }
            Log.e(tag, msg);
        }
    }

    /**
     * debug log
     *
     * @param msg
     */
    public static void d(String msg) {
        if (DEBUG) {
            if (null == msg) {
                msg = "数据为null";
            }
            Log.d(getClassName(), msg);
        }
    }

    /**
     * debug log
     *
     * @param msg
     */
    public static void i(String msg) {
        if (DEBUG) {
            if (null == msg) {
                msg = "数据为null";
            }
            Log.i(getClassName(), msg);
        }
    }

    /**
     * error log
     *
     * @param msg
     */
    public static void e(String msg) {
        if (DEBUG) {
            if (null == msg) {
                msg = "数据为null";
            }
            Log.e(getClassName(), msg);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            if (null == msg) {
                msg = "数据为null";
            }
            Log.i(tag, msg);
        }
    }


    public static void w(String tag, String msg) {
        if (DEBUG) {
            if (null == msg) {
                msg = "数据为null";
            }
            Log.w(tag, msg);
        }
    }

}
