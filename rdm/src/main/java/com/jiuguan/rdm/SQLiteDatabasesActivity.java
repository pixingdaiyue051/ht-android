package com.jiuguan.rdm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SQLiteDatabasesActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private String dbName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_databases);

        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        btn1.setOnClickListener(this::createDb);
        btn2.setOnClickListener(this::removeDb);
        dbName = getFilesDir() + "/test.db";
    }

    private void createDb(View view) {
        Log.d(TAG, "createDb: ");
        SQLiteDatabase db = openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        if (null != db) {
            Log.d(TAG, "数据库已创建,路径:" + db.getPath());
        } else {
            Log.w(TAG, "数据库创建失败");
        }
    }

    private void removeDb(View view) {
        Log.d(TAG, "removeDb: ");
        boolean deleted = deleteDatabase(dbName);
        if(deleted) {
            Log.d(TAG, "数据库已删除");
        } else {
            Log.d(TAG, "数据库删除失败");
        }
    }
}