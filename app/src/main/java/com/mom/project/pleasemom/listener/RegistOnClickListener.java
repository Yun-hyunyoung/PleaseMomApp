package com.mom.project.pleasemom.listener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.mom.project.pleasemom.dto.MemberDTO;
import com.mom.project.pleasemom.volley.CustomJsonRequest;
import com.mom.project.pleasemom.volley.CustomVolley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 08_718 on 2016-11-08.
 */
public class RegistOnClickListener implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {
    String url;
    Context ctx;
    TextView txtName;
    DatePicker datePicker;
    MemberDTO dto;
    Toast toast;

    public RegistOnClickListener(String url, Context ctx, TextView txtName, DatePicker datePicker, MemberDTO dto) {
        this.url = url;
        this.ctx = ctx;
        this.txtName = txtName;
        this.datePicker = datePicker;
        this.dto = dto;
    }

    @Override
    public void onClick(View v) {
        if ("".equals(txtName.getText().toString())){
            toast = Toast.makeText(ctx, "이름을 입력하세요.", Toast.LENGTH_SHORT);
            toast.show();
        } else if ("".equals(datePicker.getYear()) || "".equals(datePicker.getDayOfMonth())){
            toast = Toast.makeText(ctx, "생일을 입력하세요.", Toast.LENGTH_SHORT);
            toast.show();
        } else{
            int year = datePicker.getYear() + 1;
            int month = datePicker.getMonth();
            int day = datePicker.getDayOfMonth();
            String birth = year + "-" + month + "-" + day;
            RequestQueue mQueue = CustomVolley
                    .getInstance(ctx)
                    .getRequestQueue();
            url += "?mem_id="+Uri.encode(dto.getMem_id(), "UTF-8")+"&mem_passwd="+Uri.encode(dto.getMem_passwd(), "UTF-8")+"&mem_name="+ Uri.encode(txtName.getText().toString(), "UTF-8")+"&mem_birth="+Uri.encode(birth, "UTF-8");
            final CustomJsonRequest jsonRequest = new CustomJsonRequest(Request.Method.GET, url, new JSONObject(), this, this);
            jsonRequest.setTag("MainActivity");
            mQueue.add(jsonRequest);
        }
    }
    @Override
    public void onErrorResponse(VolleyError error) { Log.i("TAG", error.toString()); }

    @Override
    public void onResponse(JSONObject response) {
        try {
            if(response.get("registResult") != null) {
                toast = Toast.makeText(ctx, "회원가입을 환영합니다.", Toast.LENGTH_SHORT);
                toast.show();

                SharedPreferences sharedPreferences = ctx.getSharedPreferences(LoginActivity.PREFERENCES_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("login", String.valueOf(response.get("registResult")));
                editor.commit();

                Intent intent = new Intent(ctx, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                ctx.startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
