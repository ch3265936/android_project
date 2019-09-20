package com.hanglinpai.ui.bean;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chihai
 * @function Created on 2017/6/20.
 */

public class UploadBean {


    private String code;
    private String msg;
    private UploadBean.ListBean data;

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
        private List<String> url = new ArrayList<>();

        public List<String> getUrl() {
            return url;
        }

        public void setUrl(List<String> url) {
            this.url = url;
        }
    }
}
