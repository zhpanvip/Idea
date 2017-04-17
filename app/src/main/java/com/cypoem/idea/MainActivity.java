package com.cypoem.idea;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showProgress("");
        initView();
    }

    private void initView() {
        getSubTitle().setVisibility(View.GONE);
    }

    @Override
    protected boolean isShowBacking() {
        return false;
    }
}
