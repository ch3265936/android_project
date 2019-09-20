package com.hanglinpai.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chihai on 2018/4/23.
 */

public class OrderProgress implements Parcelable {
    private String status_name;
    private String add_time;

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    @Override
    public String toString() {
        return "OrderProgress{" +
                "status_name='" + status_name + '\'' +
                ", add_time='" + add_time + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status_name);
        dest.writeString(this.add_time);
    }

    public OrderProgress() {
    }

    protected OrderProgress(Parcel in) {
        this.status_name = in.readString();
        this.add_time = in.readString();
    }

    public static final Parcelable.Creator<OrderProgress> CREATOR = new Parcelable.Creator<OrderProgress>() {
        @Override
        public OrderProgress createFromParcel(Parcel source) {
            return new OrderProgress(source);
        }

        @Override
        public OrderProgress[] newArray(int size) {
            return new OrderProgress[size];
        }
    };
}
