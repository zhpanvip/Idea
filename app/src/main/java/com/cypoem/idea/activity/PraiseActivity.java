package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.cypoem.idea.R;
import butterknife.BindView;
import butterknife.OnClick;

public class PraiseActivity extends BaseActivity {

    @BindView(R.id.iv_head1)
    ImageView mIvHead1;
    @BindView(R.id.iv_head2)
    ImageView mIvHead2;
    @BindView(R.id.tv_wallet)
    TextView mTvWallet;
    @BindView(R.id.lv_like_details)
    ListView mLvLikeDetails;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_praise;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, PraiseActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.iv_head1, R.id.iv_head2, R.id.tv_wallet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head1:
                break;
            case R.id.iv_head2:
                break;
            case R.id.tv_wallet:
                WalletActivity.start(this);
                break;
        }
    }
}
