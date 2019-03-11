package com.jianguo.aoaocar.model;

import java.io.Serializable;

/**
 * Created by 22077 on 2018/1/26.
 */

public class MyLocation implements Serializable {
    public double latitude;
    public double longitude;
    public String city;
    public String complang;
    public String address;

    public MyLocation(double latitude, double longitude, String city, String complang, String address){
        this.latitude=latitude;
        this.longitude=longitude;
        this.city=city;
        this.complang=complang;
        this.address=address;

    }
}

