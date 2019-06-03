package com.example.sxb.mywechat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sxb.mywechat.util.ActivityCollector;
import com.example.sxb.mywechat.util.BaseActivity;

public class ChatActivity extends BaseActivity {

    EditText et_msg;
    TextView tv_msg;
    TextView tv_chatting;
    String friend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.add(this);
        setContentView(R.layout.activity_chat);//加载布局
        et_msg=(EditText) findViewById(R.id.et_msg);//控件绑定，全局声明，加载布局之后赋值
        tv_msg=(TextView) findViewById(R.id.tv_msg);
        tv_chatting=(TextView) findViewById(R.id.tv_chatting);
        Intent intent=getIntent();
        //获取上一界面传递过来的数据显示在TextView上
       friend= intent.getStringExtra("friend");
        tv_chatting.setText("你正在与"+friend+"聊天\n你发送的消息:");

    }

    public void click(View v){
        switch (v.getId()){
            case(R.id.bt_send):
                tv_msg.setText( et_msg.getText().toString());//获取EditText中输入的内容，再赋给TextView
                et_msg.setText("");//清空EditText
                break;
            default:
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra("msg",tv_msg.getText().toString());
        intent.putExtra("friend",friend);
        setResult(10,intent);//用Intent携带数据返回给初始Activity
        finish();//最后销毁该Activity
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.remove(this);
    }
}
