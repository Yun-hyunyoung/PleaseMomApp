package com.mom.project.pleasemom.listener;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mom.project.pleasemom.dto.DataDTO;
import com.mom.project.pleasemom.dto.MemberDTO;
import com.mom.project.pleasemom.volley.CustomJsonRequest;
import com.mom.project.pleasemom.volley.CustomVolley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 08_718 on 2016-11-01.
 */
public class MyMediateApprovalOnClickListener implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {

    Context ctx;
    DataDTO bDTO;
    MemberDTO mDTO;
    Button approvalBtn;
    public MyMediateApprovalOnClickListener(Context ctx,DataDTO bDTO,MemberDTO mDTO,Button approvalBtn){
        this.ctx=ctx;
        this.bDTO=bDTO;
        this.mDTO=mDTO;
        this.approvalBtn=approvalBtn;
        if(bDTO.getScb_mediate().equals("Y") && mDTO.getMem_case().equalsIgnoreCase("confirm")){
            approvalBtn.setEnabled(false);
            Log.i("TAG","리스너 111");
        }
        else {
            approvalBtn.setEnabled(true);
            Log.i("TAG","리스너 222");
        }
    }
    @Override
    public void onErrorResponse(VolleyError error) { Log.i("TAG", error.toString()); }
    @Override
    public void onResponse(JSONObject response) {
        //승인
        Log.i("TAG","response=="+response);
        try {
            if(response.getString("result").equalsIgnoreCase("true")){
                approvalBtn.setEnabled(false);
                Log.i("TAG","true===");
            }
            else{
                approvalBtn.setEnabled(true);
                Log.i("TAG","false===");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        ctx = v.getContext();
        RequestQueue mQueue = CustomVolley
                .getInstance(ctx)
                .getRequestQueue();
        String url = "http://210.125.213.72:8090/SampleProject/android/mediate/mediateRetrieve.jsp";
        url+="?login_mem_num="+mDTO.getMem_num()+"&scb_num="+bDTO.getScb_num();
        final CustomJsonRequest jsonRequest = new CustomJsonRequest(Request.Method.GET, url, new JSONObject(), this, this);
        jsonRequest.setTag("MainActivity");
        mQueue.add(jsonRequest);

    }
}
