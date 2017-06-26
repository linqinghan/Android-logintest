package com.example.zdy.android_logintest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView m_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_tv = (TextView)findViewById(R.id.textView);


        Intent i = getIntent();

        String str_user = i.getStringExtra("user");
        String str_pwd = i.getStringExtra("pwd");
        String str = str_user + " : " + str_pwd;
        m_tv.setText(String.valueOf(str));

    }

}
