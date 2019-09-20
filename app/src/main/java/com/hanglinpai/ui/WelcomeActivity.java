package com.hanglinpai.ui;

import android.Manifest;
import android.animation.Animator;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;


import butterknife.Bind;
import rx.functions.Action1;
import www.meiyaoni.com.common.autUpdata.UpdateService;
import www.meiyaoni.com.common.base.BaseActivity;
import www.meiyaoni.com.common.baseapp.AppManager;
import www.meiyaoni.com.common.baserx.RxSchedulers;
import www.meiyaoni.com.common.baserx.RxSubscriber;
import www.meiyaoni.com.common.toolUtils.SPManager;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

import com.google.gson.Gson;
import com.hanglinpai.R;
import com.hanglinpai.api.Api;
import com.hanglinpai.api.ApiConstants;
import com.hanglinpai.api.HostType;
import com.hanglinpai.app.AppApplication;
import com.hanglinpai.ui.bean.ConfigBean;
import com.hanglinpai.ui.bean.Version_info;
import com.hanglinpai.ui.home.HomeActivity;
import com.hanglinpai.ui.order.ChatListActivity;
import com.hanglinpai.ui.order.SpecialistListActivity;
import com.hanglinpai.ui.user.LoginActivity;
import com.hanglinpai.ui.user.LoginOrRegActivity;
import com.hanglinpai.util.ThreadPool;
import com.hanglinpai.widget.CommonDialog;
import com.tbruyelle.rxpermissions.RxPermissions;

public class WelcomeActivity extends BaseActivity {
    @Bind(R.id.welcome)
    ImageView welcome;
    Version_info version_info;

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome_layout;
    }

    @Override
    public void initPresenter() {
        PackageInfo pInfo = null;

        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        final PackageInfo finalPInfo = pInfo;
        Api.getDefault(HostType.BASE_DATA_URL).config(finalPInfo.versionName, "1")
                .compose(RxSchedulers.<ConfigBean>io_main()).subscribe(new RxSubscriber<ConfigBean>(mContext, false) {
            @Override
            protected void _onNext(ConfigBean bean) {
                switch (bean.getCode()) {
                    case ApiConstants.SUCCESS:
                        if (bean.getData() != null) {
                            if (bean.getData().getVersion_info() != null) {
                                Gson gson = new Gson();
                                version_info = bean.getData().getVersion_info();
                                String Static = gson.toJson(bean.getData().getStatic_data());
                                if (Static != null) {
                                    SPManager.getInstance().putString("Static", Static);
                                }
                            }
                        }
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
        });
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    //   | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(getResources().getColor(R.color.transparent));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


        welcome.setAlpha(0.2f);
        welcome.animate().alpha(0.8f).setDuration(2000).setListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (version_info != null) {
                    if (version_info.getUpdate_status() == 1 || version_info.getUpdate_status() == 2) {
                        RxPermissions.getInstance(mActivity)
                                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE)
                                .subscribe(new Action1<Boolean>() {
                                    @Override
                                    public void call(Boolean aBoolean) {
                                        if (aBoolean) {
                                            downLoadApk(version_info);
                                        } else {
                                            ToastUtils.showShotToast("没有权限,无法下载更新");
                                        }
                                    }
                                });
                    } else {//启动时间到了 但是无法获取本次版本更新信息  直接越过本次版本更新
                        toNext();
                    }
                } else {//启动时间到了 但是无法获取本次版本更新信息  直接越过本次版本更新
                    toNext();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        }).start();


    }


    private CommonDialog commonDialog;
    private boolean isUpData = false;


    private void downLoadApk(final Version_info bean) {
        //说明有新版本,开始下载
        String cancer = "取消";
        if (version_info != null && version_info.getUpdate_status() == 2) {
            cancer = "退出";
        }
        CommonDialog dialog = new CommonDialog(mActivity, bean.getTitle(),
                bean.getDesc().replace("|", "\n"), "安装",
                cancer, LinearLayout.LayoutParams.MATCH_PARENT, new CommonDialog.DialogClickListener() {
            @Override
            public void onDialogClick() {
                ToastUtils.showShotToast("后台更新版本中...");
                Intent intent = new Intent(mActivity, UpdateService.class);
                intent.putExtra(UpdateService.DOWNLOADURL, bean.getDownload_link());
                startService(intent);
                toNext();
            }
        }, new CommonDialog.DialogClickListener() {
            @Override
            public void onDialogClick() {
                if (version_info != null && version_info.getUpdate_status() == 2) {//强制更新  取消则关闭所有 页面
                    AppManager.getAppManager().finishAllActivity();
                } else {//非强更新  正常进入APP
                    toNext();
                }
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    private void toNext() {
        if (SPManager.getInstance().getBoolean("intro", true)) {
            startActivity(new Intent(mContext, IntroActivity.class));
        } else {
            if (SPManager.getInstance().getUserToken().length() > 0) {
                startActivity(new Intent(mContext, HomeActivity.class));
            } else {
                startActivity(new Intent(mContext, LoginOrRegActivity.class));
            }

        }
        finish();
    }
}
