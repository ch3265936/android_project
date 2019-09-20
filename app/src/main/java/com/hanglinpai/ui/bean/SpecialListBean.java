package com.hanglinpai.ui.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chihai
 * @function Created on 2017/6/20.
 */

public class SpecialListBean {


    private String code;
    private String msg;
    private List<Expert> data;

    public List<Expert> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "SpecialListBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public void setData(List<Expert> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
