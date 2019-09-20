package com.hanglinpai.ui.order.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import com.hanglinpai.ui.bean.HomeOrderBean;
import com.hanglinpai.ui.bean.ItemSelectBean;
import com.hanglinpai.ui.home.adapter.HomeDataAdapter;
import com.hanglinpai.ui.order.SpeciaChangeActivity;
import com.hanglinpai.util.DensityUtil;
import com.hanglinpai.widget.Divider;
import com.hanglinpai.widget.RoundedImageView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import it.gmariotti.recyclerview.itemanimator.SlideInOutBottomItemAnimator;


/**
 * Created by chihai on 2017/6/16.
 */
public class SpecialAdapter extends RecyclerView.Adapter {
    private Activity context;
    final int type99 = 99;
    final int type1 = 1;
    public float scale = 0;
    private List<Expert> data = new ArrayList<>();
    private Set<String> inconformity = new HashSet<>();
    private GridLayoutManager mLayoutManager;
    private boolean mulselect = false;

    public SpecialAdapter(Activity activity) {
        this.context = activity;
    }

    public void setList(List<Expert> data) {
        this.data = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_special, parent, false));
    }


    RelativeLayout r_search;

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
        //添加Divider
        textVH.recycleView.addItemDecoration(new Divider(
                //分割线宽1dp
                DensityUtil.dp2px(0),
                //分割线颜色#DDDDDD
                Color.parseColor("#f6f7f6"),
                false,
                //分割线左侧留出20dp的空白，不绘制
                DensityUtil.dp2px(0), 0, 0, 0));
        mLayoutManager = new GridLayoutManager(context, 3);
        textVH.recycleView.setLayoutManager(mLayoutManager);
        textVH.recycleView.setNestedScrollingEnabled(false);
        textVH.adapter.setList(selectList, textVH.set);
//        AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(textVH.adapter, textVH.recycleView);
//        textVH.recycleView.setItemAnimator(new SlideInOutBottomItemAnimator(textVH.recycleView));

        textVH.recycleView.setAdapter(textVH.adapter);
        textVH.adapter.setOnItemClickListener(new HomeDataAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (mulselect) {

                } else {//单选
                    if (!textVH.set.contains(selectList.get(postion).id)) {
                        textVH.set.clear();
                        textVH.set.add(selectList.get(postion).id);
                    }else{

                    }

                }
                textVH.adapter.notifyDataSetChanged();
            }
        });

        if (data.get(position).getStatus()==2) {
            textVH.adapter.showBg(false);// 不符合 就不显示
            textVH.r_button.setVisibility(View.GONE);
            textVH.img_bfh.setVisibility(View.VISIBLE);
        } else {
            textVH.adapter.showBg(true);// 符合 不显示
            textVH.r_button.setVisibility(View.VISIBLE);
            textVH.img_bfh.setVisibility(View.GONE);
        }
        textVH.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectId = "";
                for (String a : textVH.set) {
                    selectId = a;
                }
                if(expert.getId()!=null) {
                    mItemCheckListener.onItemCheck(expert.getId(),selectId);
                }
            }
        });
        textVH.inconformity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expert.getId() != null)
                    myItemIncoformListener.onItemCheck(expert.getId());
            }
        });
        textVH.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view, position);
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
            select = v.findViewById(R.id.select);
            inconformity = v.findViewById(R.id.inconformity);
            r_button = v.findViewById(R.id.r_button);
            img_bfh = v.findViewById(R.id.img_bfh);
            recycleView = v.findViewById(R.id.recycleView);
            adapter = new SelectAdapter();
            set = new HashSet<>();
        }
        final Set<String> set ;
        SelectAdapter adapter;
        RoundedImageView img;
        ImageView img_bfh;
        TextView name, age, desc, inconformity, select;
        LinearLayout item;
        RelativeLayout r_button;
        RecyclerView recycleView;
    }

    MyItemClickListener mItemClickListener;
    MyItemCheckListener mItemCheckListener;
    MyItemIncoformListener myItemIncoformListener;

    public void setOnItemClickListener(MyItemClickListener listener, MyItemCheckListener check, MyItemIncoformListener myItemIncoformListener) {
        this.mItemClickListener = listener;
        this.mItemCheckListener = check;
        this.myItemIncoformListener = myItemIncoformListener;
    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }

    public interface MyItemCheckListener {
        public void onItemCheck(String id,String server_id);
    }

    public interface MyItemIncoformListener {
        public void onItemCheck(String id);
    }

    private void setupRecyclerView(RecyclerView recycleView) {


    }

}
