package com.mom.project.pleasemom.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.adapter.MediateListAdapter;
import com.mom.project.pleasemom.dto.DataDTO;
import com.mom.project.pleasemom.dto.MemberDTO;
import com.mom.project.pleasemom.listener.MyGuiderOnClickListener;
import com.mom.project.pleasemom.listener.MyRequestOnClickListener;
import com.mom.project.pleasemom.volley.CustomJsonRequest;
import com.mom.project.pleasemom.volley.CustomVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 08_718 on 2016-11-08.
 */
public class BoardMediateActivity extends Activity {
    ListView listView;
    MediateListAdapter mediateListAdapter;
    ArrayList<DataDTO> list;
    MemberDTO dto;
    Button requestBtn;
    Button guiderBtn;

    TextView txtAlert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediate);

        Intent intent = getIntent();
        dto = (MemberDTO)intent.getSerializableExtra("memberDTO");
        Log.i("TAG", dto.toString());
        listView=(ListView)findViewById(R.id.mediate_list);
        requestBtn=(Button)findViewById(R.id.myRequestBtn);
        guiderBtn=(Button)findViewById(R.id.myGuiderBtn);
        list=new ArrayList<>();
        mediateListAdapter=new MediateListAdapter(BoardMediateActivity.this,R.layout.mediate_list_item,list);
        listView.setAdapter(mediateListAdapter);

        txtAlert = (TextView)findViewById(R.id.txtMediateAlert);

        requestBtn.setOnClickListener(new MyRequestOnClickListener(getApplicationContext(),listView,dto, txtAlert));
        guiderBtn.setOnClickListener(new MyGuiderOnClickListener(getApplicationContext(),listView,dto, txtAlert));
    }
}
