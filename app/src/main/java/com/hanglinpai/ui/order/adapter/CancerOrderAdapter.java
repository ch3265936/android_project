package com.hanglinpai.ui.order.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hanglinpai.R;
import com.hanglinpai.ui.bean.Expert;
import com.hanglinpai.ui.bean.ItemSelectBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by chihai on 2017/6/16.
 */
public class CancerOrderAdapter extends RecyclerView.Adapter {
    private Activity context;
    public float scale = 0;
    private LinearLayoutManager mLayoutManager;
    private List<ItemSelectBean> data = new ArrayList<>();
    public String set = "";

    public CancerOrderAdapter(Activity activity) {
        this.context = activity;
    }

    public void setList(List<ItemSelectBean> data) {
        this.data = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new TextVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_cancer, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final TextVH textVH = (TextVH) holder;
        if (set.equals(data.get(position).id)) {
            textVH.check.setChecked(true);
        } else {
            textVH.check.setChecked(false);
        }
        if (data.get(position).name != null) {
            textVH.name.setText(data.get(position).name);
        }

        textVH.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (set.equals(data.get(position).id)) {
                    set = "";
                } else {
                    set = data.get(position).id;
                }
                mItemCheckListener.onItemCheck(set);
                notifyDataSetChanged();
            }
        });


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
            check = v.findViewById(R.id.check);
        }

        LinearLayout item;
        TextView name;
        CheckBox check;
    }

    MyItemClickListener mItemClickListener;
    MyItemCheckListener mItemCheckListener;

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setOnItemCheck(MyItemCheckListener listener) {
        this.mItemCheckListener = listener;
    }


    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }

    public interface MyItemCheckListener {
        public void onItemCheck(String id);
    }


}
