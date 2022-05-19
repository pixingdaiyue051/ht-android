package com.tequeno.rdm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPrefs = findViewById(R.id.btn_shared_prefs);
        Button btnSql = findViewById(R.id.btn_sqlite);
        Button btnFile = findViewById(R.id.btn_external_file);
        Button btnPermission = findViewById(R.id.btn_permission);
        btnPrefs.setOnClickListener(this::prefs);
        btnSql.setOnClickListener(this::sql);
        btnFile.setOnClickListener(this::file);
        btnPermission.setOnClickListener(this::permission);
    }

    private void prefs(View view) {
        Intent intent = new Intent(this, SharedPrefsActivity.class);
        startActivity(intent);
    }

    private void sql(View view) {
        Intent intent = new Intent(this, SQLiteDbActivity.class);
        startActivity(intent);
    }

    private void file(View view) {
        Intent intent = new Intent(this, ExternalFileActivity.class);
        startActivity(intent);
    }

    private void permission(View view) {
        Intent intent = new Intent(this, PermissionActivity.class);
        startActivity(intent);
    }
}