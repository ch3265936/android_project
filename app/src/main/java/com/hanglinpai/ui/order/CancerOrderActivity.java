package com.hanglinpai.ui.order;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hanglinpai.R;
import com.hanglinpai.ui.bean.Expert;
import com.hanglinpai.ui.bean.ItemSelectBean;
import com.hanglinpai.ui.bean.Static_info;
import com.hanglinpai.ui.order.adapter.CancerOrderAdapter;
import com.hanglinpai.ui.order.adapter.MyChatLogListAdapter;
import com.hanglinpai.ui.order.constract.OrderCancerConstract;
import com.hanglinpai.ui.order.constract.SpecialConstract;
import com.hanglinpai.ui.order.model.CancerOrderModel;
import com.hanglinpai.ui.order.model.SpecialModel;
import com.hanglinpai.ui.order.presenter.CancerOrderPresenter;
import com.hanglinpai.ui.order.presenter.SpecialPresenter;
import com.hanglinpai.util.DensityUtil;
import com.hanglinpai.widget.Divider;
import com.hanglinpai.widget.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import it.gmariotti.recyclerview.itemanimator.SlideInOutBottomItemAnimator;
import www.meiyaoni.com.common.base.BaseActivity;
import www.meiyaoni.com.common.toolUtils.SPManager;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

public class CancerOrderActivity extends BaseActivity<CancerOrderPresenter, CancerOrderModel> implements OrderCancerConstract.View {
    @Bind(R.id.r_top)
    RelativeLayout r_top;
    @Bind(R.id.title_back)
    TextView title_back;
    @Bind(R.id.title_title)
    TextView title_title;
    @Bind(R.id.content)
    EditText content;
    @Bind(R.id.submit)
    Button submit;
    private String content_str = "";
    private String order_id = "", cancer_id = "";
    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    private FullyLinearLayoutManager mLayoutManager;
    private List<ItemSelectBean> data = new ArrayList<>();
    private CancerOrderAdapter adapter;

    @Override

    public int getLayoutId() {
        return R.layout.activity_order_cancer;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);

    }

    @Override
    public void initView() {
        order_id = getIntent().getStringExtra("order_id");//订单号
        title_title.setText(R.string.cancer_order);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//  键盘冲突直接该状态栏颜色为标题栏白色  （透明状态栏也是白色效果一样 键盘上顶不冲突）
            Window window = getWindow();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.WHITE);
            window.setNavigationBarColor(getResources().getColor(www.meiyaoni.com.common.R.color.pickerview_bgColor_overlay));
        }

        setupRecyclerView();
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @OnClick({R.id.title_back, R.id.submit})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.submit:
                content_str = content.getText().toString().trim();
                if (order_id == null) {
                    return;
                }
                if (cancer_id != null && cancer_id.length() > 0) {
                    mPresenter.cancerOrder(order_id, "1",cancer_id, content_str);//
                } else {
                    ToastUtils.showShort(R.string.cancer_order_tip);
                }

                break;
            default:
                break;
        }

    }

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
        adapter = new CancerOrderAdapter(mActivity);
        adapter.setOnItemCheck(new CancerOrderAdapter.MyItemCheckListener() {
            @Override
            public void onItemCheck(String id) {
                if (id != null && id.length() > 0) {
                    cancer_id = id;
                } else {
                    cancer_id = "";
                }
            }
        });
        AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(adapter, recycleView);
        recycleView.setItemAnimator(new SlideInOutBottomItemAnimator(recycleView));
        recycleView.setAdapter(animatorAdapter);
        String str = SPManager.getInstance().getString("Static", "");
        if (str != null && str.length() > 0) {
            Gson gson = new Gson();
            Static_info si = gson.fromJson(str, Static_info.class);
            if (si != null && si.getCancel_reason() != null && si.getCancel_reason().size() > 0) {
                data.clear();
                data.addAll(si.getCancel_reason());
                adapter.setList(data);
                adapter.notifyDataSetChanged();
            }
        }

    }


    @Override
    public void returnCancerSuccess() {
        setResult(12);
        finish();
    }
}
