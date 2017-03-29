package com.mom.project.pleasemom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mom.project.pleasemom.R;

import java.util.ArrayList;

/**
 * Created by 08_718 on 2016-10-31.
 */
public class UserInfoAdapter extends BaseAdapter {
    Context ctx;
    int layout;
    ArrayList<String> list;
    LayoutInflater inflater;

    public UserInfoAdapter(Context ctx, int layout, ArrayList<String> list) {
        this.ctx = ctx;
        this.layout = layout;
        this.list = list;
        // inflator 얻어오기
        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int position) { return list.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(layout, null);
        ImageView img = (ImageView)convertView.findViewById(R.id.imgUserInfoItem);
        TextView txt = (TextView)convertView.findViewById(R.id.txtUserInfoItemName);

        // 1. 내정보, 내 글 리스트, 계정관리, 설정
        int[] imgs = {R.drawable.ic_item_account_36dp, R.drawable.ic_item_list_36dp,
                    R.drawable.ic_item_key_36dp, R.drawable.ic_item_settings_36dp, R.drawable.ic_item_logout_36dp};

        img.setImageResource(imgs[position]);
        txt.setText(list.get(position));

        return convertView;
    }
}
