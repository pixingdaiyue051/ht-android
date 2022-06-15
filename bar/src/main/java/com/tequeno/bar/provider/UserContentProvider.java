package com.tequeno.bar.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * 对外提供contentProvider访问本应用内部数据
 * 使用唯一的URI访问
 * content:// AUTHORITY_URI / data_path / id
 * provider的创建的会在application之前
 */
public class UserContentProvider extends ContentProvider {

    private final static String TAG = "UserContentProvider";
    private UserOpenHelper userOpenHelper;

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate: ");
        userOpenHelper = UserOpenHelper.getInstance(getContext());
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase wdb = userOpenHelper.openWdb();
        return wdb.delete(User.Table.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = userOpenHelper.insert(values);
        // 写入成功需要返回最新的uri
        if (rowId > 0) {
            uri = ContentUris.withAppendedId(uri, rowId);
            // 同时可能需要通知监听器 需要配合contentObserver使用
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return uri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase rdb = userOpenHelper.openRdb();
        return rdb.query(User.Table.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}