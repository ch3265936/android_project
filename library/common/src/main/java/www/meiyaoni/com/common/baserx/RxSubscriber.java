package www.meiyaoni.com.common.baserx;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.kaopiz.kprogresshud.KProgressHUD;

import pl.droidsonroids.gif.GifImageView;
import rx.Subscriber;
import www.meiyaoni.com.common.R;
import www.meiyaoni.com.common.baseapp.BaseApplication;
import www.meiyaoni.com.common.toolUtils.LodingDialog;
import www.meiyaoni.com.common.toolUtils.NetWorkUtils;


public abstract class RxSubscriber<T> extends Subscriber<T> {
    private KProgressHUD hud;
    private Context mContext;
    private String msg;
    private boolean showDialog = true;

    /**
     * 是否显示浮动dialog
     */
    public void showDialog() {
        this.showDialog = true;
    }

    public void hideDialog() {
        this.showDialog = true;
    }

    public RxSubscriber(Context context, String msg, boolean showDialog) {
        this.mContext = context;
        this.msg = msg;
        this.showDialog = showDialog;
    }

    public RxSubscriber(Context context) {
        this(context, BaseApplication.getAppContext().getString(R.string.loading), true);
    }

    public RxSubscriber(Context context, boolean showDialog) {
        this(context, BaseApplication.getAppContext().getString(R.string.loading), showDialog);
    }

    private LodingDialog lodingDialog;

    public void Loding(Activity context) {
        if (lodingDialog != null && lodingDialog.isShowing()) {
            return;
        }
        if (lodingDialog == null) {
            lodingDialog = new LodingDialog(context);
        }
        lodingDialog.show();
    }

    @Override
    public void onCompleted() {
        if (showDialog && lodingDialog != null)
            lodingDialog.dismiss();
//            hud.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (showDialog) {
            try {
                if (mContext instanceof Activity) {
                    Loding((Activity) mContext);
                }

//                hud = KProgressHUD.create(mContext)
//                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setDimAmount(0.5f).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onNext(T t) {
        Log.i("999", t.toString());
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        Log.i("999", e.toString());
        if (showDialog && lodingDialog != null)
            lodingDialog.dismiss();
//            hud.dismiss();
        e.printStackTrace();
        //网络
        if (!NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
            _onError(BaseApplication.getAppContext().getString(R.string.no_net));
        }
        //服务器
        else if (e instanceof ServerException) {
            _onError(e.getMessage());
        }
        //其它
        else {
            _onError(BaseApplication.getAppContext().getString(R.string.net_error));
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

}
