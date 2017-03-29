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
import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.activity.MainActivity;
import com.mom.project.pleasemom.activity.ManageAccountActivity;
import com.mom.project.pleasemom.volley.CustomJsonRequest;
import com.mom.project.pleasemom.volley.CustomVolley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 08_718 on 2016-11-07.
 */
public class ChangePasswdOnClickListener implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {
    private String url;
    private Context ctx;
    TextView txtPasswd;
    TextView txtPasswdConfirm;
    private RequestQueue mQueue;
    Toast toast;

    public ChangePasswdOnClickListener(String url, Context ctx, TextView txtPasswd, TextView txtPasswdConfirm){
        this.url = url;
        this.ctx = ctx;
        this.txtPasswd = txtPasswd;
        this.txtPasswdConfirm = txtPasswdConfirm;
    }

    @Override
    public void onClick(View v) {
        String pass1 = txtPasswd.getText().toString();
        String pass2 = txtPasswdConfirm.getText().toString();

        if (pass1.equals(pass2)) {
            url += "&passwd="+pass1;
            mQueue = CustomVolley
                    .getInstance(ctx)
                    .getRequestQueue();
            final CustomJsonRequest jsonRequest = new CustomJsonRequest(Request.Method.GET, url, new JSONObject(), this, this);
            jsonRequest.setTag("MainActivity");
            mQueue.add(jsonRequest);
        } else{
            toast = Toast.makeText(ctx, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) { Log.i("TAG", error.toString()); }

    @Override
    public void onResponse(JSONObject response) {
        try{
            if (response.get("changePasswdResult") != null){
                toast = Toast.makeText(ctx, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(ctx, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
