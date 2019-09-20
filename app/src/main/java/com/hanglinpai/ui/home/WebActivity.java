package com.hanglinpai.ui.home;

import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanglinpai.R;
import com.hanglinpai.api.ApiConstants;
import com.hanglinpai.util.DensityUtil;

import butterknife.Bind;
import butterknife.OnClick;
import www.meiyaoni.com.common.base.BaseActivity;


public class WebActivity extends BaseActivity {
    @Bind(R.id.webView)
    WebView webView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_layout;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        String url = getIntent().getStringExtra("url");
        if (url != null) {
            webView.loadUrl(ApiConstants.BASE_HOST + url);
        }
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        StatusBarTranslucent();
    }

    @Override
    protected void onDestroy() {
        webView.removeAllViews();
        webView.destroy();
        super.onDestroy();
    }
}
