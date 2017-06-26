package com.example.zdy.android_logintest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    private Button m_btn_login;
    private Button m_btn_register;
    private Button m_btn_forget_pwd;

    private TextView m_et_user;
    private TextView m_et_pwd;

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

                //Toast.makeText(getApplicationContext(), str_user + ": " + str_pwd, Toast.LENGTH_SHORT).show();

                if (str_user.equals("zdy") && str_pwd.equals("zdy")) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("user", str_user);
                    i.putExtra("pwd", str_pwd);
                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.login_fail_info), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void register_func(View v) {
        Toast.makeText(this, "register", Toast.LENGTH_SHORT).show();
    }

    public void forget_pwd_func(View v) {
        Toast.makeText(this, "forget password", Toast.LENGTH_SHORT).show();
    }

}
