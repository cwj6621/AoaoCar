package com.jianguo.aoaocar.model;

/**
 * Created by 22077 on 2018/2/2.
 */

public class DriverOrder {

    public int id;
    public String carName;
    public String carNumber;
    public float costMoney;
    public String  time;
    public String  address;
    public User  mUser;

    public DriverOrder(int id, String carName, String carNumber, float costMoney, String time, String address, User mUser) {
        this.id = id;
        this.carName = carName;
        this.carNumber = carNumber;
        this.costMoney = costMoney;
        this.time = time;
        this.address = address;
        this.mUser = mUser;
    }
}
