package com.tequeno.rdm;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ExternalFileActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private String filePath;
    private String picPath;
    private String dir;
    private TextView tvShow;
    private EditText edSrc;
    private ImageView imgShow;
    private ImageView imgShow0;
    private ImageView imgShow1;
    private ImageView imgShow2;
    private ImageView imgShow3;
    private ImageView imgShow4;
    private ImageView imgShow5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_file);

        edSrc = findViewById(R.id.ed_src);
        tvShow = findViewById(R.id.tv_show);
        imgShow = findViewById(R.id.img_show);

        imgShow0 = findViewById(R.id.gl_img_0);
        imgShow1 = findViewById(R.id.gl_img_1);
        imgShow2 = findViewById(R.id.gl_img_2);
        imgShow3 = findViewById(R.id.gl_img_3);
        imgShow4 = findViewById(R.id.gl_img_4);
        imgShow5 = findViewById(R.id.gl_img_5);

        Button btnSave = findViewById(R.id.btn_save);
        Button btnRead = findViewById(R.id.btn_read);
        Button btnInstall = findViewById(R.id.btn_install);
        Button btnReadPic = findViewById(R.id.btn_read_pic);
        Button btnMediaStore = findViewById(R.id.btn_media_store);
        btnSave.setOnClickListener(this::save);
        btnRead.setOnClickListener(this::read);
        btnInstall.setOnClickListener(this::install);

        // 注册跳转选择图库的activityResult
        ActivityResultLauncher<Intent> picRegister = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Log.i(TAG, result.toString());
            Intent resultIntent = result.getData();
            if (null != resultIntent) {
                Uri uri = resultIntent.getData();
                imgShow.setImageURI(uri);
            }
        });
        // 跳转选择图库 并获得所选图片
        btnReadPic.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            picRegister.launch(intent);
        });

        btnMediaStore.setOnClickListener(view -> {
            if (PermissionUtil.checkPermission(this, PermissionEnum.READ_EXTERNAL_STORAGE)) {
                this.mediaStore();
            }
        });

//        // 外部存储空间-公共读写  /sdcard/Downloads
//        dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//        // 外部存储空间-私有读写 /sdcard/Android/data/Downloads/应用包名/files
        dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();
        filePath = "/test1.txt";
        picPath = "/test1.jpg";
    }

    private void save(View view) {
        // 保存文本
        Log.d(TAG, "save: " + dir + filePath);
        try (FileChannel fileChannel = FileChannel.open(Paths.get(dir, filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            ByteBuffer wrap = ByteBuffer.wrap(edSrc.getText().toString().getBytes(StandardCharsets.UTF_8));
            fileChannel.write(wrap);
            wrap.clear();
        } catch (Exception e) {
            Log.e(TAG, "save: error", e);
        }

        // 保存图片
        Log.d(TAG, "save: " + dir + picPath);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_151853);
        try (FileOutputStream fos = new FileOutputStream(dir + picPath)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            Log.e(TAG, "save: error", e);
        }
    }

    private void read(View view) {
        // 读取文本
        Log.d(TAG, "read: " + dir + filePath);
        try (FileChannel fileChannel = FileChannel.open(Paths.get(dir, filePath), StandardOpenOption.READ)) {
            ByteBuffer dst = ByteBuffer.allocate((int) fileChannel.size());
            fileChannel.read(dst);
            dst.flip();
            String text = new String(dst.array(), StandardCharsets.UTF_8);
            Log.d(TAG, "read: " + text);
            tvShow.setText(text);
        } catch (Exception e) {
            Log.e(TAG, "save: error", e);
        }

        // 读取图片
        Log.d(TAG, "read: " + dir + picPath);
        String absolutePath = dir + picPath;

        imgShow.setImageURI(Uri.parse(absolutePath));

//        Bitmap bitmap = BitmapFactory.decodeFile(absolutePath);
//        imgShow.setImageBitmap(bitmap);

//        try (FileInputStream fis = new FileInputStream(absolutePath)) {
//            Bitmap bitmap = BitmapFactory.decodeStream(fis);
//            imgShow.setImageBitmap(bitmap);
//        } catch (Exception e) {
//            Log.e(TAG, "read: error", e);
//        }
    }

    /**
     * 从服务器下载了某安装包 调用系统应用安装第三方包
     * @param view
     */
    private void install(View view) {
        // 1.获取安装包 模拟安装包位置在外部存储公共读 Download下
        String apkPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/f51061b3803e8fc2e6c675dc3969b227.apk";
        // 2.检查安装包是否完整 尝试解析apk文件
        PackageManager packageManager = getPackageManager();
        // 尝试解析获得activity信息 如果为空则说明文件已损害不完整 因为一个完整的apk至少有一个启动的activity
        PackageInfo archiveInfo = packageManager.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if(null ==archiveInfo) {
            return;
        }
        // 启动安装应用程序
        MyApplication.getInstance().apkInstaller(apkPath);
    }

    private void mediaStore() {
        String[] columns = {
                MediaStore.Images.ImageColumns._ID, // 图片id
                MediaStore.Images.ImageColumns.TITLE, // 图片名
                MediaStore.Images.ImageColumns.DATA, // 图片路径
                MediaStore.Images.ImageColumns.SIZE, // 图片大小
        };
        // 只查询600k以内图片
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
                "_size < 614400", null, "_size desc", null);
        if (null == cursor) {
            return;
        }
        int i = 0;
        while (cursor.moveToNext() && i < 6) {
            String id = cursor.getString(0);
            String title = cursor.getString(1);
            String data = cursor.getString(2);
            String size = cursor.getString(3);
            Log.d(TAG, "mediaStore: " + id + title + data + size);

            Uri imageURI = Uri.parse(data);
//            Android7.0之后 为安全性考虑可以使用下面方式获取可以由外部访问的uri
//            Uri imageURI = FileProvider.getUriForFile(this, getString(R.string.rdm_file_provider), new File(data));


            switch (i) {
                case 0:
                    this.setImageUri(imgShow0, imageURI);
                    break;
                case 1:
                    this.setImageUri(imgShow1, imageURI);
                    break;
                case 2:
                    this.setImageUri(imgShow2, imageURI);
                    break;
                case 3:
                    this.setImageUri(imgShow3, imageURI);
                    break;
                case 4:
                    this.setImageUri(imgShow4, imageURI);
                    break;
                case 5:
                    this.setImageUri(imgShow5, imageURI);
                    break;
                default:break;
            }
            i++;
        }
        cursor.close();
    }

    private void setImageUri(ImageView imageView, Uri imageURI) {
        imageView.setImageURI(imageURI);
        if(!imageView.hasOnClickListeners()) {
            imageView.setOnClickListener(view -> imgShow.setImageURI(imageURI));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionEnum.READ_EXTERNAL_STORAGE.getCode() && PackageManager.PERMISSION_GRANTED == grantResults[0]) {
            this.mediaStore();
        }
    }
}