package com.yks.urc.vo;

import java.io.Serializable;

public class DataColumnVO implements Serializable {
    private static final long serialVersionUID = -3182815269631884996L;
    private  String key;
    private String name;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
