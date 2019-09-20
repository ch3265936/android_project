package com.hanglinpai.ui.user.model;

import com.hanglinpai.api.Api;
import com.hanglinpai.api.HostType;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.UploadBean;
import com.hanglinpai.ui.user.constract.AttestaionConstract;
import com.hanglinpai.ui.user.constract.PasswordConstract;

import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import www.meiyaoni.com.common.baserx.RxSchedulers;
import www.meiyaoni.com.common.toolUtils.SPManager;

public class AttestaionModel implements AttestaionConstract.Model{



    @Override
    public Observable<BaseBean> Attestaion(String avl,String name, int sex, String phone, String company_name, String position_name, String email,  String business_card) {
       String token =SPManager.getInstance().getUserToken();
        return Api.getDefault(HostType.BASE_DATA_URL).verified(token,avl,name,sex,phone,company_name,position_name,email,business_card)
                .compose(RxSchedulers.<BaseBean>io_main());
    }

    @Override
    public Observable<UploadBean> upload(Map<String, RequestBody> map) {
        return Api.getDefault(HostType.BASE_DATA_URL).upload(map)
                .compose(RxSchedulers.<UploadBean>io_main());
    }
}
