package com.hanglinpai.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author chihai
 * @function Created on 2017/6/20.
 */

public class HistoryPassBean implements Parcelable {


    private String code;
    private String msg;
    private List<HistoryPass> data;

    @Override
    public String toString() {
        return "HistoryPassBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public List<HistoryPass> getData() {
        return data;
    }

    public void setData(List<HistoryPass> data) {
        this.data = data;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.msg);
        dest.writeTypedList(this.data);
    }

    public HistoryPassBean() {
    }

    protected HistoryPassBean(Parcel in) {
        this.code = in.readString();
        this.msg = in.readString();
        this.data = in.createTypedArrayList(HistoryPass.CREATOR);
    }

    public static final Parcelable.Creator<HistoryPassBean> CREATOR = new Parcelable.Creator<HistoryPassBean>() {
        @Override
        public HistoryPassBean createFromParcel(Parcel source) {
            return new HistoryPassBean(source);
        }

        @Override
        public HistoryPassBean[] newArray(int size) {
            return new HistoryPassBean[size];
        }
    };
}
