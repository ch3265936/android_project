package com.hanglinpai.ui.bean;

/**
 * @author chihai
 * @function Created on 2017/6/20.
 */

public class OrderDetailBean {


    private String code;
    private String msg;
    private OrderDetail data;

    @Override
    public String toString() {
        return "OrderDetailBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public OrderDetail getData() {
        return data;
    }

    public void setData(OrderDetail data) {
        this.data = data;
    }
}
