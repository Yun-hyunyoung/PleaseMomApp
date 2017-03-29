package com.mom.project.pleasemom.listener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mom.project.pleasemom.activity.LoginActivity;
import com.mom.project.pleasemom.activity.MainActivity;
import com.mom.project.pleasemom.activity.RegistActivityTwo;
import com.mom.project.pleasemom.dto.MemberDTO;
import com.mom.project.pleasemom.volley.CustomJsonRequest;
import com.mom.project.pleasemom.volley.CustomVolley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 08_718 on 2016-11-08.
 */
public class RegistOneOnClickListener implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {
    String url;
    Context ctx;
    TextView txtEmail;
    TextView txtPasswd;
    Toast toast;

    public RegistOneOnClickListener(String url, Context ctx, TextView txtEmail, TextView txtPasswd) {
        this.url = url;
        this.ctx = ctx;
        this.txtEmail = txtEmail;
        this.txtPasswd = txtPasswd;
    }

    @Override
    public void onClick(View v) {
        if ("".equals(txtEmail.getText().toString())){
            toast = Toast.makeText(ctx, "이메일을 입력하세요.", Toast.LENGTH_SHORT);
            toast.show();
        } else if("".equals(txtPasswd.getText().toString())){
            toast = Toast.makeText(ctx, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT);
            toast.show();
        } else if(!isValidEmail(txtEmail.getText())){
            toast = Toast.makeText(ctx, "이메일 형식이 아닙니다.", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            url += "?mem_id="+txtEmail.getText().toString();
            RequestQueue mQueue = CustomVolley
                    .getInstance(ctx)
                    .getRequestQueue();
            final CustomJsonRequest jsonRequest = new CustomJsonRequest(Request.Method.GET, url, new JSONObject(), this, this);
            jsonRequest.setTag("MainActivity");
            mQueue.add(jsonRequest);
        }
    }

    public boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    public void onErrorResponse(VolleyError error) { Log.i("TAG", error.toString()); }

    @Override
    public void onResponse(JSONObject response) {
        try {
            if ("none".equals(response.get("idCheckResult"))) {
                MemberDTO dto = new MemberDTO();
                dto.setMem_id(txtEmail.getText().toString());
                dto.setMem_passwd(txtPasswd.getText().toString());

                Intent intent = new Intent(ctx, RegistActivityTwo.class);
                intent.putExtra("regist", dto);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            } else{
                toast = Toast.makeText(ctx, "중복된 이메일입니다.", Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
