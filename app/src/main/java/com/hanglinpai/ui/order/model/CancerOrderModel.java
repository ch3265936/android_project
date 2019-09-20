package com.hanglinpai.ui.order.model;

import com.hanglinpai.api.Api;
import com.hanglinpai.api.HostType;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.OrderDetailBean;
import com.hanglinpai.ui.order.constract.OrderCancerConstract;
import com.hanglinpai.ui.order.constract.OrderConstract;

import rx.Observable;
import www.meiyaoni.com.common.baserx.RxSchedulers;
import www.meiyaoni.com.common.toolUtils.SPManager;

public class CancerOrderModel implements OrderCancerConstract.Model{




    @Override
    public Observable<BaseBean> cancerOrder(String id,String type, String cancel_reason,String cancel_reason_desc) {
        return Api.getDefault(HostType.BASE_DATA_URL).cancerOrder(SPManager.getInstance().getUserToken(),id,type,cancel_reason,cancel_reason_desc)
                .compose(RxSchedulers.<BaseBean>io_main());
    }


}
