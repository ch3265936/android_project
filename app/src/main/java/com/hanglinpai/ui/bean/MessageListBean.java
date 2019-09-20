package com.hanglinpai.ui.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chihai
 * @function Created on 2017/6/20.
 */

public class MessageListBean {


    private String code;
    private String msg;
    private MessageListBean.ListBean data;

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
        private List<MessageDetail> list = new ArrayList<>();

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

        public List<MessageDetail> getList() {
            return list;
        }

        public void setList(List<MessageDetail> list) {
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
