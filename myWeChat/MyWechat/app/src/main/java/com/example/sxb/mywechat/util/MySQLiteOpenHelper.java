package com.example.sxb.mywechat.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sxb.mywechat.R;


/**
 * Created by Administrator on 2018/6/1.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    //参数context:上下文环境
    //参数name:数据库名称
    //参数factory：游标工厂,一般填null
    //参数version：数据库版本
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //数据库被创建的时候调用:定义数据库所需要的表
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表message
        db.execSQL("create table message (" +
                "id integer primary key autoincrement," +
                "imgHeadId integer,"+
                "friend varchar(10)," +
                "msg varchar(50)," +
                "time varchar(10))");
//        List<Msg> msgList=new ArrayList();
//        for(int i=0;i<5;i++){
//            Msg msg=new Msg(R.drawable.a,"小张","你吃饭了吗","19:00");
//            msgList.add(msg);
//            msg=new Msg(R.drawable.b,"腾讯新闻","习大大推崇的英雄精神","18.00");
//            msgList.add(msg);
//            msg=new Msg(R.drawable.c,"爸爸","找女朋友了吗","17.00");
//            msgList.add(msg);
//            msg=new Msg(R.drawable.d,"嘻哈族","谁今天请客？","16.00");
//            msgList.add(msg);
//        }
        ContentValues cv=new ContentValues();
        //cv.put(字段名，字段值)
        //cv可以理解为表中一条记录对应的Java对象
        for(int i=0;i<5;i++) {
            cv.put("imgHeadId", R.drawable.a);
            cv.put("friend", "小张");
            cv.put("msg", "你吃饭了吗");
            cv.put("time", "19:00");
            db.insert("message", null, cv);
            cv.put("imgHeadId", R.drawable.b);
            cv.put("friend", "腾讯新闻");
            cv.put("msg", "习大大好棒");
            cv.put("time", "18:00");
            db.insert("message", null, cv);
            cv.put("imgHeadId", R.drawable.c);
            cv.put("friend", "老爸");
            cv.put("msg", "还有生活费吗");
            cv.put("time", "17:00");
            db.insert("message", null, cv);
            cv.put("imgHeadId", R.drawable.d);
            cv.put("friend", "嘻哈族");
            cv.put("msg", "今天去哪嗨");
            cv.put("time", "16:00");
            db.insert("message", null, cv);
        }
    }

    //数据库更新的时候调用
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
