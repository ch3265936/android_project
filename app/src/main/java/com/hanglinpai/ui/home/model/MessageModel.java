package com.hanglinpai.ui.home.model;

import com.hanglinpai.api.Api;
import com.hanglinpai.api.HostType;
import com.hanglinpai.ui.bean.MessageListBean;
import com.hanglinpai.ui.bean.OrderListBean;
import com.hanglinpai.ui.home.constract.MessageListConstract;
import com.hanglinpai.ui.home.constract.OrderConstract;

import rx.Observable;
import www.meiyaoni.com.common.baserx.RxSchedulers;
import www.meiyaoni.com.common.toolUtils.SPManager;

public class MessageModel implements MessageListConstract.Model{

    @Override
    public Observable<MessageListBean> messageList(String pageNum) {
        return Api.getDefault(HostType.BASE_DATA_URL).getSystemMessage(SPManager.getInstance().getUserToken(),pageNum,"10","add_time_str,date")
                .compose(RxSchedulers.<MessageListBean>io_main());
    }
}
