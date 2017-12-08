package com.cypoem.idea.activity;

import android.os.Bundle;
import android.view.View;

public class ReadMeetingActivity extends BasicWebViewActivity {

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        getToolbar().setVisibility(View.GONE);
    }
}
