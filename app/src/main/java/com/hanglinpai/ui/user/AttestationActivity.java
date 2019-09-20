package com.hanglinpai.ui.user;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hanglinpai.R;
import com.hanglinpai.ui.bean.UploadBean;
import com.hanglinpai.ui.home.HomeActivity;
import com.hanglinpai.ui.user.constract.AttestaionConstract;
import com.hanglinpai.ui.user.model.AttestaionModel;
import com.hanglinpai.ui.user.presenter.AttestaionPresenter;
import com.hanglinpai.util.DensityUtil;
import com.hanglinpai.util.FileSizeUtil;
import com.hanglinpai.util.RegexValidateUtil;
import com.hanglinpai.util.SystemUtils;
import com.hanglinpai.widget.RoundedImageView;
import com.hanglinpai.widget.SexDialog;
import com.hanglinpai.widget.TodoDialog;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yuyh.library.imgsel.ImgSelActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;
import www.meiyaoni.com.common.base.BaseActivity;
import www.meiyaoni.com.common.baseapp.AppManager;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

public class AttestationActivity extends BaseActivity<AttestaionPresenter, AttestaionModel> implements AttestaionConstract.View {
    @Bind(R.id.title_back)
    TextView title_back;
    @Bind(R.id.title_title)
    TextView title_title;
    @Bind(R.id.title_right)
    TextView title_right;
    @Bind(R.id.submit_reg)
    Button submit_reg;
    @Bind(R.id.img_card)
    ImageView img_card;
    @Bind(R.id.avl)
    RoundedImageView avl;
    @Bind(R.id.phone)
    TextView phone;
    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.company)
    EditText company;
    @Bind(R.id.position)
    EditText position;
    @Bind(R.id.email)
    EditText email;
    @Bind(R.id.sex)
    TextView sex;
    @Bind(R.id.red_phone_tip)
    TextView red_phone_tip;
    @Bind(R.id.red_name_tip)
    TextView red_name_tip;
    @Bind(R.id.red_company_tip)
    TextView red_company_tip;
    @Bind(R.id.red_position_tip)
    TextView red_position_tip;
    @Bind(R.id.red_email_tip)
    TextView red_email_tip;
    @Bind(R.id.red_sex_tip)
    TextView red_sex_tip;
    @Bind(R.id.r_top)
    RelativeLayout r_top;
    @Bind(R.id.r_sex)
    RelativeLayout r_sex;
    private int sexIndex = 0;//1 2
    private int index = 0;
    private String avlPath = "", cardPath = "",  h="";
    private String avlPath_server = "", cardPath_server = "";
    private String phone_str, name_str, company_str, position_str, email_str;

    @Override
    public int getLayoutId() {
        return R.layout.activity_attesta;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        h= getIntent().getStringExtra("HOME");
        if (h != null && h.equals("HOME")) {//首页强制跳转  隐藏跳过
            title_right.setVisibility(View.GONE);
            title_back.setVisibility(View.VISIBLE);
        } else {//注册进入 可选择跳过
            title_right.setVisibility(View.VISIBLE);
            title_right.setText(R.string.just);
            title_right.setTextColor(getResources().getColor(R.color.colorAccent));
            title_back.setVisibility(View.GONE);
        }
        String phone_str = getIntent().getStringExtra("phone");
        if (phone_str != null) {
            phone.setText(phone_str);
        }
        title_title.setText(R.string.attestation);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//  键盘冲突直接该状态栏颜色为标题栏白色  （透明状态栏也是白色效果一样 键盘上顶不冲突）
            Window window = getWindow();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.WHITE);
            window.setNavigationBarColor(getResources().getColor(www.meiyaoni.com.common.R.color.pickerview_bgColor_overlay));
        }
    }

    @OnClick({R.id.title_back, R.id.title_right, R.id.r_sex, R.id.submit_reg, R.id.img_card, R.id.avl})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_right:
                AppManager.getAppManager().finishAllActivity();
                startActivity(new Intent(mActivity, HomeActivity.class));
                break;
            case R.id.avl:
                index = 1;
                RxPermissionsPhoto();
                break;
            case R.id.img_card:
                index = 2;
                RxPermissionsCard();
                break;
            case R.id.r_sex:
                showSexDialog(mActivity);
                break;
            case R.id.submit_reg:
                redTip();
                if ((!RegexValidateUtil.checkMobileNumber(phone.getText().toString().trim()))) {
                    ToastUtils.showShort(R.string.input_phone);
                    return;
                }
                phone_str = phone.getText().toString().trim();
                if (name.getText().toString().trim().length() <= 0) {
                    ToastUtils.showShort(R.string.input_name);
                    return;
                }
                name_str = name.getText().toString().trim();
                if (sexIndex == 0) {
                    ToastUtils.showShort(R.string.select_sex);
                    return;
                }
                if (company.getText().toString().trim().length() <= 0) {
                    ToastUtils.showShort(R.string.input_company);
                    return;
                }
                company_str = company.getText().toString().trim();
