package com.tequeno.bar;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MyIntentActivity extends AppCompatActivity {

    private static final String TAG = "MyIntentActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_intent);


        String u = "content://com.tequeno.rdm.UserContentProvider/vvswws";
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse(u), null, null, null, null);
        while (cursor.moveToNext()) {
            long id = cursor.getLong(0);
            String name = cursor.getString(1);
            Log.d(TAG, "cursor: " + id + name);
        }
        cursor.close();
    }
}