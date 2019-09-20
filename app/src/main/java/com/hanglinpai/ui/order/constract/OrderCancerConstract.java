package com.hanglinpai.ui.order.constract;

import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.OrderDetail;
import com.hanglinpai.ui.bean.OrderDetailBean;

import rx.Observable;
import www.meiyaoni.com.common.base.BaseModel;
import www.meiyaoni.com.common.base.BasePresenter;
import www.meiyaoni.com.common.base.BaseView;

/**
 * @author chihai
 * @function Created on 2017/12/20.
 * @DO 抽象化MVP 接口 类
 */

public interface OrderCancerConstract {
    interface Model extends BaseModel {
        Observable<BaseBean> cancerOrder(String id, String type,String cancel_reason,String cancel_reason_desc);



    }

    interface View extends BaseView {
        //取消订单成功
        void returnCancerSuccess();



    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void cancerOrder(String id, String type,String cancel_reason,String cancel_reason_desc);

    }
}
