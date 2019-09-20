package com.hanglinpai.ui.setting;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;


import com.hanglinpai.R;
import com.hanglinpai.app.AppApplication;
import com.hanglinpai.ui.home.HomeActivity;
import com.hanglinpai.ui.user.ChangePasswordActivity;
import com.hanglinpai.ui.user.LoginActivity;
import com.hanglinpai.ui.user.LoginOrRegActivity;
import com.hanglinpai.util.DensityUtil;
import com.hanglinpai.util.FileSizeUtil;

import java.io.File;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import www.meiyaoni.com.common.base.BaseActivity;
import www.meiyaoni.com.common.baseapp.AppManager;
import www.meiyaoni.com.common.toolUtils.SPManager;

public class SettingActivity extends BaseActivity {
    @Bind(R.id.title_title)
    TextView title_title;
    @Bind(R.id.title_back)
    TextView title_back;
    @Bind(R.id.push_switch)
    Switch push_switch;
    @Bind(R.id.size)
    TextView size;
    @Bind(R.id.r_clear)
    RelativeLayout r_clear;
    @Bind(R.id.r_top)
    LinearLayout r_top;
    AppApplication app = AppApplication.getInstance();

    @Override
    public void initPresenter() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        title_title.setText(getResources().getString(R.string.my_setting));
        StatusBarTranslucent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            r_top.setPadding(0,DensityUtil.getStatusHeight(mActivity),0,0);
        }
        if (FileSizeUtil.getAutoFileOrFilesSize(app.filePath) != null && FileSizeUtil.getAutoFileOrFilesSize(app.filePath).length() > 0)
            size.setText(FileSizeUtil.getAutoFileOrFilesSize(app.filePath));
//        push_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    SPManager.getInstance().putBoolean("push", true);
//                } else {
//                    SPManager.getInstance().putBoolean("push", false);
//                }
//            }
//        });
    }


    @OnClick({R.id.title_back, R.id.r_clear, R.id.login_out, R.id.r_password})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.login_out:
//                SPManager.getInstance().putString("uid", "");
                SPManager.getInstance().setUserToken( "");
                AppManager.getAppManager().finishAllActivity();
                startActivity(new Intent(mActivity, LoginOrRegActivity.class));
                break;
            case R.id.title_back:
                finish();
                break;
            case R.id.r_password:
                startActivity(new Intent(mActivity, ChangePasswordActivity.class));
                break;
            case R.id.r_clear:
                try {
                    File dir1 = new File(app.filePath);
                    deleteFile(dir1);

                } catch (Exception e) {

                }
                break;
            default:
                break;
        }

    }

    //flie：要删除的文件夹的所在位置
    private void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                f.delete();
            }

            //如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {

        }
        size.setText("0K");
    }
}
