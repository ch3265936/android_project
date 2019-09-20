package com.hanglinpai.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chihai
 * @function Created on 2017/6/20.
 */

public class AddChatBean implements Parcelable {


    private String code;
    private String msg;
    private List<ChatDetail> data;

    @Override
    public String toString() {
        return "AddChatBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public List<ChatDetail> getData() {
        return data;
    }

    public void setData(List<ChatDetail> data) {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.msg);
        dest.writeList(this.data);
    }

    public AddChatBean() {
    }

    protected AddChatBean(Parcel in) {
        this.code = in.readString();
        this.msg = in.readString();
        this.data = new ArrayList<ChatDetail>();
        in.readList(this.data, ChatDetail.class.getClassLoader());
    }

    public static final Parcelable.Creator<AddChatBean> CREATOR = new Parcelable.Creator<AddChatBean>() {
        @Override
        public AddChatBean createFromParcel(Parcel source) {
            return new AddChatBean(source);
        }

        @Override
        public AddChatBean[] newArray(int size) {
            return new AddChatBean[size];
        }
    };
}
