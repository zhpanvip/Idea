package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cypoem.idea.R;

public class WalletActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    public static void start(Context context){
        Intent intent=new Intent(context,WalletActivity.class);
        context.startActivity(intent);
    }
}
