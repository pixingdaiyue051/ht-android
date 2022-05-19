package com.tequeno.rdm;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

/**
 * 动态获取权限
 */
public class PermissionActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private SmsGetObserver observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        Button btnLazyContact = findViewById(R.id.btn_lazy_contact);
        Button btnLazySms = findViewById(R.id.btn_lazy_sms);
        Button btnAddContact = findViewById(R.id.btn_add_contact);
        btnLazyContact.setOnClickListener(this::lazyContact);
        btnLazySms.setOnClickListener(this::lazySms);
        btnAddContact.setOnClickListener(this::addContact);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(observer);
    }

    /**
     * 检查是否有授权通信录
     *
     * @param view
     */
    private void lazyContact(View view) {
        Log.d(TAG, "lazyContact: ");
//        PermissionUtil.checkPermission(this, PermissionEnum.CONTACTS_GROUP);

        List<PermissionEnum> list = Arrays.asList(PermissionEnum.WRITE_CONTACTS, PermissionEnum.READ_CONTACTS);
//        ImmutableList<PermissionEnum> list = ImmutableList.of(PermissionEnum.WRITE_CONTACTS, PermissionEnum.READ_CONTACTS);
        if (PermissionUtil.checkPermission(this, list)) {
            ContentResolver resolver = getContentResolver();
            Cursor cursor = resolver.query(ContactsContract.RawContacts.CONTENT_URI, new String[]{ContactsContract.RawContacts._ID}, null, null);
            while (cursor.moveToNext()) {
                int rowId = cursor.getInt(0);
                Log.d(TAG, "lazyContact: " + rowId);
                Cursor cursor1 = resolver.query(Uri.parse(String.format("content://%s/contacts/%s/data", ContactsContract.AUTHORITY, rowId)), new String[]{ContactsContract.Data.RAW_CONTACT_ID, ContactsContract.Data.DATA1, ContactsContract.Data.DATA2},
                        null, null);
                while (cursor1.moveToNext()) {
                    int dataId = cursor1.getInt(0);
                    String data1 = cursor1.getString(1);
                    String data2 = cursor1.getString(2);
                    Log.d(TAG, "lazyContact1: " + dataId + data1 + data2);
                }
                cursor1.close();
            }
            cursor.close();
        }
    }

    /**
     * 检查是否有短信授权
     *
     * @param view
     */
    private void lazySms(View view) {
        Log.d(TAG, "lazySms: ");
//        PermissionUtil.checkPermission(this, PermissionEnum.SMS_GROUP);

        List<PermissionEnum> list = Arrays.asList(PermissionEnum.READ_SMS, PermissionEnum.SEND_SMS);
//        ImmutableList<PermissionEnum> list = ImmutableList.of(PermissionEnum.READ_SMS, PermissionEnum.SEND_SMS);
        if (PermissionUtil.checkPermission(this, list)) {
            ContentResolver resolver = getContentResolver();

            Uri uri = Uri.parse("content://sms");
            observer = new SmsGetObserver(this);
            resolver.registerContentObserver(uri, true, observer);
        }
    }

    private static class SmsGetObserver extends ContentObserver {

        private final Context context;
        private final UriMatcher uriMatcher;
        private final int SMS_MATCH_CODE = 1;

        public SmsGetObserver(Context context) {
            super(new Handler(Looper.getMainLooper()));
            this.context = context;
            uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
            uriMatcher.addURI("sms", "/#", SMS_MATCH_CODE);
        }

        @Override
        public void onChange(boolean selfChange, @Nullable Uri uri) {
            ContentResolver resolver = context.getContentResolver();
            if (null == uri) {
                return;
            }
            if (SMS_MATCH_CODE == uriMatcher.match(uri)) {
                Cursor cursor = resolver.query(uri, new String[]{"address", "body", "date"}, null, null, "date desc");
                if (cursor.moveToNext()) {
                    String address = cursor.getString(0);
                    String body = cursor.getString(1);
                    String date = cursor.getString(2);
                    System.out.println("onChange------" + address + body + date);
                }
                cursor.close();
            }
        }
    }

    /**
     * 通过contentResolver操作系统通信录
     * <p>
     * 添加数据到row_content data mime_type
     *
     * @param view
     */
    private void addContact(View view) {
        Log.d(TAG, "addContact: ");

        ContentResolver resolver = getContentResolver();
        // 先写入 row_content 得到 rowContactId
        ContentValues values = new ContentValues();
        Uri contactUri = resolver.insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rowContactId = ContentUris.parseId(contactUri);

        // 写入姓名
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rowContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.Data.DATA2, "jasper1");
        resolver.insert(ContactsContract.Data.CONTENT_URI, values);

        // 写入手机号
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.Data.DATA1, "13653489658");
        values.put(ContactsContract.Data.DATA2, ContactsContract.CommonDataKinds.Phone.TYPE_HOME);
        resolver.insert(ContactsContract.Data.CONTENT_URI, values);

        // 写入邮箱
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.Data.DATA1, "d3242@fxcvdwgfs.com");
        values.put(ContactsContract.Data.DATA2, ContactsContract.CommonDataKinds.Email.TYPE_HOME);
        resolver.insert(ContactsContract.Data.CONTENT_URI, values);

//        ContentProviderOperation rowOpt = ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
//                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null) // 需要设置一个账户名让option实例化
//                .build();
//
//        ContentProviderOperation nameOpt = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0) // 回调填充
//                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
//                .withValue(ContactsContract.Data.DATA2, "jasper")
//                .build();
//
//        ContentProviderOperation phoneOpt = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0) // 回调填充
//                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//                .withValue(ContactsContract.Data.DATA1, "13654589658")
//                .withValue(ContactsContract.Data.DATA2, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
//                .build();
//
//        ContentProviderOperation emailOpt = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0) // 回调填充
//                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
//                .withValue(ContactsContract.Data.DATA1, "dsfds@fsgs.com")
//                .withValue(ContactsContract.Data.DATA2, ContactsContract.CommonDataKinds.Email.TYPE_HOME)
//                .build();
//
//        ArrayList<ContentProviderOperation> list = new ArrayList<>();
//        list.add(rowOpt);
//        list.add(nameOpt);
//        list.add(phoneOpt);
//        list.add(emailOpt);
//        try {
//            resolver.applyBatch(ContactsContract.AUTHORITY, list);
//        } catch (OperationApplicationException e) {
//            e.printStackTrace();
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: " + requestCode + Arrays.toString(permissions) + Arrays.toString(grantResults));

//        if (PackageManager.PERMISSION_DENIED == grantResults[0]) {
//            MyApplication.getInstance().appInfo();
//        }

    }
}