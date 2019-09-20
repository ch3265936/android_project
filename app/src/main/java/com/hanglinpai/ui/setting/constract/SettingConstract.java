package com.hanglinpai.ui.setting.constract;

import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.UploadBean;


import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import www.meiyaoni.com.common.base.BaseModel;
import www.meiyaoni.com.common.base.BasePresenter;
import www.meiyaoni.com.common.base.BaseView;

/**
 * @author chihai
 * @function Created on 2017/12/20.
 * @DO 抽象化MVP 接口 类
 */

public interface SettingConstract {
    interface Model extends BaseModel {
        Observable<BaseBean> feedBacks(String content, List<String> list);
        Observable<UploadBean> upload(Map<String, RequestBody> map);
    }

    interface View extends BaseView {
        void feedBackSuccess();
        void uploadSuccess(UploadBean.ListBean bean);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void feedBacks(String content,List<String> list);
        public abstract void upload(List<String> list);
    }
}
