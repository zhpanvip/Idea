package com.cypoem.idea;

import android.os.Bundle;

import com.airong.core.BaseRxActivity;

public class BaseActivity extends BaseRxActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}
