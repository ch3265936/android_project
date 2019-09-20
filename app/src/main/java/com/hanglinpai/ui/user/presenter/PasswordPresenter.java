package com.hanglinpai.ui.user.presenter;


import android.content.Intent;

import com.hanglinpai.api.ApiConstants;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.LoginBean;
import com.hanglinpai.ui.user.LoginActivity;
import com.hanglinpai.ui.user.LoginOrRegActivity;
import com.hanglinpai.ui.user.constract.PasswordConstract;
import com.hanglinpai.ui.user.constract.UserConstract;

import www.meiyaoni.com.common.baseapp.AppManager;
import www.meiyaoni.com.common.baserx.RxSubscriber;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

/**
 * @author chihai
 * @function Created on 2017/12/20.
 */

public class PasswordPresenter extends PasswordConstract.Presenter {

    @Override
    public void resetPassword(String phone, String code, String password) {
        mRxManage.add(mModel.resetPassword(phone, code, password).subscribe(new RxSubscriber<BaseBean>(mContext, true) {
            @Override
            protected void _onNext(BaseBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        ToastUtils.showShotToast(bean.getMsg());
                        mView.resetPassword();
                        break;
                    case ApiConstants.ERRO:
                        ToastUtils.showShotToast(bean.getMsg());
                        AppManager.getAppManager().finishAllActivity();
                        mActivity.startActivity(new Intent(mContext, LoginOrRegActivity.class));
                        break;
                    default:
                        ToastUtils.showShotToast(bean.getMsg());
                        break;
                }

            }

            @Override
            protected void _onError(String message) {
                ToastUtils.showShotToast(message);
            }
        }));
    }

    @Override
    public void changePassword(String old_password, String new_password, String comfirm_password) {
        mRxManage.add(mModel.changePassword(old_password, new_password, comfirm_password).subscribe(new RxSubscriber<BaseBean>(mContext, true) {
            @Override
            protected void _onNext(BaseBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        ToastUtils.showShotToast(bean.getMsg());
                        mView.changePassword();
                        break;
                    case ApiConstants.ERRO:
                        ToastUtils.showShotToast(bean.getMsg());
                        AppManager.getAppManager().finishAllActivity();
                        mActivity.startActivity(new Intent(mContext, LoginOrRegActivity.class));

                        break;
                    default:
                        ToastUtils.showShotToast(bean.getMsg());
                        break;
                }

            }

            @Override
            protected void _onError(String message) {
                ToastUtils.showShotToast(message);
            }
        }));
    }

    @Override
    public void getCode(String phone) {
        mRxManage.add(mModel.getCode(phone).subscribe(new RxSubscriber<BaseBean>(mContext, true) {
            @Override
            protected void _onNext(BaseBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        mView.getCodeSuccess();
                        break;
                    default:
                        ToastUtils.showShotToast(bean.getMsg());
                        break;
                }

            }

            @Override
            protected void _onError(String message) {
                ToastUtils.showShotToast(message);
            }
        }));
    }
}
