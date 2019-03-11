package com.jianguo.aoaocar.model;

/**
 * Created by 22077 on 2018/2/2.
 */

public class User {
    public int userId;
    public String userName;
    public String userIcon;
    public String gender;

    public User(int userId, String userName, String userIcon, String gender) {
        this.userId = userId;
        this.userName = userName;
        this.userIcon = userIcon;
        this.gender = gender;
    }
}
