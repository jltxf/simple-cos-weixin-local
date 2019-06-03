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
import com.example.mywechat.db.MySQLLiteOperHelper;
import com.example.mywechat.util.FriendAdapter;

import java.util.ArrayList;
import java.util.List;

public class FriendFragment extends Fragment {
    private RecyclerView recyclerView;
    private MySQLLiteOperHelper mySQLLiteOperHelper;
    FriendAdapter adapter;
    private Menu mMenu;
    private List<Friend> friendList = new ArrayList<Friend>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friendfragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.Lv_fri);

        mySQLLiteOperHelper = new MySQLLiteOperHelper(view.getContext(), 1);
        mySQLLiteOperHelper.getWritableDatabase();
        mMenu = mySQLLiteOperHelper.query_Menu_1();
        friendList = mySQLLiteOperHelper.query_Friend(mMenu.getWeChatName());
        LinearLayoutManager linearLayout = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayout);
        adapter = new FriendAdapter(friendList);
        recyclerView.setAdapter(adapter);


        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                adapter.setOnclick(new FriendAdapter.ClickInterface() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(view.getContext(), FriendMsgActivity.class);
                        intent.putExtra("friend", friendList.get(position).getWeChatName());
                        startActivity(intent);
                    }
                });
            }
        });
        return view;
    }

}
