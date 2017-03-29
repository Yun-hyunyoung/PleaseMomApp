package com.mom.project.pleasemom;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by 08_718 on 2016-10-21.
 */
public class RosaFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static String TAG ="RosaFirebaseInstanceIDService";

    @Override
    public void onTokenRefresh() {
        // super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i("TAG", "token ==> " + token);

        //서버로 token 전달
    }
}
