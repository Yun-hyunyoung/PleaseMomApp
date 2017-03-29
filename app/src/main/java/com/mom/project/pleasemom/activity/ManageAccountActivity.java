package com.mom.project.pleasemom.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.dto.MemberDTO;
import com.mom.project.pleasemom.listener.ManageOnClickListener;

/**
 * Created by 08_718 on 2016-11-07.
 */
public class ManageAccountActivity extends Activity {
    TextView txtEmail;
    Button btnEmailAuth;

    TextView txtPhone;
    Button btnPhoneAuth;

    TextView txtConfirm;
    Button btnChangePasswd;

    Button btnDeleteMember;

    String mem_num;
    Context ctx;

    InputMethodManager imm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);
        ctx = getApplicationContext();

        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFERENCES_NAME, Context.MODE_PRIVATE);
        mem_num = sharedPreferences.getString("login", null);

        txtEmail = (TextView)findViewById(R.id.txtManageEmailAuth);
        btnEmailAuth = (Button)findViewById(R.id.btnManageEmailAuth);
        txtPhone = (TextView)findViewById(R.id.txtManagePhoneAuth);
        btnPhoneAuth = (Button)findViewById(R.id.btnManagePhoneAuth);
        txtConfirm = (TextView)findViewById(R.id.txtManageChangePasswd);
        btnChangePasswd = (Button)findViewById(R.id.btnManageChangePasswd);
        btnDeleteMember = (Button)findViewById(R.id.btnManageDeleteMember);

        Intent intent = getIntent();
        MemberDTO dto = (MemberDTO)intent.getSerializableExtra("memberDTO");

        txtEmail.setText(dto.getMem_id());
        if ("null".equals(dto.getMem_phone())) txtPhone.setText("등록되지 않음");
        else txtPhone.setText(dto.getMem_phone());

        if ("Y".equals(dto.getMem_email_ck())){
            btnEmailAuth.setText("인증완료");
        } else{
            btnEmailAuth.setText("인증하기");
            String url = "http://210.125.213.72:8090/SampleProject/android/member/emailAuth.jsp?mem_num=" + mem_num;
            btnEmailAuth.setOnClickListener(new ManageOnClickListener(url, ctx, false));
        }

        if ("Y".equals(dto.getMem_phone_ck())){
            btnPhoneAuth.setText("인증완료");
        } else{
            btnPhoneAuth.setText("인증하기");

            btnPhoneAuth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ManageAccountActivity.this, PhoneAuthActivity.class);
                    startActivity(intent);
                }
            });
        }

        btnChangePasswd.setText("변경하기");
        String url = "http://210.125.213.72:8090/SampleProject/android/member/changePasswdConfirm.jsp?mem_num=" + mem_num;
        ManageOnClickListener listener = new ManageOnClickListener(url, ctx, true);
        listener.setTxtPasswd(txtConfirm);
        btnChangePasswd.setOnClickListener(listener);

        btnDeleteMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(ManageAccountActivity.this);
                alert_confirm.setMessage("탈퇴하지 마세요.").setCancelable(false).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /*Intent intent = new Intent(ManageAccountActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);*/
                            }
                        }).setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { return; }
                        });
                AlertDialog alert = alert_confirm.create();
                alert.show();
            }
        });

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
    }

    public void linearOnClick(View v){
        imm.hideSoftInputFromWindow(txtConfirm.getWindowToken(), 0);
    }
}
