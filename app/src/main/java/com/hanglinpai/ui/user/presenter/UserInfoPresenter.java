package com.hanglinpai.ui.user.presenter;


import android.content.Intent;

import com.hanglinpai.api.ApiConstants;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.LoginBean;
import com.hanglinpai.ui.bean.UploadBean;

import com.hanglinpai.ui.user.LoginActivity;
import com.hanglinpai.ui.user.LoginOrRegActivity;
import com.hanglinpai.ui.user.constract.UserInfoConstract;
import com.hanglinpai.util.PhotoUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import www.meiyaoni.com.common.baseapp.AppManager;
import www.meiyaoni.com.common.baserx.RxSubscriber;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

/**
 * @author chihai
 * @function Created on 2017/12/20.
 */

public class UserInfoPresenter extends UserInfoConstract.Presenter {
    @Override
    public void upload(List<String> list) {
        Map<String, RequestBody> map = new HashMap<>();
        File file;
        List<String> paths = new ArrayList<>();
        //对图片压缩处理
        for (int i = 0; i < list.size(); i++) {
            paths.add(PhotoUtil.saveBitmap(mContext, list.get(i)));
        }
        for (int i = 0; i < paths.size(); i++) {
            file = new File(paths.get(i));
            map.put("attachment[]" + "\";filename=\"" + file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
        }
        mRxManage.add(mModel.upload(map).subscribe(new RxSubscriber<UploadBean>(mContext, true) {
            @Override
            protected void _onNext(UploadBean baseRespose) {

                switch (baseRespose.getCode()) {
                    case ApiConstants.SUCCESS:
                        mView.uploadSuccess(baseRespose.getData());
                        break;
                    case ApiConstants.ERRO:
                        ToastUtils.showShotToast(baseRespose.getMsg());
                        AppManager.getAppManager().finishAllActivity();
                        mActivity.startActivity(new Intent(mContext, LoginOrRegActivity.class));
                        break;
                    default:
                        ToastUtils.showShotToast(baseRespose.getMsg());

                        break;
                }

            }

            @Override
            protected void _onError(String message) {
                ToastUtils.showShotToast(message);
            }
        }));
    }


    @Override
    public void editUserInfo(String avatar, int status) {
        mRxManage.add(mModel.editUserInfo(avatar, status).subscribe(new RxSubscriber<BaseBean>(mContext, false) {
            @Override
            protected void _onNext(BaseBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        mView.editUserInfo();
                        break;
                    case ApiConstants.ERRO:
                        ToastUtils.showShotToast(bean.getMsg());
                        AppManager.getAppManager().finishAllActivity();
                        mActivity.startActivity(new Intent(mContext, LoginOrRegActivity.class));
                        break;
                    default:
                        ToastUtils.showShotToast(bean.getMsg());
                        break;
                }

            }

            @Override
            protected void _onError(String message) {
                ToastUtils.showShotToast(message);
            }
        }));
    }


    @Override
    public void getUser(boolean loding) {
        mRxManage.add(mModel.getUser().subscribe(new RxSubscriber<LoginBean>(mContext, loding) {
            @Override
            protected void _onNext(LoginBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        if (bean.getList() != null)
                            mView.getUser(bean.getList());
                        break;
                    case ApiConstants.ERRO:
                        ToastUtils.showShotToast(bean.getMsg());
                        AppManager.getAppManager().finishAllActivity();
                        mActivity.startActivity(new Intent(mContext, LoginOrRegActivity.class));
                        break;
                    default:
                        ToastUtils.showShotToast(bean.getMsg());
                        break;
                }

            }

            @Override
            protected void _onError(String message) {
                ToastUtils.showShotToast(message);
            }
        }));
    }
}
