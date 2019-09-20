package com.hanglinpai.util;

import android.app.Activity;
import android.content.Context;
import android.text.LoginFilter;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class SoftInputUtil {
    private static InputMethodManager mInputMethodManager;

    private SoftInputUtil() {

    }

    public static void hideInput(View v, Context context) {
        if (context == null) return;
        try {
            mInputMethodManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (mInputMethodManager != null && mInputMethodManager.isActive()
                    && v != null)
                mInputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),
                        0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showInput(View v) {
        try {
            v.requestFocus();
            mInputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            mInputMethodManager.showSoftInputFromInputMethod(
                    v.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
    public static void setMode(Activity act, int softInputAdjustResize) {
        try {
            act.getWindow().setSoftInputMode(softInputAdjustResize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 关闭软键盘
     *
     * @param
     * @param
     */
    public static void closeKeybord(EditText mEditText, Context mContext)
    {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }
    /**
     * 打卡软键盘
     *
     */
    public static void openKeybord(EditText mEditText, Context mContext)
    {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    public class MyInputFilter extends LoginFilter.UsernameFilterGMail {

        public MyInputFilter() {
            super();
        }

        @Override
        public boolean isAllowed(char c) {
            if (c == '@' || c == '_') {
                return true;
            }
            // Allow [a-zA-Z0-9@.]
            if ('0' <= c && c <= '9')
                return true;
            if ('a' <= c && c <= 'z')
                return true;
            if ('A' <= c && c <= 'Z')
                return true;
            return false;
        }

    }
}
