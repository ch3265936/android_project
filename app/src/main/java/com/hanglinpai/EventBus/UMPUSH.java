package com.hanglinpai.EventBus;

/**
 * @author chihai
 * @function Created on 2017/6/14.
 */

public class UMPUSH {
    private String message;
    private String type;
    private String order_id;
    public UMPUSH(String type,String order_id) {
        this.type = type;
        this.order_id = order_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
