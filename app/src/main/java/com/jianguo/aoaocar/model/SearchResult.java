package com.jianguo.aoaocar.model;

/**
 * Created by 22077 on 2017/12/13.
 */

public class SearchResult {
    private int  id;
    private String name;
    private String address;
    private float distruct;

    public SearchResult(int id, String name, String address, float distruct) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.distruct = distruct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getDistruct() {
        return distruct;
    }

    public void setDistruct(float distruct) {
        this.distruct = distruct;
    }
}
