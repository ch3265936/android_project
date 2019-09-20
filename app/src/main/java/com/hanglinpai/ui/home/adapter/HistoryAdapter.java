package com.hanglinpai.ui.home.adapter;

import android.app.Activity;
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
import android.widget.TextView;

import com.hanglinpai.R;
import com.hanglinpai.ui.bean.ItemSelectBean;
import com.hanglinpai.ui.bean.OrderDetail;
import com.hanglinpai.util.DataUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by chihai on 2017/6/16.
 */
public class HistoryAdapter extends RecyclerView.Adapter {
    private Activity context;
    final int type0 = 0;
    final int type1 = 1;
    public float scale = 0;
    private LinearLayoutManager mLayoutManager;
    private List<OrderDetail> data = new ArrayList<>();

    public HistoryAdapter(Activity activity) {
        this.context = activity;
    }

    public void setList(List<OrderDetail> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home1, parent, false));
    }

    int status = 0;
    private OrderDetail od = null;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        final TextVH textVH = (TextVH) holder;
        od = data.get(position);
        if (od != null) {
            (textVH).other_tip.setVisibility(View.GONE);
            //相同部分
            if (od.getStatus_name() != null) {
                textVH.status_tip.setText(od.getStatus_name());
            }

            if (od.getIndustry_type_name() != null) {
                textVH.title.setText(od.getIndustry_type_name());
            }
            if (od.getService_type_name() != null && od.getService_type_name().size() > 0) {
                StringBuffer sb = new StringBuffer();

                for(int i =0;i<od.getService_type_name().size();i++){
                    if(i==0){
                        sb.append(od.getService_type_name().get(i).name);
                    }else{
                        sb.append("、"+od.getService_type_name().get(i).name);
                    }
                }

                textVH.description.setText("服务类型："+sb.toString());
            }
            String time = "", server_time = "",server_type="", expert_name = "";
            if (od.getAdd_time_str_list() != null&&od.getAdd_time_str_list().length()>0) {
                time = "下单时间："+od.getAdd_time_str_list();
            }
            if (od.getView_time_str() != null ) {
                server_time="服务时间：" +od.getView_time_str();
            }
            if(od.getView_type_name()!=null&&od.getView_type_name().length()>0){
                server_type ="服务类型："+od.getView_type_name();
            }
            if (od.getExpert_name() != null&&od.getExpert_name().length()>0) {
                expert_name = "专家："+ od.getExpert_name();
            }
            textVH.status_tip.setTextColor(context.getResources().getColor(R.color.white));
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
                    textVH.description.setText(server_type);
                    (textVH).other_tip.setVisibility(View.VISIBLE);
                    textVH.other_tip.setText(expert_name);
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

                case 13:
                    textVH.status_tip.setBackground(context.getResources().getDrawable(R.mipmap.btn_yiyuqi));
                    textVH.status_tip.setTextColor(context.getResources().getColor(R.color.red));
                    textVH.date.setText(time);
                    (textVH).other_tip.setVisibility(View.VISIBLE);
                    textVH.other_tip.setText(expert_name);
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

    @Override
    public int getItemCount() {
        return data.size();
    }


    static final class TextVH extends RecyclerView.ViewHolder {
        TextVH(View v) {
            super(v);
            item = (FrameLayout) v.findViewById(R.id.item);
            date = v.findViewById(R.id.date);
            title = v.findViewById(R.id.title);
            description = v.findViewById(R.id.description);
            other_tip = v.findViewById(R.id.other_tip);
            status_tip =v.findViewById(R.id.status_tip);
        }

        FrameLayout item;
        TextView status_tip,date, title, description, other_tip;
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
