package com.hanglinpai.ui.order;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hanglinpai.EventBus.UMPUSH;
import com.hanglinpai.R;

import com.hanglinpai.ui.bean.ItemSelectBean;
import com.hanglinpai.ui.bean.OrderDetail;
import com.hanglinpai.ui.order.constract.OrderConstract;
import com.hanglinpai.ui.order.model.OrderModel;
import com.hanglinpai.ui.order.presenter.OrderPresenter;
import com.hanglinpai.util.DataUtils;
import com.hanglinpai.widget.CommonTipDialog;
import com.hanglinpai.widget.EvaluateDialog;
import com.hanglinpai.widget.OrderDialog;
import com.hanglinpai.widget.SelectDateDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;
import www.meiyaoni.com.common.base.BaseActivity;
import www.meiyaoni.com.common.toolUtils.DensityUtil;
import www.meiyaoni.com.common.toolUtils.LodingDialog;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

public class OrderDetailActivity extends BaseActivity<OrderPresenter, OrderModel> implements OrderConstract.View {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.customer)
    TextView customer;
    //CELL
    @Bind(R.id.order_status)
    TextView order_status;
    @Bind(R.id.order_detail_2)
    TextView order_detail_2;
    @Bind(R.id.order_detail_desc)
    TextView order_detail_desc;
    @Bind(R.id.status_img)
    ImageView status_img;
    @Bind(R.id.v1)
    View v1;
    @Bind(R.id.appointment_type)
    TextView appointment_type;
    @Bind(R.id.appointment_time)
    TextView appointment_time;
    @Bind(R.id.quote)
    TextView quote;
    @Bind(R.id.remark)
    TextView remark;
    @Bind(R.id.cancer_reason)
    TextView cancer_reason;
    @Bind(R.id.cancer_order)
    TextView cancer_order;
    //orderinfo
    @Bind(R.id.order_no)
    TextView order_no;
    @Bind(R.id.make_order_time)
    TextView make_order_time;
    @Bind(R.id.guild_type)
    TextView guild_type;
    @Bind(R.id.serve_type)
    TextView serve_type;
    @Bind(R.id.serve_date)
    TextView serve_date;
    @Bind(R.id.serve_time)
    TextView serve_time;
    @Bind(R.id.serve_address)
    TextView serve_address;
    @Bind(R.id.describe)
    TextView describe;
    @Bind(R.id.r_appointment)
    LinearLayout r_appointment;
    @Bind(R.id.red_point)
    ImageView red_point;
    @Bind(R.id.gray_button)
    TextView gray_button;
    @Bind(R.id.red_button)
    TextView red_button;
    @Bind(R.id.l_expand)
    LinearLayout l_expand;
    @Bind(R.id.r_expand)
    RelativeLayout r_expand;
    @Bind(R.id.cancer_wait)
    TextView cancer_wait;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.r_status_cell)
    RelativeLayout r_status_cell;
    @Bind(R.id.r_old_recommend)
    RelativeLayout r_old_recommend;
    @Bind(R.id.recommend_history)
    TextView recommend_history;
    private String orderId = "";
    private static final int GOTOSPECIAL = 13;//查看专家
    private static final int GOTOCANCER = 12;//取消訂單
    private static final int GOTOEDIT = 11;//编辑

    private boolean hasEditSuccess = false;//修改过订单
    private OrderDetail bean;
    int expand_height;

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);


    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        UMShareAPI.get(this).release();
        super.onDestroy();

    }

    @Override
    public void initView() {
        orderId = getIntent().getStringExtra("orderId");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.order_detail));
        }
        if (orderId != null)
            mPresenter.getOrderDetail(orderId);
        expand_height = l_expand.getMeasuredHeight();//加载内容 重新获取高度
    }

    @OnClick({R.id.back, R.id.order_status})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.order_status:
                if (bean != null && bean.getOrder_progress() != null && bean.getOrder_progress().size() > 0)
                    showOrderStatusDialog(mActivity);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GOTOSPECIAL:
                if (resultCode == GOTOSPECIAL) {
                    //确认过专家或更换专家  刷新数据
                    mPresenter.getOrderDetail(orderId);
                }
            case GOTOCANCER:
                if (resultCode == GOTOCANCER) {
                    //取消订单 填写理由
                    mPresenter.getOrderDetail(orderId);
                }
                break;
            case GOTOEDIT:
                if (resultCode == GOTOEDIT) {
                    //编辑订单
                    mPresenter.getOrderDetail(orderId);
                }
                break;
        }
    }

    //订单详情
    @Override
    public void returnOrderBeanData(OrderDetail bean) {
        this.bean = bean;
        if (l_expand.getHeight() == 0) {
            expand = !expand;
            l_expand.getLayoutParams().height = expand_height;
            l_expand.requestLayout();
        }
        showInfo();
    }


    //无内容
    @Override
    public void returnNoData() {

    }


    @Override
    public void returnErrorData() {

    }

    @Override
    public void returnExpertSelectSuccess() {
        if (orderId != null)
        mPresenter.getOrderDetail(orderId);

    }


    //操作约访
    @Override
    public void returnExpertViewSuccess() {
        mPresenter.getOrderDetail(orderId);
    }

    //评价
    @Override
    public void returnReviewSuccess() {
        mPresenter.getOrderDetail(orderId);
    }

    @Override
    public void returnCancerSuccess() {
        mPresenter.getOrderDetail(orderId);
    }

    private int status = 0;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UMPUSH(UMPUSH umpush) {
        if (umpush.getType().equals("1") && umpush.getOrder_id() != null && umpush.getOrder_id().length() > 0) {// 订单类型 刷新
            if (umpush.getOrder_id().equals(orderId)) {//当前订单变更就刷新
                mPresenter.getOrderDetail(orderId);
            }
        } else if (umpush.getType().equals("2")) {
            red_point.setVisibility(View.VISIBLE);
        }
    }

    private boolean expand = true;

    private void showInfo() {
        if (bean != null) {
            status = bean.getStatus();
            if (bean.getStatus_name() != null) {
                order_status.setText(bean.getStatus_name());
            }
            if (bean.getStatus_desc() != null) {
                order_detail_2.setText(bean.getStatus_desc());
            }
            if (bean.getStatus_desc_add() != null) {
                order_detail_desc.setText(bean.getStatus_desc_add());
            }
            if (bean.getOrder_no() != null) {
                order_no.setText(bean.getOrder_no());
            }
            if (bean.getAdd_time_str() != null) {
                make_order_time.setText(bean.getAdd_time_str());
            }
            if (bean.getIndustry_type_name() != null) {
                guild_type.setText(bean.getIndustry_type_name());
            }
            if (bean.getService_type_name() != null && bean.getService_type_name().size() > 0) {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < bean.getService_type_name().size(); i++) {
                    if (bean.getService_type_name().get(i).name != null) {
                        if (i == 0) {
                            sb.append(bean.getService_type_name().get(i).name);
                        } else {
                            sb.append("、" + bean.getService_type_name().get(i).name);
                        }
                    }
                }
                serve_type.setText(sb.toString());
            }

            if (bean.getService_dates() != null && bean.getService_dates().size() > 0) {
                serve_date.setText(showMulDate(bean.getService_dates()));
            }
            if (bean.getService_time_name() != null) {
                serve_time.setText(bean.getService_time_name());
            }
            if (bean.getSite() != null && bean.getSite().length() > 0) {
                serve_address.setText(bean.getSite());
            } else {
                serve_address.setText("暂无");
            }
            if (bean.getDesc() != null && bean.getDesc().length() > 0) {
                describe.setText(bean.getDesc());
            } else {
                describe.setText("暂无描述");
            }
            r_old_recommend.setVisibility(View.GONE);
            cancer_wait.setVisibility(View.GONE);
            cancer_reason.setVisibility(View.GONE);
            remark.setVisibility(View.GONE);
            r_appointment.setVisibility(View.GONE);
            gray_button.setVisibility(View.GONE);
            red_button.setVisibility(View.GONE);
            //
            Drawable drawable1 = getResources().getDrawable(R.mipmap.icon_ph);
            drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
            Drawable drawable2 = getResources().getDrawable(R.mipmap.icon_customerservice);
            drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
            if (bean.getUser_apply() == 1) {
                cancer_wait.setVisibility(View.VISIBLE);
                cancer_wait.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showExitCancerOrderDialog(mActivity);
                    }
                });
            }
            if (status == 1 || status == 9 || status == 10 || status == 11 || status == 12 || status == 13) {
                customer.setCompoundDrawables(drawable1, null, null, null);
                customer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showCallDialog(mActivity);
                    }
                });
                red_point.setVisibility(View.GONE);
            } else {
                customer.setCompoundDrawables(drawable2, null, null, null);
                customer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (orderId != null) {
                            red_point.setVisibility(View.GONE);
                            Intent i = new Intent(mActivity, ChatListActivity.class);
                            i.putExtra("orderId", orderId);
                            startActivity(i);

                        }
                    }
                });
                if (bean.getOrder_unread_msg() > 0) {
                    red_point.setVisibility(View.VISIBLE);
                }
            }
            cancer_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bean.getUser_apply() == 1) {
                        ToastUtils.showShort(R.string.cancer_order_wait);
                    } else {
                        Intent i = new Intent(mActivity, CancerOrderActivity.class);
                        i.putExtra("order_id", orderId);
                        startActivityForResult(i, GOTOCANCER);
                    }
                }
            });
            r_expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    performAnim2();
                }
            });
            v1.setVisibility(View.GONE);
            switch (status) {
                case 1://待接单
                    status_img.setImageResource(R.mipmap.icon_stayinorder);
                    red_button.setVisibility(View.VISIBLE);
                    red_button.setText(R.string.change_order);
                    red_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(mActivity, EditOrderActivity.class);
                            Bundle b = new Bundle();
                            b.putParcelable("orderDetail", bean);
                            i.putExtras(b);
                            startActivityForResult(i, GOTOEDIT);
                        }
                    });

                    break;
                case 2://已接单
                    status_img.setImageResource(R.mipmap.icon_done);
                    break;
                case 3://推荐专家
                    status_img.setImageResource(R.mipmap.icon_recommendedexpert);
                    red_button.setVisibility(View.VISIBLE);
                    red_button.setText(R.string.select_specialist);
                    red_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(mActivity, SpecialistListActivity.class);
                            i.putExtra("order_id", bean.getId());
                            startActivityForResult(i, GOTOSPECIAL);
                        }
                    });
                    if (bean.getRemark() != null && bean.getRemark().length() > 0) {
                        v1.setVisibility(View.VISIBLE);
                        remark.setVisibility(View.VISIBLE);
                        remark.setText("顾问备注：" + bean.getRemark());
                    }
                    showHistoryTj();
                    break;
                case 4://更换专家中
                    status_img.setImageResource(R.mipmap.icon_changeexperts);
                    if (bean.getRemark() != null && bean.getRemark().length() > 0) {
                        v1.setVisibility(View.VISIBLE);
                        remark.setVisibility(View.VISIBLE);
                        remark.setText("顾问备注：" + bean.getRemark());
                    }
                    showHistoryTj();
                    break;
                case 5://专家确认
                    r_status_cell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (bean.getExpert_id() != null) {
                                Intent i = new Intent(mActivity, SpecialistDetailActivity.class);
                                i.putExtra("id", bean.getExpert_id());
                                startActivity(i);
                            }
                        }
                    });
                    status_img.setImageResource(R.mipmap.icon_selectedexpert);
                    if (bean.getRemark() != null && bean.getRemark().length() > 0) {
                        v1.setVisibility(View.VISIBLE);
                        remark.setVisibility(View.VISIBLE);
                        remark.setText("顾问备注：" + bean.getRemark());
                    }

                    if (bean.getStatus_desc() != null) {
                        order_detail_2.setText(bean.getStatus_desc());
                    }
                    richText(order_detail_2, bean.getStatus_desc(), bean.getExpert_name());
                    showHistoryTj();
                    break;
                case 6://客户确认
                    r_status_cell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (bean.getExpert_id() != null) {
                                Intent i = new Intent(mActivity, SpecialistDetailActivity.class);
                                i.putExtra("id", bean.getExpert_id());
                                startActivity(i);
                            }
                        }
                    });
                    status_img.setImageResource(R.mipmap.icon_confirmexpertreply);
                    if (bean.getRemark() != null && bean.getRemark().length() > 0) {
                        v1.setVisibility(View.VISIBLE);
                        remark.setVisibility(View.VISIBLE);
                        remark.setText("顾问备注：" + bean.getRemark());
                    }
                    red_button.setVisibility(View.VISIBLE);
                    red_button.setText(R.string.affirm_expert);
                    gray_button.setVisibility(View.VISIBLE);
                    gray_button.setText(R.string.changeSpecial);
                    gray_button.setOnClickListener(new View.OnClickListener() {//确认约访
                        @Override
                        public void onClick(View view) {
                            showChangeExpertDialog(mActivity);
                        }
                    });
                    red_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (bean.getExpert_service_time() != null && bean.getExpert_service_time().size() > 0 && bean.getOffer() != null)
                                showSelectDateDialog(mActivity, bean.getExpert_service_time(), bean.getOffer());//选择具体日期 确认约访
                        }
                    });
                    richText(order_detail_2, bean.getStatus_desc(), bean.getExpert_name());
                    showHistoryTj();
                    break;
                case 7://服务待进行  // 不显示约访底部控件
                    r_status_cell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (bean.getExpert_id() != null) {
                                Intent i = new Intent(mActivity, SpecialistDetailActivity.class);
                                i.putExtra("id", bean.getExpert_id());
                                startActivity(i);
                            }
                        }
                    });
                    status_img.setImageResource(R.mipmap.icon_tovisit);
                    if (bean.getRemark() != null && bean.getRemark().length() > 0) {
                        v1.setVisibility(View.VISIBLE);
                        remark.setVisibility(View.VISIBLE);
                        remark.setText("顾问备注：" + bean.getRemark());
                    }
                    red_button.setVisibility(View.VISIBLE);
                    red_button.setText(R.string.changeSpecial);
                    red_button.setOnClickListener(new View.OnClickListener() {//确认约访
                        @Override
                        public void onClick(View view) {
                            showChangeExpertDialog(mActivity);
                        }
                    });
                    r_appointment.setVisibility(View.VISIBLE);
                    if (bean.getView_type_name() != null && bean.getView_type_name().length() > 0) {
                        appointment_type.setText(bean.getView_type_name());
                    }
                    if (bean.getView_time_str() != null && bean.getView_time_str().length() > 0) {//变更
                        appointment_time.setText(bean.getView_time_str());
                    }
                    if (bean.getOffer() != null) {
                        quote.setText(bean.getOffer());
                    }
                    richText(order_detail_desc, bean.getStatus_desc_add(), bean.getExpert_name());
                    showHistoryTj();
                    break;
                case 8://服务进行中
                    r_status_cell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (bean.getExpert_id() != null) {
                                Intent i = new Intent(mActivity, SpecialistDetailActivity.class);
                                i.putExtra("id", bean.getExpert_id());
                                startActivity(i);
                            }
                        }
                    });
                    status_img.setImageResource(R.mipmap.icon_interviewprocess);
                    if (bean.getRemark() != null && bean.getRemark().length() > 0) {
                        v1.setVisibility(View.VISIBLE);
                        remark.setVisibility(View.VISIBLE);
                        remark.setText("顾问备注：" + bean.getRemark());
                    }
                    r_appointment.setVisibility(View.VISIBLE);
                    if (bean.getView_type_name() != null && bean.getView_type_name().length() > 0) {
                        appointment_type.setText(bean.getView_type_name());
                    }
                    if (bean.getView_time_str() != null && bean.getView_time_str().length() > 0) {
                        appointment_time.setText(bean.getView_time_str());
                    }
                    if (bean.getOffer() != null) {
                        quote.setText(bean.getOffer());
                    }
                    red_button.setVisibility(View.VISIBLE);
                    red_button.setText(R.string.changeSpecial);
                    red_button.setOnClickListener(new View.OnClickListener() {//确认约访
                        @Override
                        public void onClick(View view) {
                            showChangeExpertDialog(mActivity);
                        }
                    });
                    richText(order_detail_desc, bean.getStatus_desc_add(), bean.getExpert_name());
                    showHistoryTj();
                    break;
                case 9://顾问取消
                    status_img.setImageResource(R.mipmap.icon_cancelled);
                    if (bean.getCancel_reason_name() != null && bean.getCancel_reason_name().length() > 0) {
                        v1.setVisibility(View.VISIBLE);
                        cancer_reason.setVisibility(View.VISIBLE);
                        cancer_reason.setText("取消原因：" + bean.getCancel_reason_name());
                    }
                    cancer_wait.setVisibility(View.GONE);// 取消订单 隐藏图标
                    cancer_order.setVisibility(View.GONE);
                    showHistoryTj();
                    break;
                case 10://评价
                    r_status_cell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (bean.getExpert_id() != null) {
                                Intent i = new Intent(mActivity, SpecialistDetailActivity.class);
                                i.putExtra("id", bean.getExpert_id());
                                startActivity(i);
                            }
                        }
                    });
                    status_img.setImageResource(R.mipmap.icon_haveorder);
                    if (bean.getRemark() != null && bean.getRemark().length() > 0) {
                        remark.setVisibility(View.VISIBLE);
                        remark.setText("顾问备注：" + bean.getRemark());
                    }
                    r_appointment.setVisibility(View.VISIBLE);
                    if (bean.getView_type_name() != null && bean.getView_type_name().length() > 0) {
                        appointment_type.setText(bean.getView_type_name());
                    }
                    if (bean.getView_time_str() != null && bean.getView_time_str().length() > 0) {
                        appointment_time.setText(bean.getView_time_str());
                    }
                    if (bean.getOffer() != null) {
                        quote.setText(bean.getOffer());
                    }
                    red_button.setVisibility(View.VISIBLE);
                    red_button.setText(R.string.evaluate);
                    red_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showEvaluateDialog(mActivity, true);
                        }
                    });
                    cancer_order.setVisibility(View.GONE);
                    richText(order_detail_desc, bean.getStatus_desc_add(), bean.getExpert_name());
                    showHistoryTj();
                    break;
                case 11:
                    r_status_cell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (bean.getExpert_id() != null) {
                                Intent i = new Intent(mActivity, SpecialistDetailActivity.class);
                                i.putExtra("id", bean.getExpert_id());
                                startActivity(i);
                            }
                        }
                    });
                    status_img.setImageResource(R.mipmap.icon_haveorder);
                    if (bean.getRemark() != null && bean.getRemark().length() > 0) {
                        v1.setVisibility(View.VISIBLE);
                        remark.setVisibility(View.VISIBLE);
                        remark.setText("顾问备注：" + bean.getRemark());
                    }
                    r_appointment.setVisibility(View.VISIBLE);
                    if (bean.getView_type_name() != null) {
                        appointment_type.setText(bean.getView_type_name());
                    }
                    if (bean.getView_start_time_str() != null && bean.getView_end_time_str() != null) {
                        appointment_time.setText(bean.getView_time_str());
                    }
                    if (bean.getOffer() != null) {
                        quote.setText(bean.getOffer());
                    }
                    red_button.setVisibility(View.VISIBLE);
                    red_button.setText(R.string.look_evaluate);
                    red_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showEvaluateDialog(mActivity, false);
                        }
                    });
                    cancer_order.setVisibility(View.GONE);
                    richText(order_detail_desc, bean.getStatus_desc_add(), bean.getExpert_name());
                    showHistoryTj();
                    break;
                case 12:
                    status_img.setImageResource(R.mipmap.icon_cancelled);
                    break;
                case 13:
                    status_img.setImageResource(R.mipmap.icon_yiyuqi);
                    showHistoryTj();
                    red_button.setVisibility(View.VISIBLE);
                    red_button.setText(R.string.activate);
                    red_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(mActivity, EditOrderActivity.class);
                            Bundle b = new Bundle();
                            b.putParcelable("orderDetail", bean);
                            b.putString("status", "13");
                            i.putExtras(b);
                            startActivityForResult(i, GOTOEDIT);
                        }
                    });
                    break;
            }

        }
        l_expand.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                expand_height = l_expand.getHeight();
                l_expand.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }


    private void showHistoryTj() {
        if (bean.getHistory_rmd_count() > 0) {//推荐历史数量大于0
            ShowOldRrecommend(bean.getHistory_rmd_count());
        }
    }

    private void ShowOldRrecommend(int number) {
        r_old_recommend.setVisibility(View.VISIBLE);
        recommend_history.setText("翰林派顾问已为您推荐了" + number + "位专家");
        r_old_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mActivity, SpecialistHistoryListActivity.class);
                i.putExtra("order_id", orderId);
                startActivity(i);
            }
        });

    }

    private CommonTipDialog callDialog;

    public void showCallDialog(Activity context) {
        if (callDialog != null && callDialog.isShowing()) {
            return;
        }
        if (bean.getConsumer_hotline() != null) {
            String tip = "客服经理联系方式\n" + bean.getConsumer_hotline();
            callDialog = new CommonTipDialog(context, tip, R.string.affirm, R.string.cancer, 260, new CommonTipDialog.DialogClickListener() {
                @Override
                public void onDialogClick() {
                    String callNumber = bean.getConsumer_hotline();
                    diallPhone(callNumber.replace("-", ""));
                }
            }, new CommonTipDialog.DialogClickListener() {
                @Override
                public void onDialogClick() {

                }
            });
            callDialog.show();
        }
    }

    private CommonTipDialog exitCancerOrderDialog;

    public void showExitCancerOrderDialog(Activity context) {
        if (exitCancerOrderDialog != null && exitCancerOrderDialog.isShowing()) {
            return;
        }
        if (bean.getConsumer_hotline() != null) {
            String title = getResources().getString(R.string.exit_order_cancer);
            exitCancerOrderDialog = new CommonTipDialog(context, title, R.string.affirm, R.string.cancer, 260, new CommonTipDialog.DialogClickListener() {
                @Override
                public void onDialogClick() {
                    mPresenter.cancerOrder(orderId, "2", "", "");
                }
            }, new CommonTipDialog.DialogClickListener() {
                @Override
                public void onDialogClick() {

                }
            });
            exitCancerOrderDialog.show();
        }
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

    private CommonTipDialog changeExpertDialog;

    public void showChangeExpertDialog(Activity context) {
        if (changeExpertDialog != null && changeExpertDialog.isShowing()) {
            return;
        }
        String string = getResources().getString(R.string.changeExpert);
        changeExpertDialog = new CommonTipDialog(context, string, R.string.affirm, R.string.cancer, 260, new CommonTipDialog.DialogClickListener() {
            @Override
            public void onDialogClick() {
                if (orderId != null)
                    mPresenter.expertSelect(orderId, "", "0", "", "");
            }
        }, new CommonTipDialog.DialogClickListener() {
            @Override
            public void onDialogClick() {

            }
        });
        changeExpertDialog.show();
    }

    private SelectDateDialog selectDateDialog;
    private String date_ids = "";

    public void showSelectDateDialog(Activity context, List<String> expert_service_time, String offer) {
        if (selectDateDialog != null && selectDateDialog.isShowing()) {
            return;
        }
        selectDateDialog = new SelectDateDialog(context, expert_service_time, offer, R.string.appointment, R.string.cancer, 260, new SelectDateDialog.ComfirmListenner() {
            @Override
            public void comfirm(List<String> backDate) {
                if (backDate != null && backDate.size() > 0) {
                    mPresenter.expertView(orderId, "1", backDate, "");
                }
            }

        }, new SelectDateDialog.DialogClickListener() {
            @Override
            public void onDialogClick() {

            }
        });
        selectDateDialog.show();
    }

    //评价
    private EvaluateDialog evaluateDialog;

    public void showEvaluateDialog(Activity context, boolean showAffirm) {
        if (evaluateDialog != null && evaluateDialog.isShowing()) {
            return;
        }
        if (bean != null) {
            String a = "", b = "", avl = "";
            float c = 0, d = 0;
            if (bean.getView_type_name() != null) {
                a = bean.getView_type_name();
            }
            if (bean.getService_dates() != null && bean.getService_dates().size() > 0) {
                b = bean.getView_time_str();
            }
            if (bean.getReview() != null) {
                c = Float.parseFloat(bean.getReview());
            }
            if (bean.getExpectation() != null) {
                d = Float.parseFloat(bean.getExpectation());
            }
            if (bean.getExpert_avatar() != null) {
                avl = bean.getExpert_avatar();
            }

            evaluateDialog = new EvaluateDialog(context, a, b, avl, c, d, showAffirm, new EvaluateDialog.DialogClickListener() {
                @Override
                public void onDialogClick(String a, String b) {
                    mPresenter.review(bean.getId(), a, b);
                }
            }, new EvaluateDialog.CancerDialogClickListener() {
                @Override
                public void onCancerDialogClick() {

                }


            });
            evaluateDialog.show();
        }

    }

    //订单流
    private OrderDialog orderDialog;

    public void showOrderStatusDialog(Activity context) {
        if (orderDialog != null && orderDialog.isShowing()) {
            return;
        }
        boolean last = false;
        if (bean.getStatus() == 9 || bean.getStatus() == 11 || bean.getStatus() == 12) {
            last = true;
        }
        orderDialog = new OrderDialog(context, bean.getOrder_progress(), last, new OrderDialog.DialogClickListener() {
            @Override
            public void onDialogClick() {

            }
        }, new OrderDialog.DialogClickListener() {
            @Override
            public void onDialogClick() {

            }
        });
        orderDialog.show();
    }


    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showShotToast("成功");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showShotToast("分享失败");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showShotToast("分享取消");

        }
    };

    private void performAnim2() {
        //View是否显示的标志
        expand = !expand;
        //属性动画对象
        ValueAnimator va;

        if (expand) {
            //显示view，高度从0变到height值
            va = ValueAnimator.ofInt(0, expand_height);
            ObjectAnimator ra = ObjectAnimator.ofFloat(img, "rotation", 180f, 0);
            ra.setDuration(300);
            ra.start();
        } else {
            //隐藏view，高度从height变为0
            va = ValueAnimator.ofInt(expand_height, 0);
            ObjectAnimator ra = ObjectAnimator.ofFloat(img, "rotation", 0, 180f);
            ra.setDuration(300);
            ra.start();
        }
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //获取当前的height值
                int h = (Integer) valueAnimator.getAnimatedValue();
                //动态更新view的高度
                l_expand.getLayoutParams().height = h;
                l_expand.requestLayout();

            }
        });
        va.setDuration(300);
        //开始动画
        va.start();
    }

    private void data_10_13(List<String> muldate) {
        for (int i = 0; i < muldate.size(); i++) {
            if (muldate.get(i) != null && muldate.get(i).length() == 10) {//MS
                String a = muldate.get(i);
                muldate.set(i, a + "000");
            }
        }
    }

    private String showMulDate(List<String> muldate) {
        data_10_13(muldate);
        StringBuffer sb = new StringBuffer();
        List<Long> l = new ArrayList<>();
        for (String s : muldate) {
            l.add(Long.parseLong(s));
        }
        Long[] array = l.toArray(new Long[0]);
        Arrays.sort(array);
        for (Long a : array) {
            sb.append(DataUtils.stampToDate3(a + "") + "、");
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    /**
     * @param tv     textView控件
     * @param str    原文本
     * @param regExp 正则表达式
     * @author xiaoming 2015年8月17日
     * @describe 设置富文本，改变textView部分文字颜色
     * @returnType void
     */
    public void richText(TextView tv, String str, String regExp) {
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        Pattern p = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        while (m.find()) {
            int start = m.start(0);
            int end = m.end(0);
            style.setSpan(new BackgroundColorSpan(Color.WHITE), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //指定位置文本的背景颜色
            style.setSpan(new ForegroundColorSpan(mActivity.getResources().getColor(R.color.colorAccent)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //指定位置文本的字体颜色
        }
        tv.setText(style);
    }
}
