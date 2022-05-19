package com.tequeno.rdm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.tequeno.rdm.dao.BookDao;

public class SQLiteDbActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private String dbName;
    private UserOpenHelper userOpenHelper;
    private BookDao mBookDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_db);

        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        btn1.setOnClickListener(this::createDb);
        btn2.setOnClickListener(this::removeDb);
        // 内部存储空间  /data/data/应用包名/files/test1.db
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
        if (deleted) {
            Log.d(TAG, "数据库已删除");
        } else {
            Log.d(TAG, "数据库删除失败");
        }
    }

    private void c(View view) {
        Log.d(TAG, "c: ");
        User user = new User().setName("test1");
        boolean ok = userOpenHelper.insert(user);
        if (ok) {
            Log.d(TAG, "c: " + user);
        }

//        Book book = new Book();
//        book.setName("zgjdbg");
//        book.setPress("svdgdh");
//        book.setPrice(1L);
//        mBookDao.insert(book);
//        Log.d(TAG, "c: " + book);
    }

    private void r(View view) {
        Log.d(TAG, "r: ");
        userOpenHelper.query("");

//        List<Book> bookList = mBookDao.queryAll();
//        bookList.forEach(System.out::println);
    }

    private void u(View view) {
        Log.d(TAG, "u: ");
        User user = new User().set_id(1L).setName("name1");
        boolean ok = userOpenHelper.update(user);
        if (ok) {
            Log.d(TAG, "u: " + user);
        }
    }

    private void d(View view) {
        Log.d(TAG, "d: ");
        boolean ok = userOpenHelper.delete(2L);
        if (ok) {
            Log.d(TAG, "d: deleted");
        }
    }

    private void up(View view) {
        Log.d(TAG, "up: ");
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
        userOpenHelper = UserOpenHelper.getInstance(this);
        mBookDao = MyApplication.getInstance().getDb().bookDao();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
        userOpenHelper.close();
    }
}