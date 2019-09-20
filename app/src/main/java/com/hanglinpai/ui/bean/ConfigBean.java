package com.hanglinpai.ui.bean;

import android.icu.util.VersionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chihai
 * @function Created on 2017/6/20.
 */

public class ConfigBean {


    private String code;
    private String msg;
    private ConfigBean.ListBean data;

    @Override
    public String toString() {
        return "ConfigBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

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
      private Version_info version_info;
        private Static_info static_data;


        @Override
        public String toString() {
            return "ListBean{" +
                    "version_info=" + version_info +
                    ", static_data=" + static_data +
                    '}';
        }

        public Version_info getVersion_info() {
            return version_info;
        }

        public void setVersion_info(Version_info version_info) {
            this.version_info = version_info;
        }

        public Static_info getStatic_data() {
            return static_data;
        }

        public void setStatic_data(Static_info static_data) {
            this.static_data = static_data;
        }
    }
}
