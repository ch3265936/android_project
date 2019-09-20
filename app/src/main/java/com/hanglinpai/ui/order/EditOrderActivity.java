package com.hanglinpai.ui.order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.hanglinpai.EventBus.ACTIVATE;
import com.hanglinpai.R;
import com.hanglinpai.ui.bean.ItemSelectBean;
import com.hanglinpai.ui.bean.OrderDetail;
import com.hanglinpai.ui.bean.Static_info;
import com.hanglinpai.ui.order.constract.AddOrderConstract;
import com.hanglinpai.ui.order.model.AddOrderModel;
import com.hanglinpai.ui.order.presenter.AddOrderPresenter;
import com.hanglinpai.util.DataUtils;
import com.hanglinpai.util.SystemUtils;
import com.hanglinpai.widget.SelectPopWidow;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import www.meiyaoni.com.common.base.BaseActivity;
import www.meiyaoni.com.common.toolUtils.SPManager;
import www.meiyaoni.com.common.toolUtils.ToastUtils;

public class EditOrderActivity extends BaseActivity<AddOrderPresenter, AddOrderModel> implements AddOrderConstract.View {
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
    TextView server_address;
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
    TimePickerView pvTime;
    private String industry_type_id = "", industry_type_name = "", server_time_id = "", server_time_name = "", desc_str = "", server_address_str = "";
    private List<ItemSelectBean> industry_type = new ArrayList<>();
    private List<ItemSelectBean> industry_type_select = new ArrayList<>();
    private List<ItemSelectBean> service_type = new ArrayList<>();
    private List<ItemSelectBean> service_type_select = new ArrayList<>();
    private List<ItemSelectBean> serverTime = new ArrayList<>();
    private List<ItemSelectBean> serverTime_select = new ArrayList<>();
    private Static_info static_info = null;
    List<String> ids = new ArrayList<>();
    private final int GOTOMULDATE = 3;
    private final int GOTOGUID = 2;
    List<String> muldate = new ArrayList<>();
    private OrderDetail detail = null;
private boolean isActivate =false;
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

        title_title.setText(R.string.change_order);
        issue.setText(R.string.comfirm_change);
        Bundle b = getIntent().getExtras();
        if (b != null && b.getParcelable("orderDetail") != null) {
            detail = b.getParcelable("orderDetail");
            if(b.get("status")!=null&&b.get("status").equals("13")){
                title_title.setText(R.string.overdue_order);
                isActivate =true;
            }
            if (detail != null)
                initDetail(detail);
        }
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

    private void initDetail(OrderDetail detail) {
        if (detail.getIndustry_type() != null && detail.getIndustry_type_name() != null) {
            industry_type_id = detail.getIndustry_type();
            industry_type_name = detail.getIndustry_type_name();
            ItemSelectBean i = new ItemSelectBean();
            i.id = industry_type_id;
            i.name = industry_type_name;
            industry_type_select.add(i);
            guild.setText(industry_type_name);
        }
        if (detail.getService_type_name() != null) {
            service_type_select.clear();
            service_type_select.addAll(detail.getService_type_name());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < service_type_select.size(); i++) {
                if (i == 0) {
                    sb.append(service_type_select.get(i).name);

                } else {
                    sb.append("、" + service_type_select.get(i).name);
                }
                ids.add(service_type_select.get(i).id);
                server.setText(sb.toString());
            }
        }
        if(detail.getStatus()==13){//逾期
            guild.setEnabled(false);
        }else{
            guild.setEnabled(true);
            if(detail.getService_dates()!=null&&detail.getService_dates().size()>0){// 选好的日期
                muldate.clear();
                muldate.addAll(detail.getService_dates());
                data_10_13();
                showMulDate();
            }
        }

        if (detail.getService_time() != null && detail.getService_time_name() != null) {
            server_time_id = detail.getService_time();
            server_time_name = detail.getService_time_name();
            ItemSelectBean i = new ItemSelectBean();
            i.id = server_time_id;
            i.name = server_time_name;
            serverTime_select.add(i);
            server_time.setText(server_time_name);
        }
        if (detail.getSite() != null) {
            server_address.setText(detail.getSite());
        }
        if (detail.getDesc() != null) {
            desc.setText(detail.getDesc());
        }
        issue.setBackground(getResources().getDrawable(R.drawable.bg_select));
        issue.setTextColor(getResources().getColor(R.color.white));


    }

    @OnClick({R.id.title_back, R.id.guild, R.id.issue, R.id.start_time,R.id.server, R.id.server_time})
    public void click(View v) {

        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.issue:
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
            ;
                if (date != null && date.length() <= 0) {
                    ToastUtils.showShotToast("请选择服务日期");
                    return;
                }


                if (!SystemUtils.isFastClick()) {
                    ToastUtils.showShort(R.string.click_fast);
                    return;
                }
                if (server_time_id == null || server_time_id.length() <= 0) {
                    ToastUtils.showShotToast(getResources().getString(R.string.select_serve_time));
                    return;
                }
//                if (server_address.getText().toString().trim().length() <= 0) {
//                    ToastUtils.showShotToast(getResources().getString(R.string.select_serve_address));
//                    return;
//                }
                server_address_str = server_address.getText().toString().trim();

                desc_str = desc.getText().toString().trim();
                data_13_10();
                if (detail != null && detail.getId() != null) {
                    mPresenter.putOrder(detail.getId(), industry_type_id, ids, muldate, server_time_id, server_address_str, desc_str);
                }
                break;
            case R.id.guild:
                Intent i = new Intent(mActivity, GuildListActivity.class);
                i.putExtra("select", industry_type_id);
                i.putExtra("selectName", industry_type_name);
                startActivityForResult(i, GOTOGUID);
                break;
            case R.id.server:
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
            case R.id.server_time:
                SelectPopWidow.getInstance().showPopwindow(getResources().getString(R.string.serve_time), serverTime, serverTime_select, false, mActivity, new SelectPopWidow.ComfirmListenner() {
                    @Override
                    public void comfirm(List<ItemSelectBean> backDate) {
                        if (backDate != null && backDate.get(0) != null && backDate.get(0).id != null && backDate.get(0).name != null) {
                            server_time_id = backDate.get(0).id;
                            server_time_name = backDate.get(0).name;
                            server_time.setText(server_time_name);
                        }
                    }
                });
                break;
            case R.id.start_time:
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


    private void data_10_13(){
        for(int i =0; i<muldate.size();i++){
            if(muldate.get(i)!=null&&muldate.get(i).length()==10){//MS
                String a =muldate.get(i);
                muldate.set(i,a+"000");
            }
        }
    }
    private void data_13_10(){
        for(int i =0; i<muldate.size();i++){
            if(muldate.get(i)!=null&&muldate.get(i).length()==13){//MS
                String a =muldate.get(i).substring(0,10);
                muldate.set(i,a);
            }
        }
    }
    @Override
    public void returnPutSuccess() {
        if(isActivate){
            EventBus.getDefault().post(new ACTIVATE(""));
        }
        setResult(11);
        finish();
    }

    @Override
    public void returnAddSuccess() {

    }
}
