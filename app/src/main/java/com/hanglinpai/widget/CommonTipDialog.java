package com.hanglinpai.widget;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanglinpai.R;

/**
* @description 通用消息对话框
 * @author chihai
*/ 
public class CommonTipDialog extends Dialog
{
	private ViewGroup contentView;
	private TextView btnConfirm;
	private TextView btnCancel;
	private TextView signalBtnConfirm;
	private TextView titleView;
	private TextView contentTextView;

	public interface DialogClickListener
	{
		void onDialogClick();
	}


	public CommonTipDialog(Context context,  String contentMsg, int confirmText, int cancelText,
                           int dialogWidth, DialogClickListener confirmClick)
	{
		this(context, contentMsg, confirmText, cancelText, dialogWidth, confirmClick, null);
	}

	public CommonTipDialog(Context context, String title,String contentMsg, int confirmText, int cancelText,
						   int dialogWidth, DialogClickListener confirmClick, DialogClickListener cancelClick)
	{
		super(context, R.style.Base_Theme_AppCompat_Dialog);
		initDialogStyle( contentMsg, title,confirmText, cancelText, dialogWidth, confirmClick, cancelClick);
	}

	public CommonTipDialog(Context context, String title, int confirmText, int cancelText,
                           int dialogWidth, DialogClickListener confirmClick, DialogClickListener cancelClick)
	{
		super(context, R.style.Base_Theme_AppCompat_Dialog);

		initDialogStyle( title, "",confirmText, cancelText, dialogWidth, confirmClick, cancelClick);
	}

	private void initDialogStyle(String contentMsg,String title, int confirmText, int cancelText, int dialogWidth,
                                 final DialogClickListener confirmClick, final DialogClickListener cancelClick)
	{
		setContentView(createDialogView(R.layout.dialog_common));
		setParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		LinearLayout layout1 = (LinearLayout) findChildViewById(R.id.all_layout);
		LinearLayout layout2 = (LinearLayout) findChildViewById(R.id.signal_layout);
		titleView = (TextView) findChildViewById(R.id.title);
		contentTextView = (TextView) findChildViewById(R.id.message);
		contentTextView.setText(contentMsg);
		contentTextView.setGravity(Gravity.CENTER);
		if(title.length()>0){
			titleView.setText(title);
		}
		if (cancelText == 0)
		{
			layout1.setVisibility(View.GONE);
			layout2.setVisibility(View.VISIBLE);
			signalBtnConfirm = (TextView) findChildViewById(R.id.signal_confirm_btn);
			signalBtnConfirm.setText(confirmText);
			signalBtnConfirm.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					dismiss();
					confirmClick.onDialogClick();
				}
			});
		}
		else
		{
			layout1.setVisibility(View.VISIBLE);
			layout2.setVisibility(View.GONE);
			btnConfirm = (TextView) findChildViewById(R.id.confirm_btn);
			btnCancel = (TextView) findChildViewById(R.id.cancle_btn);
			btnConfirm.setText(confirmText);
			btnCancel.setText(cancelText);
			btnCancel.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					dismiss();
					if (cancelClick != null)
					{
						cancelClick.onDialogClick();
					}
				}
			});
			btnConfirm.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					dismiss();
					confirmClick.onDialogClick();
				}
			});
		}
	}

	public void setParams(int width, int height)
	{
		WindowManager.LayoutParams dialogParams = this.getWindow().getAttributes();
		dialogParams.width = width;
		dialogParams.height = height;
		this.getWindow().setAttributes(dialogParams);
	}

	private ViewGroup createDialogView(int layoutId)
	{
		contentView = (ViewGroup) LayoutInflater.from(getContext()).inflate(layoutId, null);
		return contentView;
	}

	public View findChildViewById(int id)
	{
		return contentView.findViewById(id);
	}

	public void setPositiveButtonColor(int color)
	{
		signalBtnConfirm.setTextColor(color);
	}
}