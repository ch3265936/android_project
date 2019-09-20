package www.meiyaoni.com.common.broad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import www.meiyaoni.com.common.toolUtils.CommonDialog;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

/**
 * @author chihai
 * @function Created on 2017/7/10.
 */

public class InterNetBroadCastManager extends BroadcastReceiver {
    private CommonDialog dialog;
    public static final String NO_INTERNET = "www.meiyaonitech.com.nointernet";
    public static final String HAS_INTERNET= "www.meiyaonitech.com.hasinternet";
    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkInfo.State wifiState = null;
        NetworkInfo.State mobileState = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (wifiState != null && mobileState != null
                && NetworkInfo.State.CONNECTED != wifiState
                && NetworkInfo.State.CONNECTED == mobileState) {
            dissMissDialog();
            // 手机网络连接成功
        } else if (wifiState != null && mobileState != null
                && NetworkInfo.State.CONNECTED != wifiState
                && NetworkInfo.State.CONNECTED != mobileState) {
            // 手机没有任何的网络
//            ToastUtils.showShotToast("无任何网络");
            showDialog(context);
            context.sendBroadcast(new Intent(NO_INTERNET));
        } else if (wifiState != null && NetworkInfo.State.CONNECTED == wifiState) {
            // 无线网络连接成功
//            ToastUtils.showShotToast("成功链接到网络");
            dissMissDialog();
            context.sendBroadcast(new Intent(HAS_INTERNET));
        }
    }
    private void showDialog(final Context context){
        if (dialog == null) {
            dialog = new CommonDialog(context, "网络提示", "您的网络不可用,请点‘确定’设置网络", "确定", "取消", new CommonDialog.DialogClickListener() {
                @Override
                public void onDialogClick() {
                    // 跳转到系统的网络设置界面
                    Intent intent = null;
                    // 先判断当前系统版本
                    if (android.os.Build.VERSION.SDK_INT > 10) {  // 3.0以上
                        intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    } else {
                        intent = new Intent();
                        intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
                    }
                    context.startActivity(intent);
                }
            });
        }
        dialog.show();
    }
    private void dissMissDialog(){
        if (dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
            ToastUtils.showShotToast("您的网络又回来啦(:");
        }
    }
}
