package com.mom.project.pleasemom.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.dto.DataDTO;
import com.mom.project.pleasemom.dto.ReviewDTO;

/**
 * Created by 08_718 on 2016-11-09.
 */
public class ReviewRetrieveActivity extends Activity{
    ImageView imgProfile;
    TextView txtReqName;
    TextView txtWriteDate;
    TextView txtTimeDiff;

    RatingBar txtStar;
    TextView txtGuiName;
    TextView txtPath;
    TextView txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_retrieve);

        imgProfile = (ImageView)findViewById(R.id.imgRRProfile);
        txtReqName = (TextView)findViewById(R.id.txtRRRequesterName);
        txtWriteDate = (TextView)findViewById(R.id.txtRRWriteDate);
        txtTimeDiff = (TextView)findViewById(R.id.txtRRTimeDiff);

        txtStar = (RatingBar)findViewById(R.id.txtRRStar);
        txtGuiName = (TextView)findViewById(R.id.txtRRGuiderName);
        txtPath = (TextView)findViewById(R.id.txtRRPath);
        txtContent = (TextView)findViewById(R.id.txtRRContent);

        Intent intent = getIntent();
        ReviewDTO dto = (ReviewDTO)intent.getSerializableExtra("reviewDTO");

        imgProfile.setImageResource(R.drawable.default_profile);
        txtReqName.setText(dto.getReqMemDto().getMem_name());
        txtWriteDate.setText(dto.getReview_wdate());
        txtTimeDiff.setText(dto.getDiffDate());

        txtStar.setStepSize((float) 0.1);        //별 색깔이 1칸씩줄어들고 늘어남 0.5로하면 반칸씩 들어감
        txtStar.setRating((float) dto.getReview_star());      // 처음보여줄때(색깔이 한개도없음) default 값이 0  이다
        txtStar.setIsIndicator(true);           //true - 별점만 표시 사용자가 변경 불가 , false - 사용자가 변경가능

        txtGuiName.setText(dto.getGuiMemDto().getMem_name());

        DataDTO dataDTO = dto.getBoardDto();
        String start = dataDTO.getStart();
        String via = dataDTO.getScb_via();
        String arrival = dataDTO.getArrival();
        String path = start + "->" + via + "->" + arrival;
        txtPath.setText(path);

        txtContent.setText(dto.getReview_content());
    }
}
