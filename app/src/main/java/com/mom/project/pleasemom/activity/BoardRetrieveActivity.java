package com.mom.project.pleasemom.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.dto.DataDTO;
import com.mom.project.pleasemom.volley.CustomJsonRequest;
import com.mom.project.pleasemom.volley.CustomVolley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by sms on 2016-11-02.
 */
public class BoardRetrieveActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    TextView scb_title;
    TextView scb_content;
    TextView scb_mem_name;
    TextView start;
    TextView arrival;
    TextView scb_via;
    TextView scb_sdate;
    ImageView profileImg;
    ImageView email_ck;
    ImageView phone_ck;

    Button btnApproval;
    Button btnList;

    DataDTO dDTO;
    String mem_num;
    String scb_name;
    String scb_case;

    TextView txtCountry;
    TextView txtAge;
    TextView txtJob;

    RequestQueue mQueue;

    Activity activity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_retrieve);

        activity = BoardRetrieveActivity.this;
        btnApproval=(Button)findViewById(R.id.approval);
        btnList=(Button)findViewById(R.id.board_retrieve_list);
        Intent intent=getIntent();
        dDTO=(DataDTO)intent.getSerializableExtra("dto");
        mem_num=intent.getStringExtra("mem_num");
        scb_case=intent.getStringExtra("scb_case");

        String scb_mem_num = dDTO.getScb_mem_num();
        if (mem_num.equals(scb_mem_num)){
            btnApproval.setVisibility(View.INVISIBLE);
        }

        scb_title=(TextView)findViewById(R.id.board_retrieve_title);
        scb_content=(TextView)findViewById(R.id.board_retrieve_content);
        scb_mem_name=(TextView)findViewById(R.id.board_retrieve_mem_name);
        start=(TextView)findViewById(R.id.board_retrieve_start);
        arrival=(TextView)findViewById(R.id.board_retrieve_arrival);
        scb_via=(TextView)findViewById(R.id.board_retrieve_via);
        scb_sdate=(TextView)findViewById(R.id.board_retrieve_sdate);
        profileImg=(ImageView)findViewById(R.id.profile_img);
        email_ck=(ImageView)findViewById(R.id.board_retrieve_email_ck);
        phone_ck=(ImageView)findViewById(R.id.board_retrieve_phone_ck);

        txtCountry = (TextView)findViewById(R.id.board_retrieve_country);
        txtAge = (TextView)findViewById(R.id.board_retrieve_age);
        txtJob = (TextView)findViewById(R.id.board_retrieve_job);

        mQueue = CustomVolley
                .getInstance(getApplicationContext())
                .getRequestQueue();

        String url ="http://210.125.213.72:8090/SampleProject/android/member/loginMember.jsp";
        url+="?scb_num_num="+dDTO.getScb_mem_num();

        final CustomJsonRequest jsonRequest = new CustomJsonRequest(Request.Method.GET, url, new JSONObject(), this, this);
        jsonRequest.setTag("LoginActivity");
        mQueue.add(jsonRequest);

        profileImg.setImageResource(R.drawable.default_profile);
        email_ck.setImageResource(R.drawable.verified);
        phone_ck.setImageResource(R.drawable.unverified);

        scb_title.setText(dDTO.getScb_title());
        scb_content.setText(dDTO.getScb_content());
        start.setText(dDTO.getStart());
        arrival.setText(dDTO.getArrival());
        scb_via.setText(dDTO.getScb_via());
        String sdate = dDTO.getScb_sdate().substring(0, 16);
        scb_sdate.setText(sdate);

        if(scb_case.equals("WAIT")){
            btnApproval.setEnabled(true);
        }
        else {
            btnApproval.setEnabled(false);
        }
    }
    public void boardList(View v){
        this.finish();
    }

    public void boardApproval(View v){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://210.125.213.72:8090/SampleProject/android/board/boardUpdate.jsp";
        Log.i("TAG","sub_num"+dDTO.getScb_num());
        Log.i("TAG","sub_mem_num=="+dDTO.getScb_mem_num());
        Log.i("TAG","loginDTO=="+mem_num);

        url+="?scb_num="+dDTO.getScb_num()+"&loginMemberNum="+mem_num;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ///////////작업하자
                        btnApproval.setEnabled(false);
                        Log.i("TAG","response===="+response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TAG","That didn't work!");
            }
        });
        queue.add(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) { Log.i("TAG", error.toString()); }

    @Override
    public void onResponse(JSONObject response) {
        try {
            String name = response.getString("mem_name");
            scb_mem_name.setText(name);
            scb_name = name;

            String birth = response.getString("mem_birth");

            txtCountry.setText("한국");

            int thisYear = Calendar.getInstance().get(Calendar.YEAR);
            int year = Integer.parseInt(birth.substring(0, 4));
            Log.i("BoardRetrieve year: ", year+"");
            Log.i("BoardRetrieve tyear: ", thisYear+"");

            int age = thisYear - year + 1;
            txtAge.setText(age+"");
            txtJob.setText("학생");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
