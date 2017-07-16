package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import com.airong.core.utils.Utils;
import com.cypoem.idea.R;
import butterknife.BindView;

/**
 * 通用 WebView 考虑以后所有展示 WebView 的界面都从这个类继承
 */
public class BasicWebViewActivity extends BaseActivity {

    protected static final String INTENT_KEY_TITLE = "title";

    protected static final String INTENT_KEY_URL = "url";

    WebView mWebView;

    String mTitle;

    String mUrl;
    @BindView(R.id.containerLayout)
    LinearLayout mContainerLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_basic_webview;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
        initWebView();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mTitle = intent.getStringExtra(INTENT_KEY_TITLE);
            mUrl = intent.getStringExtra(INTENT_KEY_URL);
            setToolBarTitle(mTitle);
        }
    }

    private void initWebView() {
        mWebView = new WebView(Utils.getContext());
        mWebView.setWebViewClient(new Client());
        mWebView.setBackgroundColor(getResources().getColor(R.color.background));
        WebSettings setting = mWebView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setDisplayZoomControls(false);
        setting.setSupportZoom(false);
        setting.setBuiltInZoomControls(false);
        //setting.setSavePassword(false); // 安全要求

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContainerLayout.addView(mWebView, layoutParams);

        mWebView.loadUrl(mUrl);
    }

    public static void start(@NonNull Context context, @NonNull String title, @NonNull String url) {
        Intent intent = new Intent(context, BasicWebViewActivity.class);
        intent.putExtra(INTENT_KEY_TITLE, title);
        intent.putExtra(INTENT_KEY_URL, url);
        context.startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mContainerLayout.removeAllViews();
            mWebView.destroy();
        }
        mWebView = null;
    }

    public class Client extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
