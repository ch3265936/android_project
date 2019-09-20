package com.hanglinpai.ui.bean;

import java.util.List;

/**
 * @author chihai
 * @function Created on 2017/6/20.
 */

public class Static_info {
    private List<ItemSelectBean> order_status;//0 1 2
    private List<ItemSelectBean> industry_type;
    private List<ItemSelectBean> service_type;
    private List<ItemSelectBean> service_time;
    private List<ItemSelectBean> cancel_reason;

    public List<ItemSelectBean> getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(List<ItemSelectBean> cancel_reason) {
        this.cancel_reason = cancel_reason;
    }

    public List<ItemSelectBean> getOrder_status() {
        return order_status;
    }

    public void setOrder_status(List<ItemSelectBean> order_status) {
        this.order_status = order_status;
    }

    public List<ItemSelectBean> getIndustry_type() {
        return industry_type;
    }

    public void setIndustry_type(List<ItemSelectBean> industry_type) {
        this.industry_type = industry_type;
    }

    public List<ItemSelectBean> getService_type() {
        return service_type;
    }

    public void setService_type(List<ItemSelectBean> service_type) {
        this.service_type = service_type;
    }

    public List<ItemSelectBean> getService_time() {
        return service_time;
    }

    public void setService_time(List<ItemSelectBean> service_time) {
        this.service_time = service_time;
    }

    @Override
    public String toString() {
        return "Static_info{" +
                "order_status=" + order_status +
                ", industry_type=" + industry_type +
                ", service_type=" + service_type +
                ", service_time=" + service_time +
                ", cancel_reason=" + cancel_reason +
                '}';
    }
}
