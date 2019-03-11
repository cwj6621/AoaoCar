package com.jianguo.aoaocar.model;

/**
 * Created by 22077 on 2017/8/31.
 * 优惠券
 */

public class Coupon {
    public int id;
    public String value;
    public String rule;
    public String overtime;


    public Coupon(int id,String value,String rule,String overtime){
        this.id=id;
        this.value=value;
        this.rule=rule;
        this.overtime=overtime;

    }

}
