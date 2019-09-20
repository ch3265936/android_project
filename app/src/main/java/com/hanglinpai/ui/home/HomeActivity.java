
package com.hanglinpai.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;

import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.Bind;
import www.meiyaoni.com.common.base.BaseActivity;
import www.meiyaoni.com.common.baseapp.AppManager;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

import com.hanglinpai.EventBus.ACTIVATE;
import com.hanglinpai.EventBus.UMPUSH;
import com.hanglinpai.R;
import com.hanglinpai.ui.bean.LoginBean;
import com.hanglinpai.ui.bean.UploadBean;
import com.hanglinpai.ui.home.fragment.HomeFragment;
import com.hanglinpai.ui.home.fragment.ProfileFragment;
import com.hanglinpai.ui.order.NewOrderActivity;
import com.hanglinpai.ui.user.AttestationActivity;
import com.hanglinpai.ui.user.RegActivity;
import com.hanglinpai.ui.user.constract.UserInfoConstract;
import com.hanglinpai.ui.user.model.UserInfoModel;
import com.hanglinpai.ui.user.presenter.UserInfoPresenter;
import com.hanglinpai.util.KeepLiveReceiver;
import com.hanglinpai.util.KeepLiveSevice;
import com.hanglinpai.widget.TodoDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class HomeActivity extends BaseActivity<UserInfoPresenter, UserInfoModel> implements UserInfoConstract.View {
    private Fragment mCurrentFrag;
    private FragmentManager fm;
    private HomeFragment homeFragment;
    public ProfileFragment profileFragment;

    @Bind(R.id.group)
    RadioGroup group;
    @Bind(R.id.home)
    RadioButton home;
    @Bind(R.id.plus)
    ImageView plus;
    @Bind(R.id.profile)
    RadioButton profile;
    @Bind(R.id.r)
    RelativeLayout r;
    KeepLiveReceiver receiver;
    public static LoginBean.ListBean userinfo = null;
    private static final int GOTOATTETION = 4;
    private static final int GOTONEWORDER = 5;
    private static final int GOTOORDERDETAIL = 6;

    public HomeActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
//        receiver = new KeepLiveReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Intent.ACTION_SCREEN_OFF);
//        filter.addAction(Intent.ACTION_USER_PRESENT);
//        filter.addAction("android.intent.action.MY_BROADCAST");
//        registerReceiver(receiver, filter);

        Log.i("999", android.os.Process.myPid() + "");
        StatusBarTranslucent();
        fm = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        switchContent(homeFragment);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.home:
                        switchContent(homeFragment);
                        break;

                    case R.id.profile:
                        switchContent(profileFragment);
                        break;
                }
            }
        });
        mPresenter.getUser(false);//获取用户信息

    }


    /**
     * 动态添加fragment，不会重复创建fragment
     *
     * @param to 将要加载的fragment
     */
    public void switchContent(Fragment to) {
        if (mCurrentFrag != to) {
            if (!to.isAdded()) {// 如果to fragment没有被add则增加一个fragment
                if (mCurrentFrag != null) {
                    fm.beginTransaction().hide(mCurrentFrag).commit();
                }
                fm.beginTransaction()
                        .add(R.id.frame, to)
                        .commit();
            } else {
                fm.beginTransaction().hide(mCurrentFrag).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            mCurrentFrag = to;
        }
    }

    private Long firstTime = 0L;

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 1500) {
            ToastUtils.showShort(R.string.back_again_exit);
            firstTime = secondTime;
        } else {
            System.exit(0);
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        EventBus.getDefault().unregister(this);
//        unregisterReceiver(receiver);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ACTIVATE(ACTIVATE activate) {
        homeFragment.refreshOrderList();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UMPUSH(UMPUSH umpush) {
        if (umpush.getType().equals("1")) {// 订单类型 刷新
            homeFragment.refreshOrderList();
        } else if(umpush.getType().equals("0")) {
            mPresenter.getUser(false);//获取用户信息
        }else if(umpush.getType().equals("2")){
            homeFragment.refreshOrderList();
        }
    }
    public void newOrder(View view) {
        if (userinfo != null) {
            if (userinfo.getAuth_status() == 0) {
                tuAttestation();
            } else {
                startActivityForResult(new Intent(mActivity, NewOrderActivity.class), GOTONEWORDER);
                overridePendingTransition(R.anim.down_up, R.anim.up_down);
            }
        }

    }

    //未填写资料 部分操作跳转填写资料
    private void tuAttestation() {
        Intent i = new Intent(mActivity, AttestationActivity.class);
        i.putExtra("HOME", "HOME");
        i.putExtra("phone", userinfo.getPhone());
        startActivityForResult(i, GOTOATTETION);
        ToastUtils.showShotToast(getResources().getString(R.string.reg_no_attetion));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOTOATTETION && resultCode == GOTOATTETION) {
            //认证资料提交完毕
            mPresenter.getUser(false);//获取用户信息
        }

        //新增订单
        if (homeFragment != null && requestCode == GOTONEWORDER && resultCode == GOTONEWORDER) {
            homeFragment.refreshOrderList();//刷新
        }
        //详情页
        if (homeFragment != null && requestCode == GOTOORDERDETAIL) {
            homeFragment.refreshOrderList();//刷新
        }


        if (profileFragment != null && requestCode == ProfileFragment.TOUSERINFO && resultCode == ProfileFragment.TOUSERINFO) {
            profileFragment.onActivityResult(requestCode, resultCode, data);
        }
        if (profileFragment != null && requestCode == ProfileFragment.TOMESSAGE && resultCode == ProfileFragment.TOMESSAGE) {
            profileFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void uploadSuccess(UploadBean.ListBean bean) {

    }

    @Override
    public void editUserInfo() {

    }

    @Override
    public void getUser(LoginBean.ListBean listBean) {
        userinfo = listBean;
        if (listBean.getAuth_status() == 0) {//未填写资料

        } else if (listBean.getAuth_status() == 2) {//通过未提示
            showAttentionDialog(AppManager.getAppManager().currentActivity());
            mPresenter.editUserInfo("", 3);//更新提示状态
        }
        if(homeFragment!=null&&listBean.getOverdue_order_count()>0){
            homeFragment.showOverdue(true);
        }else{
            homeFragment.showOverdue(false);
        }
        if (profileFragment != null) {
            profileFragment.showInfo(listBean);
        }
    }


    private TodoDialog attentionDialog;

    public void showAttentionDialog(Activity context) {
        if (attentionDialog != null && attentionDialog.isShowing()) {
            return;
        }
        attentionDialog = new TodoDialog(context, R.string.attestation_success, R.string.attestation_tip, R.string.to_new_order, R.mipmap.image_renzhen, new TodoDialog.DialogClickListener() {
            @Override
            public void onDialogClick() {
                //to_do
                startActivityForResult(new Intent(mActivity, NewOrderActivity.class),GOTONEWORDER);
                overridePendingTransition(R.anim.down_up, R.anim.up_down);
            }
        }, new TodoDialog.DialogClickListener() {
            @Override
            public void onDialogClick() {
            }
        });
        attentionDialog.show();
    }

}
