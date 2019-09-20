package com.hanglinpai.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanglinpai.EventBus.ACTIVATE;
import com.hanglinpai.R;
import com.hanglinpai.ui.bean.OrderDetail;
import com.hanglinpai.ui.bean.OrderListBean;
import com.hanglinpai.ui.home.adapter.HistoryAdapter;
import com.hanglinpai.ui.home.constract.OrderConstract;
import com.hanglinpai.ui.home.model.OrderModel;
import com.hanglinpai.ui.home.presenter.OrderPresenter;
import com.hanglinpai.ui.order.OrderDetailActivity;
import com.hanglinpai.util.DensityUtil;
import com.hanglinpai.widget.Divider;
import com.hanglinpai.widget.FullyLinearLayoutManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import it.gmariotti.recyclerview.itemanimator.SlideInOutBottomItemAnimator;
import www.meiyaoni.com.common.base.BaseActivity;

public class HistoryListActivity extends BaseActivity<OrderPresenter, OrderModel> implements OrderConstract.View {
    @Bind(R.id.r_top)
    FrameLayout r_top;
    @Bind(R.id.title_title)
    TextView title_title;
    @Bind(R.id.title_back)
    TextView title_back;
    @Bind(R.id.no_data)
    LinearLayout no_data;
    @Bind(R.id.l)
    LinearLayout l;
    private HistoryAdapter adapter;
    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private FullyLinearLayoutManager mLayoutManager;
    private List<OrderDetail> data = new ArrayList<>();
    private int pageNumber = 1, total = 0;
    private String historyOrder = "1";

    @Override
    public int getLayoutId() {
        return R.layout.activity_history_list;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }


    @Override
    public void returnOrderBeanListData(OrderListBean.ListBean bean) {
        no_data.setVisibility(View.GONE);
        l.setBackgroundColor(getResources().getColor(R.color.background));
        if (bean.get_meta() != null) {
            total = bean.get_meta().getPageCount();
        }
        if (total <= pageNumber) {
            refreshLayout.setLoadmoreFinished(true);
        } else {
            refreshLayout.setLoadmoreFinished(false);
        }
        if (pageNumber == 1) {
            data.clear();
            data.addAll(bean.getList());
        } else {
            data.addAll(bean.getList());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void returnNoData() {
        no_data.setVisibility(View.VISIBLE);
        data.clear();
        adapter.notifyDataSetChanged();
        l.setBackgroundColor(getResources().getColor(R.color.white));
    }

    @Override
    public void returnErrorData() {

    }


    @Override
    public void initView() {
        title_title.setText(R.string.history_order);
        StatusBarTranslucent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            r_top.setPadding(0, DensityUtil.getStatusHeight(mActivity), 0, 0);
        }
        setupRecyclerView();
        refreshLayout.autoRefresh();
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
        adapter = new HistoryAdapter(mActivity);
        adapter.setList(data);
        AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(adapter, recycleView);
        recycleView.setItemAnimator(new SlideInOutBottomItemAnimator(recycleView));
        recycleView.setAdapter(animatorAdapter);
        adapter.setOnItemClickListener(new HistoryAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {

                if (data.get(postion) != null && data.get(postion).getId() != null) {
                    Intent i = new Intent(mActivity, OrderDetailActivity.class);
                    i.putExtra("orderId", data.get(postion).getId());
                    startActivityForResult(i, 16);
                }

            }
        });
        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                if (refreshLayout != null) {
                    refreshLayout.setLoadmoreFinished(false);
                    refreshLayout.finishRefresh();
                    pageNumber = 1;
                    mPresenter.orderList(pageNumber + "", historyOrder);//  获取历史数据
                }
            }

            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                if (refreshLayout != null) {
                    refreshlayout.finishLoadmore();
                    pageNumber += 1;
                    mPresenter.orderList(pageNumber + "", historyOrder);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 16) {
            refreshLayout.autoRefresh();
        }
    }
}
