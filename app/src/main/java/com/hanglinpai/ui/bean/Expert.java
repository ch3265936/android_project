package com.hanglinpai.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by chihai on 2018/4/23.
 */

public class Expert implements Parcelable {
    private String id;
    private String name;
    private String expert_no;
    private int sex;
    private int age;
    private int status;
    private String first_pinyin;
private String unsuitable_reason;
    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Expert{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", expert_no='" + expert_no + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", status=" + status +
                ", first_pinyin='" + first_pinyin + '\'' +
                ", unsuitable_reason='" + unsuitable_reason + '\'' +
                ", avatar='" + avatar + '\'' +
                ", education_name='" + education_name + '\'' +
                ", college_type_name='" + college_type_name + '\'' +
                ", education_remark='" + education_remark + '\'' +
                ", position='" + position + '\'' +
                ", background='" + background + '\'' +
                ", birthday='" + birthday + '\'' +
                ", share_url='" + share_url + '\'' +
                ", down_url='" + down_url + '\'' +
                ", service_type_arr=" + service_type_arr +
                '}';
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFirst_pinyin() {
        return first_pinyin;
    }

    public String getUnsuitable_reason() {
        return unsuitable_reason;
    }

    public void setUnsuitable_reason(String unsuitable_reason) {
        this.unsuitable_reason = unsuitable_reason;
    }

    public void setFirst_pinyin(String first_pinyin) {
        this.first_pinyin = first_pinyin;
    }

    private String avatar;
    private String education_name;
    private String college_type_name;
    private String education_remark;
    private String position;
    private String background;
    private String birthday;
    private String share_url;
    private String down_url;
    private List<ItemSelectBean> service_type_arr;

    public String getExpert_no() {
        return expert_no;
    }

    public void setExpert_no(String expert_no) {
        this.expert_no = expert_no;
    }

    public List<ItemSelectBean> getService_type_arr() {
        return service_type_arr;
    }

    public void setService_type_arr(List<ItemSelectBean> service_type_arr) {
        this.service_type_arr = service_type_arr;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getDown_url() {
        return down_url;
    }

    public void setDown_url(String down_url) {
        this.down_url = down_url;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEducation_name() {
        return education_name;
    }

    public void setEducation_name(String education_name) {
        this.education_name = education_name;
    }

    public String getCollege_type_name() {
        return college_type_name;
    }

    public void setCollege_type_name(String college_type_name) {
        this.college_type_name = college_type_name;
    }

    public String getEducation_remark() {
        return education_remark;
    }

    public void setEducation_remark(String education_remark) {
        this.education_remark = education_remark;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Expert() {
    }

    public Expert(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.expert_no);
        dest.writeInt(this.sex);
        dest.writeInt(this.age);
        dest.writeInt(this.status);
        dest.writeString(this.first_pinyin);
        dest.writeString(this.unsuitable_reason);
        dest.writeString(this.avatar);
        dest.writeString(this.education_name);
        dest.writeString(this.college_type_name);
        dest.writeString(this.education_remark);
        dest.writeString(this.position);
        dest.writeString(this.background);
        dest.writeString(this.birthday);
        dest.writeString(this.share_url);
        dest.writeString(this.down_url);
        dest.writeTypedList(this.service_type_arr);
    }

    protected Expert(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.expert_no = in.readString();
        this.sex = in.readInt();
        this.age = in.readInt();
        this.status = in.readInt();
        this.first_pinyin = in.readString();
        this.unsuitable_reason = in.readString();
        this.avatar = in.readString();
        this.education_name = in.readString();
        this.college_type_name = in.readString();
        this.education_remark = in.readString();
        this.position = in.readString();
        this.background = in.readString();
        this.birthday = in.readString();
        this.share_url = in.readString();
        this.down_url = in.readString();
        this.service_type_arr = in.createTypedArrayList(ItemSelectBean.CREATOR);
    }

    public static final Creator<Expert> CREATOR = new Creator<Expert>() {
        @Override
        public Expert createFromParcel(Parcel source) {
            return new Expert(source);
        }

        @Override
        public Expert[] newArray(int size) {
            return new Expert[size];
        }
    };
}
