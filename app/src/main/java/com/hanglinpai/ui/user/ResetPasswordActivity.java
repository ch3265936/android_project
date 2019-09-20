package com.hanglinpai.ui.user;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import www.meiyaoni.com.common.toolUtils.ToastUtils;

public class ResetPasswordActivity extends BaseActivity<PasswordPresenter, PasswordModel> implements PasswordConstract.View {

    @Bind(R.id.title_back)
    TextView title_back;
    @Bind(R.id.title_title)
    TextView title_title;
    @Bind(R.id.reset_password)
    Button reset_password;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.user_code)
    EditText user_code;
    @Bind(R.id.get_code)
    TextView get_code;
    @Bind(R.id.r_top)
    LinearLayout r_top;
    @Bind(R.id.user_account)
    EditText user_account;
    private static final int INIT_TIME = 60;
    private static final int TASK_CODE_TIME = 0;
    private int time = INIT_TIME;
    boolean mIsSend = false;//短信正发送中

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        title_title.setText(R.string.reset_password);
        StatusBarTranslucent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            r_top.setPadding(0, DensityUtil.getStatusHeight(mActivity),0,0);
        }
    }

    @OnClick({R.id.reset_password, R.id.get_code,R.id.title_back})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.reset_password:
                if (!RegexValidateUtil.checkMobileNumber(user_account.getText().toString().trim())) {
                    ToastUtils.showShort(R.string.input_phone);
                    return;
                }
                if (password.getText().toString().trim().length() < 6 || password.getText().toString().trim().length() > 20) {
                    ToastUtils.showShort(R.string.input_password_620);
                    return;
                }
                if (user_code.getText().toString().trim().length() != 6) {
                    ToastUtils.showShort(R.string.input_code6);
                    return;
                }
                mPresenter.resetPassword(user_account.getText().toString().trim(), user_code.getText().toString().trim(),password.getText().toString().trim());
                break;
            case R.id.get_code:
                if (!RegexValidateUtil.checkMobileNumber(user_account.getText().toString().trim())) {
                    ToastUtils.showShort(R.string.input_phone);
                    return;
                }
                mPresenter.getCode(user_account.getText().toString().trim());
                break;
            default:
                break;
        }

    }

    /*
       定时器
        */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TASK_CODE_TIME:
                    //更新UI
                    get_code.setText("倒计时" + time + "s");
                    break;
            }
        }
    };

    private void startTask() {
        mHandler.postDelayed(r, 1000);
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            try {
                time--;
                if (time < 0) {
                    mIsSend = false;
                    get_code.setEnabled(true);
                    get_code.setText("发送验证码");
                    time = 60;
                    mHandler.removeMessages(TASK_CODE_TIME);
                    mHandler.removeCallbacks(r);
                } else {
                    mHandler.postDelayed(r, 1000);
                    mHandler.sendEmptyMessage(TASK_CODE_TIME);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }


    @Override
    public void resetPassword() {
        finish();
    }

    @Override
    public void changePassword() {

    }

    @Override
    public void getCodeSuccess() {
        get_code.setEnabled(false);
        get_code.setText("倒计时" + time + "s");
        mIsSend = true;
        //开启计时
        startTask();
    }
}
