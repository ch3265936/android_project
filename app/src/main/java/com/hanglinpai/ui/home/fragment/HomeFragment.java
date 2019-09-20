package com.hanglinpai.ui.home.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import it.gmariotti.recyclerview.itemanimator.SlideInOutBottomItemAnimator;
import www.meiyaoni.com.common.base.BaseFragment;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

import com.hanglinpai.R;
import com.hanglinpai.api.ApiConstants;
import com.hanglinpai.ui.bean.HomeOrderBean;
import com.hanglinpai.ui.bean.OrderDetail;
import com.hanglinpai.ui.bean.OrderListBean;
import com.hanglinpai.ui.home.HistoryListActivity;
import com.hanglinpai.ui.home.HomeActivity;
import com.hanglinpai.ui.home.WebActivity;
import com.hanglinpai.ui.home.adapter.HomeDataAdapter;
import com.hanglinpai.ui.home.constract.OrderConstract;
import com.hanglinpai.ui.home.model.OrderModel;
import com.hanglinpai.ui.home.presenter.OrderPresenter;
import com.hanglinpai.ui.order.NewOrderActivity;
import com.hanglinpai.ui.order.OrderDetailActivity;

import com.hanglinpai.ui.user.AttestationActivity;
import com.hanglinpai.ui.user.RegActivity;
import com.hanglinpai.ui.user.fragment.RegFragment;
import com.hanglinpai.util.DataUtils;
import com.hanglinpai.util.DensityUtil;
import com.hanglinpai.widget.Divider;
import com.hanglinpai.widget.FullyLinearLayoutManager;
import com.hanglinpai.widget.TodoDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment<OrderPresenter, OrderModel> implements OrderConstract.View {
    private FullyLinearLayoutManager mLayoutManager;
    private HomeDataAdapter adapter;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    private int scrollY = 0;
    @Bind(R.id.l_top)
    RelativeLayout l_top;
    @Bind(R.id.order)
    ImageView order;
    @Bind(R.id.img_query)
    ImageView img_query;
    @Bind(R.id.to_new_order)
    Button to_new_order;
    @Bind(R.id.r_top)
    RelativeLayout r_top;
    @Bind(R.id.no_data)
    RelativeLayout no_data;
    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.yq_tip)
    ImageView yq_tip;
    private float lastY = 0;
    private int total = 0, pageNumber = 1;
    private List<OrderDetail> data = new ArrayList<>();
    private String homeOrder = "0";

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        if (RegFragment.regSuccess) {//第一次注册进来
            showRegDialog(getActivity());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            r_top.setPadding(0, DensityUtil.getStatusHeight(getActivity()), 0, 0);
        }
        setupRecyclerView();
        recycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //mImage这个view的高度
                int imageHeight = DensityUtil.dp2px(170);
                scrollY += dy;
                if (scrollY < 0) {

                } else {
                    //“图片”已经滑动，而且还没有全部滑出屏幕，根据滑出高度的比例设置透明度的比例
                    if (scrollY < imageHeight) {
                        int progress = (int) (new Float(scrollY) / new Float(imageHeight) * 255);//255
                        adapter.scale = progress;
                        adapter.notifyDataSetChanged();
                        l_top.setVisibility(View.GONE);
                        r_top.getBackground().setAlpha(progress);
                    } else {
                        l_top.setVisibility(View.VISIBLE);
                        r_top.getBackground().setAlpha(255);
                    }
                }

            }


        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HistoryListActivity.class));
                showOverdue(false);
            }
        });
        img_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), WebActivity.class);
                i.putExtra("url", ApiConstants.SHAREWEB);
                getActivity().startActivity(i);
            }
        });
        to_new_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newOrder();
            }
        });
        date.setText(DataUtils.getDate() + " " + DataUtils.getWeek());
    }

    public void newOrder() {
        if (HomeActivity.userinfo != null) {
            if (HomeActivity.userinfo.getAuth_status() == 0) {
                tuAttestation();
            } else {
                getActivity().startActivityForResult(new Intent(getActivity(), NewOrderActivity.class), 5);
                getActivity().overridePendingTransition(R.anim.down_up, R.anim.up_down);
            }
        }

    }

    //未填写资料 部分操作跳转填写资料
    private void tuAttestation() {
        Intent i = new Intent(getActivity(), AttestationActivity.class);
        i.putExtra("HOME", "HOME");
        i.putExtra("phone", HomeActivity.userinfo.getPhone());
        getActivity().startActivityForResult(i, 4);
        ToastUtils.showShotToast(getActivity().getResources().getString(R.string.reg_no_attetion));
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
        mLayoutManager = new FullyLinearLayoutManager(getActivity());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setNestedScrollingEnabled(false);
        adapter = new HomeDataAdapter(getActivity());

        adapter.setList(data);
        AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(adapter, recycleView);
        recycleView.setItemAnimator(new SlideInOutBottomItemAnimator(recycleView));
        recycleView.setAdapter(animatorAdapter);
        adapter.setOnItemClickListener(new HomeDataAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (data.size() > 0 && data.get(postion - 1) != null && data.get(postion - 1).getId() != null) {
                    Intent i = new Intent(getActivity(), OrderDetailActivity.class);
                    i.putExtra("orderId", data.get(postion - 1).getId());
                    getActivity().startActivityForResult(i, 6);
                }

            }
        });
        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                if (refreshLayout != null) {
                    refreshLayout.setLoadmoreFinished(false);
                    refreshLayout.finishRefresh();
                    refreshOrderList();
                }
            }

            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                if (refreshLayout != null)
                    refreshlayout.finishLoadmore();
                pageNumber++;
                mPresenter.orderList(pageNumber + "", homeOrder);//首页0
            }
        });
        refreshLayout.autoRefresh();

    }
    public void showOverdue(boolean a){
        if(a) {
            yq_tip.setVisibility(View.VISIBLE);
        }else {
            yq_tip.setVisibility(View.GONE);
        }
    }
    public void refreshOrderList() {
        pageNumber = 1;
        mPresenter.orderList(pageNumber + "", homeOrder);//首页0
    }

    private TodoDialog regDialog;

    public void showRegDialog(Activity context) {
        if (regDialog != null && regDialog.isShowing()) {
            return;
        }
        regDialog = new TodoDialog(context, R.string.reg_success, R.string.reg_success_tip, R.string.to_do_rz, R.mipmap.image_registeredsuccessfully, new TodoDialog.DialogClickListener() {
            @Override
            public void onDialogClick() {
                String callNumber = getString(R.string.business_number);
                diallPhone(callNumber.replace("-", ""));
            }
        }, new TodoDialog.DialogClickListener() {
            @Override
            public void onDialogClick() {

            }
        });
        regDialog.show();
        RegActivity.regSuccess = false;
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void diallPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void returnOrderBeanListData(OrderListBean.ListBean bean) {
        no_data.setVisibility(View.GONE);
        if (bean.get_meta() != null) {
            total = bean.get_meta().getPageCount();
        }
        if (total <= pageNumber) {
            refreshLayout.setLoadmoreFinished(true);
        }else{
            refreshLayout.setLoadmoreFinished(false);
        }
        if (bean.getList() != null && bean.getList().size() > 0) {
            if (pageNumber == 1) {
                data.clear();
                data.addAll(bean.getList());
            } else {
                data.addAll(bean.getList());
            }
        }
        adapter.notifyDataSetChanged();


    }

    @Override
    public void returnNoData() {
        data.clear();
        adapter.notifyDataSetChanged();
        no_data.setVisibility(View.VISIBLE);
    }

    @Override
    public void returnErrorData() {

    }
}

