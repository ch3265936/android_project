package com.hanglinpai.ui.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chihai
 * @function Created on 2017/6/20.
 */

public class OrderListBean {


    private String code;
    private String msg;
    private OrderListBean.ListBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ListBean getData() {
        return data;
    }

    public void setData(ListBean data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class ListBean {
        private Links links;
        private Meta _meta;
        private List<OrderDetail> list = new ArrayList<>();

        public Links getLinks() {
            return links;
        }

        public void setLinks(Links links) {
            this.links = links;
        }

        public Meta get_meta() {
            return _meta;
        }

        public void set_meta(Meta _meta) {
            this._meta = _meta;
        }

        public List<OrderDetail> getList() {
            return list;
        }

        public void setList(List<OrderDetail> list) {
            this.list = list;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "links=" + links +
                    ", _meta=" + _meta +
                    ", list=" + list +
                    '}';
        }
    }
}
