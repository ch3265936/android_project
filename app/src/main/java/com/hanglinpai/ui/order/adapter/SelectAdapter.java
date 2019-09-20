package com.hanglinpai.ui.order.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hanglinpai.R;
import com.hanglinpai.app.AppApplication;
import com.hanglinpai.ui.bean.ItemSelectBean;
import com.hanglinpai.ui.home.adapter.HomeDataAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Administrator on 2017/3/7.
 */

public class SelectAdapter extends RecyclerView.Adapter {
    private List<ItemSelectBean> data = new ArrayList<>();
    private Set<String> set = new HashSet<>();
    private boolean showBg = true;

    public void setList(List<ItemSelectBean> data, Set<String> set) {
        this.data = data;
        this.set = set;
    }

    public void showBg(boolean a) {
        showBg =a;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Hold(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_item, parent, false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Hold hold = (Hold) holder;
        String name = data.get(position).name;
        if (showBg&&set.contains(data.get(position).id)) {
            hold.name.setBackgroundResource(R.drawable.item_click);
            hold.name.setTextColor(AppApplication.getAppContext().getResources().getColor(R.color.white));
        }else{
            hold.name.setBackgroundResource(R.drawable.item_no_click);
            hold.name.setTextColor(AppApplication.getAppContext().getResources().getColor(R.color.secondColor));
        }
        hold.name.setText(name);
        hold.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static final class Hold extends RecyclerView.ViewHolder {
        Hold(View v) {
            super(v);
            name = (TextView) itemView.findViewById(R.id.name);
        }

        private TextView name;


    }

    HomeDataAdapter.MyItemClickListener mItemClickListener;

    public void setOnItemClickListener(HomeDataAdapter.MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }


}
