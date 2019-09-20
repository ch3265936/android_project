package com.hanglinpai.ui.order.fragment;

//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//
//import butterknife.Bind;
//
///**
// * @author chihai
// * @function Created on 2017/12/20.
// */
//
//public class OrderFragment extends BaseFragment<OrderFragmentPresenter, OrderFragmentModel> implements OrderFragmentConstract.View {
//    private static final int NUMBER = 6;
//    @Bind(R.id.refreshLayout)
//    SmartRefreshLayout refreshLayout;
//    @Bind(R.id.order_list_recycler_view)
//    RecyclerView mRecyclerView;
//    @Bind(R.id.no_data_view)
//    View mNoDataView;
//    @Bind(R.id.no_network_view)
//    View mNoNetWorkView;
//    @Bind(R.id.error_view)
//    View ErrorView;
//
//    private String status="";//status
//    private int mRefreshNum = 0;
//    private OrderListAdapter mAdapter;
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    @Override
//    protected int getLayoutResource() {
//        return R.layout.fragment_order_list;
//    }
//
//    @Override
//    public void initPresenter() {
//        mPresenter.setVM(this, mModel);
//    }
//
//    @Override
//    protected void initView() {
//        mNetWorkUtils.setNetWorkChangeListener(new NetWorkChangeInterFace() {
//            @Override
//            public void HasNetWork() {
//                mNoNetWorkView.setVisibility(View.GONE);
//                mRecyclerView.setVisibility(View.VISIBLE);
//                ErrorView.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void NoNetWork() {
//                mRecyclerView.setVisibility(View.GONE);
//                mNoNetWorkView.setVisibility(View.VISIBLE);
//                mNoDataView.setVisibility(View.GONE);
//                ErrorView.setVisibility(View.GONE);
//            }
//        });
//
//        refreshLayout.setRefreshHeader(new MeiyaoniHeader(getContext()));
//        refreshLayout.setHeaderHeight(60);
//        refreshLayout.setRefreshFooter(new MeiyaoniFooter(getContext()));
//        refreshLayout.setFooterHeight(60);
//        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                mRefreshNum += NUMBER;
//                mPresenter.orderList(false, status, String.valueOf(mRefreshNum));
//                if (refreshLayout != null)
//                    refreshLayout.finishLoadmore();
//            }
//
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                mRefreshNum = 0;
//                mPresenter.orderList(true, status, ApiConstants.COMMENT_NUM_ZERO);
//                if (refreshLayout != null) {
//                    refreshlayout.setLoadmoreFinished(false);
//                    refreshlayout.finishRefresh();
//                }
//
//            }
//        });
//        if (mAdapter == null) {
//            mAdapter = new OrderListAdapter(R.layout.fragment_order_list_item_layout, status, mRxManager);
//            mAdapter.openLoadAnimation(new ScaleInAnimation());
//            mAdapter.setItemClickListener(new BasicRecyViewHolder.OnItemClickListener() {
//                @Override
//                public void OnItemClick(View v, int adapterPosition) {
//                }
//            });
//            mRecyclerView.setAdapter(mAdapter);
//            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                    super.onScrollStateChanged(recyclerView, newState);
//                }
//
//                @Override
//                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    //有时出现RecyclerView没有滑动到顶部，手指向下滑动时，触发了refreshLayout的刷新事件，造成了冲突。
//                    int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
//                    refreshLayout.setEnableRefresh(topRowVerticalPosition >= 0);
//                }
//            });
//        }
//
//    }
//
//
//    @Override
//    protected void lazyLoad() {
//        if (refreshLayout != null)
//            refreshLayout.autoRefresh();
//    }
//
//    @Override
//    public void returnOrderBeanListData(List<OrderBean.ListBean> bean) {
//        hasData(false);
//        mAdapter.refreshDatas(bean);
//    }
//
//    @Override
//    public void returnLoadMoreOrderBeanListData(List<OrderBean.ListBean> bean) {
//        mAdapter.appendDatas(bean);
//    }
//
//    @Override
//    public void returnNoData() {
//        hasData(true);
//    }
//
//    @Override
//    public void returnNoMoreData() {
//        refreshLayout.setLoadmoreFinished(true);
//    }
//
//    @Override
//    public void returnErrorData() {
//        if (mNoDataView != null)
//            mNoDataView.setVisibility(View.GONE);
//        if (ErrorView != null)
//            ErrorView.setVisibility(View.VISIBLE);
//    }
//
//    public void hasData(boolean isFlag) {
//        if (isFlag) {
//            if (mNoDataView != null)
//                mNoDataView.setVisibility(View.VISIBLE);
//            if (ErrorView != null)
//                ErrorView.setVisibility(View.GONE);
//        } else {
//            if (mNoDataView != null)
//                mNoDataView.setVisibility(View.GONE);
//            if (ErrorView != null)
//                ErrorView.setVisibility(View.GONE);
//        }
//    }
//}
