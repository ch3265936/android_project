package com.hanglinpai.ui.user.presenter;


import com.hanglinpai.api.ApiConstants;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.LoginBean;
import com.hanglinpai.ui.user.constract.UserConstract;
import www.meiyaoni.com.common.baserx.RxSubscriber;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

/**
 * @author chihai
 * @function Created on 2017/12/20.
 */

public class UserPresenter extends UserConstract.Presenter{
    @Override
    public void login(String username, String password) {
        mRxManage.add(mModel.login(username,password).subscribe(new RxSubscriber<LoginBean>(mContext,true) {
            @Override
            protected void _onNext(LoginBean loginBean) {
                    switch (loginBean.getCode()){
                        case ApiConstants.SUCCESS:
                            ToastUtils.showShotToast(loginBean.getMsg());
                            mView.loginSuccess(loginBean.getList());
                            break;
                        default:
                            ToastUtils.showShotToast(loginBean.getMsg());
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
    public void reg(String phone, String code, String password) {
        mRxManage.add(mModel.reg(phone,code,password).subscribe(new RxSubscriber<LoginBean>(mContext,true) {
            @Override
            protected void _onNext(LoginBean bean) {
                switch (bean.getCode()){
                    case ApiConstants.SUCCESS:
                        mView.regSuccess(bean.getList());
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
        mRxManage.add(mModel.getCode(phone).subscribe(new RxSubscriber<BaseBean>(mContext,true) {
            @Override
            protected void _onNext(BaseBean bean) {
                switch (bean.getCode()){
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
