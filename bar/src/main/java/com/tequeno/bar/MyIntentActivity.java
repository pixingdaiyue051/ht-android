package com.tequeno.bar;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.UriMatcher;
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
        setContentView(R.layout.activity_my_intent);


        String u = "content://com.tequeno.bar.usercontentprovider";
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse(u), null, null, null, null);
        if (null == cursor) {
            return;
        }
        while (cursor.moveToNext()) {
            long id = cursor.getLong(0);
            String name = cursor.getString(1);
            Log.d(TAG, "cursor: " + id + name);
        }
        cursor.close();
    }
}