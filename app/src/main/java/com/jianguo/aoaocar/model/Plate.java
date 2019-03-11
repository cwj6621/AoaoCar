package com.jianguo.aoaocar.model;

/**
 * Created by 22077 on 2017/11/22.
 */

public class Plate {
    public int icon;
    public String name;
    public boolean isSelect;
    public Plate(int icon,String name){
        this.icon=icon;
        this.name=name;
    }
    public Plate(int icon,String name,boolean isSelect){
        this.icon=icon;
        this.name=name;
        this.isSelect=isSelect;
    }
}
