package com.hanglinpai.ui.order.constract;

import com.hanglinpai.ui.bean.AddChatBean;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.ChatDetail;
import com.hanglinpai.ui.bean.ChatListBean;
import com.hanglinpai.ui.bean.OrderDetail;
import com.hanglinpai.ui.bean.OrderDetailBean;

import java.util.List;

import rx.Observable;
import www.meiyaoni.com.common.base.BaseModel;
import www.meiyaoni.com.common.base.BasePresenter;
import www.meiyaoni.com.common.base.BaseView;

/**
 * @author chihai
 * @function Created on 2017/12/20.
 * @DO 抽象化MVP 接口 类
 */

public interface ChatConstract {
    interface Model extends BaseModel {

        Observable<ChatListBean> addChat(String order_id,String chat_id, String content);
        Observable<ChatListBean> ChatList(String order_id, String type,String chat_id);
        Observable<ChatListBean> ChatListOld(String order_id, String type,String chat_id);


    }

    interface View extends BaseView {

        //新增聊天成功
        void returnAddChatSuccess(ChatListBean.ListBean bean );
        //获取聊天LIST成功
        void returnChatListSuccess(ChatListBean.ListBean bean);

        //获取聊天OLDLIST成功
        void returnChatOldListSuccess(ChatListBean.ListBean bean);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {

        public abstract void addChat(String order_id, String chat_id,String content);

        public abstract void ChatList(  String order_id, String type,String chat_id);

        public abstract void ChatListOld(  String order_id, String type,String chat_id);
    }
}
