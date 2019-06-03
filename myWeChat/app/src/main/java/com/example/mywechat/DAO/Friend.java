package com.example.mywechat.DAO;

import java.io.Serializable;

public class Friend implements Serializable {
    private String specialName;
    private int imageId;
    private String weChatName;
    private String myWeChatName;

    public String getSpecialName() {
        return specialName;
    }

    public void setSpecialName(String specialName) {
        this.specialName = specialName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getWeChatName() {
        return weChatName;
    }

    public void setWeChatName(String weChatName) {
        this.weChatName = weChatName;
    }

    public String getMyWeChatName() {
        return myWeChatName;
    }

    public void setMyWeChatName(String myWeChatName) {
        this.myWeChatName = myWeChatName;
    }
}
