package com.mom.project.pleasemom.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.listener.PhoneUpdateOnClickListener;
import com.mom.project.pleasemom.receiver.MessageReceiver;

/**
 * Created by 08_718 on 2016-11-08.
 */
public class PhoneAuthNextActivity extends Activity {
    private static TextView txtCertifyNumber;
    Button btnSubmit;
    private BroadcastReceiver receiver;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth_next);

        Intent intent = getIntent();
        String certifyNumber = intent.getStringExtra("certifyNumber");
        String phone = intent.getStringExtra("phone");
        Log.i("TAG", "phoneNext phone: " + phone);

        receiver = new MessageReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");

        txtCertifyNumber = (TextView)findViewById(R.id.txtPhoneAuthNext);
        btnSubmit = (Button)findViewById(R.id.btnPhoneAuthNext);

        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFERENCES_NAME, Context.MODE_PRIVATE);
        String mem_num = sharedPreferences.getString("login", null);

        String url = "http://210.125.213.72:8090/SampleProject/android/member/updatePhone.jsp?phone="+phone+"&mem_num="+mem_num;
        btnSubmit.setOnClickListener(new PhoneUpdateOnClickListener(url, getApplicationContext(), certifyNumber, txtCertifyNumber));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        Log.i("onDestory()","브로드캐스트리시버 해제됨");
    }

    public static void inputCertifyNumber(String certifyNumber){
        if(certifyNumber != null){
            txtCertifyNumber.setText(certifyNumber);
        }
    }
}
