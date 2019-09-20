package com.hanglinpai.widget;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hanglinpai.R;
import com.hanglinpai.api.ApiConstants;
import com.hanglinpai.app.AppApplication;

/**
 * @author chihai
 * @description 通用消息对话框
 */
public class EvaluateDialog extends Dialog {
    private ViewGroup contentView;
    private Button affirm;
    private ImageView close;
    private TextView type;
    private TextView time;
    private RoundedImageView avl;
    private RatingBar ratingBar, ratingBar2;
    private int sex = 1;

    public interface DialogClickListener {
        void onDialogClick(String a, String b);
    }

    public interface CancerDialogClickListener {
        void onCancerDialogClick();
    }

    public EvaluateDialog(Context context, String title, String contentMsg, String res, float a, float b, boolean showAffirm, DialogClickListener confirmClick, CancerDialogClickListener cancelClick) {
        super(context, R.style.Dialog_todo);
        initDialogStyle(title, contentMsg, res, a, b, showAffirm, confirmClick, cancelClick);
    }

    private void initDialogStyle(String t, String contenMsg, String res, float a, float b, boolean showAffirm,
                                 final DialogClickListener confirmClick, final CancerDialogClickListener cancelClick) {
        EvaluateDialog.this.getWindow().setBackgroundDrawableResource(R.color.transparent);
        setContentView(createDialogView(R.layout.dialog_evaluate_layout));
        setParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        affirm = (Button) findChildViewById(R.id.affirm);
        avl = (RoundedImageView) findChildViewById(R.id.avl);
        if (res != null && res.length() > 0) {
            Glide.with(AppApplication.getInstance()).load(ApiConstants.IMG_URL + res)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .error(R.mipmap.ic_expert_head_01)
                    .into(avl);
        } else {

        }

        type = (TextView) findChildViewById(R.id.type);
        type.setText("约见类型：" + t);
        time = (TextView) findChildViewById(R.id.time);
        time.setText("约见时间：" + contenMsg);
        ratingBar = (RatingBar) findChildViewById(R.id.ratingBar);
        ratingBar.setRating(a);
        ratingBar2 = (RatingBar) findChildViewById(R.id.ratingBar2);
        ratingBar2.setRating(b);
        if (!showAffirm) {
            affirm.setVisibility(View.GONE);
            ratingBar.setEnabled(false);
            ratingBar2.setEnabled(false);
        }
        affirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmClick.onDialogClick(ratingBar.getRating() + "", ratingBar2.getRating() + "");
                dismiss();
            }
        });
        close = (ImageView) findChildViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                cancelClick.onCancerDialogClick();
            }
        });
    }

    public void setParams(int width, int height) {
        WindowManager.LayoutParams dialogParams = this.getWindow().getAttributes();
        dialogParams.width = width;
        dialogParams.height = height;
        this.getWindow().setAttributes(dialogParams);
    }

    private ViewGroup createDialogView(int layoutId) {
        contentView = (ViewGroup) LayoutInflater.from(getContext()).inflate(layoutId, null);
        return contentView;
    }

    public View findChildViewById(int id) {
        return contentView.findViewById(id);
    }


}