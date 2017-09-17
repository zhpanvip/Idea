package com.airong.core.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.compat.BuildConfig;

import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 *     author: zhpan
 *     blog  : http://blankj.com
 *     time  : 2016/9/27
 *     desc  : 崩溃相关工具类
 * </pre>
 */
public class CrashUtils implements UncaughtExceptionHandler {

    private volatile static CrashUtils mInstance;
    private UncaughtExceptionHandler mHandler;
    private boolean mInitialized;
    private String crashDir;

    private CrashUtils() {
    }

    /**
     * 获取单例
     * <p>在Application中初始化{@code CrashUtils.getInstance().init(this);}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}</p>
     *
     * @return 单例
     */
    public static CrashUtils getInstance() {
        if (mInstance == null) {
            synchronized (CrashUtils.class) {
                if (mInstance == null) {
                    mInstance = new CrashUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化
     *
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public boolean init() {
        if (mInitialized) return true;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File baseCache = Environment.getExternalStorageDirectory();
            if (baseCache == null) return false;
            crashDir = baseCache.getPath() + File.separator + "cypoem" + File.separator + "crash" + File.separator;
        } else {
            File baseCache = Utils.getContext().getCacheDir();
            if (baseCache == null) return false;
            crashDir = baseCache.getPath() + File.separator + "crash" + File.separator;
        }
        mHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        return mInitialized = true;
    }

    @Override
    public void uncaughtException(Thread thread, final Throwable throwable) {
        String name = "android-" + new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        final String fullPath = crashDir + name + ".txt";
        MobclickAgent.reportError(Utils.getContext(),throwable.getCause());
        if (!FileUtils.createOrExistsFile(fullPath)) return;
        new Thread(() -> {

            PrintWriter pw = null;
            try {
                boolean debug = BuildConfig.DEBUG;
                pw = new PrintWriter(new FileWriter(fullPath, false));
                //pw.write(getCrashHead());
                dumpPhoneInfo(pw);
                throwable.printStackTrace(pw);
                Throwable cause = throwable.getCause();
                while (cause != null) {
                    cause.printStackTrace(pw);
                    cause = cause.getCause();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            } finally {
                CloseUtils.closeIO(pw);
            }
        }).start();
        if (mHandler != null) {
            mHandler.uncaughtException(thread, throwable);
        }
    }

    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        PackageManager pm = Utils.getContext().getPackageManager();
        PackageInfo pi = pm.getPackageInfo(Utils.getContext().getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("App Version:" + pi.versionName);
        pw.print("_");
        pw.println(pi.versionCode);

        //  Android 版本号
        pw.print("OS Version:" + Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);

        //  手机制造商
        pw.print("Device Vendor:");
        pw.println(Build.MANUFACTURER);

        //  手机型号
        pw.print("Device Model:");
        pw.println(Build.MODEL);

        //  CPU架构
        pw.print("Device CPU ABI:");
        pw.println(Build.CPU_ABI);
    }
}
