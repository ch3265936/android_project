package com.hanglinpai.ui.order.presenter;


import android.content.Intent;

import com.hanglinpai.api.ApiConstants;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.OrderDetailBean;
import com.hanglinpai.ui.order.constract.OrderCancerConstract;
import com.hanglinpai.ui.order.constract.OrderConstract;
import com.hanglinpai.ui.user.LoginActivity;
import com.hanglinpai.ui.user.LoginOrRegActivity;

import www.meiyaoni.com.common.baseapp.AppManager;
import www.meiyaoni.com.common.baserx.RxSubscriber;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

/**
 * @author chihai
 * @function Created on 2017/12/20.
 */

public class CancerOrderPresenter extends OrderCancerConstract.Presenter {
    @Override
    public void onStart() {

    }


    @Override
    public void cancerOrder(String id, String type,String cancel_reason, String cancel_reason_desc) {
        mRxManage.add(mModel.cancerOrder(id,  type,cancel_reason, cancel_reason_desc).subscribe(new RxSubscriber<BaseBean>(mContext, true) {
            @Override
            protected void _onNext(BaseBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        ToastUtils.showShotToast(bean.getMsg());
                        mView.returnCancerSuccess();
                        break;
                    case ApiConstants.ERRO:
                        ToastUtils.showShotToast(bean.getMsg());
                        AppManager.getAppManager().finishAllActivity();
                        mActivity.startActivity(new Intent(mContext, LoginOrRegActivity.class));
                        break;
                    default:
                        ToastUtils.showShotToast(bean.getMsg());
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
