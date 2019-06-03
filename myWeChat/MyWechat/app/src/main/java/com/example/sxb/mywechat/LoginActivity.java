package com.example.sxb.mywechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.sxb.mywechat.util.ActivityCollector;
import com.example.sxb.mywechat.util.BaseActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LoginActivity extends BaseActivity {

    //全局声明控件
    EditText et_admin;
    EditText et_pass;

    //记住密码的复选框
    CheckBox cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.add(this);
        //加载布局
        setContentView(R.layout.activity_login);
        //控件绑定
        et_admin = (EditText) findViewById(R.id.et_admin);
        et_pass=(EditText) findViewById(R.id.et_pass);
        cb=(CheckBox) findViewById(R.id.cb);
        //回显数据:从本地文件中读取数据（admin#pass），然后显示在EditText上
        //readText();
        readSP();

    }


    public void click(View v)  {
        switch (v.getId()) {
            case R.id.bt_login:
                //如果记住密码的复选框被勾选
                if(cb.isChecked()){
                    //将数据存储在本地:普通文本txt存储
                    //writeText(et_admin.getText().toString(),et_pass.getText().toString());
                    //将数据存储在本地:通过SharedPreferences类存储在xml文件中
                    writeSP2(et_admin.getText().toString(),et_pass.getText().toString());

                }
                Intent intent=new Intent(this,MsgListActivity.class);
                intent.putExtra("admin",et_admin.getText().toString());
                //   intent.putExtra("admin",et_admin.getText().toString());
                startActivity(intent);
                break;
            case R.id.tx_cancel:
                //finish();
                //按下取消键，退出程序，回到桌面
                ActivityCollector.finishAll();
                break;
            default:
        }
    }

    //数据持久化的第一种方式：普通文本存储数据
    public void writeText(String admin, String pass){
        BufferedWriter bw=null;
        try {
            //数据存储在data文件中，以覆盖的形式
            FileOutputStream out=openFileOutput("data", MODE_PRIVATE);
            bw=new BufferedWriter(new OutputStreamWriter(out));
            //把账号名和密码通过“#”拼接进一个字符串中，然后写入到文件data中
            bw.write(admin+"#"+pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void readText(){
        BufferedReader br=null;
        try {
            FileInputStream in=openFileInput("data");
            br=new BufferedReader(new InputStreamReader(in));
            String line;
            String content=new String();
            while((line=br.readLine())!=null)
                content=content+line;
            //将从data文件中读取的字符串进行分割，分割成字符串数组。
            //数组第0个元素是admin，第1个元素是pass
            String[] data=content.split("#");
            et_admin.setText(data[0]);
            et_pass.setText(data[1]);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(br!=null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public void writeSP(String admin,String pass){
        //数据存储在data_sp的xml文件中
        SharedPreferences sp=getSharedPreferences("data_sp",MODE_PRIVATE);
        //拿到SP的编辑器，往xml中写数据。通过键值对形式存储数据
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("admin",admin);
        editor.putString("pass",pass);
        //数据写完要提交
        editor.commit();
    }

    public void writeSP2(String admin,String pass){
        SharedPreferences sp1=getSharedPreferences("userdata",MODE_PRIVATE);
       SharedPreferences sp2=getPreferences(MODE_PRIVATE);
       SharedPreferences sp3= PreferenceManager.getDefaultSharedPreferences(this);
       SharedPreferences.Editor editor=sp1.edit();
       editor.putString("admin",admin);
       editor.putString("pass",pass);
       editor.apply();

    }

    public void readSP(){
        SharedPreferences sp=getSharedPreferences("data_sp",MODE_PRIVATE);
        //通过键名取得相应数据，设置给EditText
        et_admin.setText(sp.getString("admin",""));
        et_pass.setText(sp.getString("pass",""));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.remove(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //按下返回键退出程序，回到桌面
        ActivityCollector.finishAll();
    }
}
