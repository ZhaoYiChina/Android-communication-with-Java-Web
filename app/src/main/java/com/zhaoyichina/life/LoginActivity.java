package com.zhaoyichina.life;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;


public class LoginActivity extends AppCompatActivity {
    private EditText account;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取用户名的id
        account = findViewById(R.id.uname);
        //获取密码的id
        password = findViewById(R.id.upass);
    }

    public void LoginAsyncHttpClient(View view){
        //获取用户名的值
        String name=account.getText().toString();
        //获取密码的值
        String pass=password.getText().toString();
        //获取网络上的servlet路径
        String path="http://192.168.1.14:8080/myerp/login.action";
        //使用第三方
        AsyncHttpClient ahc=new AsyncHttpClient();
        //请求参数
        RequestParams params=new RequestParams();
        //给请求参数设键和值（键的名字和web后台保持一致）
        params.put("name",name);
        params.put("pwd",pass);
        //设值提交方式
        ahc.post(this,path,params,new TextHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //super.onFailure(statusCode, headers, responseBody, error);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                System.out.println("服务器返回的内容为："+responseString);
                JSONObject obj = (JSONObject)JSON.parse(responseString);
                String result = obj.get("result").toString();
                //吐司Android studio与web后台数据交互获得的值
                Toast.makeText(LoginActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                if(result.equals("success")){
                    System.out.println("跳转页面");
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    //关闭当前界面
                    finish();
                }
            }
        });
    }
}