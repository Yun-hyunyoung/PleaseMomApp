package com.mom.project.pleasemom.listener;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by 08_718 on 2016-11-04.
 */
public class BackPressCloseListener {
    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressCloseListener(Activity context){
        this.activity = context;
    }

    public void onBackPressed(){
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showToast();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            toast.cancel();
        }
    }

    public void showToast(){
        toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 클릭시 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
}
