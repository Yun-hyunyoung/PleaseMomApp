package com.mom.project.pleasemom.listener;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mom.project.pleasemom.activity.MainActivity;
import com.mom.project.pleasemom.activity.PhoneAuthNextActivity;
import com.mom.project.pleasemom.volley.CustomJsonRequest;
import com.mom.project.pleasemom.volley.CustomVolley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 08_718 on 2016-11-07.
 */
public class PhoneUpdateOnClickListener implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {
    private String url;
    private Context ctx;
    private String certifyNumber;
    private TextView txtCertifyNumber;
    private RequestQueue mQueue;
    Toast toast;

    public PhoneUpdateOnClickListener(String url, Context ctx, String certifyNumber, TextView txtCertifyNumber){
        this.url = url;
        this.ctx = ctx;
        this.certifyNumber = certifyNumber;
        this.txtCertifyNumber = txtCertifyNumber;
    }

    @Override
    public void onClick(View v) {
        Log.i("TAB", "인증번호: "+certifyNumber);
        Log.i("TAB", "입력번호: "+txtCertifyNumber.getText());
        if (certifyNumber.equals(txtCertifyNumber.getText().toString())) {
            mQueue = CustomVolley
                    .getInstance(ctx)
                    .getRequestQueue();
            final CustomJsonRequest jsonRequest = new CustomJsonRequest(Request.Method.GET, url, new JSONObject(), this, this);
            jsonRequest.setTag("MainActivity");
            mQueue.add(jsonRequest);
        } else{
            toast = Toast.makeText(ctx, "인증번호가 일치하지 않습니다.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) { Log.i("TAG", error.toString()); }

    @Override
    public void onResponse(JSONObject response) {
        try{
            if (response.get("updatePhoneResult") != null) {
                Intent intent = new Intent(ctx, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
                Toast.makeText(ctx, "휴대폰 인증이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
