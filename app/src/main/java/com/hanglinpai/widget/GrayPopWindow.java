package com.hanglinpai.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by chihai on 2018/4/10.
 * 添加背景色开启关闭
 */

public class GrayPopWindow extends PopupWindow {
    private Activity activity;

    public GrayPopWindow(Activity activity,View contentView, int width, int height) {
        super(contentView, width, height);
        this.activity = activity;
    }

    public GrayPopWindow(Context context) {
        super(context);

    }

    public GrayPopWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GrayPopWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        setWindowBg(0.6f);
        super.showAtLocation(parent, gravity, x, y);

    }


    @Override
    public void dismiss() {
        setWindowBg(1);
        super.dismiss();
    }

    public void setWindowBg(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }

}
