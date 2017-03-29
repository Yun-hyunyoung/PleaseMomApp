package com.mom.project.pleasemom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mom.project.pleasemom.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 08_718 on 2016-11-15.
 */
public class NotiListAdapter extends BaseAdapter {
    Context ctx;
    int layout;
    ArrayList<HashMap<String, String>> list;
    LayoutInflater inflater;

    public NotiListAdapter(Context ctx, int layout, ArrayList<HashMap<String, String>> list) {
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

        TextView notiId=(TextView)converView.findViewById(R.id.notiListId);
        TextView notiContent=(TextView)converView.findViewById(R.id.notiListContent);
        TextView notiWdate=(TextView)converView.findViewById(R.id.notiListWdate);

        HashMap<String, String> map = list.get(position);
        notiId.setText(map.get("_id"));
        notiContent.setText(map.get("content"));
        notiWdate.setText(map.get("wdate"));

        return converView;
    }
}
