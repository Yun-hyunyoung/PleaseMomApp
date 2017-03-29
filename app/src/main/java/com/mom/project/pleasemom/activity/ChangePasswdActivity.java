package com.mom.project.pleasemom.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.listener.ChangePasswdOnClickListener;

/**
 * Created by 08_718 on 2016-11-07.
 */
public class ChangePasswdActivity extends Activity {
    private String mem_num;
    TextView txtPasswd;
    TextView txtPasswdConfirm;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passwd);

        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFERENCES_NAME, Context.MODE_PRIVATE);
        mem_num = sharedPreferences.getString("login", null);

        txtPasswd = (TextView)findViewById(R.id.txtChangePasswd);
        txtPasswdConfirm = (TextView)findViewById(R.id.txtChangePasswdConfirm);
        btnSubmit = (Button)findViewById(R.id.btnChangePasswdSubmit);

        String url = "http://210.125.213.72:8090/SampleProject/android/member/changePasswd.jsp?mem_num="+mem_num;
        btnSubmit.setOnClickListener(new ChangePasswdOnClickListener(url, getApplicationContext(), txtPasswd, txtPasswdConfirm));
    }
}
