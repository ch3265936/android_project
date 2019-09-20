package www.meiyaoni.com.common.basebean;

import java.io.Serializable;

/**
 * des:封装服务器返回数据
 * Created by xsf
 * on 2016.09.9:47
 */
public class BaseRespose<T> implements Serializable {
    public String status;
    public String action;
    public String key;
    public String totalnum;
    public String msg;

    public T list;

    public boolean success() {
        return "1".equals(status);
    }

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

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "BaseRespose{" +
                "status='" + status + '\'' +
                ", action='" + action + '\'' +
                ", key='" + key + '\'' +
                ", totalnum='" + totalnum + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + list +
                '}';
    }
}
