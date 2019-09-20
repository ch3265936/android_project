package com.hanglinpai.ui.home;

import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanglinpai.R;
import com.hanglinpai.ui.bean.ChatDetail;
import com.hanglinpai.ui.bean.ChatListBean;
import com.hanglinpai.ui.bean.MessageDetail;
import com.hanglinpai.ui.bean.MessageListBean;
import com.hanglinpai.ui.home.adapter.MessageAdapter;
import com.hanglinpai.ui.home.constract.MessageListConstract;
import com.hanglinpai.ui.home.model.MessageModel;
import com.hanglinpai.ui.home.presenter.MessagePresenter;
import com.hanglinpai.ui.order.adapter.MyChatLogListAdapter;
import com.hanglinpai.ui.order.constract.ChatConstract;
import com.hanglinpai.ui.order.model.ChatModel;
import com.hanglinpai.ui.order.presenter.ChatPresenter;
import com.hanglinpai.util.DensityUtil;
import com.hanglinpai.util.ThreadPool;
import com.hanglinpai.widget.Divider;
import com.hanglinpai.widget.FullyLinearLayoutManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import it.gmariotti.recyclerview.itemanimator.SlideInOutBottomItemAnimator;
import retrofit2.http.Body;
import www.meiyaoni.com.common.base.BaseActivity;

public class MessageListActivity extends BaseActivity<MessagePresenter, MessageModel> implements MessageListConstract.View {
    @Bind(R.id.r_top)
    FrameLayout r_top;
    @Bind(R.id.title_title)
    TextView title_title;
    @Bind(R.id.title_back)
    TextView title_back;
    @Bind(R.id.no_data)
    LinearLayout no_data;
    private MessageAdapter adapter;
    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private FullyLinearLayoutManager mLayoutManager;
    private List<MessageDetail> data = new ArrayList<>();
    private int pageNumber = 1, total = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_message_list;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }


    @Override
    public void returnMessageBeanListData(MessageListBean.ListBean bean) {
        setResult(3);
        no_data.setVisibility(View.GONE);
        total = bean.get_meta().getPageCount();
        if (total <= pageNumber) {
            refreshLayout.setLoadmoreFinished(true);
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
    }

    @Override
    public void returnErrorData() {

    }


    @Override
    public void initView() {
        title_title.setText(R.string.system_message);
        StatusBarTranslucent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            r_top.setPadding(0, DensityUtil.getStatusHeight(mActivity), 0, 0);
        }
        setupRecyclerView();
        mPresenter.messageList(pageNumber + "");
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
                DensityUtil.dp2px(0), 10, 0, 0));
        mLayoutManager = new FullyLinearLayoutManager(mActivity);
        recycleView.setLayoutManager(mLayoutManager);
        adapter = new MessageAdapter(mActivity);
        adapter.setList(data);
        AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(adapter, recycleView);
        recycleView.setItemAnimator(new SlideInOutBottomItemAnimator(recycleView));
        recycleView.setAdapter(animatorAdapter);
        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                if (refreshLayout != null) {
                    refreshLayout.setLoadmoreFinished(false);
                    refreshLayout.finishRefresh();
                    pageNumber = 1;
                    mPresenter.messageList(pageNumber + "");//  获取历史数据
                }
            }

            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                if (refreshLayout != null) {
                    refreshlayout.finishLoadmore();
                    pageNumber += 1;
                    mPresenter.messageList(pageNumber + "");
                }
            }
        });

    }


}
