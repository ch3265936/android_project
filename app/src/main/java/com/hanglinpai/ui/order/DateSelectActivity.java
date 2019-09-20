package com.hanglinpai.ui.order;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hanglinpai.R;
import com.hanglinpai.ui.bean.MessageDetail;
import com.hanglinpai.ui.home.adapter.MessageAdapter;
import com.hanglinpai.ui.order.adapter.DateAdapter;
import com.hanglinpai.ui.user.AgreementActivity;
import com.hanglinpai.util.DataUtils;
import com.hanglinpai.util.DensityUtil;
import com.hanglinpai.widget.Divider;
import com.hanglinpai.widget.FullyLinearLayoutManager;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.OnMultiChooseListener;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.weiget.CalendarView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import it.gmariotti.recyclerview.itemanimator.SlideInOutBottomItemAnimator;
import www.meiyaoni.com.common.base.BaseActivity;

public class DateSelectActivity extends BaseActivity {
    @Bind(R.id.title_title)
    TextView title_title;
    @Bind(R.id.title_back)
    TextView title_back;
    @Bind(R.id.affirm)
    TextView affirm;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.r_top)
    LinearLayout r_top;
    @Bind(R.id.no_data)
    LinearLayout no_data;
    @Bind(R.id.calendar)
    CalendarView calendarView;

    private DateAdapter adapter;
    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    private GridLayoutManager mLayoutManager;
    private List<String> muldateList = new ArrayList<>();
    private List<String> mulselectList = new ArrayList<>();//時間戳 过渡排序临时集合
    String ad = "";

    @Override
    public void initPresenter() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_date_select;
    }

    @Override
    public void initView() {
        if (getIntent().getStringArrayListExtra("muldate") != null) {
            mulselectList = getIntent().getStringArrayListExtra("muldate");
        }

        StatusBarTranslucent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            r_top.setPadding(0, DensityUtil.getStatusHeight(mActivity), 0, 0);
        }
        title_title.setText(getResources().getString(R.string.start_time));
        ad = DataUtils.getCurrentData1(System.currentTimeMillis());

        if (mulselectList != null && mulselectList.size() > 0) {
            for (String a : mulselectList) {
                if (a != null && a.length() == 10) {
                    a = a + "000";
                }
                muldateList.add(DataUtils.stampToDate(a));//2018.5.1
            }
        }
        String startDate =DataUtils.getCurrentDataSelect();
        String disableStartDate =DataUtils.getCurrentDataSelect2();
        if (mulselectList.size() > 0) {
            calendarView
                    .setStartEndDate(startDate,"2020.12")
                    .setDisableStartEndDate(disableStartDate, "2020.12")
                    .setInitDate(ad)
                    .setMultiDate(muldateList)//初始化多选
                    .init();

        } else {
            calendarView
                    .setStartEndDate(startDate,"2020.12")
                    .setDisableStartEndDate(disableStartDate, "2020.12")
                    .setInitDate(ad)
                    .init();
        }
        calendarView.setMaxNum(10);//设置最大的多选数量
        //月份切换回调
        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title.setText(date[0] + "年" + date[1] + "月");
            }
        });
        calendarView.setOnMultiChooseListener(new OnMultiChooseListener() {
            @Override
            public void onMultiChoose(View view, DateBean dateBean, boolean b) {

                String d = dateBean.getSolar()[0] + "." + dateBean.getSolar()[1] + "." + dateBean.getSolar()[2];
                String dd = DataUtils.dateToStamp2(d);
                if (b) {
                    title.setText(dateBean.getSolar()[0] + "年" + dateBean.getSolar()[1] + "月");
                    mulselectList.add(dd);
                } else {
                    mulselectList.remove(dd);
                }
                if (mulselectList.size() > 0) {
                    no_data.setVisibility(View.GONE);
                } else {
                    no_data.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }
        });
        setupRecyclerView();
        title.setText(DataUtils.getMonth());
    }


    @OnClick({R.id.title_back, R.id.nextMonth, R.id.lastMonth, R.id.affirm})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.affirm:
                Intent i = new Intent();
                i.putStringArrayListExtra("muldate", (ArrayList<String>) mulselectList);
                setResult(3, i);
                finish();
                break;
            case R.id.nextMonth:
                calendarView.nextMonth();
                break;
            case R.id.lastMonth:
                calendarView.lastMonth();
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
        mLayoutManager = new GridLayoutManager(mContext, 3);
        recycleView.setLayoutManager(mLayoutManager);
        adapter = new DateAdapter(mActivity);
        adapter.setList(mulselectList);
        AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(adapter, recycleView);
        recycleView.setItemAnimator(new SlideInOutBottomItemAnimator(recycleView));
        recycleView.setAdapter(animatorAdapter);
        if (mulselectList.size() > 0) {
            no_data.setVisibility(View.GONE);
        } else {
            no_data.setVisibility(View.VISIBLE);
        }
    }

}
