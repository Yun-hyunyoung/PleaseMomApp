package com.mom.project.pleasemom.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
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
import com.mom.project.pleasemom.listener.BackPressCloseListener;
import com.mom.project.pleasemom.volley.CustomJsonRequest;
import com.mom.project.pleasemom.volley.CustomVolley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 08_718 on 2016-10-27.
 */
public class LoginActivity extends Activity implements Response.Listener<JSONObject>, Response.ErrorListener {

    public static final String PREFERENCES_NAME = "LoginPreferences";
    private BackPressCloseListener backPressCloseListener;

    TextView txtEmail;
    TextView txtPasswd;
    Button btnLogin;
    Button btnRegist;
    RequestQueue mQueue;
    Context ctx;
    Toast toast;
    InputMethodManager imm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        backPressCloseListener = new BackPressCloseListener(this);

        ctx = getApplicationContext();
        txtEmail = (TextView)findViewById(R.id.txt_login_email);
        txtPasswd = (TextView)findViewById(R.id.txt_login_passwd);
        btnLogin = (Button)findViewById(R.id.btn_login);
        btnRegist = (Button)findViewById(R.id.btn_regist);
    }

    public void login(View v){
        // Instantiate the RequestQueue.
        if("".equals(txtEmail.getText().toString())) {
            toast = Toast.makeText(ctx, "아이디를 입력하세요.", Toast.LENGTH_SHORT);
            toast.show();
        } else if("".equals(txtPasswd.getText().toString())) {
            toast = Toast.makeText(ctx, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            mQueue = CustomVolley
                    .getInstance(ctx)
                    .getRequestQueue();

            String url = "http://210.125.213.72:8090/SampleProject/android/member/login.jsp?userid=" + txtEmail.getText() + "&passwd=" + txtPasswd.getText();
            final CustomJsonRequest jsonRequest = new CustomJsonRequest(Request.Method.GET, url, new JSONObject(), this, this);
            jsonRequest.setTag("LoginActivity");
            mQueue.add(jsonRequest);
        }
    }

    public void regist(View v){
        Intent intent = new Intent(getApplicationContext(), RegistActivityOne.class);
        startActivity(intent);
    }

    @Override
    public void onErrorResponse(VolleyError error) { Log.i("TAG", error.toString()); }

    @Override
    public void onResponse(JSONObject response) {
        int mem_num = 0;
        try {
            mem_num = response.getInt("mem_num");
            Log.i("TAG", "first volley" + mem_num);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (mem_num == 0){
            Log.i("TAG", "로그인 정보 불일치");
            Toast toast = Toast.makeText(getApplicationContext(), "로그인 정보 불일치", Toast.LENGTH_SHORT);
            toast.show();
        } else{
            SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
            Editor editor = sharedPreferences.edit();
            editor.putString("login", String.valueOf(mem_num));
            editor.commit();

            Intent intent = new Intent(getApplicationContext(), CheckTokenActivity.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            finish();
        }
    }
    public void closeLogin(View v){
        finish();
    }

    @Override public void onSaveInstanceState(Bundle outState){ super.onSaveInstanceState(outState); }
    @Override public void onRestoreInstanceState(Bundle savedInstanceState) { super.onRestoreInstanceState(savedInstanceState); }

    @Override
    public void onBackPressed() {
        backPressCloseListener.onBackPressed();
    }

    public void relativeOnClick(View v){
        imm.hideSoftInputFromWindow(txtEmail.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(txtPasswd.getWindowToken(), 0);
    }
}
