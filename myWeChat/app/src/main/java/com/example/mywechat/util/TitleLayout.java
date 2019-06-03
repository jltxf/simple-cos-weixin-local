package com.example.mywechat.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mywechat.R;

public class TitleLayout extends LinearLayout {
    ImageButton btn_choose, btn_break;
    TextView content;


    public TitleLayout(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.title, this);

        ImageButton btn_choose = (ImageButton) findViewById(R.id.btn_choose);
        ImageButton btn_break = (ImageButton) findViewById(R.id.btn_break);
        final TextView content = (TextView) findViewById(R.id.Tx_content);
        btn_break.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });

    }
}