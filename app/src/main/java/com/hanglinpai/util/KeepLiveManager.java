package com.hanglinpai.util;

import android.content.Context;
import android.content.Intent;

public class KeepLiveManager {

    private static KeepLiveManager mKeepLiveManager = null;
    private Context mContext;
    private KeepLiveActivity mKeepLiveActivity;
    private boolean hasLive = false;

    private KeepLiveManager() {

    }

    public static KeepLiveManager getInstance() {
        if (mKeepLiveManager == null) {
            mKeepLiveManager = new KeepLiveManager();
        }

        return mKeepLiveManager;
    }

    public void init(Context context) {
        mContext = context;
    }

    public void startKeepLiveActivity() {

        Intent off_intent = new Intent(mContext, KeepLiveActivity.class);
        mContext.startActivity(off_intent);
    }

    public void setKeepLiveActivity(KeepLiveActivity activity) {
        mKeepLiveActivity = activity;
    }

    public void finishKeepLiveActivity() {
        if (mKeepLiveActivity != null) {
            mKeepLiveActivity.finish();
        }
    }
}
