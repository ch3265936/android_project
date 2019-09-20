package com.hanglinpai.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;

public class KeepLiveReceiver extends BroadcastReceiver{
    String TAG = "KeepLiveReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        String act = intent.getAction();
        if (act.equals(Intent.ACTION_SCREEN_OFF)){
        	KeepLiveManager.getInstance().startKeepLiveActivity();
        }else if (act.equals(Intent.ACTION_USER_PRESENT)){
        	KeepLiveManager.getInstance().finishKeepLiveActivity();
        }
    }

}