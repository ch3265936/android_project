package com.hanglinpai.ui.order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.hanglinpai.R;
import com.hanglinpai.ui.bean.ItemSelectBean;
import com.hanglinpai.ui.bean.OrderDetail;
import com.hanglinpai.ui.bean.Static_info;
import com.hanglinpai.ui.home.HomeActivity;
import com.hanglinpai.ui.order.constract.AddOrderConstract;
import com.hanglinpai.ui.order.constract.OrderConstract;
import com.hanglinpai.ui.order.model.AddOrderModel;
import com.hanglinpai.ui.order.model.OrderModel;
import com.hanglinpai.ui.order.presenter.AddOrderPresenter;
import com.hanglinpai.ui.order.presenter.OrderPresenter;
import com.hanglinpai.util.DataUtils;
import com.hanglinpai.util.IEditTextChangeListener;
import com.hanglinpai.util.RegexValidateUtil;
import com.hanglinpai.util.SystemUtils;
import com.hanglinpai.util.WorksSizeCheckUtil;
import com.hanglinpai.widget.SelectPopWidow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;
import www.meiyaoni.com.common.base.BaseActivity;
import www.meiyaoni.com.common.toolUtils.SPManager;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

public class NewOrderActivity extends BaseActivity<AddOrderPresenter, AddOrderModel> implements AddOrderConstract.View {
    @Bind(R.id.title_back)
    TextView title_back;
    @Bind(R.id.title_title)
    TextView title_title;
    @Bind(R.id.guild)
    TextView guild;
    @Bind(R.id.server)
    TextView server;
    @Bind(R.id.server_time)
    TextView server_time;
    @Bind(R.id.start_time)
    TextView start_time;
    @Bind(R.id.server_address)
    EditText server_address;
    @Bind(R.id.issue)
    Button issue;
    @Bind(R.id.desc)
    EditText desc;
    @Bind(R.id.red_guild_tip)
    TextView red_guild_tip;
    @Bind(R.id.red_server_tip)
    TextView red_server_tip;
    @Bind(R.id.red_server_start_time_tip)
    TextView red_server_start_time_tip;
    @Bind(R.id.red_server_time_tip)
    TextView red_server_time_tip;
    @Bind(R.id.red_server_address_tip)
    TextView red_server_address_tip;
    @Bind(R.id.r_guild)
    RelativeLayout r_guild;
    @Bind(R.id.r_server)
    RelativeLayout r_server;
    @Bind(R.id.r_start_time)
    RelativeLayout r_start_time;
    @Bind(R.id.r_server_time)
    RelativeLayout r_server_time;

    TimePickerView pvTime;
    private String industry_type_id = "", industry_type_name = "", server_time_id = "", server_time_name = "", desc_str = "", server_address_str = "";
    private List<ItemSelectBean> industry_type = new ArrayList<>();
    private List<ItemSelectBean> service_type = new ArrayList<>();
    private List<ItemSelectBean> service_type_select = new ArrayList<>();
    private List<ItemSelectBean> serverTime = new ArrayList<>();
    private List<ItemSelectBean> serverTime_select = new ArrayList<>();
    private Static_info static_info = null;
    List<String> ids = new ArrayList<>();
    List<String> muldate = new ArrayList<>();
    long k, d, e;
    private final int GOTOMULDATE = 3;
    private final int GOTOGUID = 2;


    @Override
    public int getLayoutId() {
        return R.layout.activity_new_order;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        title_title.setText(R.string.release_order);
        String Static = SPManager.getInstance().getString("Static", "");
        Gson gson = new Gson();
        static_info = gson.fromJson(Static, Static_info.class);
        if (static_info != null) {
            if (static_info.getIndustry_type() != null) {
                industry_type.addAll(static_info.getIndustry_type());
            }
            if (static_info.getService_type() != null) {
                service_type.addAll(static_info.getService_type());
            }
            if (static_info.getService_time() != null) {
                serverTime.addAll(static_info.getService_time());
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//  键盘冲突直接该状态栏颜色为标题栏白色  （透明状态栏也是白色效果一样 键盘上顶不冲突）
            Window window = getWindow();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.WHITE);
            window.setNavigationBarColor(getResources().getColor(www.meiyaoni.com.common.R.color.pickerview_bgColor_overlay));
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.down_up2, R.anim.up_down2);
    }

