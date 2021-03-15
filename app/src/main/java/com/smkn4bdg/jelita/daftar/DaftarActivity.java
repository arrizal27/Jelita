package com.smkn4bdg.jelita.daftar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.smkn4bdg.jelita.R;

public class DaftarActivity extends AppCompatActivity {
    EditText nama,username,pass,emailhp,role;
    Button btndaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        findView();

    }

    private void findView(){
        nama = findViewById(R.id.txt_nama);
        username = findViewById(R.id.txt_username);
        pass = findViewById(R.id.txt_pass);
        emailhp = findViewById(R.id.txt_email_hp);
        role = findViewById(R.id.txt_role);
        btndaftar = findViewById(R.id.btn_daftar);
    }
}