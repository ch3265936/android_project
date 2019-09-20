package com.hanglinpai.ui.user.constract;

import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.LoginBean;
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

public interface UserInfoConstract {
    interface Model extends BaseModel {
        Observable<UploadBean> upload(Map<String, RequestBody> map);
        Observable<BaseBean> editUserInfo(String avatar,int status);
        Observable<LoginBean> getUser();
    }
    interface View extends BaseView {
        void uploadSuccess(UploadBean.ListBean bean);
        void editUserInfo();
        void getUser(LoginBean.ListBean listBean);
    }
    abstract static class Presenter extends BasePresenter<View,Model> {
        public abstract void editUserInfo(String avatar,int status);
        public abstract void upload(List<String> list);
        public abstract void getUser(boolean loding);
    }
}
