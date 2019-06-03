package com.example.mywechat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class MsgListActivity extends BaceActivity {
    private Button btn_wechat, btn_contact, btn_find, btn_mine;
    private Drawable drawable1, drawable2, drawable3, drawable4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_list);

        init();
        btn_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDrawable(R.drawable.weixin_pressed, R.drawable.contact_list_normal, R.drawable.find_normal, R.drawable.profile_normal);
               jumpFragment(new MegListFragment());
            }
        });
        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDrawable(R.drawable.weixin_normal, R.drawable.contact_list_pressed, R.drawable.find_normal, R.drawable.profile_normal);
                jumpFragment(new FriendFragment());
            }
        });
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDrawable(R.drawable.weixin_normal, R.drawable.contact_list_normal, R.drawable.find_pressed, R.drawable.profile_normal);
            }
        });
        btn_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDrawable(R.drawable.weixin_normal, R.drawable.contact_list_normal, R.drawable.find_normal, R.drawable.profile_pressed);
            }
        });

    }


    private void initDrawable(int pricture1, int pricture2, int pricture3, int pricture4) {
        drawable1 = getResources().getDrawable(pricture1);
        drawable2 = getResources().getDrawable(pricture2);
        drawable3 = getResources().getDrawable(pricture3);
        drawable4 = getResources().getDrawable(pricture4);
        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
        drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
        drawable4.setBounds(0, 0, drawable4.getMinimumWidth(), drawable4.getMinimumHeight());

        //必须设置图片大小，否则不显示图片
        btn_wechat.setCompoundDrawables(null, drawable1, null, null);
        btn_contact.setCompoundDrawables(null, drawable2, null, null);
        btn_find.setCompoundDrawables(null, drawable3, null, null);
        btn_mine.setCompoundDrawables(null, drawable4, null, null);
    }

    private void init() {
        btn_wechat = (Button) findViewById(R.id.btn_wechat);
        btn_contact = (Button) findViewById(R.id.btn_contact);
        btn_find = (Button) findViewById(R.id.btn_find);
        btn_mine = (Button) findViewById(R.id.btn_mine);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void jumpFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction =fragmentManager.beginTransaction();
        transaction.replace(R.id.megListFragment,fragment);//Activity设置的布局中的ViewGroup组件id
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
