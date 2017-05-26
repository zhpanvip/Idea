package com.cypoem.idea.app;

import com.airong.core.BaseApp;
import com.airong.core.utils.Utils;
import com.squareup.leakcanary.LeakCanary;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static okhttp3.internal.Internal.instance;

/**
 * Created by zhpan on 2017/4/16.
 *
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
    }

    public static String getPublicKeyStore(){
        try
        {
            InputStreamReader inputReader = new InputStreamReader(Utils.getContext().getResources().getAssets().open("publicKey.keystore"));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
            {
                if (line.charAt(0) == '-')
                {
                    continue;
                }
                Result += line;
            }
            return Result;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
