package com.hanglinpai.ui.setting.model;

import com.google.gson.Gson;
import com.hanglinpai.api.Api;
import com.hanglinpai.api.HostType;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.UploadBean;
import com.hanglinpai.ui.setting.constract.SettingConstract;

import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import www.meiyaoni.com.common.baserx.RxSchedulers;
import www.meiyaoni.com.common.toolUtils.SPManager;

public class SettingModel implements SettingConstract.Model{

    @Override
    public Observable<BaseBean> feedBacks(String content, List<String> list) {

        return Api.getDefault(HostType.BASE_DATA_URL).feedBacks(SPManager.getInstance().getUserToken(),content,list)
                .compose(RxSchedulers.<BaseBean>io_main());
    }
    @Override
    public Observable<UploadBean> upload(Map<String, RequestBody> map) {
        return Api.getDefault(HostType.BASE_DATA_URL).upload(map)
                .compose(RxSchedulers.<UploadBean>io_main());
    }
}
