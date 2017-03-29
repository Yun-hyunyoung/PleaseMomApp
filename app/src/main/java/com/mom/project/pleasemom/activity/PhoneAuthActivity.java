package com.mom.project.pleasemom.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.listener.PhoneAuthOnClickListener;
import com.mom.project.pleasemom.receiver.MessageReceiver;

/**
 * Created by 08_718 on 2016-11-07.
 */
public class PhoneAuthActivity extends Activity {
    private TextView txtPhone;
    private Button btnPhone;
    private BroadcastReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        receiver = new MessageReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(receiver, intentFilter);
        Log.i("onCreate()","브로드캐스트리시버 등록됨");

        txtPhone = (TextView)findViewById(R.id.txtPhoneAuth);
        btnPhone = (Button)findViewById(R.id.btnPhoneAuth);

        String url = "http://210.125.213.72:8090/SampleProject/android/member/phoneAuth.jsp";
        btnPhone.setOnClickListener(new PhoneAuthOnClickListener(url, getApplicationContext(), txtPhone));
    }
}