    @OnClick({R.id.title_back, R.id.r_guild, R.id.issue, R.id.r_start_time, R.id.r_server_time, R.id.r_server})
    public void click(View v) {

        switch (v.getId()) {
            case R.id.title_back:
                finish();
                overridePendingTransition(R.anim.down_up2, R.anim.up_down2);
                break;

            case R.id.issue:
                if (!SystemUtils.isFastClick()) {
                    ToastUtils.showShort(R.string.click_fast);
                    return;
                }
                redTip();
                if (industry_type_id == null || industry_type_id.length() <= 0) {
                    ToastUtils.showShotToast(getResources().getString(R.string.select_guild));
                    return;
                }
                if (ids == null || ids.size() <= 0) {
                    ToastUtils.showShotToast(getResources().getString(R.string.select_serve));
                    return;
                }
                String date = start_time.getText().toString().trim();
                if (date == null || date.length() <= 0) {
                    ToastUtils.showShotToast("请选择服务日期");
                    return;
                }


                if (server_time_id == null || server_time_id.length() <= 0) {
                    ToastUtils.showShotToast(getResources().getString(R.string.select_serve_time));
                    return;
                }
//                if (server_address.getText().toString().length() <= 1) {
//                    ToastUtils.showShotToast(getResources().getString(R.string.select_serve_address));
//                    return;
//                }
                server_address_str = server_address.getText().toString().trim();
//                if (desc.getText().toString().trim().length() < 5) {
//                    ToastUtils.showShotToast("需求描述最少输入5个字以上");
//                    return;
//                }
                desc_str = desc.getText().toString().trim();
                data_13_10();
                mPresenter.addOrder(industry_type_id, ids, muldate, server_time_id, server_address_str, desc_str);
                break;
            case R.id.r_guild:
//                SelectPopWidow.getInstance().showPopwindow(getResources().getString(R.string.guild), industry_type, industry_type_select, false, mActivity, new SelectPopWidow.ComfirmListenner() {
//                    @Override
//                    public void comfirm(List<ItemSelectBean> backDate) {
//                        if (backDate != null && backDate.size() > 0 && backDate.get(0) != null && backDate.get(0).id != null && backDate.get(0).name != null) {
//                            industry_type_id = backDate.get(0).id;
//                            industry_type_name = backDate.get(0).name;
//                            guild.setText(industry_type_name);
//                        }
//                    }
//                });
                Intent i = new Intent(mActivity, GuildListActivity.class);
                i.putExtra("select", industry_type_id);
                i.putExtra("selectName", industry_type_name);
                startActivityForResult(i, GOTOGUID);
                break;
            case R.id.r_server:
                SelectPopWidow.getInstance().showPopwindow(getResources().getString(R.string.serve), service_type, service_type_select, true, mActivity, new SelectPopWidow.ComfirmListenner() {
                    @Override
                    public void comfirm(List<ItemSelectBean> backDate) {
                        ids.clear();
                        if(backDate.size()==0){
                            server.setText("");
                        }else {
                            StringBuffer sb = new StringBuffer();
                            for (int i = 0; i < backDate.size(); i++) {
                                if (i == 0) {
                                    sb.append(backDate.get(i).name);

                                } else {
                                    sb.append("、" + backDate.get(i).name);
                                }
                                ids.add(backDate.get(i).id);
                                server.setText(sb.toString());
                            }
                        }
                    }
                });
                break;
            case R.id.r_server_time:
                SelectPopWidow.getInstance().showPopwindow(getResources().getString(R.string.serve_time), serverTime, serverTime_select, false, mActivity, new SelectPopWidow.ComfirmListenner() {
                    @Override
                    public void comfirm(List<ItemSelectBean> backDate) {
                        if (backDate != null && backDate.size() > 0 && backDate.get(0) != null && backDate.get(0).id != null && backDate.get(0).name != null) {
                            server_time_id = backDate.get(0).id;
                            server_time_name = backDate.get(0).name;
                            server_time.setText(server_time_name);
                        } else {
                            server_time.setText("");
                        }
                    }
                });
                break;
            case R.id.r_start_time:
                Intent i2 = new Intent(mActivity, DateSelectActivity.class);
                i2.putStringArrayListExtra("muldate", (ArrayList<String>) muldate);
                startActivityForResult(i2, GOTOMULDATE);
                break;

            default:
                break;
        }

    }


    private void redTip() {
        if (industry_type_id == null || industry_type_id.length() <= 0) {
            red_guild_tip.setVisibility(View.VISIBLE);
        } else {
            red_guild_tip.setVisibility(View.GONE);
        }
        if (ids == null || ids.size() <= 0) {
            red_server_tip.setVisibility(View.VISIBLE);
        } else {
            red_server_tip.setVisibility(View.GONE);
        }
        String date = start_time.getText().toString().trim();
        if (date != null && date.length() <= 0) {
            red_server_start_time_tip.setVisibility(View.VISIBLE);
        } else {
            red_server_start_time_tip.setVisibility(View.GONE);
        }


        if (server_time_id == null || server_time_id.length() <= 0) {
            red_server_time_tip.setVisibility(View.VISIBLE);
        } else {
            red_server_time_tip.setVisibility(View.GONE);
        }
//        if (server_address.getText().toString().length() <= 0) {
//            red_server_address_tip.setVisibility(View.VISIBLE);
//        } else {
//            red_server_address_tip.setVisibility(View.GONE);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GOTOMULDATE && resultCode == GOTOMULDATE) {
            if (data.getStringArrayListExtra("muldate") != null) {
                muldate.clear();
                muldate = data.getStringArrayListExtra("muldate");
                showMulDate();
            }
        }
        if (requestCode == GOTOGUID && resultCode == GOTOGUID) {
            industry_type_id = data.getStringExtra("select");
            industry_type_name = data.getStringExtra("selectName");
            guild.setText(industry_type_name);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void showMulDate() {
        if (muldate != null && muldate.size() > 0) {
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
            start_time.setText(sb.toString().substring(0, sb.toString().length() - 1));
        }
    }

    @Override
    public void returnPutSuccess() {

    }

    @Override
    public void returnAddSuccess() {
        setResult(5);
        finish();
        overridePendingTransition(R.anim.down_up2, R.anim.up_down2);
    }

    private void data_13_10() {
        for (int i = 0; i < muldate.size(); i++) {
            if (muldate.get(i) != null && muldate.get(i).length() == 13) {//MS
                String a = muldate.get(i).substring(0, 10);
                muldate.set(i, a);
            }
        }
    }
}
