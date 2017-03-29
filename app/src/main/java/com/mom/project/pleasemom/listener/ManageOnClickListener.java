package com.mom.project.pleasemom.listener;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.activity.ChangePasswdActivity;
import com.mom.project.pleasemom.activity.ManageAccountActivity;
import com.mom.project.pleasemom.volley.CustomJsonRequest;
import com.mom.project.pleasemom.volley.CustomVolley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 08_718 on 2016-11-07.
 */
public class ManageOnClickListener implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {
    private String url;
    private RequestQueue mQueue;
    private boolean needMerge;
    private Toast toast;
    private Context ctx;
    TextView txtPasswd;

    public ManageOnClickListener(String url, Context ctx, boolean needMerge) {
        this.url = url;
        this.ctx = ctx;
        this.needMerge = needMerge;
    }

    public void setTxtPasswd(TextView txtPasswd){
        this.txtPasswd = txtPasswd;
    }

    @Override
    public void onClick(View v) {
        if (needMerge) {
            url += "&passwd=" + txtPasswd.getText().toString();
        }

        mQueue = CustomVolley
                .getInstance(ctx)
                .getRequestQueue();

        final CustomJsonRequest jsonRequest = new CustomJsonRequest(Request.Method.GET, url, new JSONObject(), this, this);
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonRequest.setTag("MainActivity");
        mQueue.add(jsonRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) { Log.i("TAG", error.toString()); }

    @Override
    public void onResponse(JSONObject response) {
        String str = response.toString();
        try {
            if(response.has("passConfirmResult")){
                if ("success".equals(response.get("passConfirmResult"))) {
                    Intent intent = new Intent(ctx, ChangePasswdActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(intent);
                } else{
                    toast = Toast.makeText(ctx, "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            if(response.has("sendMailResult")){
                if ("success".equals(response.get("sendMailResult"))) {
                    toast = Toast.makeText(ctx, "메일 발송에 성공하였습니다. 메일을 확인해주세요.", Toast.LENGTH_LONG);
                    toast.show();
                } else{
                    toast = Toast.makeText(ctx, "메일 발송에 실패하였습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
