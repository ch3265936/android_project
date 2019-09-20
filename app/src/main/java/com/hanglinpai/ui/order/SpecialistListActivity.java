package com.hanglinpai.ui.order;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.hanglinpai.R;
import com.hanglinpai.ui.bean.Expert;
import com.hanglinpai.ui.bean.ExpertBean;
import com.hanglinpai.ui.bean.HistoryPass;
import com.hanglinpai.ui.bean.HomeOrderBean;
import com.hanglinpai.ui.bean.OrderDetail;
import com.hanglinpai.ui.home.adapter.HomeDataAdapter;
import com.hanglinpai.ui.order.adapter.SpecialAdapter;
import com.hanglinpai.ui.order.constract.SpecialConstract;
import com.hanglinpai.ui.order.model.SpecialModel;
import com.hanglinpai.ui.order.presenter.SpecialPresenter;
import com.hanglinpai.util.DensityUtil;
import com.hanglinpai.widget.CommonDialog;
import com.hanglinpai.widget.CommonTipDialog;
import com.hanglinpai.widget.Divider;
import com.hanglinpai.widget.FullyLinearLayoutManager;
import com.hanglinpai.widget.TodoDialog;
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
import www.meiyaoni.com.common.toolUtils.ToastUtils;

public class SpecialistListActivity extends BaseActivity<SpecialPresenter, SpecialModel> implements SpecialConstract.View {
    @Bind(R.id.r_top)
    LinearLayout r_top;
    @Bind(R.id.title_back)
    TextView title_back;
    @Bind(R.id.title_title)
    TextView title_title;
    private SpecialAdapter adapter;
    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private FullyLinearLayoutManager mLayoutManager;
    private String expertId = "";
    private String server_id = "";
    List<Expert> list = new ArrayList<>();
    private String orderId = "";
    private static final int GOTOSPECHANGE = 17;
    private boolean inconformity =false;
    private String expert_id = "";// 点击不符合按钮记录专家ID

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
        title_title.setText(R.string.expert_list);
        orderId = getIntent().getStringExtra("order_id");

        StatusBarTranslucent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            r_top.setPadding(0, DensityUtil.getStatusHeight(mActivity), 0, 0);
        }
        setupRecyclerView();
        if (orderId != null)
            mPresenter.SpecialList(orderId);
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
        adapter = new SpecialAdapter(mActivity);
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
                    mPresenter.SpecialList(orderId);//  获取历史数据
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
        adapter.setOnItemClickListener(new SpecialAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (list.get(postion).getId() != null) {
                    Intent i = new Intent(mActivity, SpecialistDetailActivity.class);
                    i.putExtra("id", list.get(postion).getId());
                    i.putExtra("bfh", "0");
                    startActivity(i);
                }
            }
        }, new SpecialAdapter.MyItemCheckListener() {
            @Override
            public void onItemCheck(String id, String ser_id) {//选择服务类型  需要传专家ID 和服务类型
                if (id != null && id.length() > 0 && ser_id != null && ser_id.length() > 0) {
                    server_id = ser_id;
                    expertId = id;
                        showComDialog(mActivity);
                } else {
                    ToastUtils.showShort(R.string.expert_no_server);
                }
            }
        }, new SpecialAdapter.MyItemIncoformListener() {////不符合专家反馈  需要传专家ID
            @Override
            public void onItemCheck(String id) {
                expert_id = id;
                Intent i = new Intent(mContext, SpeciaChangeActivity.class);
                i.putExtra("order_id", orderId);
                i.putExtra("expert_id", id);

                startActivityForResult(i, GOTOSPECHANGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOTOSPECHANGE && resultCode == GOTOSPECHANGE) {//不符合专家 提交成功后
            mPresenter.SpecialList(orderId);

        }
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
        list.clear();
        list.addAll(data);
        adapter.notifyDataSetChanged();
        inconformity= false;
        //最新数据如果是每一条都不符合   -》inconformity=false
        for(int i =0;i<list.size();i++){
            if(list.get(i).getStatus()==0){
                inconformity =true;
            }else{
            }
        }
        if(!inconformity){
            mPresenter.expertSelect(orderId, "", "0", "", "");//请求更换专家
        }
    }

    @Override
    public void returnHistoryPassListSuccess(List<HistoryPass> data) {

    }

    private CommonTipDialog commonDialog;

    public void showComDialog(Activity context) {
        if (commonDialog != null && commonDialog.isShowing()) {
            return;
        }
        String string = getResources().getString(R.string.select_expert_tip);
        commonDialog = new CommonTipDialog(context, string, R.string.affirm, R.string.cancer, 260, new CommonTipDialog.DialogClickListener() {
            @Override
            public void onDialogClick() {
                //确认选择专家
                mPresenter.expertSelect(orderId, expertId, "1", server_id, "");

            }
        }, new CommonTipDialog.DialogClickListener() {
            @Override
            public void onDialogClick() {

            }
        });
        commonDialog.show();
    }

}
