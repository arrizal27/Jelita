package com.smkn4bdg.jelita.daftar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.smkn4bdg.jelita.R;

public class DaftarBerhasilActivity extends AppCompatActivity {
    Button btnsuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_berhasil);

        btnsuccess = findViewById(R.id.btn_daftar_berhasil);
    }
}