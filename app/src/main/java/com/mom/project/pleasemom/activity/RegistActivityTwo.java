package com.mom.project.pleasemom.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.dto.MemberDTO;
import com.mom.project.pleasemom.listener.RegistOnClickListener;

import java.util.Calendar;

/**
 * Created by 08_718 on 2016-11-08.
 */
public class RegistActivityTwo extends Activity {
    TextView txtName;
    Button btnRegist;
    MemberDTO dto;
    Intent intent;

    DatePicker datePicker;

    InputMethodManager imm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_two);

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        intent = getIntent();
        dto = (MemberDTO)intent.getSerializableExtra("regist");

        txtName = (TextView)findViewById(R.id.txtRegistName);
        btnRegist = (Button)findViewById(R.id.btnRegist);
        datePicker = (DatePicker) findViewById(R.id.registDatePicker);
        datePicker.setMaxDate(Calendar.getInstance().getTimeInMillis());

        String url = "http://210.125.213.72:8090/SampleProject/android/member/regist.jsp";
        btnRegist.setOnClickListener(new RegistOnClickListener(url, getApplicationContext(), txtName, datePicker, dto));
    }
    public void back(View v){
        finish();
    }

    public void relativeOnClick(View v){
        imm.hideSoftInputFromWindow(txtName.getWindowToken(), 0);
    }
}
