package com.hanglinpai.ui.user.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hanglinpai.R;
import com.hanglinpai.ui.bean.LoginBean;
import com.hanglinpai.ui.home.HomeActivity;
import com.hanglinpai.ui.user.ResetPasswordActivity;
import com.hanglinpai.ui.user.constract.UserConstract;
import com.hanglinpai.ui.user.model.UserModel;
import com.hanglinpai.ui.user.presenter.UserPresenter;
import com.hanglinpai.util.RegexValidateUtil;

import butterknife.Bind;
import butterknife.OnClick;
import www.meiyaoni.com.common.base.BaseActivity;
import www.meiyaoni.com.common.base.BaseFragment;
import www.meiyaoni.com.common.toolUtils.SPManager;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

public class LoginFragment extends BaseFragment<UserPresenter, UserModel> implements UserConstract.View {
    @Bind(R.id.forget_password)
    TextView forget_password;
    @Bind(R.id.login)
    Button login;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.user_account)
    EditText user_account;
    @Bind(R.id.r_top)
    RelativeLayout r_top;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_login;
    }

    @Override
    public void initPresenter() {
       mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {

    }

    @OnClick({ R.id.login, R.id.forget_password})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.forget_password:
                startActivity(new Intent(getActivity(), ResetPasswordActivity.class));
                break;
            case R.id.login:
                if (!RegexValidateUtil.checkMobileNumber(user_account.getText().toString().trim())) {
                    ToastUtils.showShort(R.string.input_phone);
                    return;
                }
                if (password.getText().toString().trim().length() < 6 || password.getText().toString().trim().length() > 20) {
                    ToastUtils.showShort(R.string.input_password_620);
                    return;
                }
                mPresenter.login(user_account.getText().toString().trim(), password.getText().toString().trim());
                break;

            default:
                break;
        }

    }

    @Override
    public void loginSuccess(LoginBean.ListBean bean) {
        RegFragment.regSuccess =false;
        if (bean.getToken() != null) {
            SPManager.getInstance().setUserToken(bean.getToken());
        }
        startActivity(new Intent(getActivity(), HomeActivity.class));
        getActivity().finish();
    }

    @Override
    public void regSuccess(LoginBean.ListBean bean) {

    }

    @Override
    public void getCodeSuccess() {

    }
}
