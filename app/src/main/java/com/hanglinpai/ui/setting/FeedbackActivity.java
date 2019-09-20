package com.hanglinpai.ui.setting;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.hanglinpai.R;
import com.hanglinpai.app.AppApplication;
import com.hanglinpai.ui.bean.UploadBean;
import com.hanglinpai.ui.setting.adapter.AlarmImgAdapter;
import com.hanglinpai.ui.setting.constract.SettingConstract;
import com.hanglinpai.ui.setting.model.SettingModel;
import com.hanglinpai.ui.setting.presenter.SettingPresenter;
import com.hanglinpai.util.DensityUtil;
import com.hanglinpai.util.FileSizeUtil;
import com.hanglinpai.util.SystemUtils;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yuyh.library.imgsel.ImgSelActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;
import www.meiyaoni.com.common.base.BaseActivity;
import www.meiyaoni.com.common.baseapp.AppManager;
import www.meiyaoni.com.common.toolUtils.SPManager;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

public class FeedbackActivity extends BaseActivity<SettingPresenter, SettingModel> implements SettingConstract.View {
    @Bind(R.id.title_title)
    TextView title_title;
    @Bind(R.id.title_back)
    TextView title_back;
    @Bind(R.id.content)
    EditText content;
    @Bind(R.id.feedback)
    Button feedback;
    @Bind(R.id.photo_recycleView)
    RecyclerView photo_recycleView;
    @Bind(R.id.r_top)
    RelativeLayout r_top;
    private AlarmImgAdapter adapter;
    private List<String> photos = new ArrayList<>();
    private String content_str = "";

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initView() {
        StatusBarTranslucent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            r_top.setPadding(0, DensityUtil.getStatusHeight(mActivity), 0, 0);
        }
        title_title.setText(getResources().getString(R.string.feedback));

        if (adapter == null) {
            adapter = new AlarmImgAdapter();
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            photo_recycleView.setLayoutManager(manager);
            photos.add("add");
            adapter.setList(photos);
            photo_recycleView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        adapter.setOnImgClickListener(new AlarmImgAdapter.ImgOnClick() {
            @Override
            public void onClick(int position, int widht, int height, ImageView imageView) {
                if (photos.get(position).equals("add")) {
                    if (photos.size() - 1 < 3) {
                        RxPermissionsPhoto();
                    }
                    return;
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
            if (photos.size() == 0) {
                photos.clear();
                photos.add("add");
            }
            photos.addAll(photoPathList);
            adapter.notifyDataSetChanged();
        }
    }

    private void RxPermissionsPhoto() {
        RxPermissions.getInstance(mActivity).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            SystemUtils.SelectMulti(mActivity, photos.size() - 1);
                            SystemUtils.dismissDioalog();
                        } else {
                            //用户拒绝改权限
                            SystemUtils.startToSettingAuth(mActivity, "您的应用缺少相应权限,请到系统设置");
                        }
                    }
                });
    }

    @OnClick({R.id.title_back, R.id.feedback})
    public void click(View v) {
        switch (v.getId()) {

            case R.id.title_back:
                finish();
                break;
            case R.id.feedback:
                content_str = content.getText().toString().trim();
                if (content_str != null&&content_str.length()>9) {
                    if(photos!=null&&photos.size()>1){
                        photos.remove(0);
                        mPresenter.upload(photos);
                    }else{
                        mPresenter.feedBacks(content_str, null);
                    }

                }else{
                    ToastUtils.showShort(R.string.feedback_content);
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void feedBackSuccess() {
        finish();
    }



    @Override
    public void uploadSuccess(UploadBean.ListBean bean) {
        if (bean.getUrl() != null&&bean.getUrl().size()>0) {
            String[] imgs = new String[bean.getUrl().size()];
            bean.getUrl().toArray(imgs);
            mPresenter.feedBacks(content_str, bean.getUrl());
        }
    }
}
