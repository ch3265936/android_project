package com.hanglinpai.ui.home.constract;

import com.hanglinpai.ui.bean.OrderListBean;

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
        Observable<OrderListBean> orderList(String pageNum,String type);
    }
    interface View extends BaseView {
        void returnOrderBeanListData(OrderListBean.ListBean bean);
        //无数据显示
        void returnNoData();
        //网络访问错误
        void returnErrorData();
    }
    abstract static class Presenter extends BasePresenter<View,Model> {
        public abstract void orderList(String pageNum,String type);
    }
}
