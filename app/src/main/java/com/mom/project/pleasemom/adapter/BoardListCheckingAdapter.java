package com.mom.project.pleasemom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.dto.DataDTO;

import java.util.ArrayList;

/**
 * Created by 08_718 on 2016-11-01.
 */
public class BoardListCheckingAdapter extends BaseAdapter{
    Context ctx;
    int layout;
    ArrayList<DataDTO> list;
    LayoutInflater inflater;
    CheckBox ckWait;
    CheckBox ckDuring;
    CheckBox ckConfirm;

    public BoardListCheckingAdapter(Context ctx, int layout, ArrayList<DataDTO> list, CheckBox ckWait, CheckBox ckDuring, CheckBox ckConfirm) {
        this.ctx = ctx;
        this.layout = layout;
        this.list = list;
        this.ckWait = ckWait;
        this.ckDuring = ckDuring;
        this.ckConfirm = ckConfirm;
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
        boolean wait = ckWait.isChecked();
        boolean during = ckDuring.isChecked();
        boolean confirm = ckConfirm.isChecked();

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

        if (!wait && dto.getScb_case() == 2130837631){
            converView=inflater.inflate(R.layout.board_list_item_empty,null);
        }else if (!during && dto.getScb_case() == 2130837623){
            converView=inflater.inflate(R.layout.board_list_item_empty,null);
        }else if (!confirm && dto.getScb_case() == 2130837610){
            converView=inflater.inflate(R.layout.board_list_item_empty,null);
        }
        return converView;
    }
}
