package com.hanglinpai.ui.setting.adapter;

import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hanglinpai.R;
import com.hanglinpai.app.AppApplication;
import com.hanglinpai.widget.RoundedImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import www.meiyaoni.com.common.toolUtils.ToastUtils;


/**
 * Created by Administrator on 2017/3/7.
 */

public class AlarmImgAdapter extends RecyclerView.Adapter {
    private FistImgOnClick listener;
    private ImgOnClick mListener;
    private DeleteOnClick mDeleteListener;
    private List<String> data = new ArrayList<>();

    public void setList(List<String> data) {
        this.data = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Hold(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_alarm_img_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        String url = data.get(position);
        final Hold hold = (Hold) holder;
        if (url.equals("add")) {
            hold.mImgView.setImageResource(R.mipmap.icon_addpt);
        } else {
            if (url.startsWith("http")) {//將网络图片保存村BYTE 存入内存卡替换当前 对应data
                Glide.with(AppApplication.getInstance()).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
                    @Override
                    public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                        try {
                            data.set(position, savaFileToSD(System.currentTimeMillis() + ".jpg", bytes));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                Glide.with(AppApplication.getInstance()).load(url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .crossFade()
                        .error(R.mipmap.ic_launcher)
                        .into(hold.mImgView);
            } else {
                Glide.with(AppApplication.getInstance()).load(url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .crossFade()
                        .error(R.mipmap.ic_launcher)
                        .into(hold.mImgView);
            }
            hold.mImgView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(position==0){
                        return true;
                    }
                    if (hold.mDeleteView.getVisibility() == View.GONE) {
                        hold.mDeleteView.setVisibility(View.VISIBLE);
                    } else {
                        hold.mDeleteView.setVisibility(View.GONE);
                    }
                    return true;
                }
            });
            hold.mDeleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                    if (mDeleteListener != null) {
                        mDeleteListener.onClick(position);
                    }
                }
            });
        }


        if (hold.mDeleteView.getVisibility() == View.VISIBLE) {
            hold.mDeleteView.setVisibility(View.GONE);
        }

        hold.mImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hold.mDeleteView.getVisibility() == View.VISIBLE) {
                    hold.mDeleteView.setVisibility(View.GONE);
                } else {
                    if (mListener != null) {
                        mListener.onClick(position, hold.mImgView.getWidth(), hold.mImgView.getHeight(), hold.mImgView);
                    }
                }
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
            mImgView = (RoundedImageView) itemView.findViewById(R.id.img_view);
            mDeleteView = itemView.findViewById(R.id.mDeleteView);
        }

        private RoundedImageView mImgView;
        private ImageView mDeleteView;


    }

    public void setOnImgClickListener(ImgOnClick listener) {
        mListener = listener;
    }

    public void setOnDeleteClickListener(DeleteOnClick listener) {
        mDeleteListener = listener;
    }

    public interface FistImgOnClick {
        void onClick();
    }

    public interface ImgOnClick {
        void onClick(int position, int width, int height, ImageView img);
    }

    public interface DeleteOnClick {
        void onClick(int position);
    }

    //往SD卡写入文件的方法
    public String savaFileToSD(String filename, byte[] bytes) throws Exception {
        AppApplication app = AppApplication.getInstance();
        //如果手机已插入sd卡,且app具有读写sd卡的权限
            String filePath = app.filePath;
            File dir1 = new File(filePath);
            if (!dir1.exists()) {
                dir1.mkdirs();
            }
            filename = filePath + "/" + filename;
            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
            FileOutputStream output = new FileOutputStream(filename);
            output.write(bytes);
            //将bytes写入到输出流中
            output.close();
            //关闭输出流
            Log.e("aaaa", "图片保存成功");
            return filename;

    }
}
