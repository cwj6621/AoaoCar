package com.jianguo.aoaocar.model;

import java.io.Serializable;

/**
 * Created by 22077 on 2017/12/4.
 */

public class Order  implements Serializable{
    public int id;
    public String carName;
    public String carNumber;
    public float costMoney;
    public OrderState  mOrderState;
    public String  startTime;
    public String  endTime;
    public String  startAddress;
    public String  endAddress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public float getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(float costMoney) {
        this.costMoney = costMoney;
    }

    public OrderState getmOrderState() {
        return mOrderState;
    }

    public void setmOrderState(OrderState mOrderState) {
        this.mOrderState = mOrderState;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public enum  OrderState{
         COMPLETE("已完成"),
         NOT_COMPLETE("未完成"),
         CANCEL("已取消");
         private String desc;
         OrderState(String desc){
             this.desc=desc;
         }
         public String getDesc() {
            return desc;
         }
    }
}
