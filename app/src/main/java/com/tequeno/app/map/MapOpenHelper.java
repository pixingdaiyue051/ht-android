package com.tequeno.app.map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.tequeno.app.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class MapOpenHelper extends SQLiteOpenHelper {

    private final static String TAG = "MapOpenHelper";

    private static MapOpenHelper INSTANCE = null;
    private SQLiteDatabase mRdb = null;
    private SQLiteDatabase mWdb = null;

    /**
     * @param context
     */
    private MapOpenHelper(Context context) {
        super(context, MyApplication.DB_NAME, null, 1);
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
    public static MapOpenHelper getInstance(Context context) {
        if (null == INSTANCE) {
            synchronized (MapOpenHelper.class) {
                if (null == INSTANCE) {
                    INSTANCE = new MapOpenHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 获得读数据库连接
     *
     * @return
     */
    public SQLiteDatabase openRdb() {
        if (null == mRdb || !mRdb.isOpen()) {
            mRdb = INSTANCE.getReadableDatabase();
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
            mWdb = INSTANCE.getWritableDatabase();
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
        String sql = String.format("CREATE table if not exists %s(%s integer primary key autoincrement not null," +
                        "      %s varchar(32) not null default 'a'," +
                        "      %s DECIMAL(3,5) not null default 0.0," +
                        "      %s DECIMAL(3,5) not null default 0.0," +
                        "      %s DATETIME NOT NULL DEFAULT current_timestamp);",
                Map.Table.TABLE_NAME, Map.Table.COLUMN_ID, Map.Table.COLUMN_TARGET, Map.Table.COLUMN_LONGITUDE, Map.Table.COLUMN_LATITUDE, Map.Table.COLUMN_CREATE_TIME);
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

    public boolean insert(Map map) {
        ContentValues content = new ContentValues();
        content.put(Map.Table.COLUMN_LATITUDE, map.latitude);
        content.put(Map.Table.COLUMN_LONGITUDE, map.longitude);
        content.put(Map.Table.COLUMN_TARGET, map.target);
        SQLiteDatabase wdb = this.openWdb();
        wdb.beginTransaction();
        long rowId = 0;
        try {
            rowId = wdb.insert(Map.Table.TABLE_NAME, "", content);
            wdb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "insert: ", e);
        } finally {
            wdb.endTransaction();
        }
        if (rowId > 0) {
            map._id = rowId;
            return true;
        }
        return false;
    }

    public List<LatLng> query(String target, int listSize) {
        String[] columns = new String[]{Map.Table.COLUMN_ID, Map.Table.COLUMN_LONGITUDE, Map.Table.COLUMN_LATITUDE};
        String where = String.format("%s = ?", Map.Table.COLUMN_TARGET);
        String[] args = new String[]{target};
        Cursor cursor = this.openRdb().query(Map.Table.TABLE_NAME, columns, where, args, null, null, Map.Table.COLUMN_CREATE_TIME);
//        String sql = String.format("select %s,%s,%s,%s from %s where %s = ? order by %s",
//                Map.Table.COLUMN_ID, Map.Table.COLUMN_TARGET, Map.Table.COLUMN_LONGITUDE, Map.Table.COLUMN_LATITUDE,
//                Map.Table.TABLE_NAME, Map.Table.COLUMN_TARGET, Map.Table.COLUMN_CREATE_TIME);
//        String[] args = new String[]{target};
//        Cursor cursor = this.openRdb().rawQuery(sql, args);
        if (null == cursor || cursor.isClosed()) {
            return null;
        }
        List<LatLng> pointList = new ArrayList<>(listSize);

        while (cursor.moveToNext()) {
            long _id1 = cursor.getLong(0);
            double longitude1 = cursor.getDouble(1);
            double latitude1 = cursor.getDouble(2);
            Log.d(TAG, "query:" + _id1 + target + longitude1 + latitude1);
            pointList.add(new LatLng(latitude1, longitude1));
        }
        cursor.close();
        return pointList;
    }
}