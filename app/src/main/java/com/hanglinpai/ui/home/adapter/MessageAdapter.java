package com.hanglinpai.ui.home.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hanglinpai.R;
import com.hanglinpai.ui.bean.HomeOrderBean;
import com.hanglinpai.ui.bean.MessageDetail;
import com.hanglinpai.util.DataUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by chihai on 2017/6/16.
 */
public class MessageAdapter extends RecyclerView.Adapter {
    private Activity context;
    public float scale = 0;
    private LinearLayoutManager mLayoutManager;
    private List<MessageDetail> data = new ArrayList<>();

    public MessageAdapter(Activity activity) {
        this.context = activity;
    }

    public void setList( List<MessageDetail> data) {
        this.data = data;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final TextVH textVH = (TextVH) holder;
        MessageDetail message = data.get(position);
        if (message != null) {
            if (message.getTitle() != null) {
                textVH.title.setText(message.getTitle());
            }
            if (message.getContent() != null) {
                textVH.content.setText(message.getContent());
            }
            if (message.getAdd_time_str() != null) {
                textVH.date.setText(message.getAdd_time_str());
            }

        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static final class TextVH extends RecyclerView.ViewHolder {
        TextVH(View v) {
            super(v);
            date = v.findViewById(R.id.date);
            title = v.findViewById(R.id.title);
            content = v.findViewById(R.id.content);
            item = (LinearLayout) v.findViewById(R.id.item);
        }

        TextView time, date, title, content;
        LinearLayout item;
    }

    MyItemClickListener mItemClickListener;

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }


}
