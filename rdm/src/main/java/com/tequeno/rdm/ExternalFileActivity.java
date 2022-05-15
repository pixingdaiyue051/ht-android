package com.tequeno.rdm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_file);

        edSrc = findViewById(R.id.ed_src);
        tvShow = findViewById(R.id.tv_show);
        imgShow = findViewById(R.id.img_show);
        Button btnSave = findViewById(R.id.btn_save);
        Button btnRead = findViewById(R.id.btn_read);
        btnSave.setOnClickListener(this::save);
        btnRead.setOnClickListener(this::read);

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
        try (FileChannel fileChannel = FileChannel.open(Paths.get(dir, filePath), StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            ByteBuffer wrap = ByteBuffer.wrap(edSrc.getText().toString().getBytes(StandardCharsets.UTF_8));
            fileChannel.write(wrap);
            wrap.clear();
        } catch (Exception e) {
            Log.e(TAG, "save: error", e);
        }

        // 保存图片
        Log.d(TAG, "save: " + dir + picPath);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
        try (FileOutputStream fos = new FileOutputStream(dir + picPath)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            Log.e(TAG, "save: error", e);
        }
    }

    private void read(View view) {
        // 读取文本
        Log.d(TAG, "read: " + dir + filePath);
        try (FileChannel fileChannel = FileChannel.open(Paths.get(dir, filePath), StandardOpenOption.READ, StandardOpenOption.WRITE)) {
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
}