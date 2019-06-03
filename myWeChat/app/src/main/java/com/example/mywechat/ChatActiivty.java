package com.example.mywechat;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.mywechat.DAO.Friend;
import com.example.mywechat.DAO.Information;
import com.example.mywechat.DAO.Menu;
import com.example.mywechat.db.MySQLLiteOperHelper;
import com.example.mywechat.util.InformationAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatActiivty extends BaceActivity {
    private List<Information> informationlist = new ArrayList<Information>();
    private String weChatName;
    private String friend;
    private static Menu mMenu = new Menu();
    private static Friend mFriend = new Friend();

    private EditText inputText;
    private Button send;

    private RecyclerView informationRecyclerView;
    private MySQLLiteOperHelper mySQLLiteOperHelper;
    private InformationAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        informationRecyclerView = (RecyclerView) findViewById(R.id.information_recycler_view);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mySQLLiteOperHelper = new MySQLLiteOperHelper(this, 1);
        mySQLLiteOperHelper.getWritableDatabase();


        friend = getIntent().getStringExtra("friend");
        mMenu = mySQLLiteOperHelper.query_Menu_1();
        mFriend = mySQLLiteOperHelper.query_Friend(friend, mMenu.getWeChatName());
        weChatName = mMenu.getWeChatName();

        if (mySQLLiteOperHelper.double_Flag_db_Information(friend, weChatName)) {
            inits();
        }

        informationlist = mySQLLiteOperHelper.query_Information(friend, weChatName);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        informationRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new InformationAdapter(informationlist);
        informationRecyclerView.setAdapter(adapter);
    //    information1 = informationlist.get(informationlist.size() - 1);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    Information information = new Information(friend, weChatName, content, Information.TYPE_SENT, System.currentTimeMillis());
                    informationlist.add(information);
                    adapter.notifyItemChanged(informationlist.size() - 1);// 当有新消息时，刷新ListView中的显示
                    informationRecyclerView.scrollToPosition(informationlist.size() - 1);// 将ListView定位到最后一行
                    mySQLLiteOperHelper.addInformation(information.getFriend(), information.getWeChatName(), information.getContent(), information.getType(), information.getmTime());
                    mySQLLiteOperHelper.update_Message(information.getContent(), information.getFriend(), information.getWeChatName(), information.getmTime());
                    mySQLLiteOperHelper.update_Message(information.getContent(), information.getWeChatName(), information.getFriend(), information.getmTime());
                    inputText.setText("");// 清空输入框中的内容

                }
            }
        });

    }


    private void inits() {
        mySQLLiteOperHelper.addInformation(friend, weChatName, "Hello," + weChatName, Information.TYPE_RECEIVED, System.currentTimeMillis());//Type 谁发的
        mySQLLiteOperHelper.addInformation(friend, weChatName, "Hello. Who is that?", Information.TYPE_SENT, System.currentTimeMillis() + 20);
        mySQLLiteOperHelper.addInformation(friend, weChatName, "This is " + friend + ". Nice talking to you. ", Information.TYPE_RECEIVED, System.currentTimeMillis() + 100);
    }

    public static Friend getmFriend() {
        return mFriend;
    }

    public static Menu getmMenu() {
        return mMenu;
    }


}
