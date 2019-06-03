package com.example.mywechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mywechat.DAO.Friend;
import com.example.mywechat.DAO.Menu;
import com.example.mywechat.DAO.Msg;
import com.example.mywechat.db.MySQLLiteOperHelper;
import com.example.mywechat.util.MsgAdapter;

import java.util.ArrayList;
import java.util.List;

public class MegListFragment extends Fragment {
    private List<Msg> msgList = new ArrayList<Msg>();
    private List<Friend> friendList = new ArrayList<Friend>();
    private MySQLLiteOperHelper mySQLLiteOperHelper;
    //private Information information;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    private Menu mMenu;
    private int i = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meglistfragment, container, false);


        mySQLLiteOperHelper = new MySQLLiteOperHelper(getContext(), 1);
        mySQLLiteOperHelper.getWritableDatabase();
        mMenu = mySQLLiteOperHelper.query_Menu_1();


        msgRecyclerView = (RecyclerView) view.findViewById(R.id.msg_recycler_view);
        msgList = mySQLLiteOperHelper.query_Message(mMenu.getWeChatName());
        friendList = mySQLLiteOperHelper.query_Friend(mMenu.getWeChatName());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        msgRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MsgAdapter(msgList, friendList);
        msgRecyclerView.setAdapter(adapter);

        msgRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                adapter.setOnclick(new MsgAdapter.ClickInterface() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getContext(), ChatActiivty.class);
                        intent.putExtra("friend", msgList.get(position).getM_friend());
                        startActivity(intent);
                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        msgList.clear();
        msgList = mySQLLiteOperHelper.query_Message(mMenu.getWeChatName());
        adapter.notifyDataSetChanged();
//        if (information != null) {
//            for (Msg msg1 : msgList) {
//                if (msg1.getM_friend().equals(information.getFriend())) {
//                    msg1.setM_msg(information.getContent().trim());
//                    msg1.setM_time(information.getmTime());
//                }
//            }
//        }
    }
}
