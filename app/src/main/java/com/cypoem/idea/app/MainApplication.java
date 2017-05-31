package com.cypoem.idea.app;

import android.support.v7.app.AppCompatDelegate;

import com.airong.core.BaseApp;
import com.airong.core.utils.Utils;
import com.cypoem.idea.module.bean.User;
import com.cypoem.idea.utils.SharedPreferencesHelper;
import com.cypoem.idea.utils.UserInfoTools;
import com.squareup.leakcanary.LeakCanary;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import cn.sharesdk.framework.ShareSDK;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_AUTO;

/**
 * Created by zhpan on 2017/4/16.
 */

public class MainApplication extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        UserInfoTools.init(this);
        //  初始化mob
        ShareSDK.initSDK(this);

        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_AUTO);
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
