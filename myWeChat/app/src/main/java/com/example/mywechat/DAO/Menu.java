package com.example.mywechat.DAO;

import java.io.Serializable;

public class Menu implements Serializable {
    private String name;
    private String WeChatName;
    private String password;
    private int imageId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getWeChatName() {
        return WeChatName;
    }

    public void setWeChatName(String weChatName) {
        WeChatName = weChatName;
    }
}