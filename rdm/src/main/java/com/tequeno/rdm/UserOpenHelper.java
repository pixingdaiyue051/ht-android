package com.tequeno.rdm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

public class UserOpenHelper extends SQLiteOpenHelper {

    private final String TAG = this.getClass().getSimpleName();

    private static UserOpenHelper mHelper = null;

    private final String TABLE_NAME = "user";
    private SQLiteDatabase mRdb = null;
    private SQLiteDatabase mWdb = null;

    /**
     * @param context
     */
    private UserOpenHelper(Context context) {
        super(context, MyApplication.getInstance().DB_NAME, null, 1);
    }

    /**
     * 单例模式获取SQLHelper
     * 每次创建SQLHelper时都会触发onCreate方法
     * 如果db_version有升级会触发onUpgrade方法
     * 如果db_version有降级会触发onDowngrade方法
     *
     * @param context
     * @return
     */
    public static UserOpenHelper getInstance(Context context) {
        if (null == mHelper) {
            mHelper = new UserOpenHelper(context);
        }
        return mHelper;
    }

    /**
     * 获得读数据库连接
     *
     * @return
     */
    public SQLiteDatabase openRdb() {
        if (null == mRdb || !mRdb.isOpen()) {
            mRdb = mHelper.getReadableDatabase();
        }
        return mRdb;
    }

    /**
     * 获得写数据库连接
     *
     * @return
     */
    public SQLiteDatabase openWdb() {
        if (null == mWdb || !mWdb.isOpen()) {
            mWdb = mHelper.getWritableDatabase();
        }
        return mWdb;
    }

    public void closeRdb() {
        if (null != mRdb && mRdb.isOpen()) {
            mRdb.close();
            mRdb = null;
        }
    }

    public void closeWdb() {
        if (null != mWdb && mWdb.isOpen()) {
            mWdb.close();
            mWdb = null;
        }
    }

    public void close() {
        closeRdb();
        closeWdb();
    }

    /**
     * 创建数据库
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + TABLE_NAME + " (id integer primary key autoincrement not null, name varchar(32) not null default '')";
        db.execSQL(sql);
        // 内部存储空间  /data/data/应用包名/databases/test.db
        Log.d(TAG, "onCreate: SQLiteDatabase：" + db.getPath());
    }

    /**
     * 数据库版本升级
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: SQLiteDatabase：" + db.getPath());
        Log.d(TAG, "onUpgrade: oldVersion：" + oldVersion);
        Log.d(TAG, "onUpgrade: newVersion：" + newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public long insert(ContentValues content) {
        long rowId = 0;
        SQLiteDatabase wdb = this.openWdb();
        wdb.beginTransaction();
        try {
            rowId = wdb.insert(TABLE_NAME, "", content);
//            rowId = wdb.insert(TABLE_NAME, "", content);
//            rowId = wdb.insert(TABLE_NAME, "", content);
//            rowId = wdb.insert(TABLE_NAME, "", content);
//            Log.d(TAG, "insert: " + 1 / 0);
            wdb.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "insert: ", e);
            // 异常 数据回滚
        } finally {
            wdb.endTransaction();
        }
        return rowId;
    }

    public boolean insert(User user) {
        ContentValues content = new ContentValues();
        content.put("name", user.getName());
        long rowId = this.insert(content);
        if (rowId > 0) {
            user.set_id(rowId);
            return true;
        }
        return false;
    }

    public boolean update(User user) {
        ContentValues content = new ContentValues();
        content.put("name", user.getName());
        int affectedRows = this.openWdb().update(TABLE_NAME, content, "id=?", new String[]{user.get_id().toString()});
        return affectedRows > 0;
    }

    public boolean delete(Long id) {
        long affectedRows = this.openWdb().delete(TABLE_NAME, "id=?", new String[]{id.toString()});
        return affectedRows > 0;
    }

    public void query(String name) {
        String sql;
        String[] selectionArgs;
        if (TextUtils.isEmpty(name)) {
            sql = "select id,name from user where 1=1";
            selectionArgs = null;
        } else {
            sql = "select id,name from user where 1=1 and name =?";
            selectionArgs = new String[]{name};
        }
        Cursor cursor = this.openRdb().rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            long id = cursor.getLong(0);
            String name1 = cursor.getString(1);
            Log.d(TAG, "query-id:" + id + " name:" + name1);
        }
        cursor.close();
    }
}