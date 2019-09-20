package com.hanglinpai.ui.order.constract;

import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.Expert;
import com.hanglinpai.ui.bean.ExpertBean;
import com.hanglinpai.ui.bean.HistoryPass;
import com.hanglinpai.ui.bean.HistoryPassBean;
import com.hanglinpai.ui.bean.OrderDetailBean;
import com.hanglinpai.ui.bean.SpecialListBean;

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

public interface SpecialConstract {
    interface Model extends BaseModel {
        Observable<BaseBean> expertSelect(String id, String expertId,String type,String view_type, String replace_reason);//type 0 更换 1确定
        Observable<BaseBean> expertInconformity(String id, String expertId,  String unsuitable_reason);//不符合专家
        Observable<ExpertBean> SpecialDetail(String id);
        Observable<SpecialListBean> SpecialList(String id);
       Observable<HistoryPassBean> HistoryPassList(String id);
    }

    interface View extends BaseView {

        //操作专家成功
        void returnExpertSelectSuccess();

        //操作专家成功
        void returnExpertInconformity();
        //操作专家详情
        void returnSpecialDetailSuccess(Expert bean);
        void returnSpecialListSuccess(List<Expert> data);
        void returnHistoryPassListSuccess(List<HistoryPass> data);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void expertSelect(String id, String expertId,String type,String view_type,  String replace_reason);
        public abstract void expertInconformity(String id, String expertId, String unsuitable_reason);
        public abstract void SpecialDetail(String id);
        public abstract void  SpecialList(String id);
        public abstract void  HistoryPassList(String id);
    }
}
