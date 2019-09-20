package com.hanglinpai.ui.order;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.hanglinpai.R;
import com.hanglinpai.api.ApiConstants;
import com.hanglinpai.app.AppApplication;
import com.hanglinpai.ui.bean.Expert;
import com.hanglinpai.ui.bean.ExpertBean;
import com.hanglinpai.ui.bean.HistoryPass;
import com.hanglinpai.ui.order.constract.SpecialConstract;
import com.hanglinpai.ui.order.model.SpecialModel;
import com.hanglinpai.ui.order.presenter.SpecialPresenter;
import com.hanglinpai.util.HttpDownloader;
import com.hanglinpai.util.SystemUtils;
import com.hanglinpai.widget.RoundedImageView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import rx.functions.Action1;
import www.meiyaoni.com.common.base.BaseActivity;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

public class SpecialistDetailActivity extends BaseActivity<SpecialPresenter, SpecialModel> implements SpecialConstract.View {
    @Bind(R.id.pullZoomView)
    PullToZoomScrollViewEx pullZoomView;
    @Bind(R.id.title_back)
    ImageView title_back;
    @Bind(R.id.share)
    ImageView share;
    @Bind(R.id.down)
    ImageView down;
    @Bind(R.id.r_bfh)
    RelativeLayout r_bfh;
    @Bind(R.id.close)
    ImageView close;
    @Bind(R.id.rs)
    TextView rs_tv;
    TextView name, birthday, sex, present, background_info;
    RoundedImageView iv_user_head;
    private String id, bfh, rs;
    private String shareUrl = "", downUrl = "";
    String path = "";
    String filename = "";
    private downloadMP3Thread thread;
    AppApplication App = AppApplication.getInstance();
    KProgressHUD hud;
    private final String[][] MIME_MapTable = {
            //{后缀名，MIME类型}
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},

    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_special_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);

    }

    public void setWindowBg(float bgAlpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        mActivity.getWindow().setAttributes(lp);
    }

    @Override
    public void initView() {
        id = getIntent().getStringExtra("id");
        bfh = getIntent().getStringExtra("bfh");
        rs = getIntent().getStringExtra("rs");
        if (bfh != null && bfh.equals("1")) {
            r_bfh.setVisibility(View.VISIBLE);
            if (rs != null && rs.length() > 0) {
                rs_tv.setText("不符合："+rs);
            }
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    r_bfh.setVisibility(View.GONE);
                }
            });
        } else {
            r_bfh.setVisibility(View.GONE);
        }
        if (id != null && id.length() > 0) {
            mPresenter.SpecialDetail(id);
        }
        StatusBarTranslucent();
        View headView = LayoutInflater.from(mContext).inflate(R.layout.layout_zoom_heard, null);
        iv_user_head = headView.findViewById(R.id.iv_user_head);
        View zoomView = LayoutInflater.from(mContext).inflate(R.layout.layout_zoom_view, null);

        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (downUrl != null && downUrl.length() > 0) {
                    path = downUrl;
                    if (filename.length() <= 0) {
                        filename = System.currentTimeMillis() + "";
                    }
                    setWindowBg(0.6f);
                    hud = KProgressHUD.create(mContext)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).show();
                    thread = new downloadMP3Thread();
                    thread.start();
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shareUrl != null && shareUrl.length() > 0) {
//                    RxPermissionsShare();
                    UMImage thub = new UMImage(mActivity, R.mipmap.share_logo);
                    String title = getString(R.string.share_title) + "-" + expert_id;
                    String content = getString(R.string.share_title);
                    UMWeb web = new UMWeb(shareUrl);
                    web.setTitle(title);//标题
                    web.setThumb(thub);  //缩略图
                    web.setDescription(content);//描述
                    new ShareAction(mActivity).withMedia(web).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                            .setCallback(umShareListener).open();
                }
            }
        });
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_zoom_content, null);
        name = contentView.findViewById(R.id.name);
        birthday = contentView.findViewById(R.id.birthday);
        sex = contentView.findViewById(R.id.sex);
        present = contentView.findViewById(R.id.present);
        background_info = contentView.findViewById(R.id.background_info);
        pullZoomView.setScrollContentView(contentView);
        pullZoomView.setHeaderView(headView);
        pullZoomView.setZoomView(zoomView);
        pullZoomView.setParallax(false);
    }


    @Override
    public void returnExpertSelectSuccess() {

    }

    @Override
    public void returnExpertInconformity() {

    }

    private String expert_id = "";

    @Override
    public void returnSpecialDetailSuccess(Expert bean) {
        showInfo(bean);
    }

    @Override
    public void returnSpecialListSuccess(List<Expert> data) {

    }

    @Override
    public void returnHistoryPassListSuccess(List<HistoryPass> data) {

    }

    private void showInfo(Expert bean) {
        if (bean.getExpert_no() != null) {
            name.setText(bean.getExpert_no());
        }
        if (bean.getId() != null) {
            expert_id = bean.getId();
        }
        int defoultAvl = 0;
        if (bean.getBirthday() != null) {
            if (bean.getSex() == 2) {
                sex.setText("性别：女");
                defoultAvl = R.mipmap.ic_expert_head_02;
            } else {
                sex.setText("性别：男");
                defoultAvl = R.mipmap.ic_expert_head_01;
            }

        }
        if (bean.getAvatar() != null) {
            Glide.with(AppApplication.getInstance()).load(ApiConstants.IMG_URL + bean.getAvatar())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .error(defoultAvl)
                    .into(iv_user_head);
        }
        if (bean.getBirthday() != null && bean.getBirthday().length() > 0) {
            birthday.setText("生日：" + bean.getBirthday());
        } else {
            birthday.setText("生日：暂无");
        }
        if (bean.getPosition() != null) {
            present.setText("现任：" + bean.getPosition());
        } else {
            present.setText("职位：暂无");
        }
        if (bean.getBackground() != null && bean.getBackground().length() > 0) {
            background_info.setText(bean.getBackground());
        } else {
            background_info.setText("暂无");
        }
        if (bean.getDown_url() != null && bean.getDown_url().length() > 0) {
            downUrl = bean.getDown_url();
            String a[] = bean.getDown_url().split("&");
            if (a.length > 0) {
                filename = a[a.length - 1];
            } else {
                if ((bean.getId() != null && bean.getId().length() > 0)) {
                    filename = bean.getId() + ".docx";
                }
            }

        }
        if (bean.getShare_url() != null) {
            shareUrl = bean.getShare_url();
        }

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            hud.dismiss();
            setWindowBg(1);
            if (msg.what == 1) {
                ToastUtils.showShort("简历已经存在");
                down.setImageResource(R.mipmap.icon_fs);
                down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openFile(new File(App.filePath + File.separator + filename));
                    }
                });
            } else if (msg.what == 0) {
                ToastUtils.showShort("简历下载完成");
                down.setImageResource(R.mipmap.icon_fs);
                down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openFile(new File(App.filePath + File.separator + filename));
                    }
                });
            } else {
                ToastUtils.showShort("简历下载失败");
            }


        }
    };

    /**
     * 打开文件
     *
     * @param file
     */
    private void openFile(File file) {

        try {
            Uri uri = null;
            if (Build.VERSION.SDK_INT >= 24) {
                uri = FileProvider.getUriForFile(this, "www.meiyaoni.com.common.fileprovider", file);
            } else {
                uri = Uri.fromFile(file);
            }
            String type = getMIMEType(file);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(uri, type);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    private String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
    /* 获取文件的后缀名*/
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "") return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) { //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }


    class downloadMP3Thread extends Thread {
        public void run() {
            HttpDownloader httpDownloader = new HttpDownloader();
            int downloadResult = httpDownloader.downloadFiles(
                    path, App.filePath, filename);
            System.out.println("下载结果：" + downloadResult);
            Log.i("999", downloadResult + "");
            if (downloadResult == 0) {
                mHandler.sendEmptyMessageDelayed(0, 0);
            } else {
                mHandler.sendEmptyMessageDelayed(downloadResult, 0);
            }
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();

    }

}
