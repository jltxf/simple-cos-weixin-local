package com.example.sxb.mywechat;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.sxb.mywechat.util.ActivityCollector;
import com.example.sxb.mywechat.util.BaseActivity;

public class LoadingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Activity创建之后都要加进activityList中，以便管理
        ActivityCollector.add(this);
       //去掉标题栏
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
            actionBar.hide();
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_loading);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    Intent intent=new Intent(LoadingActivity.this,LoginActivity.class);
                    startActivity(intent);
                    //finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity对象销毁以后需要从容器中移除
        ActivityCollector.remove(this);
    }
}
