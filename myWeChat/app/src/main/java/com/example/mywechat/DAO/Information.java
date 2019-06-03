package com.example.mywechat.DAO;

import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Information implements Serializable {
    public static final int TYPE_SENT = 1;
    public static final int TYPE_RECEIVED = 0;
    private String friend;
    private String WeChatName;
    private String content;
    private int type;
    private Long mTime;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public Information(String friend, String WeChatName, String content, int type, Long mTime) {
        this.friend = friend;
        this.WeChatName = WeChatName;
        this.content = content;
        this.type = type;
        this.mTime = mTime;
    }

    public Information() {
    }

    public String getWeChatName() {
        return WeChatName;
    }

    public void setWeChatName(String WeChatName) {
        WeChatName = WeChatName;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Long getmTime() {
        return mTime;
    }

    public void setmTime(Long mTime) {
        this.mTime = mTime;
    }

    private String setM_DateString(String m_time) {
        String str = m_time.substring(11, 16);
        return str;
    }
}
