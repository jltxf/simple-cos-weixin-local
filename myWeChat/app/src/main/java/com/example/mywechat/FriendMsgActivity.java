package com.example.mywechat;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mywechat.DAO.Friend;
import com.example.mywechat.DAO.Menu;
import com.example.mywechat.db.MySQLLiteOperHelper;

public class FriendMsgActivity extends Activity {
    private String friend;
    private Friend friends;
    private TextView tv_fri_wechat_name, tv_fri_spec;

    private ImageView iv_fri_photo;
    private Button btn_fri_msg;
    private MySQLLiteOperHelper mySQLLiteOperHelper;
    private Menu mMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_msg);
        friend = getIntent().getStringExtra("friend");
        initView();

        mySQLLiteOperHelper = new MySQLLiteOperHelper(this, 1);
        mySQLLiteOperHelper.getWritableDatabase();
        mMenu = mySQLLiteOperHelper.query_Menu_1();
        friends = mySQLLiteOperHelper.query_Friend(friend, mMenu.getWeChatName());

        iv_fri_photo.setImageResource(friends.getImageId());
        tv_fri_wechat_name.setText(friends.getWeChatName());
        tv_fri_spec.setText(friends.getSpecialName());
        btn_fri_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendMsgActivity.this, ChatActiivty.class);
                intent.putExtra("friend", friend);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        tv_fri_spec = (TextView) findViewById(R.id.tv_fri_spec);
        tv_fri_wechat_name = (TextView) findViewById(R.id.tv_fri_wechat_name);
        iv_fri_photo = (ImageView) findViewById(R.id.iv_fri_photo);
        btn_fri_msg = (Button) findViewById(R.id.btn_fri_msg);
    }

}