//                if (position.getText().toString().trim().length() <= 0) {
//                    ToastUtils.showShort(R.string.input_position);
//                    return;
//                }
                position_str = position.getText().toString().trim();
                if (email.getText().toString().trim().length() <= 0 || (!RegexValidateUtil.checkEmail(email.getText().toString().trim()))) {
                    ToastUtils.showShort(R.string.input_email);
                    return;
                }
                email_str = email.getText().toString().trim();
                if (cardPath.length() <= 0) {
                    ToastUtils.showShort(R.string.select_card);
                    return;
                }
                if (!SystemUtils.isFastClick()) {
                    ToastUtils.showShort(R.string.click_fast);
                } else {
                    List<String> list = new ArrayList<>();
                    if (avlPath != null && avlPath.length() > 0) {
                        list.add(avlPath);
                    }
                    list.add(cardPath);
                    mPresenter.upload(list);

                }
                break;

            default:
                break;
        }

    }

    private void redTip() {
        if (phone.getText().toString().trim().length() <= 0) {
            red_phone_tip.setVisibility(View.VISIBLE);
        } else {
            red_phone_tip.setVisibility(View.GONE);
        }
        if (name.getText().toString().trim().length() <= 0) {
            red_name_tip.setVisibility(View.VISIBLE);
        } else {
            red_name_tip.setVisibility(View.GONE);
        }
        if (sex.getText().toString().trim().length() <= 0) {
            red_sex_tip.setVisibility(View.VISIBLE);
        } else {
            red_sex_tip.setVisibility(View.GONE);
        }
        if (company.getText().toString().trim().length() <= 0) {
            red_company_tip.setVisibility(View.VISIBLE);
        } else {
            red_company_tip.setVisibility(View.GONE);
        }
//        if (position.getText().toString().trim().length() <= 0) {
//            red_position_tip.setVisibility(View.VISIBLE);
//        } else {
//            red_position_tip.setVisibility(View.GONE);
//        }

        if (email.getText().toString().trim().length() <= 0) {
            red_email_tip.setVisibility(View.VISIBLE);
        } else {
            red_email_tip.setVisibility(View.GONE);
        }

    }

    private void RxPermissionsPhoto() {
        RxPermissions.getInstance(mActivity).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            SystemUtils.SelectSinge(mActivity, 200, 200);
                            SystemUtils.dismissDioalog();
                        } else {
                            //用户拒绝改权限
                            SystemUtils.startToSettingAuth(mActivity, "您的应用缺少相应权限,请到系统设置");
                        }
                    }
                });
    }

    private void RxPermissionsCard() {
        RxPermissions.getInstance(mActivity).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            SystemUtils.SelectSinge(mActivity, 90, 54);
                            SystemUtils.dismissDioalog();
                        } else {
                            //用户拒绝改权限
                            SystemUtils.startToSettingAuth(mActivity, "您的应用缺少相应权限,请到系统设置");
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            List<String> photoPathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            if (index == 1) {
                Glide.with(mActivity).load(photoPathList.get(0))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .crossFade()
                        .error(R.mipmap.ic_user_def)
                        .into(avl);
            } else if (index == 2) {
                Glide.with(mActivity).load(photoPathList.get(0))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .crossFade()
                        .error(R.mipmap.ic_launcher)
                        .into(img_card);
            }
            Glide.with(mActivity).load(photoPathList.get(0)).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
                @Override
                public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                    try {
                        String savePath = FileSizeUtil.savaFileToSD(index + ".jpg", bytes);
                        if (index == 1) {
                            avlPath = savePath;
                        } else if (index == 2) {
                            cardPath = savePath;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private SexDialog sexDialog;

    public void showSexDialog(Activity context) {
        if (sexDialog != null && sexDialog.isShowing()) {
            return;
        }
        sexDialog = new SexDialog(mActivity, new SexDialog.DialogClickListener() {
            @Override
            public void onDialogClick() {
                sexIndex = 1;
                sex.setText("男");
            }
        }, new SexDialog.DialogClickListener() {
            @Override
            public void onDialogClick() {
                sexIndex = 2;
                sex.setText("女");
            }
        });
        sexDialog.show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void AttestaionSuccess() {
        if (h != null && h.equals("HOME")){
            setResult(4);
        }else {
            startActivity(new Intent(mActivity, HomeActivity.class));
        }
        finish();
    }

    @Override
    public void uploadSuccess(UploadBean.ListBean bean) {
        if (bean.getUrl() != null && bean.getUrl().size() == 2) {
            avlPath_server = bean.getUrl().get(0);
            cardPath_server = bean.getUrl().get(1);
            mPresenter.Attestaion(avlPath_server, name_str, sexIndex, phone_str, company_str, position_str, email_str, cardPath_server);
        } else if (bean.getUrl() != null && bean.getUrl().size() == 1) {
            cardPath_server = bean.getUrl().get(0);
            mPresenter.Attestaion("", name_str, sexIndex, phone_str, company_str, position_str, email_str, cardPath_server);
        }
    }
}
