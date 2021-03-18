package com.smkn4bdg.jelita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.smkn4bdg.jelita.R;
import com.smkn4bdg.jelita.riwayat.RiwayatActivity;

public class DaftarActivity extends AppCompatActivity {
    TextInputEditText nama,username,pass,notelp;
    MaterialButton btndaftar;
    ImageButton back;
    Spinner role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        findView();

        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DaftarActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void findView(){
        nama = findViewById(R.id.txt_nama);
        username = findViewById(R.id.txt_username);
        pass = findViewById(R.id.txt_password);
        notelp = findViewById(R.id.txt_telp);
        role = findViewById(R.id.dropdown_role);
        btndaftar = findViewById(R.id.btn_daftar);
        back = findViewById(R.id.back_daftar);
    }
}