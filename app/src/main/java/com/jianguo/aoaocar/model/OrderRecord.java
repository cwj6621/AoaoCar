package com.jianguo.aoaocar.model;

/**
 * Created by 22077 on 2017/12/5.
 */

public class OrderRecord {
    public int id;
    public String stateName;
    public String  stateTime;
   public OrderRecord(int id,String stateName, String  stateTime){
       this.id=id;
       this.stateName=stateName;
       this.stateTime=stateTime;
   }

}
