package com.hanglinpai.ui.order.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanglinpai.R;
import com.hanglinpai.ui.bean.ItemSelectBean;
import com.hanglinpai.ui.bean.OrderProgress;
import com.hanglinpai.ui.home.adapter.HomeDataAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Administrator on 2017/3/7.
 */

public class OrderStatusAdapter extends RecyclerView.Adapter {
    private List<OrderProgress> data = new ArrayList<>();
    private boolean last;

    public void setList(List<OrderProgress> data, boolean last) {
        this.data = data;
        this.last = last;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Hold(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_status, parent, false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Hold hold = (Hold) holder;
        if (data.get(position).getStatus_name() != null) {
            hold.order_status.setText(data.get(position).getStatus_name());
        }
        if (data.get(position).getAdd_time() != null) {
            hold.time.setText(data.get(position).getAdd_time());
        }
        hold.order_point.setVisibility(View.VISIBLE);
        hold.dirver.setVisibility(View.VISIBLE);
        if (data.size() > 0) {
            if (position == data.size() - 1) {//最后一条 同时状态为结束状态
                if (last) {// 收尾状态同时最后一条
                    hold.order_point.setVisibility(View.VISIBLE);
                } else {
                    hold.order_point.setVisibility(View.INVISIBLE);
                }
                hold.dirver.setVisibility(View.GONE);
            }
        }
        if(data.size()==1){
            hold.dirver.setVisibility(View.GONE);
            hold.order_point.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static final class Hold extends RecyclerView.ViewHolder {
        Hold(View v) {
            super(v);
            order_status = (TextView) itemView.findViewById(R.id.order_status);
            time = (TextView) itemView.findViewById(R.id.time);
            dirver = (View) itemView.findViewById(R.id.dirver);
            order_point = (ImageView) itemView.findViewById(R.id.order_point);
        }

        private TextView time, order_status;
        private ImageView order_point;
        private View dirver;


    }

    HomeDataAdapter.MyItemClickListener mItemClickListener;

    public void setOnItemClickListener(HomeDataAdapter.MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }


}
