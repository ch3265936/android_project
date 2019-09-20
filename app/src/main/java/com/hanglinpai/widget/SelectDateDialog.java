package com.hanglinpai.widget;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.TextView;

import com.hanglinpai.R;
import com.hanglinpai.ui.bean.ItemSelectBean;
import com.hanglinpai.ui.home.adapter.HomeDataAdapter;
import com.hanglinpai.ui.order.adapter.SelectAdapter;
import com.hanglinpai.ui.order.adapter.ServiceTimeAdapter;
import com.hanglinpai.util.DensityUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import it.gmariotti.recyclerview.itemanimator.SlideInOutBottomItemAnimator;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

/**
 * @author chihai
 * @description 通用消息对话框
 */
public class SelectDateDialog extends Dialog {
    private ViewGroup contentView;
    private TextView btnConfirm;
    private TextView btnCancel;
    private TextView signalBtnConfirm;
    private TextView titleView;
    private Activity context;
    private TextView contentTextView;
    private RecyclerView recycleView;
    private ServiceTimeAdapter adapter;
    private Set<String> set = new HashSet<>();//临时选中高亮
    private List<String> data = new ArrayList<>();
    private List<String> backData = new ArrayList<>();
    private GridLayoutManager mLayoutManager;

    public interface DialogClickListener {
        void onDialogClick();
    }

    public interface ComfirmListenner {
        void comfirm(List<String> backDate);
    }


    public SelectDateDialog(Context context, List<String> expert_service_time, String contentMsg, int confirmText, int cancelText,
                            int dialogWidth, ComfirmListenner confirmClick, DialogClickListener cancelClick) {
        super(context, R.style.Base_Theme_AppCompat_Dialog);
        initDialogStyle(expert_service_time, contentMsg, confirmText, cancelText, dialogWidth, confirmClick, cancelClick);
    }

private List<String> position_str_list =new ArrayList<>();
    private void initDialogStyle(List<String> expert_service_time, String contentMsg, int confirmText, int cancelText, int dialogWidth,
                                 final ComfirmListenner confirmClick, final DialogClickListener cancelClick) {
        setContentView(createDialogView(R.layout.dialog_select_date_layout));
        this.data.clear();
        this.set.clear();
        this.data.addAll(expert_service_time);
        setParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        contentTextView = (TextView) findChildViewById(R.id.message);
        btnConfirm = (TextView) findChildViewById(R.id.confirm_btn);
        btnConfirm.setText(R.string.appointment);
        recycleView = (RecyclerView) findChildViewById(R.id.recycleView);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (backData != null && backData.size() > 0) {
                    for(String a :data){
                     if(backData.contains(a)){
                         position_str_list.add(a);
                     }
                    }
                    confirmClick.comfirm(position_str_list);
                    dismiss();
                } else {
                    ToastUtils.showShort(R.string.expert_no_yj);
                }
            }
        });
        btnCancel = (TextView) findChildViewById(R.id.cancle_btn);
        btnCancel.setText(R.string.cancer);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelClick.onDialogClick();
                dismiss();
            }
        });
        contentTextView.setText("专家报价：" + contentMsg);
        setupRecyclerView(recycleView);

    }

    private void setupRecyclerView(RecyclerView recycleView) {
        //添加Divider
        recycleView.addItemDecoration(new Divider(
                //分割线宽1dp
                DensityUtil.dp2px(0),
                //分割线颜色#DDDDDD
                Color.parseColor("#f6f7f6"),
                false,
                //分割线左侧留出20dp的空白，不绘制
                DensityUtil.dp2px(0), 0, 0, 0));
        mLayoutManager = new GridLayoutManager(context, 2);
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setNestedScrollingEnabled(false);
        adapter = new ServiceTimeAdapter();
        adapter.setList(data, set);
        AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(adapter, recycleView);
        recycleView.setItemAnimator(new SlideInOutBottomItemAnimator(recycleView));
        recycleView.setAdapter(animatorAdapter);
        adapter.setOnItemClickListener(new HomeDataAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (set.contains(data.get(postion))) {
                    set.remove(data.get(postion));
                    for (int i = 0; i < backData.size(); i++) {
                        if (backData.get(i).equals(data.get(postion))) {
                            backData.remove(i);
                        }
                    }
                } else {
                    set.add(data.get(postion));
                    backData.add(data.get(postion));
                }

                adapter.notifyDataSetChanged();

            }
        });

    }

    public void setParams(int width, int height) {
        WindowManager.LayoutParams dialogParams = this.getWindow().getAttributes();
        dialogParams.width = (width);
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

    public void setPositiveButtonColor(int color) {
        signalBtnConfirm.setTextColor(color);
    }
}