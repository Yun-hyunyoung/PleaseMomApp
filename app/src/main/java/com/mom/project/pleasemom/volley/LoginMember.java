package com.mom.project.pleasemom.volley;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.mom.project.pleasemom.dto.MemberDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sms on 2016-11-07.
 */
public class LoginMember {

    Handler handler=new Handler();
    String data;
    MemberDTO dto;
    public LoginMember(final String mem_num){
        Thread t=new Thread(new Runnable() {
            @Override
            public void run(){
                try {
                    URL url=new URL("http://210.125.213.78:8090/SampleProject/android/member/loginMember.jsp");
                    HttpURLConnection con=(HttpURLConnection)url.openConnection();
                    con.setDoOutput(true);
                    //핸드폰 -> jsp
                    OutputStream out=new BufferedOutputStream(con.getOutputStream());
                    PrintWriter writer=new PrintWriter(out);
                    String mesg="loginMemberMum="+mem_num;
                    writer.print(mesg);
                    writer.close();

                    //jsp -> 핸드폰
                    InputStream in=con.getInputStream();
                    data=streamToString(in);

                    //handler 이용하여 작업스레드
                    //textResult.setText(data);
                    //메인 영역
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("TAG",data+"000");
                        }
                    });
                    Log.i("TAG", data+"001");
                   /* JSONArray jsonArray=new JSONArray(data);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        Log.i("TAG","a=="+jsonObject.getString("a"));
                        Log.i("TAG","b=="+jsonObject.getString("b"));
                        Log.i("TAG","c=="+jsonObject.getString("c"));
                        //Log.i("TAG","dto=="+jsonObject.getString("dto"));
                    }*/
                    in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public String streamToString(InputStream is){
        StringBuffer buffer=new StringBuffer();
        try{
            BufferedReader reader=new BufferedReader(new InputStreamReader(is));
            String data=reader.readLine();
            while(data != null){
                buffer.append(data+"\n");
                data=reader.readLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public MemberDTO getDto() {
        return dto;
    }

    public void setDto(MemberDTO dto) {
        this.dto = dto;
    }
}
