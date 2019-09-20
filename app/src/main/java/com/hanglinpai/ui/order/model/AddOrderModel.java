package com.hanglinpai.ui.order.model;

import com.hanglinpai.api.Api;
import com.hanglinpai.api.HostType;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.OrderDetail;
import com.hanglinpai.ui.bean.OrderDetailBean;
import com.hanglinpai.ui.bean.OrderDetailPut;
import com.hanglinpai.ui.order.constract.AddOrderConstract;
import com.hanglinpai.ui.order.constract.OrderConstract;

import java.util.List;

import retrofit2.http.Body;
import rx.Observable;
import www.meiyaoni.com.common.baserx.RxSchedulers;
import www.meiyaoni.com.common.toolUtils.SPManager;

public class AddOrderModel implements AddOrderConstract.Model{


    @Override
    public Observable<BaseBean> addOrder(String industry_type, List<String> service_type,  List<String> service_dates, String service_time, String site, String desc) {
        return Api.getDefault(HostType.BASE_DATA_URL).addOrder(SPManager.getInstance().getUserToken(),industry_type,service_type,service_dates,service_time,site,desc)
                .compose(RxSchedulers.<BaseBean>io_main());
    }

    @Override
    public Observable<BaseBean> putOrder(String id, String industry_type, List<String> service_type, List<String> service_date, String service_time, String site, String desc) {
        OrderDetailPut put =new OrderDetailPut();
        put.id=(id);
        put.industry_type=(industry_type);
        put.service_type=(service_type);
        put.service_dates=(service_date);
        put.service_time=(service_time);
        put.site=(site);
        put.desc=(desc);
        return Api.getDefault(HostType.BASE_DATA_URL).editOrders(SPManager.getInstance().getUserToken(),id,put)
                .compose(RxSchedulers.<BaseBean>io_main());
    }

}
