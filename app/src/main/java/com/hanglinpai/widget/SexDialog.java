package com.hanglinpai.widget;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanglinpai.R;

/**
 * @author chihai
 * @description 通用消息对话框
 */
public class SexDialog extends Dialog {
    private ViewGroup contentView;
    private TextView man,women;
    private TextView cancer;


    public interface DialogClickListener {
        void onDialogClick();
    }


    public SexDialog(Context context, DialogClickListener man, DialogClickListener women) {
        super(context, R.style.Dialog_todo);
        initDialogStyle(man, women);
    }

    private void initDialogStyle(
                                 final DialogClickListener manClick, final DialogClickListener womenClick) {
        SexDialog.this.getWindow().setBackgroundDrawableResource(R.color.transparent);
        setContentView(createDialogView(R.layout.dialog_sex_layout));
        setParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        man= (TextView) findChildViewById(R.id.man);
        women= (TextView) findChildViewById(R.id.women);
        cancer= (TextView) findChildViewById(R.id.cancer);

        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                manClick.onDialogClick();
            }
        });
        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                womenClick.onDialogClick();
            }
        });

        cancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void setParams(int width, int height) {
        WindowManager.LayoutParams dialogParams = this.getWindow().getAttributes();
        dialogParams.width = width;

        dialogParams.height = height;
        this.getWindow().setAttributes(dialogParams);
        this.getWindow().setGravity(Gravity.BOTTOM);
    }

    private ViewGroup createDialogView(int layoutId) {
        contentView = (ViewGroup) LayoutInflater.from(getContext()).inflate(layoutId, null);
        return contentView;
    }

    public View findChildViewById(int id) {
        return contentView.findViewById(id);
    }


}