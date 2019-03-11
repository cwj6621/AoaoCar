package com.jianguo.aoaocar.model;

/**
 * Created by 22077 on 2018/1/9.
 */

public class Detection {
    public int id;
    public String name;
    public DetectionState etectionState;

    public Detection(int id, String name, DetectionState etectionState) {
        this.id = id;
        this.name = name;
        this.etectionState = etectionState;
    }

    public enum  DetectionState{
        COMPLETE("已完成"),
        NOT_COMPLETE("未完成"),
        DETECTION_ING("正在检测..."),
        TO_DETECTION("待检测");
        private String desc;
        DetectionState(String desc){
            this.desc=desc;
        }
        public String getDesc() {
            return desc;
        }
    }
}
