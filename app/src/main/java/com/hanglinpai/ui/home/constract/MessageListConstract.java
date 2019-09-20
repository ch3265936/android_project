package com.hanglinpai.ui.home.constract;

import com.hanglinpai.ui.bean.MessageListBean;
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

public interface MessageListConstract {
    interface Model extends BaseModel {
        Observable<MessageListBean> messageList(String pageNum);
    }
    interface View extends BaseView {
        void returnMessageBeanListData(MessageListBean.ListBean bean);
        //无数据显示
        void returnNoData();
        //网络访问错误
        void returnErrorData();
    }
    abstract static class Presenter extends BasePresenter<View,Model> {
        public abstract void messageList(String pageNum);
    }
}
