package com.hanglinpai.ui.order.constract;

import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.OrderDetailBean;
import com.hanglinpai.ui.bean.OrderDetail;

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

public interface OrderConstract {
    interface Model extends BaseModel {
        Observable<OrderDetailBean> getOrderDetail(String id);
        Observable<BaseBean> expertView(String id, String type, List<String> view_time, String giveup_reason);//type 0更换 1确定
        Observable<BaseBean> review(String id, String review, String expectation);
        Observable<BaseBean> expertSelect(String id, String expertId, String type, String view_type,String replace_reason);//type 0 更换 1确定
        Observable<BaseBean> cancerOrder(String id,  String type,String cancel_reason,String cancel_reason_desc);

    }

    interface View extends BaseView {
        void returnOrderBeanData(OrderDetail bean);
        //无数据显示
        void returnNoData();
        //网络访问错误
        void returnErrorData();
        //操作专家成功
        void returnExpertSelectSuccess();
        //操作约访成功
        void returnExpertViewSuccess();
        //评价成功
        void returnReviewSuccess();
        //撤销取消订单成功
        void returnCancerSuccess();

    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getOrderDetail(String id);

        public abstract void expertView(String id,String type, List<String> view_time,String giveup_reason);

        public abstract void review(String id, String review, String expectation);
        public abstract void expertSelect(String id, String expertId, String type, String view_type,String replace_reason);
        public abstract void cancerOrder(String id, String type,String cancel_reason,String cancel_reason_desc);
    }
}
