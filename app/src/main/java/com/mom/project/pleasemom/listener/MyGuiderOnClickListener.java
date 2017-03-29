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
import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.activity.MediateRetrieveActivity;
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
public class MyGuiderOnClickListener implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {
    Context ctx;
    ListView listView;
    MemberDTO dto;
    MediateListAdapter mediateListAdapter;

    TextView alert;
    public MyGuiderOnClickListener(Context ctx,ListView listView,MemberDTO dto, TextView alert){
        this.ctx=ctx;
        this.listView = listView;
        this.dto=dto;
        this.alert = alert;
    }

    @Override
    public void onErrorResponse(VolleyError error) { Log.i("TAG", error.toString()); }

    @Override
    public void onResponse(JSONObject response) {
        final ArrayList<DataDTO> list = new ArrayList<>();
        JSONArray jsonArrayGuider = new JSONArray();
        Log.i("TAG", "MyGuiderOnCLick");
        try {
            if (response.has("guider")) {
                jsonArrayGuider = response.getJSONArray("guider");
                String scb_mediate1[] = new String[jsonArrayGuider.length()];
                String scb_content1[] = new String[jsonArrayGuider.length()];
                final String scb_case1[] = new String[jsonArrayGuider.length()];
                String scb_sdate1[] = new String[jsonArrayGuider.length()];
                String scb_mem_num1[] = new String[jsonArrayGuider.length()];
                String scb_num1[] = new String[jsonArrayGuider.length()];
                String scb_title1[] = new String[jsonArrayGuider.length()];
                String scb_via1[] = new String[jsonArrayGuider.length()];
                String scb_wdate1[] = new String[jsonArrayGuider.length()];
                String start1[] = new String[jsonArrayGuider.length()];
                String arrival1[] = new String[jsonArrayGuider.length()];
                Log.i("TAG", "제이슨어레이 시작");
                for (int i = 0; i < jsonArrayGuider.length(); i++) {
                    JSONObject jsonObject = jsonArrayGuider.getJSONObject(i);
                    scb_mediate1[i] = jsonObject.getString("scb_mediate");
                    scb_content1[i] = jsonObject.getString("scb_content");
                    scb_case1[i] = jsonObject.getString("scb_case");
                    scb_sdate1[i] = jsonObject.getString("scb_sdate");
                    scb_mem_num1[i] = jsonObject.getString("scb_mem_num");
                    scb_num1[i] = jsonObject.getString("scb_num");
                    scb_title1[i] = jsonObject.getString("scb_title");
                    scb_via1[i] = jsonObject.getString("scb_via");
                    scb_wdate1[i] = jsonObject.getString("scb_wdate");
                    start1[i] = jsonObject.getString("start");
                    arrival1[i] = jsonObject.getString("arrival");
                    //scb_mediate, String scb_content, int scb_case, String scb_sdate, String scb_mem_num, String scb_num, String scb_title, String scb_via, String scb_wdate, String start, String arrival
                    int scb_case_img = R.drawable.ic_sub;
                    switch (scb_case1[i]) {
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
                    list.add(new DataDTO(scb_mediate1[i], scb_content1[i], scb_case_img, scb_sdate1[i], scb_mem_num1[i], scb_num1[i], scb_title1[i], scb_via1[i], scb_wdate1[i], start1[i], arrival1[i]));
                }
                mediateListAdapter = new MediateListAdapter(ctx, R.layout.board_list_item, list);
                listView.setAdapter(mediateListAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(ctx, MediateRetrieveActivity.class);
                        intent.putExtra("bDTO", list.get(i));
                        intent.putExtra("mDTO", dto);
                        ctx.startActivity(intent);
                    }
                });
            } else{
                alert.setVisibility(View.VISIBLE);
                alert.setText("내가 작성한 글이 없습니다.");
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
        final CustomJsonRequest jsonRequest = new CustomJsonRequest(Request.Method.GET, url, new JSONObject(), this, this);
        jsonRequest.setTag("MainActivity");
        mQueue.add(jsonRequest);
    }
}
