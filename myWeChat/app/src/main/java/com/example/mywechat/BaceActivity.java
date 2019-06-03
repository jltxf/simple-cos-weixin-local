package com.example.mywechat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.mywechat.radio.ActivityCollector;

public class BaceActivity extends AppCompatActivity {

    ForceofflineReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.sxb.FORCELINE");
        receiver = new ForceofflineReceiver();
        registerReceiver(receiver, intentFilter);
    }

    class ForceofflineReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("下线通知");
            dialog.setMessage("您有另外一台移动设备登录了此账号，需要下线");
            dialog.setCancelable(false);
            dialog.setPositiveButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCollector.finishAll();
                    Intent intent_login = new Intent(context, StartActivity.class);
                    startActivity(intent_login);
                }
            });
            dialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}


