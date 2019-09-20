package com.hanglinpai.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author chihai
 * @function Created on 2017/6/20.
 */

public class LoginBean {

    private String code;
    private String msg;
    private ListBean data;

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

    public ListBean getList() {
        return data;
    }

    public void setList(ListBean list) {
        this.data = list;
    }

    public static class ListBean {
        private String id;
        private String account;
        private String name;
        private int sex;
        private String avatar;
        private String phone;
        private String email;
        private String token;
        private String company_name;
        private String position_name;
        private String business_card;
        private int overdue_order_count;

        @Override
        public String toString() {
            return "ListBean{" +
                    "id='" + id + '\'' +
                    ", account='" + account + '\'' +
                    ", name='" + name + '\'' +
                    ", sex=" + sex +
                    ", avatar='" + avatar + '\'' +
                    ", phone='" + phone + '\'' +
                    ", email='" + email + '\'' +
                    ", token='" + token + '\'' +
                    ", company_name='" + company_name + '\'' +
                    ", position_name='" + position_name + '\'' +
                    ", business_card='" + business_card + '\'' +
                    ", overdue_order_count=" + overdue_order_count +
                    ", auth_status=" + auth_status +
                    ", unread_msg=" + unread_msg +
                    '}';
        }

        public int getOverdue_order_count() {
            return overdue_order_count;
        }

        public void setOverdue_order_count(int overdue_order_count) {
            this.overdue_order_count = overdue_order_count;
        }

        public String getBusiness_card() {
            return business_card;
        }

        public void setBusiness_card(String business_card) {
            this.business_card = business_card;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getPosition_name() {
            return position_name;
        }

        public void setPosition_name(String position_name) {
            this.position_name = position_name;
        }

        private int auth_status; //认证状态(0:未认证,1:待审核,2:已认证,3:已提示)
        private int unread_msg;//未读消息数

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getAuth_status() {
            return auth_status;
        }

        public void setAuth_status(int auth_status) {
            this.auth_status = auth_status;
        }

        public int getUnread_msg() {
            return unread_msg;
        }

        public void setUnread_msg(int unread_msg) {
            this.unread_msg = unread_msg;
        }


    }
}
