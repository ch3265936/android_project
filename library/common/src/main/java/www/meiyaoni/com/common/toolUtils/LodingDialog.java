package www.meiyaoni.com.common.toolUtils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import www.meiyaoni.com.common.R;


/**
 * @author chihai
 * @description 通用消息对话框
 */
public class LodingDialog extends Dialog {
    private ViewGroup contentView;
    private Button to_do;
    private ImageView close;
    private TextView title;
    private TextView message;
    private ImageView bg;

    public interface DialogClickListener {
        void onDialogClick();
    }


    public LodingDialog(Context context) {
        super(context, R.style.Dialog_loding);
        initDialogStyle();
    }

    private void initDialogStyle() {
        LodingDialog.this.getWindow().setBackgroundDrawableResource(R.color.transparent);
        setContentView(createDialogView(R.layout.dialog_lording));
        setParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
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