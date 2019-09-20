package com.hanglinpai.ui.bean;

/**
 * @author chihai
 * @function Created on 2017/6/20.
 */

public class Version_info {
    private int update_status;//0 1 2
    private String latest_version;
    private String download_link;
    private String desc;
    private String title;

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Version_info{" +
                "update_status=" + update_status +
                ", latest_version='" + latest_version + '\'' +
                ", download_link='" + download_link + '\'' +
                ", desc='" + desc + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUpdate_status() {
        return update_status;
    }

    public void setUpdate_status(int update_status) {
        this.update_status = update_status;
    }

    public String getLatest_version() {
        return latest_version;
    }

    public void setLatest_version(String latest_version) {
        this.latest_version = latest_version;
    }

    public String getDownload_link() {
        return download_link;
    }

    public void setDownload_link(String download_link) {
        this.download_link = download_link;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
