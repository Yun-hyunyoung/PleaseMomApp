package com.mom.project.pleasemom;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 08_718 on 2016-11-15.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "noti.sqlite", factory, version); // name = person.sqlite / version변경 시 onUpgrade가 호출되고 Create가 호출된다.
    }

    @Override
    public void onCreate(SQLiteDatabase db) {   // SQLiteDatabase ==> MyBatis의 SqlSession과 유사한 기능
        // Table 생성 ==> 자동 생성 ( Table이 없을 때 )
        Log.i("TAG", "SQLOpenHelper onCreate");
        String sql = "create table noti(_id integer primary key autoincrement, content text, wdate text)";
        db.execSQL(sql);    // auto commit
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Table 삭제 ==> 자동 삭제 ( DB 변경시 )
        String sql = "drop table noti";
        db.execSQL(sql);
        onCreate(db);
    }
}
