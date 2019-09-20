package com.hanglinpai.ui.order.presenter;


import android.content.Intent;

import com.hanglinpai.api.ApiConstants;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.order.constract.AddOrderConstract;
import com.hanglinpai.ui.user.LoginActivity;
import com.hanglinpai.ui.user.LoginOrRegActivity;

import java.util.List;

import www.meiyaoni.com.common.baseapp.AppManager;
import www.meiyaoni.com.common.baserx.RxSubscriber;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

/**
 * @author chihai
 * @function Created on 2017/12/20.
 */

public class AddOrderPresenter extends AddOrderConstract.Presenter {
    @Override
    public void onStart() {

    }

    @Override
    public void addOrder(String industry_type, List<String> service_type, List<String> service_dates, String service_time, String site, String desc) {
        mRxManage.add(mModel.addOrder(industry_type, service_type, service_dates, service_time, site, desc).subscribe(new RxSubscriber<BaseBean>(mContext, true) {
            @Override
            protected void _onNext(BaseBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        ToastUtils.showShotToast(bean.getMsg());
                        mView.returnAddSuccess();
                        break;
                    case ApiConstants.ERRO:
                        ToastUtils.showShotToast(bean.getMsg());
                        AppManager.getAppManager().finishAllActivity();
                        mActivity.startActivity(new Intent(mContext, LoginOrRegActivity.class));
                        break;
                    default:
                        ToastUtils.showShotToast(bean.getMsg());
                        mView.returnAddSuccess();
                        break;
                }
            }


            @Override
            protected void _onError(String message) {
                ToastUtils.showShotToast(message);
            }
        }));
    }

    @Override
    public void putOrder(String id, String industry_type, List<String> service_type, List<String> service_date, String service_time, String site, String desc) {
        mRxManage.add(mModel.putOrder(id, industry_type, service_type, service_date, service_time, site, desc).subscribe(new RxSubscriber<BaseBean>(mContext, true) {
            @Override
            protected void _onNext(BaseBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        ToastUtils.showShotToast(bean.getMsg());
                        mView.returnPutSuccess();
                        break;
                    case ApiConstants.ERRO:
                        ToastUtils.showShotToast(bean.getMsg());
                        AppManager.getAppManager().finishAllActivity();
                        mActivity.startActivity(new Intent(mContext, LoginOrRegActivity.class));
                        break;
                    default:
                        ToastUtils.showShotToast(bean.getMsg());
                        mView.returnPutSuccess();
                        break;
                }
            }


            @Override
            protected void _onError(String message) {
                ToastUtils.showShotToast(message);
            }
        }));
    }


}
