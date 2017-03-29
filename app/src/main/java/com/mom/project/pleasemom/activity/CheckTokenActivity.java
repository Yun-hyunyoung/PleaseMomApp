package com.mom.project.pleasemom.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mom.project.pleasemom.volley.CustomJsonRequest;
import com.mom.project.pleasemom.volley.CustomVolley;

import org.json.JSONObject;

/**
 * Created by 08_718 on 2016-11-02.
 */
public class CheckTokenActivity extends Activity implements Response.Listener<JSONObject>, Response.ErrorListener{

    RequestQueue mQueue;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getApplicationContext();

        mQueue = CustomVolley
                .getInstance(ctx)
                .getRequestQueue();

        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFERENCES_NAME, Context.MODE_PRIVATE);
        String mem_num = sharedPreferences.getString("login", null);
        String token = FirebaseInstanceId.getInstance().getToken();
        String url ="http://210.125.213.72:8090/SampleProject/android/member/checkToken.jsp?mem_num=" + mem_num + "&token=" + token;
        final CustomJsonRequest jsonRequest = new CustomJsonRequest(Request.Method.GET, url, new JSONObject(), this, this);
        jsonRequest.setTag("CheckTokenActivity");
        mQueue.add(jsonRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) { Log.i("TAG", error.toString()); }

    @Override
    public void onResponse(JSONObject response) {
        Log.i("TAG", response.toString());
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        finish();
    }
}
