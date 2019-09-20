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
import android.widget.TextView;

import com.hanglinpai.R;

/**
 * @author chihai
 * @description 通用消息对话框
 */
public class TodoDialog extends Dialog {
    private ViewGroup contentView;
    private Button to_do;
    private ImageView close;
    private TextView title;
    private TextView message;
    private ImageView bg;

    public interface DialogClickListener {
        void onDialogClick();
    }


    public TodoDialog(Context context, int title, int contentMsg, int todo,int res, DialogClickListener confirmClick, DialogClickListener cancelClick) {
        super(context, R.style.Dialog_todo);
        initDialogStyle(title, contentMsg, res, todo,confirmClick, cancelClick);
    }

    private void initDialogStyle(int t, int contenMsg, int res,int todo,
                                 final DialogClickListener confirmClick, final DialogClickListener cancelClick) {
        TodoDialog.this.getWindow().setBackgroundDrawableResource(R.color.transparent);
        setContentView(createDialogView(R.layout.dialog_todo_layout));
        setParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        to_do = (Button) findChildViewById(R.id.to_do);
        bg = (ImageView) findChildViewById(R.id.bg);
        bg.setBackgroundResource(res);
        title = (TextView) findChildViewById(R.id.title);
        title.setText(t);
        message = (TextView) findChildViewById(R.id.message);
        message.setText(contenMsg);
        to_do.setText(todo);
        to_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                confirmClick.onDialogClick();
            }
        });
        close = (ImageView) findChildViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                cancelClick.onDialogClick();
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