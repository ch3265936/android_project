package com.hanglinpai.ui.user.model;

import com.hanglinpai.api.Api;
import com.hanglinpai.api.HostType;
import com.hanglinpai.app.AppApplication;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.LoginBean;
import com.hanglinpai.ui.user.constract.UserConstract;

import rx.Observable;
import www.meiyaoni.com.common.baserx.RxSchedulers;

public class UserModel implements UserConstract.Model{
    @Override
    public Observable<LoginBean> login(String username, String password) {
        return Api.getDefault(HostType.BASE_DATA_URL).login("1", AppApplication.PUSHDEVICE,username,password)
                .compose(RxSchedulers.<LoginBean>io_main());
    }

    @Override
    public Observable<LoginBean> reg(String phone, String code, String password) {
        return Api.getDefault(HostType.BASE_DATA_URL).reg("1",AppApplication.PUSHDEVICE,phone,code,password)
                .compose(RxSchedulers.<LoginBean>io_main());
    }

    @Override
    public Observable<BaseBean> getCode(String phone) {
        return Api.getDefault(HostType.BASE_DATA_URL).getCode(phone)
                .compose(RxSchedulers.<BaseBean>io_main());
    }


}
