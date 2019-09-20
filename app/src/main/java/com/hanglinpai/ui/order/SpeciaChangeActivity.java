package com.hanglinpai.ui.order;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hanglinpai.R;
import com.hanglinpai.ui.bean.Expert;
import com.hanglinpai.ui.bean.HistoryPass;
import com.hanglinpai.ui.order.adapter.SpecialAdapter;
import com.hanglinpai.ui.order.constract.SpecialConstract;
import com.hanglinpai.ui.order.model.SpecialModel;
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
import www.meiyaoni.com.common.toolUtils.ToastUtils;

public class SpeciaChangeActivity extends BaseActivity<SpecialPresenter, SpecialModel> implements SpecialConstract.View {
    @Bind(R.id.r_top)
    RelativeLayout r_top;
    @Bind(R.id.content)
    EditText content;
    @Bind(R.id.title_title)
    TextView title_title;
    @Bind(R.id.title_back)
    TextView title_back;
    @Bind(R.id.change_special)
    Button change_special;
    @Bind(R.id.text_number_tip)
    TextView text_number_tip;
    private String content_str = "";
    private String order_id = "", expert_id = "";

    @Override

    public int getLayoutId() {
        return R.layout.activity_special_change;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);

    }

    @Override
    public void initView() {
        title_title.setText(R.string.inconformity_expert);
        order_id = getIntent().getStringExtra("order_id");//填写不符合专家需要订单号
        expert_id = getIntent().getStringExtra("expert_id");//
        StatusBarTranslucent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            r_top.setPadding(0, DensityUtil.getStatusHeight(mActivity), 0, 0);
        }
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int number = editable.length();
                text_number_tip.setText(number+"/150");
            }
        });
    }


    @OnClick({R.id.title_back, R.id.change_special})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.change_special:
                if (order_id != null && order_id.length() > 0) {
                    content_str = content.getText().toString().trim();
                    if (content_str != null && content_str.length() > 1) {
                        mPresenter.expertInconformity(order_id, expert_id, content_str);//更换专家
                    } else {
                        ToastUtils.showShort(R.string.change_expert_reason);
                    }
                }
                break;
            default:
                break;
        }

    }


    @Override
    public void returnExpertSelectSuccess() {

    }

    @Override
    public void returnExpertInconformity() {
        setResult(17);
        finish();
    }

    @Override
    public void returnSpecialDetailSuccess(Expert bean) {

    }

    @Override
    public void returnSpecialListSuccess(List<Expert> data) {

    }

    @Override
    public void returnHistoryPassListSuccess(List<HistoryPass> data) {

    }


}
