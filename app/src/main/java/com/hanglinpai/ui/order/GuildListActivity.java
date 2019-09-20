package com.hanglinpai.ui.order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hanglinpai.R;
import com.hanglinpai.ui.bean.ItemSelectBean;
import com.hanglinpai.ui.bean.MessageDetail;
import com.hanglinpai.ui.bean.MessageListBean;
import com.hanglinpai.ui.bean.Static_info;
import com.hanglinpai.ui.home.adapter.HomeDataAdapter;
import com.hanglinpai.ui.home.adapter.MessageAdapter;
import com.hanglinpai.ui.home.constract.MessageListConstract;
import com.hanglinpai.ui.home.model.MessageModel;
import com.hanglinpai.ui.home.presenter.MessagePresenter;
import com.hanglinpai.ui.order.adapter.GuildAdapter;
import com.hanglinpai.ui.order.adapter.SelectAdapter;
import com.hanglinpai.util.DensityUtil;
import com.hanglinpai.widget.Divider;
import com.hanglinpai.widget.FullyLinearLayoutManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import it.gmariotti.recyclerview.itemanimator.SlideInOutBottomItemAnimator;
import www.meiyaoni.com.common.base.BaseActivity;
import www.meiyaoni.com.common.toolUtils.SPManager;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

public class GuildListActivity extends BaseActivity {
    @Bind(R.id.r_top)
    LinearLayout r_top;
    @Bind(R.id.title_title)
    TextView title_title;
    @Bind(R.id.title_back)
    TextView title_back;
    private GuildAdapter adapter;
    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    @Bind(R.id.affirm)
    Button affirm;
    private FullyLinearLayoutManager mLayoutManager;
    private List<ItemSelectBean> data = new ArrayList<>();
    private Set<String> set = new HashSet<>();
    private Static_info static_info = null;
    private String selectId = "",selectName ="";

    @Override
    public int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    public void initPresenter() {

    }


    @Override
    public void initView() {
        title_title.setText(R.string.guild);
        StatusBarTranslucent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            r_top.setPadding(0, DensityUtil.getStatusHeight(mActivity), 0, 0);
        }
        String Static = SPManager.getInstance().getString("Static", "");
        Gson gson = new Gson();
        static_info = gson.fromJson(Static, Static_info.class);
        if (static_info != null) {//原始数据源
            if (static_info.getIndustry_type() != null) {
                data.addAll(static_info.getIndustry_type());
            }
        }
        selectId = getIntent().getStringExtra("select");
        selectName =getIntent().getStringExtra("selectName");
        if (selectId != null && selectId.length() > 0) {
            set.add(selectId);
        }
        setupRecyclerView();

    }


    @OnClick({R.id.title_back, R.id.affirm})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.affirm:
                if (selectId == null || selectId.length() <= 0) {
                    ToastUtils.showShotToast(getResources().getString(R.string.select_guild));
                    return;
                }
                Intent i = new Intent();
                i.putExtra("select", selectId);
                i.putExtra("selectName",selectName);
                setResult(2, i);
                finish();
                break;
            default:
                break;
        }

    }

    //
    private void setupRecyclerView() {
        //添加Divider
        recycleView.addItemDecoration(new Divider(
                //分割线宽1dp
                DensityUtil.dp2px(0),
                //分割线颜色#DDDDDD
                Color.parseColor("#f6f7f6"),
                false,
                //分割线左侧留出20dp的空白，不绘制
                DensityUtil.dp2px(0), 10, 0, 0));
        mLayoutManager = new FullyLinearLayoutManager(mActivity);
        recycleView.setLayoutManager(mLayoutManager);
        adapter = new GuildAdapter();
        adapter.setList(data, set);
        adapter.setOnItemClickListener(new HomeDataAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (data.get(postion) != null && data.get(postion).id != null&&data.get(postion).name != null) {
                    selectId = data.get(postion).id;
                    selectName = data.get(postion).name;
                    if (set.contains(selectId)) {
                        return;
                    } else {
                        set.clear();//单选
                        set.add(selectId);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
        AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(adapter, recycleView);
        recycleView.setItemAnimator(new SlideInOutBottomItemAnimator(recycleView));
        recycleView.setAdapter(animatorAdapter);


    }


}
