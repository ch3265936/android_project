package com.hanglinpai.ui.order.presenter;


import android.content.Intent;

import com.hanglinpai.api.ApiConstants;
import com.hanglinpai.ui.bean.AddChatBean;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.ChatListBean;
import com.hanglinpai.ui.order.constract.ChatConstract;
import com.hanglinpai.ui.order.constract.OrderCancerConstract;
import com.hanglinpai.ui.user.LoginActivity;
import com.hanglinpai.ui.user.LoginOrRegActivity;

import www.meiyaoni.com.common.baseapp.AppManager;
import www.meiyaoni.com.common.baserx.RxSubscriber;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

/**
 * @author chihai
 * @function Created on 2017/12/20.
 */

public class ChatPresenter extends ChatConstract.Presenter {
    @Override
    public void onStart() {

    }


    @Override
    public void addChat(String order_id, String chat_id, String content) {
        mRxManage.add(mModel.addChat(order_id, chat_id, content).subscribe(new RxSubscriber<ChatListBean>(mContext, true) {
            @Override
            protected void _onNext(ChatListBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        mView.returnAddChatSuccess(bean.getData());
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
    public void ChatList(String order_id, String type, String chat_id) {
        mRxManage.add(mModel.ChatList(order_id, type, chat_id).subscribe(new RxSubscriber<ChatListBean>(mContext, false) {
            @Override
            protected void _onNext(ChatListBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        mView.returnChatListSuccess(bean.getData());
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
    public void ChatListOld(String order_id, String type, String chat_id) {
        mRxManage.add(mModel.ChatList(order_id, type, chat_id).subscribe(new RxSubscriber<ChatListBean>(mContext, false) {
            @Override
            protected void _onNext(ChatListBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        mView.returnChatOldListSuccess(bean.getData());
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
