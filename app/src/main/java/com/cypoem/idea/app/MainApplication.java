package com.cypoem.idea.app;

import com.airong.core.BaseApp;
import com.squareup.leakcanary.LeakCanary;

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
}
