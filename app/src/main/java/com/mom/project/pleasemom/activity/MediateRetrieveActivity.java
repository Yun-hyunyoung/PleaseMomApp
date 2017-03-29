package com.mom.project.pleasemom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.dto.DataDTO;
import com.mom.project.pleasemom.dto.MemberDTO;
import com.mom.project.pleasemom.listener.MyMediateApprovalOnClickListener;

/**
 * Created by sms on 2016-11-09.
 */
public class MediateRetrieveActivity extends AppCompatActivity{
    DataDTO bDTO;
    MemberDTO mDTO;
    Button approvalBtn;
    TextView mediate_scb_num;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediate_retrieve);
        Intent intent=getIntent();
        bDTO=(DataDTO)intent.getSerializableExtra("bDTO");
        mDTO=(MemberDTO)intent.getSerializableExtra("mDTO");
        approvalBtn=(Button)findViewById(R.id.mediate_appoval);
        mediate_scb_num=(TextView)findViewById(R.id.mediate_scb_num);
        approvalBtn.setOnClickListener(new MyMediateApprovalOnClickListener(getApplicationContext(),bDTO,mDTO,approvalBtn));
        //텍스트내용 넣기
        mediate_scb_num.setText(bDTO.getScb_num());
        ///////////////
    }
}
