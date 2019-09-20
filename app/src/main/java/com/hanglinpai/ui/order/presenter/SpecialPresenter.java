package com.hanglinpai.ui.order.presenter;


import android.content.Intent;

import com.hanglinpai.api.ApiConstants;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.ExpertBean;
import com.hanglinpai.ui.bean.HistoryPassBean;
import com.hanglinpai.ui.bean.OrderDetailBean;
import com.hanglinpai.ui.bean.SpecialListBean;
import com.hanglinpai.ui.order.constract.OrderConstract;
import com.hanglinpai.ui.order.constract.SpecialConstract;
import com.hanglinpai.ui.order.model.SpecialModel;
import com.hanglinpai.ui.user.LoginActivity;
import com.hanglinpai.ui.user.LoginOrRegActivity;

import www.meiyaoni.com.common.baseapp.AppManager;
import www.meiyaoni.com.common.baserx.RxSubscriber;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

/**
 * @author chihai
 * @function Created on 2017/12/20.
 */

public class SpecialPresenter extends SpecialConstract.Presenter {
    @Override
    public void onStart() {

    }

    @Override
    public void expertSelect(String id, String expertId, String type,String view_type,  String replace_reason) {
        mRxManage.add(mModel.expertSelect(id, expertId, type,view_type, replace_reason).subscribe(new RxSubscriber<BaseBean>(mContext, true) {
            @Override
            protected void _onNext(BaseBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        mView.returnExpertSelectSuccess();
                        ToastUtils.showShotToast(bean.getMsg());
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

    @Override
    public void expertInconformity(String id, String expertId, String unsuitable_reason) {
        mRxManage.add(mModel.expertInconformity(id, expertId, unsuitable_reason).subscribe(new RxSubscriber<BaseBean>(mContext, true) {
            @Override
            protected void _onNext(BaseBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        mView.returnExpertInconformity();
                        ToastUtils.showShotToast(bean.getMsg());
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

    @Override
    public void SpecialDetail(String id) {
        mRxManage.add(mModel.SpecialDetail(id).subscribe(new RxSubscriber<ExpertBean>(mContext, true) {
            @Override
            protected void _onNext(ExpertBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        mView.returnSpecialDetailSuccess(bean.getData());
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

    @Override
    public void SpecialList(String id) {
        mRxManage.add(mModel.SpecialList(id).subscribe(new RxSubscriber<SpecialListBean>(mContext, false) {
            @Override
            protected void _onNext(SpecialListBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        mView.returnSpecialListSuccess(bean.getData());
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

    @Override
    public void HistoryPassList(String id) {
        mRxManage.add(mModel.HistoryPassList(id).subscribe(new RxSubscriber<HistoryPassBean>(mContext, false) {
            @Override
            protected void _onNext(HistoryPassBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        mView.returnHistoryPassListSuccess(bean.getData());
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
