package com.mom.project.pleasemom.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.dto.AirportDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by 08_718 on 2016-11-11.
 */
public class BoardWriteActivity extends Activity {
    TextView txtTitle;
    static TextView txtDeparture;
    Spinner txtVia;
    static TextView txtArrival;
    TextView txtStartDate;
    TextView txtStartTime;
    TextView txtContent;

    Button btnConfirm;
    Button btnCancel;

    String date;
    String time;

    Context ctx;

    ArrayList<AirportDTO> airportDTOs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);
        ctx = BoardWriteActivity.this;
        loadAirportInfo();

        txtTitle = (TextView)findViewById(R.id.txtWriteTitle);
        txtDeparture = (TextView)findViewById(R.id.txtWriteDeparture);
        txtDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, WriteAirportActivityDrparture.class);
                startActivity(intent);
            }
        });

        txtVia = (Spinner)findViewById(R.id.txtWriteVia);

        txtArrival = (TextView)findViewById(R.id.txtWriteArrival);
        txtArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, WriteAirportActivityArrival.class);
                startActivity(intent);
            }
        });

        txtStartDate = (TextView)findViewById(R.id.txtWriteStartDate);
        txtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(ctx, listener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
                dialog.show();
            }

            private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    txtStartDate.setTextColor(Color.parseColor("#000000"));
                    txtStartDate.setText(year+"년 "+(monthOfYear+1)+"월 "+dayOfMonth +"일");
                    date = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                }
            };
        });

        txtStartTime = (TextView)findViewById(R.id.txtWriteStartTime);
        txtStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                TimePickerDialog dialog = new TimePickerDialog(ctx, listener, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false);
                dialog.show();
            }

            TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    txtStartTime.setTextColor(Color.parseColor("#000000"));
                    txtStartTime.setText(hourOfDay+"시 "+minute +"분");
                    time = "+" + hourOfDay + ":" + minute;
                }
            };
        });
        txtContent = (TextView)findViewById(R.id.txtWriteTitle);

        btnConfirm = (Button)findViewById(R.id.btnWriteConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFERENCES_NAME, Context.MODE_PRIVATE);
                String mem_num = sharedPreferences.getString("login", null);

                String scb_from = null;
                String scb_via = (String)txtVia.getSelectedItem();
                String scb_to = null;
                String scb_sdate = date + time;
                String scb_title = txtTitle.getText().toString();
                String scb_content = txtContent.getText().toString();

                ArrayList<AirportDTO> list = airportDTOs;
                for(AirportDTO dto : list){
                    if (txtDeparture.getText().equals(dto.getName_kr())){
                        scb_from = String.valueOf(dto.getNum());
                        break;
                    }
                }
                for(AirportDTO dto : list){
                    if (txtArrival.getText().equals(dto.getName_kr())){
                        scb_to = String.valueOf(dto.getNum());
                        break;
                    }
                }


                RequestQueue queue = Volley.newRequestQueue(ctx);
                String url ="http://210.125.213.72:8090/SampleProject/android/board/boardWrite.jsp?mem_num="+mem_num+"&scb_from="+scb_from+"&scb_to="+scb_to
                        +"&scb_via="+scb_via+"&scb_sdate="+Uri.encode(scb_sdate, "UTF-8")+"&scb_title="+Uri.encode(scb_title, "UTF-8")+"&scb_content="+Uri.encode(scb_content, "UTF-8");
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject obj = new JSONObject(response);
                                    if ("success".equals(obj.get("boardWriteResult"))){
                                        Toast.makeText(ctx, "동행글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ctx, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }catch(JSONException je){
                                    je.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { Log.i("TAG", "boardWriteActivity"+error.toString()); }
                });
// Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });

        btnCancel = (Button)findViewById(R.id.btnWriteCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void loadAirportInfo(){
        airportDTOs = new ArrayList<>();

        String json = null;
        try {
            InputStream is = getAssets().open("airport_info.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray array = (JSONArray)obj.get("items");
            for (int i = 0; i < array.length(); i++){
                JSONObject airport = (JSONObject)array.get(i);
                int num = airport.getInt("airport_num");
                String name_kr = airport.getString("name_kr");
                String country_kr = airport.getString("country_kr");
                String city_kr = airport.getString("city_kr");
                String location = airport.getString("location");
                AirportDTO airportDTO = new AirportDTO(num, name_kr, country_kr, city_kr, location);
                airportDTOs.add(airportDTO);
            }
        }catch (JSONException je){
            je.printStackTrace();
        }
    }
}
