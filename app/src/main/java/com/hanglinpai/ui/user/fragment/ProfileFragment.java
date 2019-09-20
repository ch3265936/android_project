package com.hanglinpai.ui.user.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hanglinpai.R;
import com.hanglinpai.api.ApiConstants;
import com.hanglinpai.app.AppApplication;
import com.hanglinpai.ui.bean.LoginBean;
import com.hanglinpai.ui.home.HomeActivity;
import com.hanglinpai.ui.home.MessageListActivity;
import com.hanglinpai.ui.setting.AboutActivity;
import com.hanglinpai.ui.setting.FeedbackActivity;
import com.hanglinpai.ui.setting.SettingActivity;
import com.hanglinpai.ui.user.AttestationActivity;
import com.hanglinpai.ui.user.InfoActivity;
import com.hanglinpai.util.DensityUtil;
import com.hanglinpai.widget.RoundedImageView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.Bind;
import butterknife.OnClick;
import www.meiyaoni.com.common.base.BaseFragment;
import www.meiyaoni.com.common.toolUtils.ToastUtils;


/**
 * Created by nick on 15/10/21.
 */
public class ProfileFragment extends BaseFragment {
    @Bind(R.id.massage)
    ImageView massage;

    @Bind(R.id.r_about)
    RelativeLayout r_about;
    @Bind(R.id.r_feedback)
    RelativeLayout r_feedback;
    @Bind(R.id.r_setting)
    RelativeLayout r_setting;
    @Bind(R.id.r_share)
    RelativeLayout r_share;
    @Bind(R.id.r_top)
    RelativeLayout r_top;
    @Bind(R.id.avl)
    RoundedImageView avl;
    @Bind(R.id.account)
    TextView account;
    @Bind(R.id.info)
    TextView info;
    @Bind(R.id.point)
    ImageView point;
    public static int TOUSERINFO = 2;
    public static int TOMESSAGE = 3;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            r_top.setPadding(0, DensityUtil.getStatusHeight(getActivity()), 0, 0);
        }
        if (HomeActivity.userinfo != null) {
            showInfo(HomeActivity.userinfo);
        }
    }


    @OnClick({R.id.massage, R.id.r_about, R.id.r_feedback, R.id.r_setting, R.id.r_top,R.id.r_share})
    public void click(View v) {
        switch (v.getId()) {

            case R.id.r_top:
                toUserInfo();
                break;
            case R.id.massage:
                getActivity().startActivityForResult(new Intent(getActivity(), MessageListActivity.class), TOMESSAGE);
                point.setVisibility(View.GONE);
                break;
            case R.id.r_about:
                startActivity(new Intent(getActivity(), AboutActivity.class));

                break;
            case R.id.r_feedback:
                startActivity(new Intent(getActivity(), FeedbackActivity.class));

                break;
            case R.id.r_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));

                break;
            case R.id.r_share:


                break;
            default:
                break;
        }

    }

    public void showInfo(LoginBean.ListBean bean) {
        if (account!=null&&bean.getAccount() != null) {
            account.setText(bean.getAccount());
        }
        if (avl!=null&&bean.getAvatar() != null) {
            Glide.with(AppApplication.getInstance()).load(ApiConstants.IMG_URL + bean.getAvatar())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .error(R.mipmap.ic_user_def)
                    .into(avl);
        }
        if(info!=null) {
            if (bean.getAuth_status() == 2 || bean.getAuth_status() == 3) {
                info.setText(R.string.attention_success);
                Drawable drawable = getResources().getDrawable(R.mipmap.icon_renzheng);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                info.setCompoundDrawables(drawable, null, null, null);
            }else if(bean.getAuth_status()==1){
                info.setText(R.string.audit);
            }
        }
        if(point!=null) {
            if (bean.getUnread_msg() > 0){
                point.setVisibility(View.VISIBLE);
            } else{
                point.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == TOUSERINFO && resultCode == TOUSERINFO) {
            String newAvl = data.getStringExtra("newAvl");
            if (newAvl != null && newAvl.length() > 0) {//修改頭像 才刷新  //数据变化多  直接采用获取用户信息
                Glide.with(AppApplication.getInstance()).load(ApiConstants.IMG_URL + newAvl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .crossFade()
                        .error(R.mipmap.ic_user_def)
                        .into(avl);
            }
        }
        if (data != null && requestCode == TOMESSAGE && resultCode == TOMESSAGE) {
            point.setVisibility(View.GONE);
        }

    }
    public void toUserInfo() {
        if (HomeActivity.userinfo != null) {
            if (HomeActivity.userinfo.getAuth_status() == 0) {
                tuAttestation();
            } else {
                getActivity().startActivityForResult(new Intent(getActivity(), InfoActivity.class), TOUSERINFO);
            }
        }

    }

    //未填写资料 部分操作跳转填写资料
    private void tuAttestation() {
        Intent i = new Intent(getActivity(), AttestationActivity.class);
        i.putExtra("HOME", "HOME");
        i.putExtra("phone", HomeActivity.userinfo.getPhone());
        getActivity().startActivityForResult(i, 4);
        ToastUtils.showShotToast(getActivity().getResources().getString(R.string.reg_no_attetion));
    }

    private void share(){
        UMImage thub = new UMImage(getActivity(), R.mipmap.ic_launcher);
        String title = getString(R.string.share_title) ;
        String content = getString(R.string.share_title);
        UMWeb web = new UMWeb("");
        web.setTitle(title);//标题
        web.setThumb(thub);  //缩略图
        web.setDescription(content);//描述
        new ShareAction(getActivity()).withMedia(web).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(umShareListener).open();
    }
    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showShotToast("成功");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showShotToast("分享失败");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showShotToast("分享取消");

        }
    };
}


