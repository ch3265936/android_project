package com.hanglinpai.ui.user;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hanglinpai.R;
import com.hanglinpai.ui.IntroActivity;
import com.hanglinpai.ui.bean.LoginBean;
import com.hanglinpai.ui.home.HomeActivity;
import com.hanglinpai.ui.user.constract.UserConstract;
import com.hanglinpai.ui.user.model.UserModel;
import com.hanglinpai.ui.user.presenter.UserPresenter;
import com.hanglinpai.util.DensityUtil;
import com.hanglinpai.util.RegexValidateUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import www.meiyaoni.com.common.base.BaseActivity;
import www.meiyaoni.com.common.toolUtils.SPManager;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

public class RegActivity extends BaseActivity<UserPresenter, UserModel> implements UserConstract.View {
    @Bind(R.id.reg_tip_bottom)
    TextView reg_tip_bottom;
    @Bind(R.id.to_login)
    TextView to_login;
    @Bind(R.id.check)
    CheckBox check;
    @Bind(R.id.reg)
    Button reg;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.user_code)
    EditText user_code;
    @Bind(R.id.get_code)
    TextView get_code;
    @Bind(R.id.r_top)
    RelativeLayout r_top;
    @Bind(R.id.user_account)
    EditText user_account;
    private static final int INIT_TIME = 60;
    private static final int TASK_CODE_TIME = 0;
    private int time = INIT_TIME;
    boolean mIsSend = false;//短信正发送中
    public static boolean regSuccess = false;
    private boolean checked = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_reg;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        StatusBarTranslucent();
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    checked = true;
                } else {
                    checked = false;
                }
            }
        });
    }

    @OnClick({R.id.to_login, R.id.reg, R.id.get_code,R.id.reg_tip_bottom})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.reg_tip_bottom:
                startActivity(new Intent(mActivity,AgreementActivity.class));
                break;
            case R.id.to_login:
                startActivity(new Intent(mActivity, LoginActivity.class));
                finish();
                break;
            case R.id.reg:
                if (!checked) {
                    ToastUtils.showShort(R.string.reg_check);
                    return;
                }
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
                mPresenter.reg(user_account.getText().toString().trim(), user_code.getText().toString().trim(), password.getText().toString().trim());
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
    public void loginSuccess(LoginBean.ListBean bean) {

    }

    @Override
    public void regSuccess(LoginBean.ListBean bean) {
        if (bean.getToken() != null) {
            SPManager.getInstance().setUserToken(bean.getToken());
            regSuccess = true;
            Intent i = new Intent(mActivity, AttestationActivity.class);
            ToastUtils.showShort(R.string.reg_success);
            i.putExtra("phone", user_account.getText().toString().trim());
            startActivity(i);
            finish();

        }

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
