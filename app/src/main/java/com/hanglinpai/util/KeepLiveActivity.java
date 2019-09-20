package com.hanglinpai.util;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

public class KeepLiveActivity extends Activity {

    private final static String TAG = "KeepLiveActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.width = 1;
        params.height = 1;
        window.setAttributes(params);
        Log.i("viclee",TAG);
        KeepLiveManager.getInstance().setKeepLiveActivity(this);
    }
}