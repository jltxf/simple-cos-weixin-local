package com.example.mywechat.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.method.QwertyKeyListener;

import com.example.mywechat.DAO.Friend;
import com.example.mywechat.DAO.Information;
import com.example.mywechat.DAO.Menu;
import com.example.mywechat.DAO.Msg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MySQLLiteOperHelper extends SQLiteOpenHelper {
    private static final String TAG = "xxxx";
    private Context MyContext;
    private List<Msg> msgList = new ArrayList<Msg>();
    private List<Information> informationList = new ArrayList<Information>();
    private List<Friend> friendList = new ArrayList<Friend>();
    private Information information;
    private Msg msg;
    private Friend friend;
    private boolean flag = false;
    private Menu menu = new Menu();


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public MySQLLiteOperHelper(Context context, int version) {
        super(context, "WeChat", null, version);
        MyContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MENU = "create table Menu(id integer primary key autoincrement,name text,WeChatName text,password text,imageId integer)";
        String CREATE_FRIEND = "create table Friend(id integer primary key autoincrement,specialName text,WeChatName text,imageId integer,myWeChatName text)";
        String CREATE_MESSAGE = "create table Message(rowids integer,id integer primary key autoincrement, friend text,WeChatName text,msg text,time integer)";
        String CREATE_INFORMATION = "create table Information(id integer primary key autoincrement,friend text,WeChatName text,content text,type integer,time integer)";

        db.execSQL(CREATE_MENU);
        db.execSQL(CREATE_FRIEND);
        db.execSQL(CREATE_MESSAGE);
        db.execSQL(CREATE_INFORMATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addMsg(Msg msger) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();//封装一条数据的容器
        Cursor cursor = db.rawQuery("select * from Message where friend=? and WeChatName=?", new String[]{msger.getM_friend(), msger.getWeChatName()});
        if (msger.getM_friend().equals(msger.getWeChatName())) {
            return;
        }
        if (cursor.getCount() == 0) {
            values.put("rowids", msger.getRowids());
            values.put("friend", msger.getM_friend());
            values.put("WeChatName", msger.getWeChatName());
            values.put("msg", msger.getM_msg());
            values.put("time", msger.getM_time());
            db.insert("Message", null, values);
        }
    }

    public List<Msg> query_Message(String WeChatName) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query("Message", null, "WeChatName=?", new String[]{WeChatName}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                msg = new Msg();
                msg.setM_friend(cursor.getString(cursor.getColumnIndex("friend")));
                msg.setM_msg(cursor.getString(cursor.getColumnIndex("msg")));
                msg.setRowids(cursor.getInt(cursor.getColumnIndex("rowids")));
                msg.setM_time(cursor.getLong(cursor.getColumnIndex("time")));
                msgList.add(msg);
            } while (cursor.moveToNext());
        }
        return msgList;
    }

    public void update_Message(String msg, String friend, String WeChatName, Long time) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();//封装一条数据的容器
        values.put("msg", msg);//数据
        values.put("time", time);
        values.put("WeChatName", WeChatName);
        //条件 "friend=?", new String[]{friend}
        db.update("Message", values, "friend=? and WeChatName=?", new String[]{friend, WeChatName});
    }

    public void add_update_Data_Menu_1(String WeChatName, String name, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();//封装一条数据的容器
        values.put("WeChatName", WeChatName);
        values.put("name", name);
        values.put("password", password);
        if (!double_Flag_db("Menu")) {
            db.delete("Menu", null, null);
        }
        db.insert("Menu", null, values);
    }

    public Menu query_Menu_1() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query("Menu", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                menu.setName(cursor.getString(cursor.getColumnIndex("name")));
                menu.setWeChatName(cursor.getString(cursor.getColumnIndex("WeChatName")));
                menu.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            } while (cursor.moveToNext());
        }
        return menu;
    }

    public void addInformation(String friend, String WeChatName, String content, int type, Long mTime) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();//封装一条数据的容器
        values.put("friend", friend);
        values.put("WeChatName", WeChatName);
        values.put("content", content);
        values.put("type", type);
        values.put("time", mTime);
        db.insert("Information", null, values);
    }

    public List<Information> query_Information(String friend, String weChatName) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Information where (weChatName=? and friend=?)or(weChatName=? and friend=?) ", new String[]{weChatName, friend, friend, weChatName});
        if (cursor.moveToFirst()) {
            do {
                information = new Information();
                information.setContent(cursor.getString(cursor.getColumnIndex("content")));
                information.setmTime(cursor.getLong(cursor.getColumnIndex("time")));
                if (weChatName.equals(cursor.getString(cursor.getColumnIndex("WeChatName")))) {
                    information.setType(cursor.getInt(cursor.getColumnIndex("type")));
                } else {
                    information.setType(cursor.getInt(cursor.getColumnIndex("type")) != 0 ? 0 : 1);
                }
                informationList.add(information);
            } while (cursor.moveToNext());
        }
        return informationList;
    }

    public void addFriend(String specialName, int imageId, String WeChatName, String myWeChatName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();//封装一条数据的容器
        if (myWeChatName.equals(WeChatName)) {
            return;
        }
        Cursor cursor = db.rawQuery("select * from Friend where myWeChatName=? and WeChatName =?", new String[]{myWeChatName, WeChatName});
        if (cursor.getCount() == 0) {
            values.put("specialName", specialName);
            values.put("imageId", imageId);
            values.put("WeChatName", WeChatName);
            values.put("myWeChatName", myWeChatName);
            db.insert("Friend", null, values);
        }
    }

    public List<Friend> query_Friend(String myWeChatName) {//myWeChatName， 用户名
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query("Friend", null, "myWeChatName=?", new String[]{myWeChatName}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                friend = new Friend();
                friend.setWeChatName(cursor.getString(cursor.getColumnIndex("WeChatName")));
                friend.setMyWeChatName(cursor.getString(cursor.getColumnIndex("myWeChatName")));
                friend.setSpecialName(cursor.getString(cursor.getColumnIndex("specialName")));
                friend.setImageId(cursor.getInt(cursor.getColumnIndex("imageId")));
                friendList.add(friend);
            } while (cursor.moveToNext());
        }
        return friendList;
    }

    public Friend query_Friend(String WeChatName, String myWeChatName) {
        SQLiteDatabase db = getWritableDatabase();
        Friend friend1 = new Friend();
        // String selection = "WeChatName=? ";
        // String selectionArgs[] = new String[]{weChatName};
        //Cursor cursor = db.query("Friend", null, selection, selectionArgs, null, null, null);
        Cursor cursor = db.rawQuery("select * from Friend where myWeChatName=? and WeChatName =?", new String[]{myWeChatName, WeChatName});
        if (cursor.moveToFirst()) {
            do {
                friend1.setImageId(cursor.getInt(cursor.getColumnIndex("imageId")));
                friend1.setSpecialName(cursor.getString(cursor.getColumnIndex("specialName")));
            } while (cursor.moveToNext());
        }
        return friend1;
    }

    //判断表中是否为空
    public Boolean double_Flag_db(String base) {
        boolean Flag = true;
        SQLiteDatabase db = getWritableDatabase();
        //判断是否为空的方法是 通过 Cursor.getCount() 函数，得到的结果是 0，表示 Cursor 为空；如果非 0，则表示 Cursor 不为空
        Cursor c = db.rawQuery("select * from " + base, null);
        // rawQuery 是直接使用 SQL 语句进行查询的，也就是第一个参数字符串，在字符串内的 “？” 会被后面的 String[]数组逐一对换掉
        if (c.getCount() != 0) {
            Flag = false;
        }
        return Flag;
    }

    //判断Information表中是否为空
    public Boolean double_Flag_db_Information(String friend, String weChatName) {
        boolean Flag = true;
        SQLiteDatabase db = getWritableDatabase();
        //判断是否为空的方法是 通过 Cursor.getCount() 函数，得到的结果是 0，表示 Cursor 为空；如果非 0，则表示 Cursor 不为空
        Cursor c = db.rawQuery("select * from Information where (weChatName=? and friend=?)or(weChatName=? and friend=?)", new String[]{weChatName, friend, friend, weChatName});
        // rawQuery 是直接使用 SQL 语句进行查询的，也就是第一个参数字符串，在字符串内的 “？” 会被后面的 String[]数组逐一对换掉
        if (c.getCount() != 0) {
            Flag = false;
        }
        return Flag;
    }


}
