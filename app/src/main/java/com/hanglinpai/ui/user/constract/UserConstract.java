package com.hanglinpai.ui.user.constract;

import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.LoginBean;

import java.util.List;

import rx.Observable;
import www.meiyaoni.com.common.base.BaseModel;
import www.meiyaoni.com.common.base.BasePresenter;
import www.meiyaoni.com.common.base.BaseView;

/**
 * @author chihai
 * @function Created on 2017/12/20.
 * @DO 抽象化MVP 接口 类
 */

public interface UserConstract {
    interface Model extends BaseModel {
        Observable<LoginBean> login(String username,String password);
        Observable<LoginBean> reg(String phone,String code,String password);
        Observable<BaseBean> getCode(String phone);
    }
    interface View extends BaseView {
        void loginSuccess(LoginBean.ListBean bean);
        void regSuccess(LoginBean.ListBean bean);
        void getCodeSuccess();
    }
    abstract static class Presenter extends BasePresenter<View,Model> {
        public abstract void login(String username,String password);
        public abstract void reg(String phone,String code,String password);
        public abstract void getCode(String phone);
    }
}
