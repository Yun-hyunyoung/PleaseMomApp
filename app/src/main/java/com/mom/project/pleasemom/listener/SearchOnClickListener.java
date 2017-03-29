package com.mom.project.pleasemom.listener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.activity.BoardRetrieveActivity;
import com.mom.project.pleasemom.activity.LoginActivity;
import com.mom.project.pleasemom.activity.MainActivity;
import com.mom.project.pleasemom.adapter.BoardListAdapter;
import com.mom.project.pleasemom.dto.DataDTO;
import com.mom.project.pleasemom.volley.CustomJsonRequest;
import com.mom.project.pleasemom.volley.CustomVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 08_718 on 2016-11-01.
 */
public class SearchOnClickListener implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {
    Context ctx;
    ListView listView;
    ArrayList<DataDTO> list;
    String mem_num;

    TextView departure;
    TextView arrival;
    TextView startDate;
    TextView endDate;

    public SearchOnClickListener(ListView listView, TextView departure, TextView arrival, TextView startDate, TextView endDate) {
        this.listView = listView;
        this.departure = departure;
        this.arrival = arrival;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public void onErrorResponse(VolleyError error) { Log.i("TAG", error.toString()); }

    @Override
    public void onResponse(JSONObject response) {
        list = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = response.getJSONArray("boardListResult");
            Log.i("TAG", "search jsonArray len: " + jsonArray.length());
            if (jsonArray.length() == 0){
                MainActivity.BoardListFragment.txtBoardListAlert.setText("검색 조건에 맞는 게시물이 존재하지 않습니다.\n다른 검색 조건으로 검색해 주세요.");
                MainActivity.BoardListFragment.linearCkbox.setVisibility(View.INVISIBLE);
            } else{
                MainActivity.BoardListFragment.list = this.list;
                MainActivity.BoardListFragment.txtBoardListAlert.setText("");
                MainActivity.BoardListFragment.linearCkbox.setVisibility(View.VISIBLE);
            }
            String scb_mediate[]=new String[jsonArray.length()];
            String scb_content[]=new String[jsonArray.length()];
            final String scb_case[]=new String[jsonArray.length()];
            String scb_sdate[]=new String[jsonArray.length()];
            String scb_mem_num[]=new String[jsonArray.length()];
            String scb_num[]=new String[jsonArray.length()];
            String scb_title[]=new String[jsonArray.length()];
            String scb_via[]=new String[jsonArray.length()];
            String scb_wdate[]=new String[jsonArray.length()];
            String start[]=new String[jsonArray.length()];
            String arrival[]=new String[jsonArray.length()];
            Log.i("TAG","제이슨어레이 시작");
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                scb_mediate[i]=jsonObject.getString("scb_mediate");
                scb_content[i]=jsonObject.getString("scb_content");
                scb_case[i]=jsonObject.getString("scb_case");
                scb_sdate[i]=jsonObject.getString("scb_sdate");
                scb_mem_num[i]=jsonObject.getString("scb_mem_num");
                scb_num[i]=jsonObject.getString("scb_num");
                scb_title[i]=jsonObject.getString("scb_title");
                scb_via[i]=jsonObject.getString("scb_via");
                scb_wdate[i]=jsonObject.getString("scb_wdate");
                start[i]=jsonObject.getString("start");
                arrival[i]=jsonObject.getString("arrival");
                //scb_mediate, String scb_content, int scb_case, String scb_sdate, String scb_mem_num, String scb_num, String scb_title, String scb_via, String scb_wdate, String start, String arrival
                int scb_case_img= R.drawable.ic_sub;
                switch (scb_case[i]){
                    case "WAIT":
                        scb_case_img=R.drawable.ic_sub;
                        break;
                    case "DURING":
                        scb_case_img=R.drawable.ic_pro;
                        break;
                    case "CONFIRM":
                        scb_case_img=R.drawable.ic_com;
                        break;
                }
                list.add(new DataDTO(scb_mediate[i],scb_content[i],scb_case_img,scb_sdate[i],scb_mem_num[i],scb_num[i],scb_title[i],scb_via[i],scb_wdate[i],start[i],arrival[i]));
            }
            Log.i("TAG","제이슨 끝");
            BoardListAdapter adapter = new BoardListAdapter(ctx, R.layout.board_list_item, list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(ctx, BoardRetrieveActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("dto", list.get(position));
                    intent.putExtra("mem_num",mem_num);
                    intent.putExtra("scb_case",scb_case[position]);
                    ctx.startActivity(intent);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        ctx = v.getContext();

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(LoginActivity.PREFERENCES_NAME, Context.MODE_PRIVATE);
        mem_num = sharedPreferences.getString("login", null);

        RequestQueue mQueue = CustomVolley
                .getInstance(ctx)
                .getRequestQueue();

        String scb_from = departure.getText().toString();
        if ("".equals(scb_from)){
            scb_from = "인천 국제공항";
            departure.setText("인천 국제공항");
        }
        String scb_to = arrival.getText().toString();
        if ("".equals(scb_to)){
            scb_to = "미국";
            arrival.setText(scb_to);
        }
        String min = startDate.getText().toString();
        if (min.length() == 0){
            min = "2016-10-11";
            startDate.setText(min);
            startDate.setTextColor(Color.parseColor("#000000"));
        }
        String max = endDate.getText().toString();
        Log.i("TAG", "default len: " + max.length());
        if (max.length() == 0){
            max = "2016-11-11";
            endDate.setText(max);
            endDate.setTextColor(Color.parseColor("#000000"));
        }

        String url = "http://210.125.213.72:8090/SampleProject/android/board/boardList.jsp?scb_from="+ Uri.encode(scb_from, "UTF-8")+"&scb_to="+Uri.encode(scb_to, "UTF-8")+"&min="+min+"&max="+max;
        final CustomJsonRequest jsonRequest = new CustomJsonRequest(Request.Method.GET, url, new JSONObject(), this, this);
        jsonRequest.setTag("MainActivity");
        mQueue.add(jsonRequest);
    }
}
