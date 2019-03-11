package com.jianguo.aoaocar.model;

/**
 * Created by 22077 on 2018/1/23.
 */

public class Partner {
    public int id;
    public String name;
    public String desc;
    public boolean isShow;

    public Partner(int id, String name, String desc, boolean isShow) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.isShow = isShow;
    }
}
