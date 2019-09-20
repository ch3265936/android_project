package www.meiyaoni.com.common.base;

import android.app.Activity;
import android.content.Context;

import www.meiyaoni.com.common.baserx.RxManager;

/**
 * 基类presenter
 * @author weikailiang
 * @function Created on 2017/7/26.
 */

public abstract class BasePresenter<T,E> {
    public Context mContext;
    public Activity mActivity;
    public E mModel;
    public T mView;
    public RxManager mRxManage = new RxManager();
    public void setVM(T v,E m){
        this.mModel = m;
        this.mView = v;
        this.onStart();
    }
    public void onStart(){}
    public void onDestroy(){
        mRxManage.clear();
    }

}
