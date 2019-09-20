package com.hanglinpai.ui.order.model;

import com.hanglinpai.api.Api;
import com.hanglinpai.api.HostType;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.OrderDetailBean;
import com.hanglinpai.ui.order.constract.OrderConstract;

import java.util.List;

import rx.Observable;
import www.meiyaoni.com.common.baserx.RxSchedulers;
import www.meiyaoni.com.common.toolUtils.SPManager;

public class OrderModel implements OrderConstract.Model{

    @Override
    public Observable<OrderDetailBean> getOrderDetail(String id) {
        return Api.getDefault(HostType.BASE_DATA_URL).getOrderDetail(SPManager.getInstance().getUserToken(),id,"status_name,industry_type_name,service_type_name,service_time_name,expert_name,expert_rmd,view_type_name,view_start_time_str,view_end_time_str,order_progress,service_start_time,service_end_time,add_time_str,cancel_reason_name,status_desc,status_desc_add,consumer_hotline,add_time_str_list,expert_avatar,order_unread_msg,service_dates_str,expert_service_time,view_time_str,history_rmd_count")
                .compose(RxSchedulers.<OrderDetailBean>io_main());
    }



    @Override
    public Observable<BaseBean> expertView(String id, String type, List<String> view_time,String giveup_reason) {
        return Api.getDefault(HostType.BASE_DATA_URL).expertView(SPManager.getInstance().getUserToken(),id,type,view_time,giveup_reason)
                .compose(RxSchedulers.<BaseBean>io_main());
    }

    @Override
    public Observable<BaseBean> review(String id, String review, String expectation) {
        return Api.getDefault(HostType.BASE_DATA_URL).evaluate(SPManager.getInstance().getUserToken(),id,review,expectation)
                .compose(RxSchedulers.<BaseBean>io_main());
    }



    @Override
    public Observable<BaseBean> expertSelect(String id, String expertId, String type, String view_type,String replace_reason) {
        return Api.getDefault(HostType.BASE_DATA_URL).expert(SPManager.getInstance().getUserToken(),id,expertId,type,view_type,replace_reason).compose(RxSchedulers.<BaseBean>io_main());
    }


    @Override
    public Observable<BaseBean> cancerOrder(String id,  String type,String cancel_reason,String cancel_reason_desc) {
        return Api.getDefault(HostType.BASE_DATA_URL).cancerOrder(SPManager.getInstance().getUserToken(),id,type,cancel_reason,cancel_reason_desc)
                .compose(RxSchedulers.<BaseBean>io_main());
    }
}
