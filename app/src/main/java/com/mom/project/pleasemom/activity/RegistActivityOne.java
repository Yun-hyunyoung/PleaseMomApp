package com.mom.project.pleasemom.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.dto.MemberDTO;
import com.mom.project.pleasemom.listener.RegistOneOnClickListener;
import com.mom.project.pleasemom.volley.CustomJsonRequest;
import com.mom.project.pleasemom.volley.CustomVolley;

import org.json.JSONObject;

/**
 * Created by 08_718 on 2016-11-08.
 */
public class RegistActivityOne extends Activity {

    TextView txtEmail;
    TextView txtPasswd;
    Button btnNext;
    Toast toast;
    InputMethodManager imm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_one);

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        txtEmail = (TextView)findViewById(R.id.txtRegistEmail);
        txtPasswd = (TextView)findViewById(R.id.txtRegistPasswd);
        btnNext = (Button)findViewById(R.id.btnRegistNext);
        String url = "http://210.125.213.72:8090/SampleProject/android/member/idCheck.jsp";
        btnNext.setOnClickListener(new RegistOneOnClickListener(url, getApplicationContext(), txtEmail, txtPasswd));
    }
    public void back(View v){
        finish();
    }

    public void relativeOnClick(View v){
        imm.hideSoftInputFromWindow(txtEmail.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(txtPasswd.getWindowToken(), 0);
    }
}
