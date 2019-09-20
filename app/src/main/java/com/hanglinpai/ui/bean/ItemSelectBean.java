package com.hanglinpai.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author chihai
 * @function Created on 2017/6/20.
 */

public class ItemSelectBean implements Parcelable {

    public String id;
    public String name;

    @Override
    public String toString() {
        return "ItemSelectBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public ItemSelectBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ItemSelectBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    protected ItemSelectBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<ItemSelectBean> CREATOR = new Parcelable.Creator<ItemSelectBean>() {
        @Override
        public ItemSelectBean createFromParcel(Parcel source) {
            return new ItemSelectBean(source);
        }

        @Override
        public ItemSelectBean[] newArray(int size) {
            return new ItemSelectBean[size];
        }
    };
}
