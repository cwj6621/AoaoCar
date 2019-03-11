package com.jianguo.aoaocar.model;

/**
 * Created by 22077 on 2017/11/22.
 */

public class Car {
    public int carId;
    public String carName;
    public  int icon;
    public float price;//价格
    public boolean isStock;//库存

    public Car(int carId,String carName,int icon){
        this.carId=carId;
        this.carName=carName;
        this.icon=icon;
    }
    public Car(int carId,String carName ){
        this.carId=carId;
        this.carName=carName;
    }

    public Car(int carId,String carName,float price,boolean isStock ){
        this.carId=carId;
        this.carName=carName;
        this.price=price;
        this.isStock=isStock;
    }


}
