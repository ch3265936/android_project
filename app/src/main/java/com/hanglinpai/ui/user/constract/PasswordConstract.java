package com.hanglinpai.ui.user.constract;

import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.LoginBean;

import rx.Observable;
import www.meiyaoni.com.common.base.BaseModel;
import www.meiyaoni.com.common.base.BasePresenter;
import www.meiyaoni.com.common.base.BaseView;

/**
 * @author chihai
 * @function Created on 2017/12/20.
 * @DO 抽象化MVP 接口 类
 */

public interface PasswordConstract {
    interface Model extends BaseModel {
        Observable<BaseBean> resetPassword(String phone,String code, String password);
        Observable<BaseBean> changePassword(String old_password, String new_password, String comfirm_password);
        Observable<BaseBean> getCode(String phone);
    }
    interface View extends BaseView {
        void resetPassword();
        void changePassword();
        void getCodeSuccess();
    }
    abstract static class Presenter extends BasePresenter<View,Model> {
        public abstract void resetPassword(String phone, String code,String password);
        public abstract void changePassword(String old_password, String new_password, String comfirm_password);
        public abstract void getCode(String phone);
    }
}
