package com.example.mywechat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mywechat.DAO.Menu;
import com.example.mywechat.DAO.Msg;
import com.example.mywechat.db.MySQLLiteOperHelper;

public class RegisteredActivity extends BaceActivity {
    Button btn_reg_shape, btn_reg_canel;
    EditText edt_reg_WeChat_name, edt_reg_name, edt_reg_password, edt_reg_password2;
    private String name_1, password_1;
    private String Msgs = "";
    private boolean flag = false;
    private boolean isflag = false;
    private MySQLLiteOperHelper mySQLLiteOperHelper;
    public static final int ADD_MESSAGE_FRIEND = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);//加载上下文
        init();
        initClick();
        mySQLLiteOperHelper = new MySQLLiteOperHelper(this, 1);
        mySQLLiteOperHelper.getWritableDatabase();
//        btn_reg_shape.setEnabled(false);

    }

    @Override
    protected void onStart() {
        super.onStart();
//        btn_reg_shape = findViewById(R.id.btn_reg_shape);
//        btn_reg_shape.setEnabled(false);

    }

    public void init() {
        btn_reg_shape = (Button) findViewById(R.id.btn_reg_shape);
        btn_reg_canel = (Button) findViewById(R.id.btn_reg_canel);


        //获取焦点
        edt_reg_WeChat_name = (EditText) findViewById(R.id.edt_reg_WeChat_name);
        edt_reg_WeChat_name.setFocusable(true);
        edt_reg_WeChat_name.setFocusableInTouchMode(true);

        edt_reg_name = (EditText) findViewById(R.id.edt_reg_name);
        edt_reg_name.setFocusable(true);
        edt_reg_name.setFocusableInTouchMode(true);

        edt_reg_password = (EditText) findViewById(R.id.edt_reg_password);
        edt_reg_password.setFocusable(true);
        edt_reg_password.setFocusableInTouchMode(true);

        edt_reg_password2 = (EditText) findViewById(R.id.edt_reg_password2);
        edt_reg_password2.setFocusable(true);
        edt_reg_password2.setFocusableInTouchMode(true);
    }


    public void initClick() {
        //监听EditText焦点变化 当获取焦点后 hasFocus 为true
        edt_reg_WeChat_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {//setOnFocusChangeListener失去焦点
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    if (edt_reg_WeChat_name.getText().toString().contains(" ")) {
                        edt_reg_WeChat_name.setError("账号不能包含空格！");
                    } else if (edt_reg_WeChat_name.getText().toString().equals("")) {
                        edt_reg_WeChat_name.setError("账号不能为空！");
                    }
                    //   btn_reg_shape.setEnabled(false);
                }
                isflag = true;
            }
        });

        edt_reg_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {//setOnFocusChangeListener失去焦点
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                name_1 = edt_reg_name.getText().toString();
                if (hasFocus == false) {
                    if (name_1.contains(" ")) {
                        edt_reg_name.setError("用户名不能包含空格！");
                    } else if (name_1.equals("")) {
                        edt_reg_name.setError("用户名不能为空！");
                    }
                    //   btn_reg_shape.setEnabled(false);
                }
                isflag = true;
            }
        });

        edt_reg_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                password_1 = edt_reg_password.getText().toString();
                if (hasFocus == false) {
                    if (password_1.contains(" ")) {
                        Msgs = new String("密码不能包含空格！");
                        flag = true;
                    } else if (!password_1.matches(".*[a-zA-z].*")) {
                        Msgs = new String("密码要有英文字符");
                        flag = true;
                    }
                    if (flag) {
                        //弹出提示对话框
                        new AlertDialog.Builder(RegisteredActivity.this).setTitle("密码错误").setMessage(Msgs)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Msgs = new String();
//                                        btn_reg_shape.setEnabled(false);
                                        flag = false;
                                        isflag = false;
                                    }
                                }).show();
                    }

                }
            }
        });
