package com.mom.project.pleasemom.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.dto.AirportDTO;

import java.util.ArrayList;

/**
 * Created by 08_718 on 2016-11-11.
 */
public class AutoCountryNameAdapter extends ArrayAdapter<AirportDTO>{
    Context context;
    int resource;
    ArrayList<AirportDTO> items,tempItems, suggestions;

    public AutoCountryNameAdapter(Context context, int resource, ArrayList<AirportDTO> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
        tempItems = new ArrayList<AirportDTO>(items); // this makes the difference.
        suggestions = new ArrayList<AirportDTO>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);
        }
        AirportDTO airport = items.get(position);
        if (airport != null) {
            TextView countryName = (TextView) view.findViewById(R.id.countryName);
            if (countryName != null)
                countryName.setText(airport.getCountry_kr());
            TextView countryLocation = (TextView) view.findViewById(R.id.countryLocation);
            if (countryLocation != null)
                countryLocation.setText(airport.getLocation());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((AirportDTO) resultValue).getCountry_kr();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (AirportDTO airport : tempItems) {
                    if (airport.getCountry_kr().contains(constraint.toString())) {
                        suggestions.add(airport);
                    }
                }
                Log.i("TAG", suggestions.toString());
                if (suggestions.size() > 0) {
                    for (int i = 0; i < suggestions.size(); i++) {
                        for (int j = 1; j < suggestions.size(); j++) {
                            String country1 = suggestions.get(i).getCountry_kr();
                            String country2 = suggestions.get(j).getCountry_kr();
                            Log.i("TAG", "i: " + i + " / j: " + j + "country1: " + country1 + " / country2: " + country2);
                            if (country1.equals(country2)){
                                suggestions.remove(j);
                            }
                        }
                    }
                }

                Log.i("TAG", "suggestions: " + suggestions);

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<AirportDTO> filterList = (ArrayList<AirportDTO>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (AirportDTO airport : filterList) {
                    add(airport);
                }
                notifyDataSetChanged();
            }
        }
    };
}
