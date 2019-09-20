package com.hanglinpai.ui.order.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hanglinpai.R;
import com.hanglinpai.api.ApiConstants;
import com.hanglinpai.app.AppApplication;
import com.hanglinpai.ui.bean.Expert;
import com.hanglinpai.ui.bean.HistoryPass;
import com.hanglinpai.ui.bean.ItemSelectBean;
import com.hanglinpai.ui.home.adapter.HomeDataAdapter;
import com.hanglinpai.util.DensityUtil;
import com.hanglinpai.widget.Divider;
import com.hanglinpai.widget.FullyLinearLayoutManager;
import com.hanglinpai.widget.RoundedImageView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by chihai on 2017/6/16.
 */
public class HistoryPassAdapter extends RecyclerView.Adapter {
    private Activity context;
    public float scale = 0;
    private List<HistoryPass> data = new ArrayList<>();
    private FullyLinearLayoutManager mLayoutManager;

    public HistoryPassAdapter(Activity activity) {
        this.context = activity;
    }

    public void setList(List<HistoryPass> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_pass, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final TextVH textVH = (TextVH) holder;
        final List<Expert> selectList = new ArrayList<>();

        final HistoryPass historyPass = data.get(position);
        if (historyPass.getName() != null && historyPass.getName().length() > 0) {
            textVH.name.setText(historyPass.getName());
        }
        if (historyPass.getList() != null && historyPass.getList().size() > 0) {
            selectList.clear();
            selectList.addAll(historyPass.getList());
        }

        //添加Divider
        textVH.recycleView.addItemDecoration(new Divider(
                //分割线宽1dp
                DensityUtil.dp2px(0),
                //分割线颜色#DDDDDD
                Color.parseColor("#f6f7f6"),
                false,
                //分割线左侧留出20dp的空白，不绘制
                DensityUtil.dp2px(0), 0, 0, 0));
        mLayoutManager = new FullyLinearLayoutManager(context);
        textVH.recycleView.setLayoutManager(mLayoutManager);
        textVH.recycleView.setNestedScrollingEnabled(false);
        textVH.adapter.setList(selectList);
        textVH.adapter.setContext(context);
        textVH.recycleView.setAdapter(textVH.adapter);


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static final class TextVH extends RecyclerView.ViewHolder {
        TextVH(View v) {
            super(v);
            item = (LinearLayout) v.findViewById(R.id.item);
            name = v.findViewById(R.id.name);
            recycleView = v.findViewById(R.id.recycleView);
            adapter = new HistorySpecialAdapter();

        }

        HistorySpecialAdapter adapter;
        TextView name;
        LinearLayout item;
        RecyclerView recycleView;
    }

    MyItemClickListener mItemClickListener;


    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;

    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }



}
