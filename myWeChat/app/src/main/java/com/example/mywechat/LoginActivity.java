package com.example.mywechat;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.mywechat.DAO.Menu;
import com.example.mywechat.db.MySQLLiteOperHelper;

public class LoginActivity extends BaceActivity {
    CheckBox chk;
    Button btn_shape, btn_canel;
    EditText edt_reg_WeChat_name, edt_password;
    private String name_1, password_1;
    private String Msgs;
    private boolean flag = true;
    private MySQLLiteOperHelper mySQLLiteOperHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);//加载上下文
        init();
        initClick();
        mySQLLiteOperHelper = new MySQLLiteOperHelper(this, 1);
        mySQLLiteOperHelper.getWritableDatabase();
        if (!mySQLLiteOperHelper.double_Flag_db("Menu")) {
           Menu menu = mySQLLiteOperHelper.query_Menu_1();
            edt_reg_WeChat_name.setText(menu.getWeChatName());
            edt_password.setText(menu.getPassword());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        btn_shape = findViewById(R.id.btn_shape);
        btn_shape.setEnabled(false);

    }

    public void init() {
        chk = (CheckBox) findViewById(R.id.chk);
        btn_shape = (Button) findViewById(R.id.btn_shape);
        btn_canel = (Button) findViewById(R.id.btn_canel);

        //获取焦点
        edt_reg_WeChat_name = (EditText) findViewById(R.id.edt_reg_WeChat_name);
        edt_reg_WeChat_name.setFocusable(true);
        edt_reg_WeChat_name.setFocusableInTouchMode(true);

        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_password.setFocusable(true);
        edt_password.setFocusableInTouchMode(true);
    }

    public void initClick() {
        //监听EditText焦点变化 当获取焦点后 hasFocus 为true
        edt_reg_WeChat_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {//setOnFocusChangeListener失去焦点
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                name_1 = edt_reg_WeChat_name.getText().toString();
                if (hasFocus == false) {
                    if (name_1.contains(" ")) {
                        edt_reg_WeChat_name.setError("账号不能包含空格！");
                    } else if (name_1.equals("")) {
                        edt_reg_WeChat_name.setError("账号名！");
                    }
                    btn_shape.setEnabled(false);

                }
            }
        });

        edt_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                password_1 = edt_password.getText().toString();
                if (hasFocus == false) {
                    if (password_1.contains(" ")) {
                        Msgs = new String("密码不能包含空格！");
                        Log.d("LoginActivity", Msgs);
                    } else if (!password_1.matches(".*[a-zA-z].*")) {
                        Msgs = new String("密码要有英文字符");
                    }
                    if (null != Msgs) {
                        //弹出提示对话框
                        new AlertDialog.Builder(LoginActivity.this).setTitle("密码错误").setMessage(Msgs)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Msgs = new String();
                                        chk.setChecked(false);//取消选中checkBox
                                        btn_shape.setEnabled(false);

                                    }
                                }).show();
                    }
                }
            }
        });
        edt_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name_1 == null || password_1 == null) {
                    chk.setEnabled(false);//对控件进行屏蔽操作
                    chk.setSelected(false);
                } else {
                    chk.setEnabled(true);
                }

            }
        });

        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chk.isChecked()) {
                    btn_shape.setEnabled(true);
                } else {
                    btn_shape.setEnabled(false);
                }
                //btn_password失去焦点
                edt_password.clearFocus();
            }
        });
        btn_shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mySQLLiteOperHelper.double_Flag_db("Menu")) {
                    Menu menu = mySQLLiteOperHelper.query_Menu_1();
                    if (edt_reg_WeChat_name.getText().toString().equals(menu.getWeChatName())&&edt_password.getText().toString().equals(menu.getPassword())) {

                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MsgListActivity.class);
                       // intent.putExtra("man", man);
                        //让man继承 implements Serializable (序列)
                        startActivity(intent);
                    }else{
                        new AlertDialog.Builder(LoginActivity.this).setTitle("账号或密码无效").setMessage(Msgs)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                    }
                }
            }
        });
        btn_canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


//    private void button() {
//        button01 = findViewById(R.id.btn_button01);
//        button01.setEnabled(false);
//        button01.setBackgroundResource("#00ADDDC3");
//
//    }
}

