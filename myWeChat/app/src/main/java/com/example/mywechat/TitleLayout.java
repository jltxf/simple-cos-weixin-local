package com.example.mywechat;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TitleLayout extends LinearLayout {
    ImageButton btn_choose, btn_break;
    TextView content;

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.title,this);
        ImageButton btn_choose = (ImageButton) findViewById(R.id.btn_choose);
        ImageButton btn_break = (ImageButton) findViewById(R.id.btn_break);
        final TextView content = (TextView) findViewById(R.id.Tx_content);
        btn_choose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void btn_break(View view){
        new Thread(){
            public void run(){
                try {
                Instrumentation inst = new Instrumentation();
                inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);

                }catch (Exception e){
                    Log.e("Exception when onBack",e.toString());
                }
            }
        }.start();
    }

}