//        edt_reg_password2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus == false) {
//
//                }
//            }
//        });
        edt_reg_password2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_reg_shape.setEnabled(true);
                isflag = true;
            }
        });
        edt_reg_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_reg_shape.setEnabled(true);
                isflag = true;
            }
        });

        btn_reg_shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt_reg_password.getText().toString().equals(edt_reg_password2.getText().toString())) {
                    Msgs = new String("两次密码不一致！");
                    flag = true;
                    isflag = false;
                }
                if (flag) {
                    //弹出提示对话框
                    new AlertDialog.Builder(RegisteredActivity.this).setTitle("确认密码错误").setMessage(Msgs)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Msgs = new String();
                                    flag = false;

                                }
                            }).show();
                } else if (!mySQLLiteOperHelper.double_Flag_db("Menu")) {
                    Menu menu = mySQLLiteOperHelper.query_Menu_1();
                    if (edt_reg_WeChat_name.getText().toString().equals(menu.getName()) && edt_reg_password.getText().toString().equals(menu.getPassword())) {
                        new AlertDialog.Builder(RegisteredActivity.this).setTitle("账号或密码已经被注册").setMessage(null)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        isflag = false;
                                    }
                                }).show();
                    }
                }//在用户表为空的时候无法注册
                if (isflag) {
                    isflag = false;
                    mySQLLiteOperHelper.add_update_Data_Menu_1(edt_reg_WeChat_name.getText().toString(), edt_reg_name.getText().toString(), edt_reg_password.getText().toString());

                    initOtherMessage();
                    initOtherFriend();
                    initMessage();
                    initFriend();
//                    MegListFragment megListFragment = new MegListFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("i", ADD_MESSAGE_FRIEND);
//                    megListFragment.setArguments(bundle);

                    Intent intent = new Intent();
                    intent.setClass(RegisteredActivity.this, MsgListActivity.class);
//                    intent.putExtra("i", ADD_MESSAGE_FRIEND);
                    //让man继承 implements Serializable (序列)
                    startActivity(intent);
                }


            }
        });
        btn_reg_canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initOtherMessage() {
        mySQLLiteOperHelper.addMsg(new Msg(edt_reg_WeChat_name.getText().toString(), "张学友", "吃了吗", 1, 1521315018));
        mySQLLiteOperHelper.addMsg(new Msg(edt_reg_WeChat_name.getText().toString(), "刘德华", "哦", 2, 1521971501));
        mySQLLiteOperHelper.addMsg(new Msg(edt_reg_WeChat_name.getText().toString(), "黎明", "没钱", 3, 11111111));
        mySQLLiteOperHelper.addMsg(new Msg(edt_reg_WeChat_name.getText().toString(), "腾讯新闻", "意思一下", 4, 222222222));
        mySQLLiteOperHelper.addMsg(new Msg(edt_reg_WeChat_name.getText().toString(), "鹿晗", "不要不好意思", 5, 1897901501));
        mySQLLiteOperHelper.addMsg(new Msg(edt_reg_WeChat_name.getText().toString(), "关晓彤", "没钱装什么", 6, 590901501));
        mySQLLiteOperHelper.addMsg(new Msg(edt_reg_WeChat_name.getText().toString(), "微信支付", "支付1000元。", 7, 1521901501));
        mySQLLiteOperHelper.addMsg(new Msg(edt_reg_WeChat_name.getText().toString(), "赵丽颖", "老板是我，隔壁老王", 8, 1521601501));
    }
    private void initMessage() {
        mySQLLiteOperHelper.addMsg(new Msg("张学友", edt_reg_WeChat_name.getText().toString(), "吃了吗", 9, 1521315018));
        mySQLLiteOperHelper.addMsg(new Msg("刘德华", edt_reg_WeChat_name.getText().toString(), "哦", 10, 1521971501));
        mySQLLiteOperHelper.addMsg(new Msg("黎明", edt_reg_WeChat_name.getText().toString(), "没钱", 11, 11111111));
        mySQLLiteOperHelper.addMsg(new Msg("腾讯新闻", edt_reg_WeChat_name.getText().toString(), "意思一下", 12, 222222222));
        mySQLLiteOperHelper.addMsg(new Msg("鹿晗", edt_reg_WeChat_name.getText().toString(), "不要不好意思", 13, 1897901501));
        mySQLLiteOperHelper.addMsg(new Msg("关晓彤", edt_reg_WeChat_name.getText().toString(), "没钱装什么", 14, 590901501));
        mySQLLiteOperHelper.addMsg(new Msg("微信支付", edt_reg_WeChat_name.getText().toString(), "支付1000元。", 15, 1521901501));
        mySQLLiteOperHelper.addMsg(new Msg("赵丽颖", edt_reg_WeChat_name.getText().toString(), "老板是我，隔壁老王", 16, 1521601501));
    }
    private void initOtherFriend() {
        mySQLLiteOperHelper.addFriend(edt_reg_name.getText().toString(), R.drawable.menu_mu, edt_reg_WeChat_name.getText().toString(), "张学友");
        mySQLLiteOperHelper.addFriend(edt_reg_name.getText().toString(), R.drawable.menu_mu, edt_reg_WeChat_name.getText().toString(), "刘德华");
        mySQLLiteOperHelper.addFriend(edt_reg_name.getText().toString(), R.drawable.menu_mu, edt_reg_WeChat_name.getText().toString(), "微信支付");
        mySQLLiteOperHelper.addFriend(edt_reg_name.getText().toString(), R.drawable.menu_mu, edt_reg_WeChat_name.getText().toString(), "腾讯新闻");
        mySQLLiteOperHelper.addFriend(edt_reg_name.getText().toString(), R.drawable.menu_mu, edt_reg_WeChat_name.getText().toString(), "黎明");
        mySQLLiteOperHelper.addFriend(edt_reg_name.getText().toString(), R.drawable.menu_mu, edt_reg_WeChat_name.getText().toString(), "赵丽颖");
        mySQLLiteOperHelper.addFriend(edt_reg_name.getText().toString(), R.drawable.menu_mu, edt_reg_WeChat_name.getText().toString(), "鹿晗");
        mySQLLiteOperHelper.addFriend(edt_reg_name.getText().toString(), R.drawable.menu_mu, edt_reg_WeChat_name.getText().toString(), "关晓彤");
    }



    private void initFriend() {
        mySQLLiteOperHelper.addFriend("张神", R.drawable.mx0, "张学友", edt_reg_WeChat_name.getText().toString());
        mySQLLiteOperHelper.addFriend("华仔", R.drawable.mx1, "刘德华", edt_reg_WeChat_name.getText().toString());
        mySQLLiteOperHelper.addFriend("微信公众号", R.drawable.mx7, "微信支付", edt_reg_WeChat_name.getText().toString());
        mySQLLiteOperHelper.addFriend("微信公众号", R.drawable.b, "腾讯新闻", edt_reg_WeChat_name.getText().toString());
        mySQLLiteOperHelper.addFriend("天王", R.drawable.mx2, "黎明", edt_reg_WeChat_name.getText().toString());
        mySQLLiteOperHelper.addFriend("赵丽颖", R.drawable.mx8, "赵丽颖", edt_reg_WeChat_name.getText().toString());
        mySQLLiteOperHelper.addFriend("不染色", R.drawable.mx4, "鹿晗", edt_reg_WeChat_name.getText().toString());
        mySQLLiteOperHelper.addFriend("不染色", R.drawable.mx5, "关晓彤", edt_reg_WeChat_name.getText().toString());
    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(receiver);
//    }
}

