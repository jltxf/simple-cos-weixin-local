package com.example.sxb.mywechat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.sxb.mywechat.util.ActivityCollector;
import com.example.sxb.mywechat.util.BaseActivity;
import com.example.sxb.mywechat.util.Msg;
import com.example.sxb.mywechat.util.MsgAdapter;
import com.example.sxb.mywechat.util.MySQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MsgListActivity extends BaseActivity {


    List<Msg> msgList;
    MsgAdapter adapter;
    MySQLiteOpenHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.add(this);
        setContentView(R.layout.activity_msglist);
        helper=new MySQLiteOpenHelper(this,"WechatStore.db",null,1);
        //线性布局管理器
        LinearLayoutManager lm=new LinearLayoutManager(this);
        RecyclerView rv=(RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(lm);
        msgList=new ArrayList<>();
        initData();
        adapter=new MsgAdapter(msgList,this);
        //给RecyclerView设置适配器
        rv.setAdapter(adapter);
    }

    public void initData(){
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
        //通过帮助类对象打开一个可读可写的数据库对象
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cursor=db.query("message",null,null,null,null,null,null);
        while(cursor.moveToNext()){

            int imgHeadId=cursor.getInt(cursor.getColumnIndex("imgHeadId"));
            String friend=cursor.getString(cursor.getColumnIndex("friend"));
            String message=cursor.getString(cursor.getColumnIndex("msg"));
            String time=cursor.getString(cursor.getColumnIndex("time"));
            Msg msg=new Msg(imgHeadId,friend,message,time);
            msgList.add(msg);
        }
        //关闭数据库
        db.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==10){
            //获取ChatActivity传递过来的数据
            String message=data.getStringExtra("msg");
            String friend=data.getStringExtra("friend");
            //遍历集合msgList
            //将集合中好友姓名为传递过来好友姓名的Msg对象的消息内容改为message
            for(Msg msg:msgList){
                if(msg.getFriend().equals(friend)){
                    msg.setMsg(message);
                }
            }
            //打开数据库
            SQLiteDatabase db=helper.getWritableDatabase();
            ContentValues cv=new ContentValues();
            cv.put("msg",message);
            db.update("message",cv,"friend=?",new String[]{friend});
            //关闭数据库
            db.close();
            //通过adapter更新RecyclerView的数据内容
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.remove(this);
    }
}
