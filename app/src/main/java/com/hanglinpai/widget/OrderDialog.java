package com.hanglinpai.widget;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hanglinpai.R;
import com.hanglinpai.ui.bean.HomeOrderBean;
import com.hanglinpai.ui.bean.ItemSelectBean;
import com.hanglinpai.ui.bean.OrderProgress;
import com.hanglinpai.ui.home.adapter.HomeDataAdapter;
import com.hanglinpai.ui.order.adapter.OrderStatusAdapter;
import com.hanglinpai.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import it.gmariotti.recyclerview.itemanimator.SlideInOutBottomItemAnimator;

/**
 * @author chihai
 * @description 通用消息对话框
 */
public class OrderDialog extends Dialog {
    private ViewGroup contentView;
    private ImageView close;
    private RecyclerView recycleView;
    private FullyLinearLayoutManager mLayoutManager;
    private OrderStatusAdapter adapter;
    private Activity context;
    private List<OrderProgress> data = new ArrayList<>();
private boolean last ;
    public interface DialogClickListener {
        void onDialogClick();
    }


    public OrderDialog(Context context, List<OrderProgress> data,boolean last, DialogClickListener confirmClick, DialogClickListener cancelClick) {
        super(context, R.style.Dialog_todo);
        this.context = (Activity) context;
        this.data = data;
        this.last =last;
        initDialogStyle(confirmClick, cancelClick);
    }

    private void initDialogStyle(final DialogClickListener confirmClick, final DialogClickListener cancelClick) {
        OrderDialog.this.getWindow().setBackgroundDrawableResource(R.color.transparent);
        setContentView(createDialogView(R.layout.dialog_order_layout));
        setParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        recycleView = (RecyclerView) findChildViewById(R.id.recycleView);
        setupRecyclerView();
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

    private void setupRecyclerView() {
        //添加Divider
        recycleView.addItemDecoration(new Divider(
                //分割线宽1dp
                DensityUtil.dp2px(0),
                //分割线颜色#DDDDDD
                Color.parseColor("#f6f7f6"),
                false,
                //分割线左侧留出20dp的空白，不绘制
                DensityUtil.dp2px(0), 0, 0, 0));
        mLayoutManager = new FullyLinearLayoutManager(context);
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setNestedScrollingEnabled(false);
        adapter = new OrderStatusAdapter();

        adapter.setList(data, last);
        recycleView.setAdapter(adapter);
        if(data!=null&&data.size()>0) {
            recycleView.smoothScrollToPosition(data.size() - 1);
        }

    }


}