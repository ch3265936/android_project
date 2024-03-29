package www.meiyaoni.com.common.broad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;



/**
 * @author chihai
 * @function Created on 2017/7/10.
 */

public class NetWorkUtils {
    public static final String NO_INTERNET = "www.meiyaonitech.com.nointernet";
    public static final String HAS_INTERNET= "www.meiyaonitech.com.hasinternet";
    private Context mContext;
    private NetBroadCast mNetBroadCast;
    private NetWorkChangeInterface listener;
    public NetWorkUtils(Context context){
        this.mContext = context;
    }
    public void registerNetBroadCast(){
        mNetBroadCast = new NetBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(NO_INTERNET);
        filter.addAction(HAS_INTERNET);
        mContext.registerReceiver(mNetBroadCast,filter);
    }
    public void unregisterNetBroadCast(){
        if (mNetBroadCast != null) {
            mContext.unregisterReceiver(mNetBroadCast);
        }
    }
    public void setNetWorkChangeListener(NetWorkChangeInterface listener){
        this.listener = listener;
    }
    public class NetBroadCast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case NO_INTERNET:
                    if (listener != null){
                        listener.NoNetWork();
                    }
                    break;
                case HAS_INTERNET:
                    if (listener != null){
                        listener.HasNetWork();
                    }
                    break;
            }
        }
    }
}
