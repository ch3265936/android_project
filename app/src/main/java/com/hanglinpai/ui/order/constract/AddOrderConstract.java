package com.hanglinpai.ui.order.constract;

import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.OrderDetail;
import com.hanglinpai.ui.bean.OrderDetailBean;

import java.util.List;

import rx.Observable;
import www.meiyaoni.com.common.base.BaseModel;
import www.meiyaoni.com.common.base.BasePresenter;
import www.meiyaoni.com.common.base.BaseView;

/**
 * @author chihai
 * @function Created on 2017/12/20.
 * @DO 抽象化MVP 接口 类
 */

public interface AddOrderConstract {
    interface Model extends BaseModel {

        Observable<BaseBean> addOrder(String industry_type, List<String> service_type, List<String> service_dates, String service_time, String site, String desc);

        Observable<BaseBean> putOrder(String id, String industry_type, List<String> service_type ,List<String> service_date, String service_time, String site, String desc);

    }

    interface View extends BaseView {


        //修改订单成功
        void returnPutSuccess();
        //新增订单成功
        void returnAddSuccess();


    }

    abstract static class Presenter extends BasePresenter<View, Model> {

        public abstract void addOrder(String industry_type, List<String> service_type,  List<String> service_dates, String service_time, String site, String desc);

        public abstract void putOrder(String id, String industry_type, List<String> service_type,  List<String> service_date, String service_time, String site, String desc);


    }
}
