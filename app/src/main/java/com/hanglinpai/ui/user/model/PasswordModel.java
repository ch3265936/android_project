package com.hanglinpai.ui.user.model;

import com.hanglinpai.api.Api;
import com.hanglinpai.api.HostType;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.LoginBean;
import com.hanglinpai.ui.user.constract.PasswordConstract;
import com.hanglinpai.ui.user.constract.UserConstract;

import rx.Observable;
import www.meiyaoni.com.common.baserx.RxSchedulers;
import www.meiyaoni.com.common.toolUtils.SPManager;

public class PasswordModel implements PasswordConstract.Model{

    @Override
    public Observable<BaseBean> resetPassword(String phone, String password, String code) {
        return Api.getDefault(HostType.BASE_DATA_URL).forgetPassword(phone,password,code)
                .compose(RxSchedulers.<BaseBean>io_main());
    }

    @Override
    public Observable<BaseBean> changePassword(String old_password, String new_password, String comfirm_password) {
        return Api.getDefault(HostType.BASE_DATA_URL).changePassword(SPManager.getInstance().getUserToken(),old_password,new_password,comfirm_password)
                .compose(RxSchedulers.<BaseBean>io_main());
    }
    @Override
    public Observable<BaseBean> getCode(String phone) {
        return Api.getDefault(HostType.BASE_DATA_URL).getCode(phone)
                .compose(RxSchedulers.<BaseBean>io_main());
    }

}
