package com.mom.project.pleasemom.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.adapter.AutoAirportNameAdapter;
import com.mom.project.pleasemom.dto.AirportDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by 08_718 on 2016-11-11.
 */
public class FindAirportActivity extends Activity {
    AutoCompleteTextView autoTextView;
    ArrayList<AirportDTO> airportDTOs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_airport);
        loadAirportInfo();

        Button btn = (Button)findViewById(R.id.btnFindAirport);
        autoTextView  = (AutoCompleteTextView)findViewById(R.id.autoFindAirport);
        autoTextView.setThreshold(1);
        AutoAirportNameAdapter adapter = new AutoAirportNameAdapter(this, R.layout.autocomplete_airport_name, airportDTOs);
        autoTextView.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.BoardListFragment.txtDeparture.setText(autoTextView.getText().toString());
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
