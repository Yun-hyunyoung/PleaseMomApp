package com.mom.project.pleasemom.listener;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mom.project.pleasemom.activity.MediateRetrieveActivity;
import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.adapter.MediateListAdapter;
import com.mom.project.pleasemom.dto.DataDTO;
import com.mom.project.pleasemom.dto.MemberDTO;
import com.mom.project.pleasemom.volley.CustomJsonRequest;
import com.mom.project.pleasemom.volley.CustomVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 08_718 on 2016-11-01.
 */
public class MyRequestOnClickListener implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {
    Context ctx;
    ListView listView;
    MemberDTO dto;
    MediateListAdapter mediateListAdapter;
    ArrayList<DataDTO> list;

    TextView alert;
    public MyRequestOnClickListener(Context ctx,ListView listView,MemberDTO dto, TextView alert){
        this.ctx=ctx;
        this.dto=dto;
        this.listView = listView;
        this.alert = alert;
    }

    @Override
    public void onErrorResponse(VolleyError error) { Log.i("TAG", error.toString()); }

    @Override
    public void onResponse(JSONObject response) {
        list = new ArrayList<>();
        JSONArray jsonArrayRequest = new JSONArray();
        Log.i("TAG", "MyRequestOnClick");
        try {
            if (response.has("request")) {
                jsonArrayRequest = response.getJSONArray("request");
                String scb_mediate[] = new String[jsonArrayRequest.length()];
                String scb_content[] = new String[jsonArrayRequest.length()];
                final String scb_case[] = new String[jsonArrayRequest.length()];
                String scb_sdate[] = new String[jsonArrayRequest.length()];
                String scb_mem_num[] = new String[jsonArrayRequest.length()];
                String scb_num[] = new String[jsonArrayRequest.length()];
                String scb_title[] = new String[jsonArrayRequest.length()];
                String scb_via[] = new String[jsonArrayRequest.length()];
                String scb_wdate[] = new String[jsonArrayRequest.length()];
                String start[] = new String[jsonArrayRequest.length()];
                String arrival[] = new String[jsonArrayRequest.length()];
                Log.i("TAG", "제이슨어레이 시작");
                for (int i = 0; i < jsonArrayRequest.length(); i++) {
                    JSONObject jsonObject = jsonArrayRequest.getJSONObject(i);
                    scb_mediate[i] = jsonObject.getString("scb_mediate");
                    scb_content[i] = jsonObject.getString("scb_content");
                    scb_case[i] = jsonObject.getString("scb_case");
                    scb_sdate[i] = jsonObject.getString("scb_sdate");
                    scb_mem_num[i] = jsonObject.getString("scb_mem_num");
                    scb_num[i] = jsonObject.getString("scb_num");
                    scb_title[i] = jsonObject.getString("scb_title");
                    scb_via[i] = jsonObject.getString("scb_via");
                    scb_wdate[i] = jsonObject.getString("scb_wdate");
                    start[i] = jsonObject.getString("start");
                    arrival[i] = jsonObject.getString("arrival");
                    //scb_mediate, String scb_content, int scb_case, String scb_sdate, String scb_mem_num, String scb_num, String scb_title, String scb_via, String scb_wdate, String start, String arrival
                    int scb_case_img = R.drawable.ic_sub;
                    switch (scb_case[i]) {
                        case "WAIT":
                            scb_case_img = R.drawable.ic_sub;
                            break;
                        case "DURING":
                            scb_case_img = R.drawable.ic_pro;
                            break;
                        case "CONFIRM":
                            scb_case_img = R.drawable.ic_com;
                            break;
                    }
                    list.add(new DataDTO(scb_mediate[i], scb_content[i], scb_case_img, scb_sdate[i], scb_mem_num[i], scb_num[i], scb_title[i], scb_via[i], scb_wdate[i], start[i], arrival[i]));
                }
                mediateListAdapter = new MediateListAdapter(ctx,R.layout.board_list_item, list);
                listView.setAdapter(mediateListAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int posion, long l) {
                        Intent intent=new Intent(ctx,MediateRetrieveActivity.class);
                        intent.putExtra("bDTO",list.get(posion));
                        intent.putExtra("mDTO",dto);
                        ctx.startActivity(intent);
                    }
                });
            } else{
                alert.setVisibility(View.VISIBLE);
                alert.setText("내가 요청한 글이 없습니다.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        alert.setVisibility(View.INVISIBLE);

        ctx = v.getContext();
        RequestQueue mQueue = CustomVolley
                .getInstance(ctx)
                .getRequestQueue();
        String url = "http://210.125.213.72:8090/SampleProject/android/mediate/mediate.jsp?login_num="+dto.getMem_num();
        Log.i("TAG", "mem_num: "+dto.getMem_num());
        final CustomJsonRequest jsonRequest = new CustomJsonRequest(Request.Method.GET, url, new JSONObject(), this, this);
        jsonRequest.setTag("MainActivity");
        mQueue.add(jsonRequest);
    }
}
