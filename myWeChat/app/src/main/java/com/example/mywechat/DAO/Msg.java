package com.example.mywechat.DAO;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;


public class Msg implements Serializable {
    private String m_friend;
    private String m_WeChatName;
    private String m_msg;
    private int rowids;
    private long m_time;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public Msg(String m_friend, String m_WeChatName, String m_msg, int rowids, long m_time) {
        this.m_friend = m_friend;
        this.m_WeChatName = m_WeChatName;
        this.m_msg = m_msg;
        this.rowids = rowids;
        this.m_time = m_time;
    }

    public Msg() {
    }


    public String getM_friend() {
        return m_friend;
    }

    public void setM_friend(String m_friend) {
        this.m_friend = m_friend;
    }

    public String getWeChatName() {
        return m_WeChatName;
    }

    public void setWeChatName(String m_WeChatName) {
        this.m_WeChatName = m_WeChatName;
    }

    public String getM_msg() {
        return m_msg;
    }

    public void setM_msg(String m_msg) {
        this.m_msg = m_msg;
    }

    public int getRowids() {
        return rowids;
    }

    public void setRowids(int rowids) {
        this.rowids = rowids;
    }

    public long getM_time() {
        return m_time;
    }

    public void setM_time(long m_time) {
        this.m_time = m_time;
    }

    public String getM_date() {
        String str = sdf.format(m_time);
        return setM_DateString(str);
    }

    public String getM_date2() {
        String str = sdf.format(m_time);
        return setM_DateString2(str);
    }

    private String setM_DateString(String m_time) {
        String str = m_time.substring(11, 16);
        return str;
    }

    private String setM_DateString2(String m_time) {
        String str = m_time.substring(6, 10);
        return str;
    }
}
