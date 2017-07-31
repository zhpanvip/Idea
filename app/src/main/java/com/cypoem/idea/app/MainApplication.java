package com.cypoem.idea.app;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.airong.core.BaseApp;
import com.airong.core.utils.Utils;
import com.cypoem.idea.BuildConfig;
import com.cypoem.idea.utils.UserInfoTools;
import com.mob.MobSDK;
import com.squareup.leakcanary.LeakCanary;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by zhpan on 2017/4/16.
 */

public class MainApplication extends BaseApp {

    private static MainApplication instance;
    private Set<Activity> allActivities;

    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;

    // 填写从短信SDK应用后台注册得到的APPKEY
    private static String APPKEY = "1e41285782564";
    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "e989c7f12c5b645b5235325ed8f35593";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        UserInfoTools.init(this);
        //  初始化mob
        //ShareSDK.initSDK(this);
        // MobSDK.init(this,APPKEY,APPSECRET);
        setNightMode();
       // installLeak();
    }

    private void installLeak() {
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }
    }

    public static synchronized MainApplication getInstance() {
        return instance;
    }

    /**
     * 初始化夜间模式
     */
    private void setNightMode() {
        boolean nightMode = UserInfoTools.isNightMode(this);
        AppCompatDelegate.setDefaultNightMode(nightMode ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

  /*    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }*/

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


    public void getScreenSize() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }


    public static String getPublicKeyStore() {
        try {
            InputStreamReader inputReader = new InputStreamReader(Utils.getContext().getResources().getAssets().open("publicKey.keystore"));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null) {
                if (line.charAt(0) == '-') {
                    continue;
                }
                Result += line;
            }
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
