package com.hanglinpai.ui.order.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.hanglinpai.ui.bean.ItemSelectBean;
import com.hanglinpai.ui.home.adapter.HomeDataAdapter;
import com.hanglinpai.ui.order.SpecialistDetailActivity;
import com.hanglinpai.util.DensityUtil;
import com.hanglinpai.widget.Divider;
import com.hanglinpai.widget.RoundedImageView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by chihai on 2017/6/16.
 */
public class HistorySpecialAdapter extends RecyclerView.Adapter {
    public float scale = 0;
    private Context context;
    private List<Expert> data = new ArrayList<>();

    public void setList(List<Expert> data) {
        this.data = data;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_special_history, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final TextVH textVH = (TextVH) holder;
        final List<ItemSelectBean> selectList = new ArrayList<>();

        final Expert expert = data.get(position);
        int defoultAvl;
        if (expert.getSex() == 2) {
            defoultAvl = R.mipmap.ic_expert_head_02;
        } else {
            defoultAvl = R.mipmap.ic_expert_head_01;
        }
        if (expert.getService_type_arr() != null && expert.getService_type_arr().size() > 0) {
            selectList.clear();
            selectList.addAll(expert.getService_type_arr());
        }
        if (expert != null) {
            if (expert.getAvatar() != null) {
                Glide.with(AppApplication.getInstance()).load(ApiConstants.IMG_URL + expert.getAvatar())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .crossFade()
                        .error(defoultAvl)
                        .into(textVH.img);
            }
            if (expert.getName() != null) {
                textVH.name.setText(expert.getName());
            }
            StringBuffer sb = new StringBuffer();

            if (expert.getSex() == 2) {
                sb.append("女 | ");
            } else if (expert.getSex() == 1) {
                sb.append("男 | ");
            }
            if (expert.getAge() > 0) {
                sb.append(expert.getAge() + "岁");
            }
            textVH.age.setText(sb.toString());
            if (expert.getPosition() != null) {
                textVH.desc.setText(expert.getPosition());
            }
        }
        if (data.get(position).getStatus() == 2) {
            textVH.inconformity.setVisibility(View.VISIBLE);
        } else {
            textVH.inconformity.setVisibility(View.GONE);
        }
        textVH.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expert.getId() != null) {

                    Intent i = new Intent(context, SpecialistDetailActivity.class);
                    i.putExtra("id", expert.getId());
                    if (expert.getStatus() == 2) {
                        i.putExtra("bfh", "1");
                        i.putExtra("rs",expert.getUnsuitable_reason());
                    } else {
                        i.putExtra("bfh", "0");
                    }
                    context.startActivity(i);
                }
            }
        });

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
            img = v.findViewById(R.id.img);
            name = v.findViewById(R.id.name);
            age = v.findViewById(R.id.age);
            desc = v.findViewById(R.id.desc);
            inconformity = v.findViewById(R.id.inconformity);


        }

        RoundedImageView img;
        ImageView inconformity;
        TextView name, age, desc;
        LinearLayout item;

    }

    MyItemClickListener mItemClickListener;


    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }


    private void setupRecyclerView(RecyclerView recycleView) {


    }

}
