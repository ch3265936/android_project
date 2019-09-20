package com.hanglinpai.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by chihai on 2018/4/23.
 */

public class HistoryPass implements Parcelable {
    private String name;
    private List<Expert> list;

    @Override
    public String toString() {
        return "HistoryPass{" +
                "name='" + name + '\'' +
                ", list=" + list +
                '}';
    }

    public List<Expert> getList() {
        return list;
    }

    public void setList(List<Expert> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeTypedList(this.list);
    }

    public HistoryPass() {
    }

    protected HistoryPass(Parcel in) {
        this.name = in.readString();
        this.list = in.createTypedArrayList(Expert.CREATOR);
    }

    public static final Creator<HistoryPass> CREATOR = new Creator<HistoryPass>() {
        @Override
        public HistoryPass createFromParcel(Parcel source) {
            return new HistoryPass(source);
        }

        @Override
        public HistoryPass[] newArray(int size) {
            return new HistoryPass[size];
        }
    };
}
