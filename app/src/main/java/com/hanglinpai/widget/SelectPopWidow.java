package com.hanglinpai.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hanglinpai.R;
import com.hanglinpai.ui.bean.ItemSelectBean;
import com.hanglinpai.ui.home.adapter.HomeDataAdapter;
import com.hanglinpai.ui.order.adapter.SelectAdapter;
import com.hanglinpai.util.DensityUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import it.gmariotti.recyclerview.itemanimator.SlideInOutBottomItemAnimator;


/**
 * Created by chihai on 2017/11/8.
 */
public class SelectPopWidow {
    private String id = "", name = "";
    private Activity context;
    private GridLayoutManager mLayoutManager;
    private SelectAdapter adapter;
    public static SelectPopWidow widow = null;
    private Set<String> set = new HashSet<>();//临时选中高亮
    private List<ItemSelectBean> data = new ArrayList<>();
    private List<ItemSelectBean> backData = new ArrayList<>();
    private boolean mulselect = false;//是否多选

    public static SelectPopWidow getInstance() {
        if (widow == null) {
            widow = new SelectPopWidow();
        }
        return widow;
    }

    /**
     * 显示popupWindow
     */
    public void showPopwindow(String t, List<ItemSelectBean> data, List<ItemSelectBean> bd, boolean mulselect, Activity activity, final ComfirmListenner listener) {
        this.Listener = listener;
        this.context = activity;
        this.data = data;//总数据源
        this.mulselect = mulselect;//多选
        this.backData = bd;//选中数据
        this.set.clear();
        for (int i = 0; i < bd.size(); i++) {//选择数据标识SET
            this.set.add(bd.get(i).id);
        }
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.v_select_pop, null);

        final GrayPopWindow window = new GrayPopWindow(context, view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        window.setFocusable(true);
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(activity.findViewById(R.id.r_top), Gravity.BOTTOM, 0, 0);

        RelativeLayout r_other = (RelativeLayout) view.findViewById(R.id.r_other);
        r_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(t);
        TextView textView = (TextView) view.findViewById(R.id.cancle);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();

            }
        });
        TextView confirm = (TextView) view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listener.comfirm(backData);
                window.dismiss();
            }
        });
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        setupRecyclerView(recyclerView);
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
        mLayoutManager = new GridLayoutManager(context, 3);
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setNestedScrollingEnabled(false);
        adapter = new SelectAdapter();
        adapter.setList(data, set);
        AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(adapter, recycleView);
        recycleView.setItemAnimator(new SlideInOutBottomItemAnimator(recycleView));
        recycleView.setAdapter(animatorAdapter);
        adapter.setOnItemClickListener(new HomeDataAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (mulselect) {
                    if (set.contains(data.get(postion).id)) {
                        set.remove(data.get(postion).id);
                        for (int i = 0; i < backData.size(); i++) {
                            if (backData.get(i).id.equals(data.get(postion).id)) {
                                backData.remove(i);
                            }
                        }
                    } else {
                        set.add(data.get(postion).id);
                        backData.add(data.get(postion));
                    }

                } else {//单选
                    if (!set.contains(data.get(postion).id)) {
                        set.clear();
                        backData.clear();
                        set.add(data.get(postion).id);
                        backData.add(data.get(postion));

                    }

                }
                adapter.notifyDataSetChanged();
            }
        });

    }

    private ComfirmListenner Listener;

    public interface ComfirmListenner {
        public void comfirm(List<ItemSelectBean> backDate);
    }

    public void setWindowBg(float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().setAttributes(lp);
    }



}
