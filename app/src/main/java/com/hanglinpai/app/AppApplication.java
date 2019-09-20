package com.hanglinpai.app;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.hanglinpai.EventBus.UMPUSH;
import com.hanglinpai.R;
import com.hanglinpai.ui.bean.OrderDetail;
import com.hanglinpai.ui.home.HomeActivity;
import com.hanglinpai.ui.home.MessageListActivity;
import com.hanglinpai.ui.order.ChatListActivity;
import com.hanglinpai.ui.order.OrderDetailActivity;
import com.hanglinpai.util.KeepLiveManager;
//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import www.meiyaoni.com.common.baseapp.AppManager;
import www.meiyaoni.com.common.baseapp.BaseApplication;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

/**
 * APPLICATION
 */
public class AppApplication extends BaseApplication {
    private static final String TAG = AppApplication.class.getName();
    public String filePath;//存储路径
    public Context mContext = null;
    private static AppApplication mInstance;
    private static final String PUSHSCREAT = "89d09686095dd7a01f78b94e98b19dce";
    public static String PUSHDEVICE = "";
    private String order_id = "", type = "";

    //    private RefWatcher mRefWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化吐司
        ToastUtils.getInstance(this);
        mInstance = this;
        mContext = getApplicationContext();
        if (android.os.Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            filePath = mContext.getExternalFilesDir("/hanglinpai").getAbsolutePath();
        } else {
            filePath = mContext.getFilesDir() + "/hanglinpai";
        }
        KeepLiveManager.getInstance().init(getAppContext());

        UMConfigure.init(mInstance, UMConfigure.DEVICE_TYPE_PHONE, PUSHSCREAT);
        UMConfigure.setLogEnabled(true);
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setNoDisturbMode(0, 0, 0, 0);
        //sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        mPushAgent.setDisplayNotificationNumber(2);
        mPushAgent.setMuteDurationSeconds(1);
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public Notification getNotification(Context context, UMessage msg) {

                Map<String, String> extra = new HashMap<>();
                if (msg != null && msg.extra != null) {
                    extra = msg.extra;
                    Log.i("999", msg.extra.toString());
                    type = extra.get("type");
                    if (type != null) {
                        if (type.equals("0")) {

                        } else if (type.equals("1")) {
                            order_id = extra.get("order_id");
                        } else if (type.equals("2")) {
                            order_id = extra.get("order_id");
                        }
                        EventBus.getDefault().post(new UMPUSH(type, order_id));
                    }
                }
                if (Build.VERSION.SDK_INT >= 26) {
                    NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationChannel channel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_HIGH);
                    if (manager != null) {
                        manager.createNotificationChannel(channel);
                    }
                    Notification.Builder builder = new Notification.Builder(context, "channel_id");
                    builder.setSmallIcon(R.mipmap.ic_launcher)
                            .setWhen(System.currentTimeMillis())
                            .setContentTitle(msg.title)
                            .setContentText(msg.text)
                            .setAutoCancel(true);
                    return builder.build();
                } else {
                    Notification.Builder builder = new Notification.Builder(context);
                    builder.setSmallIcon(R.mipmap.ic_launcher)
                            .setWhen(System.currentTimeMillis())
                            .setContentTitle(msg.title)
                            .setContentText(msg.text)
                            .setAutoCancel(true);
                    return builder.build();
                }
            }
        };
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                if (type != null && type.equals("1") && order_id.length() > 0) {
                    if (AppManager.getAppManager().currentActivity() instanceof OrderDetailActivity) {
                        AppManager.getAppManager().currentActivity().finish();
                    }
                    Intent i = new Intent(getApplicationContext(), OrderDetailActivity.class);
                    i.putExtra("orderId", order_id);
                    startActivity(i);
                } else if (type != null && type.equals("2") && order_id.length() > 0) {
                    if (AppManager.getAppManager().currentActivity() instanceof ChatListActivity) {
                        AppManager.getAppManager().currentActivity().finish();
                    }
                    Intent i = new Intent(getApplicationContext(), ChatListActivity.class);
                    i.putExtra("orderId", order_id);
                    startActivity(i);
                }
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        mPushAgent.setMessageHandler(messageHandler);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                PUSHDEVICE = deviceToken;
                Log.i("999", PUSHDEVICE);
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
        //UM
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wxf1acd9af8c5db31c", "f2cc5b954495964343854f4669d542d7");
        PlatformConfig.setQQZone("1106885824", "c7394704798a158208a74ab60104f0ba");
//        mRefWatcher = LeakCanary.install(this);
    }

    public static AppApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


}
