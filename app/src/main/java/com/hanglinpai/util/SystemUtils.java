package com.hanglinpai.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hanglinpai.R;
import com.hanglinpai.app.AppApplication;
import com.hanglinpai.widget.CommonDialog;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;


/**
 * Created by EWorld on 2017/10/27.
 */

public class SystemUtils {
    private static CommonDialog dialog;
    public static void startToSettingAuth(Context context){
        dialog = new CommonDialog(context, "提示", "您的应用缺少定位权限,请到系统设置收到开启", "确定", "取消", new CommonDialog.DialogClickListener() {
            @Override
            public void onDialogClick() {
                SystemUtils.getAppDetailSettingIntent(AppApplication.getInstance());
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public static void startToSettingAuth(Context context,String text){
        if(dialog!=null&&dialog.isShowing()){
            return;
        }
        dialog = new CommonDialog(context, "提示", text, "确定", "取消", new CommonDialog.DialogClickListener() {
            @Override
            public void onDialogClick() {
                SystemUtils.getAppDetailSettingIntent(AppApplication.getInstance());
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public static void dismissDioalog(){
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
            return;
        }

    }
    public static void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }
    // 两次点击按钮之间的点击间隔不能少于5000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 3000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        Log.i("999","999999");
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
    // 两次点击按钮之间的点击间隔不能少于5000毫秒
    private static final int MIN_CLICK_DELAY_TIME2 = 1000;
    private static long lastClickTime2;

    public static boolean isFastClickMonth() {
        boolean flag = false;
        Log.i("999","999999");
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime2) >= MIN_CLICK_DELAY_TIME2) {
            flag = true;
        }
        lastClickTime2 = curClickTime;
        return flag;
    }


    // 选择多张照片
    public static void SelectMulti(Activity context,int hasNum) {
        // 自定义图片加载器
        ImageLoader loader = new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                // TODO 在这边可以自定义图片加载库来加载ImageView，例如Glide、Picasso、ImageLoader等
                Glide.with(context).load(path).into(imageView);

            }

        };
// 自由配置选项
        ImgSelConfig config = new ImgSelConfig.Builder(context, loader)
                // 是否多选
                .multiSelect(true)
                // “确定”按钮背景色
                .btnBgColor(Color.parseColor("#FFFFFF"))
                // “确定”按钮文字颜色
                .btnTextColor(R.color.firstColor)
                // 返回图标ResId
                .backResId(R.mipmap.back)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(R.color.firstColor)
                .backResId(R.mipmap.back)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#FFFFFF"))
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(1, 1, 200, 200)
                .needCrop(false)
                // 第一个是否显示相机
                .needCamera(true)
                .rememberSelected(false)
                // 最大选择图片数量
                .maxNum(3-hasNum)
                .build();
        // 跳转到图片选择器
        ImgSelActivity.startActivity(context, config, 1);
    }

    // 选择单张照片
    public static void SelectSinge(Activity context,int a,int b) {
        // 自定义图片加载器
        ImageLoader loader = new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                // TODO 在这边可以自定义图片加载库来加载ImageView，例如Glide、Picasso、ImageLoader等
                Glide.with(context).load(path).into(imageView);

            }

        };
// 自由配置选项
        ImgSelConfig config = new ImgSelConfig.Builder(context, loader)
                // 是否多选
                .multiSelect(false)
                // “确定”按钮背景色
                .btnBgColor(Color.parseColor("#FFFFFF"))
                // “确定”按钮文字颜色
                .btnTextColor(R.color.firstColor)
                // 返回图标ResId
                // 标题
                .title("图片")
                .backResId(R.mipmap.back)
                // 标题文字颜色
                .titleColor(R.color.firstColor)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#FFFFFF"))
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(a, b, a*8, b*8)
                .needCrop(true)
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(1)
                .build();
        // 跳转到图片选择器
        ImgSelActivity.startActivity(context, config, 1);
    }

}
