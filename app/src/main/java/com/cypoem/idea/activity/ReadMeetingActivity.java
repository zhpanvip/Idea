package com.cypoem.idea.activity;

import android.os.Bundle;

import com.cypoem.idea.R;

public class ReadMeetingActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_read_meeting;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setRefreshLayout(true);
    }
}
