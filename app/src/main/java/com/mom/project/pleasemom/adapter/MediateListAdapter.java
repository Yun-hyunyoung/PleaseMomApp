package com.mom.project.pleasemom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.dto.DataDTO;

import java.util.ArrayList;

/**
 * Created by 08_718 on 2016-11-01.
 */
public class MediateListAdapter extends BaseAdapter{
    Context ctx;
    int layout;
    ArrayList<DataDTO> list;
    LayoutInflater inflater;

    public MediateListAdapter(Context ctx, int layout, ArrayList<DataDTO> list) {
        this.ctx = ctx;
        this.layout = layout;
        this.list = list;
        this.inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        converView=inflater.inflate(layout,null);


        ImageView scb_case_img=(ImageView)converView.findViewById(R.id.scb_case_img);
        ImageView right_arrow_img=(ImageView)converView.findViewById(R.id.right_arrow_img);
        TextView start=(TextView)converView.findViewById(R.id.textStart);
        TextView arrival=(TextView)converView.findViewById(R.id.textArrival);
        TextView scb_sdate=(TextView)converView.findViewById(R.id.scb_sdate);
        TextView scb_title=(TextView)converView.findViewById(R.id.scb_title);

        DataDTO dto=list.get(position);
        scb_case_img.setImageResource(dto.getScb_case());
        right_arrow_img.setImageResource(R.drawable.right_arrow);
        start.setText(dto.getStart());
        arrival.setText(dto.getArrival());
        scb_sdate.setText(dto.getScb_sdate());
        scb_title.setText(dto.getScb_title());

        return converView;
    }
}
