package com.tequeno.rdm;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.lang.annotation.ElementType;
import java.nio.file.attribute.UserDefinedFileAttributeView;

/**
 * 对外提供contentProvider访问本应用内部数据
 * 使用唯一的URI访问
 * content:// AUTHORITY_URI / data_path / id
 */
public class UserContentProvider extends ContentProvider {

    private final String TAG = this.getClass().getSimpleName();

    private UserOpenHelper userOpenHelper;

    public UserOpenHelper getUserOpenHelper() {
        if (null == userOpenHelper) {
            userOpenHelper = UserOpenHelper.getInstance(getContext());
        }
        return userOpenHelper;
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate: ");
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase wdb = this.getUserOpenHelper().openWdb();
        return wdb.delete("user", selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = this.getUserOpenHelper().insert(values);
        // 写入成功需要返回最新的uri
        if(rowId > 0 ) {
            uri = ContentUris.withAppendedId(uri, rowId);
            // 同时可能需要通知监听器 需要配合contentObserver使用
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return uri;

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase rdb = this.getUserOpenHelper().openRdb();
        return rdb.query("user", projection, selection, selectionArgs, null, null, null);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}