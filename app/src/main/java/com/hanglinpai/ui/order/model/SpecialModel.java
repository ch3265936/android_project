package com.hanglinpai.ui.order.model;

import com.hanglinpai.api.Api;
import com.hanglinpai.api.HostType;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.ExpertBean;
import com.hanglinpai.ui.bean.HistoryPassBean;
import com.hanglinpai.ui.bean.OrderDetailBean;
import com.hanglinpai.ui.bean.SpecialListBean;
import com.hanglinpai.ui.order.constract.OrderConstract;
import com.hanglinpai.ui.order.constract.SpecialConstract;

import rx.Observable;
import www.meiyaoni.com.common.baserx.RxSchedulers;
import www.meiyaoni.com.common.toolUtils.SPManager;

public class SpecialModel implements SpecialConstract.Model{


    @Override
    public Observable<BaseBean> expertSelect(String id, String expertId,String type, String view_type,String replace_reason) {
        return Api.getDefault(HostType.BASE_DATA_URL).expert(SPManager.getInstance().getUserToken(),id,expertId,type,view_type,replace_reason).compose(RxSchedulers.<BaseBean>io_main());

    }

    @Override
    public Observable<BaseBean> expertInconformity(String id, String expertId, String unsuitable_reason) {
        return Api.getDefault(HostType.BASE_DATA_URL).inconformity(SPManager.getInstance().getUserToken(),id,expertId,unsuitable_reason).compose(RxSchedulers.<BaseBean>io_main());

    }

    @Override
    public Observable<ExpertBean> SpecialDetail(String id) {
        return Api.getDefault(HostType.BASE_DATA_URL).expertsDetail(SPManager.getInstance().getUserToken(),id,"share_url,down_url,expert_no").compose(RxSchedulers.<ExpertBean>io_main());

    }

    @Override
    public Observable<SpecialListBean> SpecialList(String id) {
        return Api.getDefault(HostType.BASE_DATA_URL).expertsList(SPManager.getInstance().getUserToken(),id).compose(RxSchedulers.<SpecialListBean>io_main());

    }

    @Override
    public Observable<HistoryPassBean> HistoryPassList(String id) {
        return Api.getDefault(HostType.BASE_DATA_URL).   expertHistorysList(SPManager.getInstance().getUserToken(),id).compose(RxSchedulers.<HistoryPassBean>io_main());
    }
}
