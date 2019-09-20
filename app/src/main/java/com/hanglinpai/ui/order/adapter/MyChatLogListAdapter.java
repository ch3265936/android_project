package com.hanglinpai.ui.order.adapter;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hanglinpai.R;
import com.hanglinpai.api.ApiConstants;
import com.hanglinpai.app.AppApplication;
import com.hanglinpai.ui.bean.ChatDetail;
import com.hanglinpai.ui.home.HomeActivity;
import com.hanglinpai.ui.home.adapter.HomeDataAdapter;
import com.hanglinpai.util.DataUtils;
import com.hanglinpai.widget.RoundedImageView;


import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by chihai on 2017/6/16.
 */
public class MyChatLogListAdapter extends RecyclerView.Adapter {
    private Activity context;
    private List<ChatDetail> data = new ArrayList<>();

    public MyChatLogListAdapter(Activity activity) {
        this.context = activity;
    }

    public void setList(List<ChatDetail> data) {
        this.data = data;
    }

    private final int type0 = 0;
    private final int type1 = 1;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case type0:
                return new TextVH0(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_right, parent, false));
            case type1:
                return new TextVH1(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_left, parent, false));
        }

        return new TextVH1(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_right, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ChatDetail log = data.get(position);
        if(getItemViewType(position)==type1) {
            final TextVH1 textVH1 = (TextVH1) holder;
            if (log.getContent() != null) {
                textVH1.describe.setText(log.getContent());
            }
        }else{
            final TextVH0 textVH0 = (TextVH0) holder;
            if (log.getContent() != null) {
                textVH0.describe.setText(log.getContent());
            }
            if(HomeActivity.userinfo!=null&&HomeActivity.userinfo.getAvatar()!=null){
                Glide.with(AppApplication.getInstance()).load(ApiConstants.IMG_URL + HomeActivity.userinfo.getAvatar())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .crossFade()
                        .error(R.mipmap.ic_user_def)
                        .into(textVH0.avl);
            }
        }




    }

    @Override
    public int getItemCount() {
        return data.size();//
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).getType()==1) {
            return type1;
        } else {
            return type0;
        }
    }

    static final class TextVH0 extends RecyclerView.ViewHolder {
        TextVH0(View v) {
            super(v);
            describe = (TextView) v.findViewById(R.id.describe);
            avl = v.findViewById(R.id.avl);
        }

        TextView describe;
        RoundedImageView avl;
    }

    static final class TextVH1 extends RecyclerView.ViewHolder {
        TextVH1(View v) {
            super(v);
            describe = (TextView) v.findViewById(R.id.describe);
        }

        TextView describe;

    }

    MyItemClickListener mItemClickListener;

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }




}
