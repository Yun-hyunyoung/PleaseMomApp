package com.mom.project.pleasemom;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mom.project.pleasemom.activity.MainActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by prof-718-8 on 2016-10-19.
 */
public class RosaFirebaseMessagingService extends FirebaseMessagingService {

    private static String TAG ="RosaFirebaseMessagingService";
    Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);

        Map<String ,String > data = remoteMessage.getData();
        if(data!=null){
            Log.i("TAG", "data ==> " + data);
        }

        RemoteMessage.Notification noti =
                remoteMessage.getNotification();
        if(noti!=null){

            String title = noti.getTitle();
            Log.i("TAG", "title ==> " + title);
            String body = noti.getBody();
            Log.i("TAG", "body ==> " + body);
        }
        Map<String, String> mesgMap = new HashMap<>();
        mesgMap.put("title", noti.getTitle());
        mesgMap.put("body", noti.getBody());

        sendNotification(mesgMap);

    }//end onMessageReceived

    private void sendNotification(Map<String, String> mesgMap) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.btn_radio)
                .setContentTitle(mesgMap.get("title"))
                .setContentText(mesgMap.get("body"))
                .setSmallIcon(R.drawable.ic_noti)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(getApplicationContext(), null, null, 3);
        SQLiteDatabase db = helper.getWritableDatabase();

        Calendar c = Calendar.getInstance();
        String date = "";
        date += String.valueOf(c.get(Calendar.YEAR))+"-";
        date += String.valueOf(c.get(Calendar.MONTH))+"-";
        date += String.valueOf(c.get(Calendar.DATE))+" ";
        date += String.valueOf(c.get(Calendar.HOUR))+":";
        date += String.valueOf(c.get(Calendar.MINUTE));

        ContentValues values = new ContentValues();
        values.put("content", mesgMap.get("body"));   // put(컬럼명, 데이터);
        values.put("wdate", date);
        db.insert("noti", null, values);
        db.close();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainActivity.pushNoti();
            }
        });
    }
    private void runOnUiThread(Runnable runnable){
        handler.post(runnable);
    }

}//end class