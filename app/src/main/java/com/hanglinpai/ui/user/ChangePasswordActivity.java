package com.hanglinpai.ui.user;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hanglinpai.R;
import com.hanglinpai.ui.user.constract.PasswordConstract;
import com.hanglinpai.ui.user.model.PasswordModel;
import com.hanglinpai.ui.user.presenter.PasswordPresenter;
import com.hanglinpai.util.DensityUtil;
import com.hanglinpai.util.RegexValidateUtil;

import butterknife.Bind;
import butterknife.OnClick;
import www.meiyaoni.com.common.base.BaseActivity;
import www.meiyaoni.com.common.baseapp.AppManager;
import www.meiyaoni.com.common.toolUtils.SPManager;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

public class ChangePasswordActivity extends BaseActivity<PasswordPresenter, PasswordModel> implements PasswordConstract.View {
    @Bind(R.id.r_top)
    LinearLayout r_top;
    @Bind(R.id.old_password)
    EditText old_password;
    @Bind(R.id.new_password)
    EditText new_password;
    @Bind(R.id.comfirm_password)
    EditText comfirm_password;
    @Bind(R.id.complete)
    Button complete;
    @Bind(R.id.title_back)
    TextView title_back;
    @Bind(R.id.title_title)
    TextView title_title;


    @Override
    public int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        title_title.setText(R.string.change_password);
        StatusBarTranslucent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            r_top.setPadding(0, DensityUtil.getStatusHeight(mActivity),0,0);
        }
    }

    @OnClick({R.id.complete,R.id.title_back})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.complete:
                if (old_password.getText().toString().trim().length() < 6 || old_password.getText().toString().trim().length() > 20) {
                    ToastUtils.showShort(R.string.input_old_password);
                    return;
                }
                if (new_password.getText().toString().trim().length() < 6 || new_password.getText().toString().trim().length() > 20) {
                    ToastUtils.showShort(R.string.input_new_password);
                    return;
                }
                if (comfirm_password.getText().toString().trim().length() < 6 || comfirm_password.getText().toString().trim().length() > 20) {
                    ToastUtils.showShort(R.string.input_new_password2);
                    return;
                }
                if(!new_password.getText().toString().trim().equals(comfirm_password.getText().toString().trim())){
                    ToastUtils.showShort(R.string.password_tip);
                    return;
                }
                mPresenter.changePassword(old_password.getText().toString().trim(), new_password.getText().toString().trim(), comfirm_password.getText().toString().trim());
                break;

            default:
                break;
        }

    }





    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void resetPassword() {

    }

    @Override
    public void changePassword() {
        SPManager.getInstance().setUserToken( "");
        AppManager.getAppManager().finishAllActivity();
        startActivity(new Intent(mActivity, LoginActivity.class));
    }

    @Override
    public void getCodeSuccess() {

    }
}
