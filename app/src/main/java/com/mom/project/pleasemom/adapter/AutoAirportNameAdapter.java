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
import java.util.List;

/**
 * Created by 08_718 on 2016-11-11.
 */
public class AutoAirportNameAdapter extends ArrayAdapter<AirportDTO>{
    Context context;
    int resource;
    ArrayList<AirportDTO> items,tempItems, suggestions;

    public AutoAirportNameAdapter(Context context, int resource, ArrayList<AirportDTO> items) {
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
            TextView airportName = (TextView) view.findViewById(R.id.airportName);
            if (airportName != null)
                airportName.setText(airport.getName_kr());
            TextView airportCountry = (TextView) view.findViewById(R.id.airportCountry);
            if (airportCountry != null)
                airportCountry.setText(airport.getCountry_kr());
            TextView airportCity = (TextView) view.findViewById(R.id.airportCity);
            if (airportCity != null)
                airportCity.setText(airport.getCity_kr());
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
            String str = ((AirportDTO) resultValue).getName_kr();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (AirportDTO airport : tempItems) {
                    if (airport.getName_kr().contains(constraint.toString())) {
                        suggestions.add(airport);
                    }
                }
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
