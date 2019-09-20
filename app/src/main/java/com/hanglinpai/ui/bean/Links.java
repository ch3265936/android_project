package com.hanglinpai.ui.bean;

/**
 * Created by chihai on 2018/4/23.
 */

public class Links {
    private Self self;

    @Override
    public String toString() {
        return "Links{" +
                "self=" + self +
                '}';
    }

    public Self getSelf() {
        return self;
    }

    public void setSelf(Self self) {
        this.self = self;
    }
}
