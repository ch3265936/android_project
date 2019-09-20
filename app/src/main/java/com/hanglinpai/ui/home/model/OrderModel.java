package com.hanglinpai.ui.home.model;

import com.hanglinpai.api.Api;
import com.hanglinpai.api.HostType;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.OrderListBean;
import com.hanglinpai.ui.home.constract.OrderConstract;

import rx.Observable;
import www.meiyaoni.com.common.baserx.RxSchedulers;
import www.meiyaoni.com.common.toolUtils.SPManager;

public class OrderModel implements OrderConstract.Model{
    @Override
    public Observable<OrderListBean> orderList( String pageNum,String type) {
        return Api.getDefault(HostType.BASE_DATA_URL).getOrderList(SPManager.getInstance().getUserToken(),"status_name,industry_type_name,service_type_name,expert_name,view_start_time_str,view_end_time_str,add_time_str_list,order_unread_msg,status_name_remark,view_time_str,view_type_name",pageNum,"10",type)
                .compose(RxSchedulers.<OrderListBean>io_main());
    }


}
