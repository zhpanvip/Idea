package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cypoem.idea.R;

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

    public static void start(Context context){
        context.startActivity(new Intent(context,MainActivity.class));
    }
}
