package com.cypoem.idea.module.bean;

/**
 * Created by edianzu on 2017/5/25.
 */

public class User {
    private Long id;
    public String username;
    public String password;

    public String shortToken;
    public String longToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShortToken() {
        return shortToken;
    }

    public void setShortToken(String shortToken) {
        this.shortToken = shortToken;
    }

    public String getLongToken() {
        return longToken;
    }

    public void setLongToken(String longToken) {
        this.longToken = longToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
