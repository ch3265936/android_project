package com.hanglinpai.ui.order;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanglinpai.R;
import com.hanglinpai.ui.bean.Expert;
import com.hanglinpai.ui.bean.HistoryPass;
import com.hanglinpai.ui.bean.OrderDetail;
import com.hanglinpai.ui.order.adapter.HistoryPassAdapter;
import com.hanglinpai.ui.order.adapter.HistorySpecialAdapter;
import com.hanglinpai.ui.order.adapter.SpecialAdapter;
import com.hanglinpai.ui.order.constract.SpecialConstract;
import com.hanglinpai.ui.order.model.SpecialModel;
import com.hanglinpai.ui.order.presenter.SpecialPresenter;
import com.hanglinpai.util.DensityUtil;
import com.hanglinpai.widget.CommonTipDialog;
import com.hanglinpai.widget.Divider;
import com.hanglinpai.widget.FullyLinearLayoutManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import it.gmariotti.recyclerview.itemanimator.SlideInOutBottomItemAnimator;
import www.meiyaoni.com.common.base.BaseActivity;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

public class SpecialistHistoryListActivity extends BaseActivity<SpecialPresenter, SpecialModel> implements SpecialConstract.View {
    @Bind(R.id.r_top)
    LinearLayout r_top;
    @Bind(R.id.title_back)
    TextView title_back;
    @Bind(R.id.title_title)
    TextView title_title;
    @Bind(R.id.tip)
    TextView tip;
    private HistoryPassAdapter adapter;
    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private FullyLinearLayoutManager mLayoutManager;
    private String expertId = "";
    private String server_id = "";
    List<HistoryPass> list = new ArrayList<>();
    private String orderId = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_special_list;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);

    }

    private OrderDetail detail = null;

    @Override
    public void initView() {
        tip.setVisibility(View.GONE);
        title_title.setText(R.string.recommend_history_tip);
        orderId = getIntent().getStringExtra("order_id");

        StatusBarTranslucent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            r_top.setPadding(0, DensityUtil.getStatusHeight(mActivity), 0, 0);
        }
        setupRecyclerView();
        if (orderId != null)
            mPresenter.HistoryPassList(orderId);
    }


    @OnClick({R.id.title_back})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.title_back:
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
                DensityUtil.dp2px(0), 0, 0, 0));
        mLayoutManager = new FullyLinearLayoutManager(mActivity);
        recycleView.setLayoutManager(mLayoutManager);
        adapter = new HistoryPassAdapter(mActivity);
        adapter.setList(list);
        AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(adapter, recycleView);
        recycleView.setItemAnimator(new SlideInOutBottomItemAnimator(recycleView));
        recycleView.setAdapter(animatorAdapter);
        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                if (refreshLayout != null) {
                    refreshLayout.setLoadmoreFinished(false);
                    refreshLayout.finishRefresh();
                    mPresenter.HistoryPassList(orderId);//  获取历史数据
                }
            }

            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                if (refreshLayout != null) {
                    refreshlayout.finishLoadmore();

                }
            }
        });
        refreshLayout.setEnableLoadmore(false);
    }



    @Override
    public void returnExpertSelectSuccess() {//选择了专家// 或者请求更换专家
        setResult(13);
        finish();
    }

    @Override
    public void returnExpertInconformity() {

    }

    @Override
    public void returnSpecialDetailSuccess(Expert bean) {

    }

    @Override
    public void returnSpecialListSuccess(List<Expert> data) {


    }

    @Override
    public void returnHistoryPassListSuccess(List<HistoryPass> data) {
        list.clear();
        list.addAll(data);
        adapter.notifyDataSetChanged();

    }


}
