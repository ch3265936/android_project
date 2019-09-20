package com.hanglinpai.ui.home.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hanglinpai.R;
import com.hanglinpai.ui.bean.HomeOrderBean;
import com.hanglinpai.ui.bean.ItemSelectBean;
import com.hanglinpai.ui.bean.OrderDetail;
import com.hanglinpai.ui.bean.OrderListBean;
import com.hanglinpai.util.DataUtils;


/**
 * Created by chihai on 2017/6/16.
 */
public class HomeDataAdapter extends RecyclerView.Adapter {
    private Activity context;
    final int type0 = 0;
    final int type1 = 1;
    public float scale = 0;
    private LinearLayoutManager mLayoutManager;
    private List<OrderDetail> data = new ArrayList<>();

    public HomeDataAdapter(FragmentActivity activity) {
        this.context = activity;
    }

    public void setList(List<OrderDetail> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case type0:
                return new TextVH0(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_banner, parent, false));
            case type1:
                return new TextVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home1, parent, false));
        }
        return new TextVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home1, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return type0;
        } else {
            return type1;
        }

    }


    int status = 0;
    private OrderDetail od = null;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        if (viewType == type0) {
            final TextVH0 heard = (TextVH0) holder;
            float a = 30 - (scale * 20 / 255);
            heard.today.setTextSize(a);
            float b = scale / 255;
            heard.today.setAlpha(1 - b);
            heard.date.setAlpha(1 - b);
            heard.date.setText(DataUtils.getDate() + " " + DataUtils.getWeek());
        } else if (viewType == type1) {
            final TextVH textVH = (TextVH) holder;
            od = data.get(position - 1);
            if (od != null) {
                (textVH).other_tip.setVisibility(View.GONE);
                //相同部分
                if (od.getStatus_name() != null) {
                    textVH.status_tip.setText(od.getStatus_name());
                }
                if (od.getOrder_unread_msg() > 0) {
                    textVH.msg.setVisibility(View.VISIBLE);
                }else{
                    textVH.msg.setVisibility(View.GONE);
                }
                if (od.getIndustry_type_name() != null) {
                    textVH.title.setText(od.getIndustry_type_name());
                }
                if (od.getService_type_name() != null && od.getService_type_name().size() > 0) {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < od.getService_type_name().size(); i++) {
                        if (i == 0) {
                            sb.append(od.getService_type_name().get(i).name);
                        } else {
                            sb.append("、" + od.getService_type_name().get(i).name);
                        }
                    }
                    textVH.description.setText("服务类型："+sb.toString());
                }

                String time = "", server_time = "", server_type="",expert_name = "";
                if (od.getAdd_time_str_list() != null&&od.getAdd_time_str_list().length()>0) {
                    time = "下单时间：" + od.getAdd_time_str_list();
                }
                if (od.getView_time_str() != null ) {
                    server_time="服务时间：" +od.getView_time_str();
                }
                if(od.getView_type_name()!=null&&od.getView_type_name().length()>0){
                    server_type ="服务类型："+od.getView_type_name();
                }
                if (od.getExpert_name() != null&&od.getExpert_name().length()>0) {
                    expert_name = "专家：" + od.getExpert_name();
                }

                //不同部分
                status = od.getStatus();
                switch (status) {
                    case 1:
                        textVH.status_tip.setBackground(context.getResources().getDrawable(R.mipmap.btn_djd));
                        textVH.date.setText(time);

                        break;
                    case 2:
                        textVH.status_tip.setBackground(context.getResources().getDrawable(R.mipmap.btn_djd));
                        textVH.date.setText(time);

                        break;
                    case 3:
                        textVH.status_tip.setBackground(context.getResources().getDrawable(R.mipmap.btn_xzj));
                        textVH.date.setText(time);

                        break;
                    case 4:
                        textVH.status_tip.setBackground(context.getResources().getDrawable(R.mipmap.btn_djd));
                        textVH.date.setText(time);

                        break;
                    case 5:
                        textVH.status_tip.setBackground(context.getResources().getDrawable(R.mipmap.btn_djd));
                        textVH.date.setText(time);
                        (textVH).other_tip.setVisibility(View.VISIBLE);
                        textVH.other_tip.setText(expert_name);
                        break;
                    case 6:
                        textVH.status_tip.setBackground(context.getResources().getDrawable(R.mipmap.btn_xzj));
                        textVH.date.setText(time);
                        (textVH).other_tip.setVisibility(View.VISIBLE);
                        textVH.other_tip.setText(expert_name);
                        textVH.description.setText(server_type);
                        break;
                    case 7:
                        textVH.status_tip.setBackground(context.getResources().getDrawable(R.mipmap.btn_jxz));
                        textVH.date.setText(server_time);
textVH.description.setText(server_type);
                        (textVH).other_tip.setVisibility(View.VISIBLE);
                        textVH.other_tip.setText(expert_name);
                        break;
                    case 8:
                        textVH.status_tip.setBackground(context.getResources().getDrawable(R.mipmap.btn_jxz));
                        textVH.date.setText(server_time);
                        textVH.description.setText(server_type);
                        (textVH).other_tip.setVisibility(View.VISIBLE);
                        textVH.other_tip.setText(expert_name);
                        break;
                    case 9:
                        textVH.status_tip.setBackground(context.getResources().getDrawable(R.mipmap.btn_yqx));
                        textVH.date.setText(time);
                        break;
                    case 10:
                        textVH.status_tip.setBackground(context.getResources().getDrawable(R.mipmap.btn_xzj));
                        textVH.date.setText(server_time);
                        (textVH).other_tip.setVisibility(View.VISIBLE);
                        textVH.other_tip.setText(expert_name);
                        textVH.description.setText(server_type);
                        break;
                    case 11:
                        textVH.status_tip.setBackground(context.getResources().getDrawable(R.mipmap.btn_yqx));
                        textVH.date.setText(server_time);
                        (textVH).other_tip.setVisibility(View.VISIBLE);
                        textVH.other_tip.setText(expert_name);
                        textVH.description.setText(server_type);
                        break;

                    case 12:
                        textVH.status_tip.setBackground(context.getResources().getDrawable(R.mipmap.btn_yqx));
                        textVH.date.setText(time);
                        break;

                }
            }
            textVH.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(view, position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    static final class TextVH0 extends RecyclerView.ViewHolder {
        TextVH0(View v) {
            super(v);
            today = v.findViewById(R.id.today);
            date = v.findViewById(R.id.date);

        }

        TextView today, date;
    }


    static final class TextVH extends RecyclerView.ViewHolder {
        TextVH(View v) {
            super(v);
            item = (FrameLayout) v.findViewById(R.id.item);
            date = v.findViewById(R.id.date);
            title = v.findViewById(R.id.title);
            description = v.findViewById(R.id.description);
            other_tip = v.findViewById(R.id.other_tip);
            status_tip = v.findViewById(R.id.status_tip);
            msg = v.findViewById(R.id.msg);
        }

        FrameLayout item;
        TextView msg, status_tip, date, title, description, other_tip;
    }

    MyItemClickListener mItemClickListener;

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }

    private String showMulDate(List<String> muldate) {

        StringBuffer sb = new StringBuffer();
        List<Long> l = new ArrayList<>();
        for (String s : muldate) {
            l.add(Long.parseLong(s));
        }
        Long[] array = l.toArray(new Long[0]);
        Arrays.sort(array);
        for (Long a : array) {
            sb.append(DataUtils.stampToDate3(a + "") + "、");
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }
}
