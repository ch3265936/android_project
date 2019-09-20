package com.hanglinpai.ui.order.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hanglinpai.R;
import com.hanglinpai.app.AppApplication;
import com.hanglinpai.ui.bean.ItemSelectBean;
import com.hanglinpai.ui.home.adapter.HomeDataAdapter;
import com.hanglinpai.util.DataUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import www.meiyaoni.com.common.toolUtils.ToastUtils;


/**
 * Created by Administrator on 2017/3/7.
 */

public class ServiceTimeAdapter extends RecyclerView.Adapter {
    private List<String> data = new ArrayList<>();
    private Set<String> set = new HashSet<>();

    public void setList(List<String> data, Set<String> set) {
        this.data = data;
        this.set = set;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Hold(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_time, parent, false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Hold hold = (Hold) holder;
        final String name = data.get(position);
        hold.name.setText(name);
        if (set.contains(data.get(position))) {
            hold.img.setImageResource(R.mipmap.icon_agree_selected);
        } else {
            hold.img.setImageResource(R.mipmap.icon_agree);
        }
        hold.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passedDate(name)) {
                    ToastUtils.showShort(R.string.date_pass);
                    return;
                }
                mItemClickListener.onItemClick(view, position);
            }
        });
    }

    private boolean passedDate(String str) {
        if (str != null && str.length() > 2) {
            String d = str.substring(0,str.length() - 2);
            String y = DataUtils.getCurrentData0(System.currentTimeMillis());
            String a = y + "年" + d + " 13:00";//拼接当前时间段的年月日  --2018年6月10日 13:00
            String aa = DataUtils.dateToStamp3(a);//获取a时间戳
            String k = DataUtils.getYMD();
            String kk = DataUtils.dateToStamp4(k);//今天年月日的时间戳
            String p = DataUtils.dateToStamp4(y+ "年" + d);//标签年月日时间戳
            if (Long.parseLong(kk) > Long.parseLong(p)) {
                return true;//过期
            }
            if (str.contains("上午")) {
                if (System.currentTimeMillis() > Long.parseLong(aa)) {// 当前时间戳大于aa时间戳
                    return true;//如果是当天   并且过了13:00  也算过期
                }
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static final class Hold extends RecyclerView.ViewHolder {
        Hold(View v) {
            super(v);
            name = (TextView) itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.img);
            item = itemView.findViewById(R.id.item);
        }

        private TextView name;
        private ImageView img;
        private LinearLayout item;


    }

    HomeDataAdapter.MyItemClickListener mItemClickListener;

    public void setOnItemClickListener(HomeDataAdapter.MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }


}
