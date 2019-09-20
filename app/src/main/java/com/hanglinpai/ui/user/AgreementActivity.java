package com.hanglinpai.ui.user;

import android.os.Build;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.hanglinpai.R;
import com.hanglinpai.api.ApiConstants;
import com.hanglinpai.util.DensityUtil;

import butterknife.Bind;
import butterknife.OnClick;
import www.meiyaoni.com.common.base.BaseActivity;


public class AgreementActivity extends BaseActivity {
	@Bind(R.id.r_top)
	LinearLayout r_top;
	@Bind(R.id.webView)
	WebView webView;
	@Bind(R.id.title_back)
	TextView title_back;
	@Bind(R.id.title_title)
	TextView title_title;

	@Override
	public int getLayoutId() {
		return R.layout.activity_agreement_layout;
	}

	@Override
	public void initPresenter() {
	}

	@Override
	public void initView() {
		webView.loadUrl(ApiConstants.BASE_HOST+"/app/userAgreement.html");
		webView.getSettings().setJavaScriptEnabled(true);
		title_title.setText("协议");
		StatusBarTranslucent();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			r_top.setPadding(0, DensityUtil.getStatusHeight(mActivity),0,0);
		}
	}

	@OnClick({R.id.title_back})
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.title_back:
				finish();
				break;

			default:
				break;
		}

	}
}
