package com.example.zdy.android_logintest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class LoginActivity extends AppCompatActivity {
    private Button m_btn_login;
    private Button m_btn_register;
    private Button m_btn_forget_pwd;

    private TextView m_et_user;
    private TextView m_et_pwd;
    private Handler m_handler;

    private final static int LOGIN_RESULT = 0;

    public class Message_Result {
        public int result = -1;
        public String session_id = "";
        public Message_Result(int result, String session_id) {
            this.result = -1;
            this.session_id = session_id;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




        m_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case LOGIN_RESULT:
                        Message_Result ret = (Message_Result)msg.obj;
                        int result = ret.result;
                        Toast.makeText(getApplicationContext(), ret.session_id, Toast.LENGTH_SHORT).show();
                        if (result == 0) {
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            i.putExtra("Session", ret.session_id);
                            startActivity(i);
                        }
                        break;

                    default:
                        break;
                }
            }
        };

        m_btn_login = (Button)findViewById(R.id.login_btn_login);
        m_btn_register = (Button)findViewById(R.id.login_btn_register);
        m_btn_forget_pwd = (Button)findViewById(R.id.login_btn_forget_pwd);

        m_et_user = (TextView)findViewById(R.id.login_et_user);
        m_et_pwd = (TextView)findViewById(R.id.login_et_pwd);

        m_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_user = m_et_user.getText().toString();
                String str_pwd = m_et_pwd.getText().toString();

                SendLoginRequest(str_user, str_pwd);

            }
        });

    }
    public void register_func(View v) {
        Toast.makeText(this, "register", Toast.LENGTH_SHORT).show();
    }

    public void forget_pwd_func(View v) {
        Toast.makeText(this, "forget password", Toast.LENGTH_SHORT).show();
    }

    public void SendLoginRequest(final String user, final String pwd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str_url = "http://183.62.139.75:8080/StandardApiAction_login.action?account=" + user + "&password=" + pwd;

                try {
                    URL url = new URL(str_url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(2000);
                    conn.setRequestMethod("GET");
                    int code = conn.getResponseCode();
                    Message msg = new Message();
                    msg.what = LOGIN_RESULT;
                    Message_Result msg_ret = new Message_Result(-1, "Login Failed.");

                    if (code == 200) {
                        InputStream is = conn.getInputStream();
                        BufferedReader reader = null;
                        String result = null;
                        StringBuffer sbf = new StringBuffer();
                        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        String strRead = null;
                        while ((strRead = reader.readLine()) != null) {
                            sbf.append(strRead);
                            sbf.append("\r\n");
                        }
                        reader.close();
                        result = sbf.toString();
                        String login_ret = null;
                        try {
                            JSONObject jsonObj = new JSONObject(result);
                            login_ret = jsonObj.getString("result");
                            msg_ret.result = Integer.parseInt(login_ret);
                            if (msg_ret.result == 0) {
                                msg_ret.session_id = jsonObj.getString("jsession");
                            }
                            else if (msg_ret.result == 1) {
                                msg_ret.session_id = "User No Exist!";
                            }
                            else if (msg_ret.result == 2) {
                                msg_ret.session_id = "Password Error!";
                            }
                            else {
                                msg_ret.session_id = "Login Failed[reason unknow]!";
                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        is.close();

                    }

                    msg.obj = msg_ret;
                    m_handler.sendMessage(msg);
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = LOGIN_RESULT;
                    Message_Result msg_ret = new Message_Result(-1, "MalformedURLException");
                    //ret.result = -1;
                    //ret.session_id = "";
                    msg.obj = msg_ret;
                    m_handler.sendMessage(msg);
                    //Toast.makeText(getApplicationContext(), "MalformedURLException", Toast.LENGTH_SHORT).show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = LOGIN_RESULT;
                    Message_Result msg_ret = new Message_Result(-1, "IOException");
                    //ret.result = -1;
                    //ret.session_id = "";
                    msg.obj = msg_ret;
                    m_handler.sendMessage(msg);
                    //Toast.makeText(getApplicationContext(), "IOException", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }

}
