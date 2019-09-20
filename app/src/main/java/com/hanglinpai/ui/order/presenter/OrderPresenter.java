package com.hanglinpai.ui.order.presenter;


import android.content.Intent;

import com.hanglinpai.api.ApiConstants;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.OrderDetailBean;
import com.hanglinpai.ui.bean.OrderListBean;
import com.hanglinpai.ui.order.constract.OrderConstract;
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

public class OrderPresenter extends OrderConstract.Presenter {
    @Override
    public void onStart() {

    }

    @Override
    public void expertSelect(String id, String expertId, String type,String view_type,String replace_reason) {
        mRxManage.add(mModel.expertSelect(id, expertId, type, view_type,replace_reason).subscribe(new RxSubscriber<BaseBean>(mContext, true) {
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
    public void getOrderDetail(String id) {
        mRxManage.add(mModel.getOrderDetail(id).subscribe(new RxSubscriber<OrderDetailBean>(mContext, true) {
            @Override
            protected void _onNext(OrderDetailBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        if (bean.getData() != null) {
                            if (bean.getData() != null) {
                                mView.returnOrderBeanData(bean.getData());
                            } else {
                                mView.returnNoData();

                            }
                        }

                        break;
                    case ApiConstants.ERRO:
                        ToastUtils.showShotToast(bean.getMsg());
                        AppManager.getAppManager().finishAllActivity();
                        mActivity.startActivity(new Intent(mContext, LoginOrRegActivity.class));
                        break;
                    default:
                        ToastUtils.showShotToast(bean.getMsg());
                        mView.returnErrorData();
                        break;
                }
            }


            @Override
            protected void _onError(String message) {
                mView.returnErrorData();
            }
        }));
    }


    @Override
    public void expertView(String id, String type, List<String> view_time, String giveup_reason) {//确认约访
        mRxManage.add(mModel.expertView(id, type, view_time, giveup_reason).subscribe(new RxSubscriber<BaseBean>(mContext, true) {
            @Override
            protected void _onNext(BaseBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        ToastUtils.showShotToast(bean.getMsg());
                        mView.returnExpertViewSuccess();
                        break;
                    case ApiConstants.ERRO:
                        ToastUtils.showShotToast(bean.getMsg());
                        AppManager.getAppManager().finishAllActivity();
                        mActivity.startActivity(new Intent(mContext, LoginOrRegActivity.class));
                        break;
                    default:
                        ToastUtils.showShotToast(bean.getMsg());
                        mView.returnErrorData();
                        break;
                }
            }


            @Override
            protected void _onError(String message) {
                mView.returnErrorData();
            }
        }));
    }

    @Override
    public void review(String id, String review, String expectation) {
        mRxManage.add(mModel.review(id, review, expectation).subscribe(new RxSubscriber<BaseBean>(mContext, true) {
            @Override
            protected void _onNext(BaseBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        ToastUtils.showShotToast(bean.getMsg());
                        mView.returnReviewSuccess();
                        break;
                    case ApiConstants.ERRO:
                        ToastUtils.showShotToast(bean.getMsg());
                        AppManager.getAppManager().finishAllActivity();
                        mActivity.startActivity(new Intent(mContext, LoginOrRegActivity.class));
                        break;
                    default:
                        ToastUtils.showShotToast(bean.getMsg());
                        mView.returnErrorData();
                        break;
                }
            }


            @Override
            protected void _onError(String message) {
                mView.returnErrorData();
            }
        }));
    }


    @Override
    public void cancerOrder(String id,  String type,String cancel_reason, String cancel_reason_desc) {
        mRxManage.add(mModel.cancerOrder(id,type, cancel_reason, cancel_reason_desc).subscribe(new RxSubscriber<BaseBean>(mContext, true) {
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
