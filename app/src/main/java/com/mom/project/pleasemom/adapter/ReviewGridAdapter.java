package com.mom.project.pleasemom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.dto.ReviewDTO;

import java.util.ArrayList;

/**
 * Created by 08_718 on 2016-11-07.
 */
public class ReviewGridAdapter extends BaseAdapter {
    Context ctx;
    int layout;
    ArrayList<ReviewDTO> list;
    LayoutInflater inflater;

    public ReviewGridAdapter(Context ctx, int layout, ArrayList<ReviewDTO> list){
        this.ctx = ctx;
        this.layout = layout;
        this.list = list;
        this.inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(layout,null);
        ImageView profile = (ImageView)convertView.findViewById(R.id.imgReviewItemProfile);
        RatingBar title = (RatingBar)convertView.findViewById(R.id.txtReviewItemTitle);
        TextView content = (TextView)convertView.findViewById(R.id.txtReviewItemContent);

        ReviewDTO dto = list.get(position);
        profile.setImageResource(R.drawable.default_profile);

        title.setStepSize((float) 0.1);
        title.setRating((float) dto.getReview_star());
        title.setIsIndicator(true);

        content.setText(dto.getReview_content());

        return convertView;
    }
}
