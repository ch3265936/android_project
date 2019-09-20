package com.hanglinpai.ui.setting;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.hanglinpai.R;
import com.hanglinpai.app.AppApplication;
import com.hanglinpai.ui.user.AgreementActivity;
import com.hanglinpai.util.DensityUtil;
import com.hanglinpai.util.FileSizeUtil;

import java.io.File;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import www.meiyaoni.com.common.base.BaseActivity;
import www.meiyaoni.com.common.baseapp.AppManager;
import www.meiyaoni.com.common.toolUtils.SPManager;

public class AboutActivity extends BaseActivity {
    @Bind(R.id.title_title)
    TextView title_title;
    @Bind(R.id.title_back)
    TextView title_back;
    @Bind(R.id.vesion)
    TextView vesion;
    @Bind(R.id.r_agreement)
    RelativeLayout r_agreement;
    @Bind(R.id.r_top)
    RelativeLayout r_top;
    @Override
    public void initPresenter() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {
        StatusBarTranslucent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            r_top.setPadding(0, DensityUtil.getStatusHeight(mActivity),0,0);
        }
        title_title.setText(getResources().getString(R.string.about));
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        vesion.setText(String.format(Locale.US, "Android v%s", pInfo.versionName));
    }


    @OnClick({R.id.title_back,R.id.r_agreement})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.r_agreement:
                startActivity(new Intent(mActivity,AgreementActivity.class));
                break;

            default:
                break;
        }

    }

}
