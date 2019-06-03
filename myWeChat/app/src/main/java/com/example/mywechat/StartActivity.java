package com.example.mywechat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class StartActivity extends BaceActivity {

private Button btn_register01,btn_login01;
    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int temped = 0;
            if (msg.what == 0x101) {
                btn_login01.setVisibility(View.VISIBLE);
                btn_register01.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        btn_login01 = (Button)findViewById(R.id.btn_login01);
        btn_register01 = (Button) findViewById(R.id.btn_register01);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initWindows();

    }
//状态栏沉浸
    private void initWindows() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.BLACK);
            myThread myThread
                     = new myThread();
            myThread.start();
        }
    }

    public void login(View view) {
        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(StartActivity.this, RegisteredActivity.class);
        startActivity(intent);
    }

    public class myThread extends Thread {
        @Override
        public void run() {
            super.run();
            int temp = 5;
            for (; temp >= 0; temp--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 0x100;
                message.obj = temp;//arg1和arg2 只能传整型 ，obj能穿object
                myHandler.sendMessage(message);
            }
            Message message = new Message();
            message.what = 0x101;
            myHandler.sendMessage(message);
            super.run();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
