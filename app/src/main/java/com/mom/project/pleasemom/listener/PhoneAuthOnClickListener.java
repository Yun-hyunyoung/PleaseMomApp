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
public class PhoneAuthOnClickListener implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {
    private String url;
    private Context ctx;
    TextView txtPhone;
    private RequestQueue mQueue;
    Toast toast;

    public PhoneAuthOnClickListener(String url, Context ctx, TextView txtPhone){
        this.url = url;
        this.ctx = ctx;
        this.txtPhone = txtPhone;
    }

    @Override
    public void onClick(View v) {
        String phone = txtPhone.getText().toString();

            url += "?phone=" + phone;
            mQueue = CustomVolley
                    .getInstance(ctx)
                    .getRequestQueue();
            final CustomJsonRequest jsonRequest = new CustomJsonRequest(Request.Method.GET, url, new JSONObject(), this, this);
            jsonRequest.setTag("MainActivity");
            mQueue.add(jsonRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) { Log.i("TAG", error.toString()); }

    @Override
    public void onResponse(JSONObject response) {
        try{
            if ("error".equals(response.get("phoneAuthResult"))){
                toast = Toast.makeText(ctx, "이미 존재하는 번호입니다.", Toast.LENGTH_SHORT);
                toast.show();
            } else{
                String certifyNumber = (String)response.get("phoneAuthResult");
                Intent intent = new Intent(ctx, PhoneAuthNextActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("certifyNumber", certifyNumber);
                intent.putExtra("phone", txtPhone.getText().toString());
                Log.i("TAG", "PhoneAuthClick phone: " + txtPhone.getText().toString());
                ctx.startActivity(intent);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
