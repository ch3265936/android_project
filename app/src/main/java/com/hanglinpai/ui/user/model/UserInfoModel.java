package com.hanglinpai.ui.user.model;

import com.hanglinpai.api.Api;
import com.hanglinpai.api.HostType;
import com.hanglinpai.app.AppApplication;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.LoginBean;
import com.hanglinpai.ui.bean.UploadBean;
import com.hanglinpai.ui.user.constract.UserInfoConstract;


import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import www.meiyaoni.com.common.baserx.RxSchedulers;
import www.meiyaoni.com.common.toolUtils.SPManager;

public class UserInfoModel implements UserInfoConstract.Model {
    @Override
    public Observable<UploadBean> upload(Map<String, RequestBody> map) {
        return Api.getDefault(HostType.BASE_DATA_URL).upload(map)
                .compose(RxSchedulers.<UploadBean>io_main());
    }

    @Override
    public Observable<BaseBean> editUserInfo(String avatar, int status) {
        if (avatar.length() > 0) {
            return Api.getDefault(HostType.BASE_DATA_URL).edit(SPManager.getInstance().getUserToken(), avatar)
                    .compose(RxSchedulers.<BaseBean>io_main());
        }
        if (status == 3) {
            return Api.getDefault(HostType.BASE_DATA_URL).edit(SPManager.getInstance().getUserToken(), status)
                    .compose(RxSchedulers.<BaseBean>io_main());
        }
        return null;
    }

    @Override
    public Observable<LoginBean> getUser() {
        return Api.getDefault(HostType.BASE_DATA_URL).getUser(SPManager.getInstance().getUserToken(), AppApplication.PUSHDEVICE)
                .compose(RxSchedulers.<LoginBean>io_main());
    }
}
