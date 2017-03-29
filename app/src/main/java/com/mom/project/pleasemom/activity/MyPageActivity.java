package com.mom.project.pleasemom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.dto.MemberDTO;
import com.mom.project.pleasemom.listener.BackPressCloseListener;

public class MyPageActivity extends AppCompatActivity {
    TextView txtId;
    TextView txtName;
    TextView txtBirth;
    TextView txtPhone;
    Button btnIdAuth;
    Button btnPhoneAuth;
    MemberDTO dto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        Intent intent = getIntent();
        dto = (MemberDTO)intent.getSerializableExtra("memberDTO");

        txtId = (TextView)findViewById(R.id.txtMypageId);
        txtName = (TextView)findViewById(R.id.txtMypageName);
        txtBirth = (TextView)findViewById(R.id.txtMypageBirth);
        txtPhone = (TextView)findViewById(R.id.txtMypagePhone);
        btnIdAuth = (Button)findViewById(R.id.btnMypageIdAuth);
        btnPhoneAuth = (Button)findViewById(R.id.btnMypagePhoneAuth);

        txtId.setText(dto.getMem_id());
        txtName.setText(dto.getMem_name());
        txtBirth.setText(dto.getMem_birth().substring(0,11));

        if("null".equals(dto.getMem_phone())) txtPhone.setText("등록되지 않음");
        else txtPhone.setText(dto.getMem_phone());

        if("Y".equals(dto.getMem_email_ck())){
            btnIdAuth.setText("인증완료");
        } else {
            btnIdAuth.setText("인증하기");
            btnIdAuth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ManageAccountActivity.class);
                    intent.putExtra("memberDTO", dto);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }
            });
        }

        if("Y".equals(dto.getMem_phone_ck())){
            btnPhoneAuth.setText("인증완료");
        } else {
            btnPhoneAuth.setText("인증하기");
            btnPhoneAuth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ManageAccountActivity.class);
                    intent.putExtra("memberDTO", dto);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }
            });
        }
    }

    @Override public void onSaveInstanceState(Bundle outState){ super.onSaveInstanceState(outState); }
    @Override public void onRestoreInstanceState(Bundle savedInstanceState) { super.onRestoreInstanceState(savedInstanceState); }

}
