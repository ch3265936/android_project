package com.hanglinpai.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chihai on 2018/4/23.
 */


public class OrderDetailPut {
    public String id;
    public String industry_type;
    public List<String> service_type;
    public  List<String> service_dates;
    public String site;//地址
    public String desc;
    public String service_time;

}
