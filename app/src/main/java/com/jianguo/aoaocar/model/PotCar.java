package com.jianguo.aoaocar.model;

/**
 * Created by 22077 on 2018/1/2.
 */

public class PotCar {
    public int id;
    public String name;
    public int icon;
    public String licenseNumber;
    public int electricity;
    public int driving;

    public PotCar(int id, String name, int icon, String licenseNumber, int electricity, int driving) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.licenseNumber = licenseNumber;
        this.electricity = electricity;
        this.driving = driving;
    }
}
