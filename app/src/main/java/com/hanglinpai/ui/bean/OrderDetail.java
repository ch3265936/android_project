package com.hanglinpai.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chihai on 2018/4/23.
 */


public class OrderDetail implements Parcelable {
    private String id;
    private String order_no;
    private String user_id;
    private String service_start_time;
    private String service_end_time;
    private int status;
    private String status_name;
    private String industry_type_name;
    private String industry_type;
    private List<ItemSelectBean> service_type_name;
    private String service_time;
    private String service_time_name;
    private String site;//地址
    private String desc;
    private String review;//整体服务评价
    private String expectation;//预期
    private String remark;//顾问备注
    private String expert_name;
    private String expert_id;
    private int order_unread_msg;// 是否有新消息
    private String add_time_str;
    private String add_time_str_list;
    private String status_name_remark;

    private List<String> service_dates;//选择的日期
    private List<String> expert_service_time;//可以选择的约访时间点
    private String view_time_str;//选择好的约访时间
    private int user_apply ;
    private int history_rmd_count;//历史推荐数量

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id='" + id + '\'' +
                ", order_no='" + order_no + '\'' +
                ", user_id='" + user_id + '\'' +
                ", service_start_time='" + service_start_time + '\'' +
                ", service_end_time='" + service_end_time + '\'' +
                ", status=" + status +
                ", status_name='" + status_name + '\'' +
                ", industry_type_name='" + industry_type_name + '\'' +
                ", industry_type='" + industry_type + '\'' +
                ", service_type_name=" + service_type_name +
                ", service_time='" + service_time + '\'' +
                ", service_time_name='" + service_time_name + '\'' +
                ", site='" + site + '\'' +
                ", desc='" + desc + '\'' +
                ", review='" + review + '\'' +
                ", expectation='" + expectation + '\'' +
                ", remark='" + remark + '\'' +
                ", expert_name='" + expert_name + '\'' +
                ", expert_id='" + expert_id + '\'' +
                ", order_unread_msg=" + order_unread_msg +
                ", add_time_str='" + add_time_str + '\'' +
                ", add_time_str_list='" + add_time_str_list + '\'' +
                ", status_name_remark='" + status_name_remark + '\'' +
                ", service_dates=" + service_dates +
                ", expert_service_time=" + expert_service_time +
                ", view_time_str='" + view_time_str + '\'' +
                ", user_apply=" + user_apply +
                ", history_rmd_count=" + history_rmd_count +
                ", update_time='" + update_time + '\'' +
                ", status_desc='" + status_desc + '\'' +
                ", status_desc_add='" + status_desc_add + '\'' +
                ", cancel_reason_name='" + cancel_reason_name + '\'' +
                ", expert_rmd=" + expert_rmd +
                ", view_type_name='" + view_type_name + '\'' +
                ", view_start_time_str='" + view_start_time_str + '\'' +
                ", view_end_time_str='" + view_end_time_str + '\'' +
                ", offer='" + offer + '\'' +
                ", order_progress=" + order_progress +
                ", expert_avatar='" + expert_avatar + '\'' +
                ", consumer_hotline='" + consumer_hotline + '\'' +
                '}';
    }

    public int getHistory_rmd_count() {
        return history_rmd_count;
    }

    public void setHistory_rmd_count(int history_rmd_count) {
        this.history_rmd_count = history_rmd_count;
    }

    public int getUser_apply() {
        return user_apply;
    }

    public void setUser_apply(int user_apply) {
        this.user_apply = user_apply;
    }

    public String getExpert_id() {
        return expert_id;
    }

    public void setExpert_id(String expert_id) {
        this.expert_id = expert_id;
    }

    public List<String> getService_dates() {
        return service_dates;
    }

    public void setService_dates(List<String> service_dates) {
        this.service_dates = service_dates;
    }

    public List<String> getExpert_service_time() {
        return expert_service_time;
    }

    public void setExpert_service_time(List<String> expert_service_time) {
        this.expert_service_time = expert_service_time;
    }

    public String getView_time_str() {
        return view_time_str;
    }

    public void setView_time_str(String view_time_str) {
        this.view_time_str = view_time_str;
    }

    public String getStatus_name_remark() {
        return status_name_remark;
    }

    public void setStatus_name_remark(String status_name_remark) {
        this.status_name_remark = status_name_remark;
    }

    public String getAdd_time_str_list() {
        return add_time_str_list;
    }

    public void setAdd_time_str_list(String add_time_str_list) {
        this.add_time_str_list = add_time_str_list;
    }

    public int getOrder_unread_msg() {
        return order_unread_msg;
    }

    public void setOrder_unread_msg(int order_unread_msg) {
        this.order_unread_msg = order_unread_msg;
    }

    private String update_time;

    private String status_desc;
    private String status_desc_add;

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }

    public String getStatus_desc_add() {
        return status_desc_add;
    }

    public void setStatus_desc_add(String status_desc_add) {
        this.status_desc_add = status_desc_add;
    }

    private String cancel_reason_name;
    private List<Expert> expert_rmd = new ArrayList<>();

    public String getExpert_name() {
        return expert_name;
    }

    public void setExpert_name(String expert_name) {
        this.expert_name = expert_name;
    }

    private String view_type_name; //约见类型

    private String view_start_time_str; //约见开始时间

    private String view_end_time_str; //约见结束时间

    private String offer; //报价

    private List<OrderProgress> order_progress;//订单进度

    private String expert_avatar;//选择专家的头像
    private String consumer_hotline;

    public String getConsumer_hotline() {
        return consumer_hotline;
    }

    public void setConsumer_hotline(String consumer_hotline) {
        this.consumer_hotline = consumer_hotline;
    }

    public String getExpert_avatar() {
        return expert_avatar;
    }

    public void setExpert_avatar(String expert_avatar) {
        this.expert_avatar = expert_avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getService_start_time() {
        return service_start_time;
    }

    public void setService_start_time(String service_start_time) {
        this.service_start_time = service_start_time;
    }

    public String getService_end_time() {
        return service_end_time;
    }

    public void setService_end_time(String service_end_time) {
        this.service_end_time = service_end_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getIndustry_type_name() {
        return industry_type_name;
    }

    public void setIndustry_type_name(String industry_type_name) {
        this.industry_type_name = industry_type_name;
    }

    public String getIndustry_type() {
        return industry_type;
    }

    public void setIndustry_type(String industry_type) {
        this.industry_type = industry_type;
    }

    public List<ItemSelectBean> getService_type_name() {
        return service_type_name;
    }

    public void setService_type_name(List<ItemSelectBean> service_type_name) {
        this.service_type_name = service_type_name;
    }

    public String getService_time() {
        return service_time;
    }

    public void setService_time(String service_time) {
        this.service_time = service_time;
    }

    public String getService_time_name() {
        return service_time_name;
    }

    public void setService_time_name(String service_time_name) {
        this.service_time_name = service_time_name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getExpectation() {
        return expectation;
    }

    public void setExpectation(String expectation) {
        this.expectation = expectation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAdd_time_str() {
        return add_time_str;
    }

    public void setAdd_time_str(String add_time_str) {
        this.add_time_str = add_time_str;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getCancel_reason_name() {
        return cancel_reason_name;
    }

    public void setCancel_reason_name(String cancel_reason_name) {
        this.cancel_reason_name = cancel_reason_name;
    }

    public List<Expert> getExpert_rmd() {
        return expert_rmd;
    }

    public void setExpert_rmd(List<Expert> expert_rmd) {
        this.expert_rmd = expert_rmd;
    }

    public String getView_type_name() {
        return view_type_name;
    }

    public void setView_type_name(String view_type_name) {
        this.view_type_name = view_type_name;
    }

    public String getView_start_time_str() {
        return view_start_time_str;
    }

    public void setView_start_time_str(String view_start_time_str) {
        this.view_start_time_str = view_start_time_str;
    }

    public String getView_end_time_str() {
        return view_end_time_str;
    }

    public void setView_end_time_str(String view_end_time_str) {
        this.view_end_time_str = view_end_time_str;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public List<OrderProgress> getOrder_progress() {
        return order_progress;
    }

    public void setOrder_progress(List<OrderProgress> order_progress) {
        this.order_progress = order_progress;
    }

    public OrderDetail() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.order_no);
        dest.writeString(this.user_id);
        dest.writeString(this.service_start_time);
        dest.writeString(this.service_end_time);
        dest.writeInt(this.status);
        dest.writeString(this.status_name);
        dest.writeString(this.industry_type_name);
        dest.writeString(this.industry_type);
        dest.writeTypedList(this.service_type_name);
        dest.writeString(this.service_time);
        dest.writeString(this.service_time_name);
        dest.writeString(this.site);
        dest.writeString(this.desc);
        dest.writeString(this.review);
        dest.writeString(this.expectation);
        dest.writeString(this.remark);
        dest.writeString(this.expert_name);
        dest.writeString(this.expert_id);
        dest.writeInt(this.order_unread_msg);
        dest.writeString(this.add_time_str);
        dest.writeString(this.add_time_str_list);
        dest.writeString(this.status_name_remark);
        dest.writeStringList(this.service_dates);
        dest.writeStringList(this.expert_service_time);
        dest.writeString(this.view_time_str);
        dest.writeInt(this.user_apply);
        dest.writeInt(this.history_rmd_count);
        dest.writeString(this.update_time);
        dest.writeString(this.status_desc);
        dest.writeString(this.status_desc_add);
        dest.writeString(this.cancel_reason_name);
        dest.writeTypedList(this.expert_rmd);
        dest.writeString(this.view_type_name);
        dest.writeString(this.view_start_time_str);
        dest.writeString(this.view_end_time_str);
        dest.writeString(this.offer);
        dest.writeTypedList(this.order_progress);
        dest.writeString(this.expert_avatar);
        dest.writeString(this.consumer_hotline);
    }

    protected OrderDetail(Parcel in) {
        this.id = in.readString();
        this.order_no = in.readString();
        this.user_id = in.readString();
        this.service_start_time = in.readString();
        this.service_end_time = in.readString();
        this.status = in.readInt();
        this.status_name = in.readString();
        this.industry_type_name = in.readString();
        this.industry_type = in.readString();
        this.service_type_name = in.createTypedArrayList(ItemSelectBean.CREATOR);
        this.service_time = in.readString();
        this.service_time_name = in.readString();
        this.site = in.readString();
        this.desc = in.readString();
        this.review = in.readString();
        this.expectation = in.readString();
        this.remark = in.readString();
        this.expert_name = in.readString();
        this.expert_id = in.readString();
        this.order_unread_msg = in.readInt();
        this.add_time_str = in.readString();
        this.add_time_str_list = in.readString();
        this.status_name_remark = in.readString();
        this.service_dates = in.createStringArrayList();
        this.expert_service_time = in.createStringArrayList();
        this.view_time_str = in.readString();
        this.user_apply = in.readInt();
        this.history_rmd_count = in.readInt();
        this.update_time = in.readString();
        this.status_desc = in.readString();
        this.status_desc_add = in.readString();
        this.cancel_reason_name = in.readString();
        this.expert_rmd = in.createTypedArrayList(Expert.CREATOR);
        this.view_type_name = in.readString();
        this.view_start_time_str = in.readString();
        this.view_end_time_str = in.readString();
        this.offer = in.readString();
        this.order_progress = in.createTypedArrayList(OrderProgress.CREATOR);
        this.expert_avatar = in.readString();
        this.consumer_hotline = in.readString();
    }

    public static final Creator<OrderDetail> CREATOR = new Creator<OrderDetail>() {
        @Override
        public OrderDetail createFromParcel(Parcel source) {
            return new OrderDetail(source);
        }

        @Override
        public OrderDetail[] newArray(int size) {
            return new OrderDetail[size];
        }
    };
}
