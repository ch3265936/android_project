package com.hanglinpai.ui.home.presenter;


import android.content.Intent;

import com.hanglinpai.api.ApiConstants;
import com.hanglinpai.ui.bean.OrderListBean;
import com.hanglinpai.ui.home.constract.OrderConstract;
import com.hanglinpai.ui.user.LoginActivity;
import com.hanglinpai.ui.user.LoginOrRegActivity;

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
    public void orderList(String pageNum, String type) {
        mRxManage.add(mModel.orderList(pageNum, type).subscribe(new RxSubscriber<OrderListBean>(mContext, false) {
            @Override
            protected void _onNext(OrderListBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        if (bean.getData() != null) {
                            if (bean.getData().getList() != null && bean.getData().getList().size() == 0) {
                                mView.returnNoData();
                            } else {
                                mView.returnOrderBeanListData(bean.getData());
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
}
