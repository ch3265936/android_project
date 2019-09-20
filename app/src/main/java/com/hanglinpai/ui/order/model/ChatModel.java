package com.hanglinpai.ui.order.model;

import com.hanglinpai.api.Api;
import com.hanglinpai.api.HostType;
import com.hanglinpai.ui.bean.AddChatBean;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.ChatDetail;
import com.hanglinpai.ui.bean.ChatListBean;
import com.hanglinpai.ui.order.constract.ChatConstract;
import com.hanglinpai.ui.order.constract.OrderCancerConstract;

import rx.Observable;
import www.meiyaoni.com.common.baserx.RxSchedulers;
import www.meiyaoni.com.common.toolUtils.SPManager;

public class ChatModel implements ChatConstract.Model{




    @Override
    public Observable<ChatListBean> addChat(String order_id, String chat_id,String content) {
        return Api.getDefault(HostType.BASE_DATA_URL).chat(SPManager.getInstance().getUserToken(),order_id,chat_id,content)
                .compose(RxSchedulers.<ChatListBean>io_main());
    }

    @Override
    public Observable<ChatListBean> ChatList(String order_id, String type, String chat_id) {
        return Api.getDefault(HostType.BASE_DATA_URL).chatList(SPManager.getInstance().getUserToken(),order_id,type,chat_id)
                .compose(RxSchedulers.<ChatListBean>io_main());
    }
    @Override
    public Observable<ChatListBean> ChatListOld(String order_id, String type, String chat_id) {
        return Api.getDefault(HostType.BASE_DATA_URL).chatList(SPManager.getInstance().getUserToken(),order_id,type,chat_id)
                .compose(RxSchedulers.<ChatListBean>io_main());
    }
}
