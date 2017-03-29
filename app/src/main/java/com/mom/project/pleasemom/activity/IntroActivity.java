package com.mom.project.pleasemom.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mom.project.pleasemom.R;

public class IntroActivity extends AppCompatActivity {

    private void isIntro() {
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    ImageView intro;
    TextView txt;
    TextView txtBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    intro = (ImageView)findViewById(R.id.logo);
                    txt = (TextView)findViewById(R.id.txtIntro);
                    txtBottom = (TextView)findViewById(R.id.txtIntroBottom);
                    Animation alphaAnim = AnimationUtils.loadAnimation(IntroActivity.this, R.anim.alpha);
                    intro.startAnimation(alphaAnim);
                    txt.startAnimation(alphaAnim);
                    txtBottom.startAnimation(alphaAnim);

                    Thread.sleep(2500);
                    isIntro();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
