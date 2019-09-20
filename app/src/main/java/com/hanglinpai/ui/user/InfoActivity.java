package com.hanglinpai.ui.user;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hanglinpai.R;
import com.hanglinpai.api.ApiConstants;
import com.hanglinpai.app.AppApplication;
import com.hanglinpai.ui.bean.LoginBean;
import com.hanglinpai.ui.bean.UploadBean;
import com.hanglinpai.ui.home.HomeActivity;
import com.hanglinpai.ui.user.constract.UserConstract;
import com.hanglinpai.ui.user.constract.UserInfoConstract;
import com.hanglinpai.ui.user.model.UserInfoModel;
import com.hanglinpai.ui.user.presenter.UserInfoPresenter;
import com.hanglinpai.util.DensityUtil;
import com.hanglinpai.util.FileSizeUtil;
import com.hanglinpai.util.SystemUtils;
import com.hanglinpai.widget.RoundedImageView;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yuyh.library.imgsel.ImgSelActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;
import www.meiyaoni.com.common.base.BaseActivity;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

public class InfoActivity extends BaseActivity<UserInfoPresenter, UserInfoModel> implements UserInfoConstract.View {
    @Bind(R.id.title_back)
    TextView title_back;
    @Bind(R.id.title_title)
    TextView title_title;
    @Bind(R.id.img_card)
    ImageView img_card;
    @Bind(R.id.avl)
    RoundedImageView avl;
    @Bind(R.id.phone)
    TextView phone;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.company)
    TextView company;
    @Bind(R.id.position)
    TextView position;
    @Bind(R.id.email)
    TextView email;
    @Bind(R.id.sex)
    TextView sex;
    @Bind(R.id.r_top)
    RelativeLayout r_top;
    private int sexIndex = 0;//1 2
    private int index = 0;
    private String avlPath = "", cardPath = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_info;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        title_title.setText(R.string.editInfo);
        StatusBarTranslucent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            r_top.setPadding(0, DensityUtil.getStatusHeight(mActivity), 0, 0);
        }
        mPresenter.getUser(true);
    }

    @OnClick({R.id.title_back, R.id.avl})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                Intent i = new Intent(mActivity, HomeActivity.class);
                i.putExtra("newAvl", newAvl);
                setResult(2, i);
                finish();
                break;
            case R.id.avl:
                index = 1;
                RxPermissionsPhoto();
                break;
            default:
                break;
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
            }
            Glide.with(mActivity).load(photoPathList.get(0)).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
                @Override
                public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                    try {
                        String savePath = FileSizeUtil.savaFileToSD(index + ".jpg", bytes);
                        if (index == 1) {
                            avlPath = savePath;
                            List<String> list = new ArrayList<>();
                            list.add(avlPath);
                            mPresenter.upload(list);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private String newAvl = "";

    @Override
    public void uploadSuccess(UploadBean.ListBean bean) {
        if (bean.getUrl() != null && bean.getUrl().get(0) != null) {
            newAvl = bean.getUrl().get(0);
            mPresenter.editUserInfo(bean.getUrl().get(0), 0);

        }
    }

    @Override
    public void editUserInfo() {
        if(HomeActivity.userinfo!=null){
            HomeActivity.userinfo.setAvatar(newAvl);
        }
        ToastUtils.showShort(R.string.edit_success);
        Intent i = new Intent(mActivity, HomeActivity.class);
        i.putExtra("newAvl", newAvl);
        setResult(2, i);
        finish();
    }

    @Override
    public void getUser(LoginBean.ListBean listBean) {
        showInfo(listBean);
    }

    private void showInfo(LoginBean.ListBean bean) {

        if (bean.getAvatar() != null) {
            Glide.with(AppApplication.getInstance()).load(ApiConstants.IMG_URL + bean.getAvatar())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .error(R.mipmap.ic_user_def)
                    .into(avl);
        }
        if (bean.getAccount() != null) {
            phone.setText(bean.getAccount());
        }
        if (bean.getName() != null){
            name.setText(bean.getName());
        }
        if (bean.getSex() ==2){
            sex.setText("女");
        }else{
            sex.setText("男");
        }
        if (bean.getCompany_name() != null){
            company.setText(bean.getCompany_name());
        }
        if (bean.getPosition_name() != null){
            position.setText(bean.getPosition_name());
        }
        if (bean.getEmail() != null){
            email.setText(bean.getEmail());
        }
        if (bean.getBusiness_card() != null) {
            Glide.with(AppApplication.getInstance()).load(ApiConstants.IMG_URL + bean.getBusiness_card())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .error(R.color.background)
                    .into(img_card);
        }
    }
}
