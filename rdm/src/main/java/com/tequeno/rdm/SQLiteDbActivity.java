package com.tequeno.rdm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SQLiteDbActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private String dbName;
    private SQLHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_db);

        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        btn1.setOnClickListener(this::createDb);
        btn2.setOnClickListener(this::removeDb);
        dbName = getFilesDir() + "/test1.db";

        Button btnC = findViewById(R.id.btn_c);
        Button btnR = findViewById(R.id.btn_r);
        Button btnU = findViewById(R.id.btn_u);
        Button btnD = findViewById(R.id.btn_d);
        Button btnUp = findViewById(R.id.btn_up);
        btnC.setOnClickListener(this::c);
        btnR.setOnClickListener(this::r);
        btnU.setOnClickListener(this::u);
        btnD.setOnClickListener(this::d);
        btnUp.setOnClickListener(this::up);
    }

    private void createDb(View view) {
        Log.d(TAG, "createDb: ");
        SQLiteDatabase db = openOrCreateDatabase("/test.db", Context.MODE_PRIVATE, null);
        if (null != db) {
            Log.d(TAG, "数据库已创建,路径:" + db.getPath());
        } else {
            Log.w(TAG, "数据库创建失败");
        }
    }

    private void removeDb(View view) {
        Log.d(TAG, "removeDb: ");
        boolean deleted = deleteDatabase(dbName);
        if (deleted) {
            Log.d(TAG, "数据库已删除");
        } else {
            Log.d(TAG, "数据库删除失败");
        }
    }

    private void c(View view) {
        Log.d(TAG, "c: ");
        User user = new User().setName("test1");
        boolean ok = helper.insert(user);
        if(ok) {
            Log.d(TAG, "c: " + user);
        }
    }

    private void r(View view) {
        Log.d(TAG, "r: ");
        helper.query("");
    }

    private void u(View view) {
        Log.d(TAG, "u: ");
        User user = new User().setId(1L).setName("name1");
        boolean ok = helper.update(user);
        if(ok) {
            Log.d(TAG, "u: " + user);
        }
    }

    private void d(View view) {
        Log.d(TAG, "d: ");
        boolean ok = helper.delete(2L);
        if(ok) {
            Log.d(TAG, "d: deleted");
        }
    }

    private void up(View view) {
        Log.d(TAG, "up: ");
        helper.upgrade("ALTER TABLE user ADD code varchar(30) not null default ''");
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
        helper = SQLHelper.getInstance(this);
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
        helper.close();
    }
}