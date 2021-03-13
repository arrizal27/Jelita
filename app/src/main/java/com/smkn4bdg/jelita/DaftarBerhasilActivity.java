package com.smkn4bdg.jelita;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class DaftarBerhasilActivity extends AppCompatActivity {
    Button btnsuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_berhasil);

        btnsuccess = findViewById(R.id.btn_daftar_berhasil);
    }
}