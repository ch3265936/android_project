package com.hanglinpai.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author chihai
 * @function Created on 2017/6/20.
 */

public class HomeOrderBean {

    private String status;
    private String action;
    private String key;
    private String totalnum;
    private String msg;
    private List<ListBean> list;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(String totalnum) {
        this.totalnum = totalnum;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {

        private String shop_id;
        private String desc;
        private String card_id;
        private String name;
        private String deductible;
        private String num;
        private String condition;
        private int achieve;
        private String start_time;
        private String end_time;
        private int status;
        private String max_num;
        private String everyday_num;
        private String goods_ids;
        private String goods_name;
        private int type;
        private String create_time;
        private String num_total;
        private String received;
        private String use_card;

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getNum_total() {
            return num_total;
        }

        public void setNum_total(String num_total) {
            this.num_total = num_total;
        }

        public String getReceived() {
            return received;
        }

        public void setReceived(String received) {
            this.received = received;
        }

        public String getUse_card() {
            return use_card;
        }

        public void setUse_card(String use_card) {
            this.use_card = use_card;
        }

        private String update_time;
        private String shop_name;

        //单项券
        private String item_name;
        private String item_price;
        private String item_discount_price;

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getItem_discount_price() {
            return item_discount_price;
        }

        public void setItem_discount_price(String item_discount_price) {
            this.item_discount_price = item_discount_price;
        }

        public String getItem_price() {
            return item_price;
        }

        public void setItem_price(String item_price) {
            this.item_price = item_price;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getEveryday_num() {
            return everyday_num;
        }

        public void setEveryday_num(String everyday_num) {
            this.everyday_num = everyday_num;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getGoods_ids() {
            return goods_ids;
        }

        public void setGoods_ids(String goods_ids) {
            this.goods_ids = goods_ids;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getMax_num() {
            return max_num;
        }

        public void setMax_num(String max_num) {
            this.max_num = max_num;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public int getAchieve() {
            return achieve;
        }

        public void setAchieve(int achieve) {
            this.achieve = achieve;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getDeductible() {
            return deductible;
        }

        public void setDeductible(String deductible) {
            this.deductible = deductible;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getCard_id() {
            return card_id;
        }

        public void setCard_id(String card_id) {
            this.card_id = card_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public ListBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.shop_id);
            dest.writeString(this.desc);
            dest.writeString(this.card_id);
            dest.writeString(this.name);
            dest.writeString(this.deductible);
            dest.writeString(this.num);
            dest.writeString(this.condition);
            dest.writeInt(this.achieve);
            dest.writeString(this.start_time);
            dest.writeString(this.end_time);
            dest.writeInt(this.status);
            dest.writeString(this.max_num);
            dest.writeString(this.everyday_num);
            dest.writeString(this.goods_ids);
            dest.writeString(this.goods_name);
            dest.writeInt(this.type);
            dest.writeString(this.create_time);
            dest.writeString(this.num_total);
            dest.writeString(this.received);
            dest.writeString(this.use_card);
            dest.writeString(this.update_time);
            dest.writeString(this.shop_name);
            dest.writeString(this.item_name);
            dest.writeString(this.item_price);
            dest.writeString(this.item_discount_price);
        }

        protected ListBean(Parcel in) {
            this.shop_id = in.readString();
            this.desc = in.readString();
            this.card_id = in.readString();
            this.name = in.readString();
            this.deductible = in.readString();
            this.num = in.readString();
            this.condition = in.readString();
            this.achieve = in.readInt();
            this.start_time = in.readString();
            this.end_time = in.readString();
            this.status = in.readInt();
            this.max_num = in.readString();
            this.everyday_num = in.readString();
            this.goods_ids = in.readString();
            this.goods_name = in.readString();
            this.type = in.readInt();
            this.create_time = in.readString();
            this.num_total = in.readString();
            this.received = in.readString();
            this.use_card = in.readString();
            this.update_time = in.readString();
            this.shop_name = in.readString();
            this.item_name = in.readString();
            this.item_price = in.readString();
            this.item_discount_price = in.readString();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel source) {
                return new ListBean(source);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };
    }
}
