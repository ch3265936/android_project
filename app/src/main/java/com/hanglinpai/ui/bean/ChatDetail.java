package com.hanglinpai.ui.bean;

/**
 * Created by chihai on 2018/4/24.
 */

public class ChatDetail {
    private long id;//沟通记录编号
    private int type;//类型(0:用户,1:客服)
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ChatDetail{" +
                "id=" + id +
                ", type=" + type +
                ", content='" + content + '\'' +
                '}';
    }
}
