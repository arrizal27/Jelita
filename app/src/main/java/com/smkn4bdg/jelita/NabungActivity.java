package com.smkn4bdg.jelita;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class NabungActivity extends AppCompatActivity {
    MaterialButton btnBack, btnTabung, btnSetor;
    TextInputEditText txtJumlahMinyak;
    ProgressBar progressBarMinyak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nabung);

        findView();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnTabung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //method tambah tabungan minyak
                nabungSuccess();
            }
        });
    }

    private void findView(){
        btnBack = findViewById(R.id.btn_back);
        btnTabung = findViewById(R.id.btn_tambah_minyak);
        btnSetor = findViewById(R.id.btn_setor);
        txtJumlahMinyak = findViewById(R.id.txt_tambah_minyak);
        progressBarMinyak = findViewById(R.id.progres_nabung);
    }

    private void nabungSuccess() {
        String tambah = String.valueOf(txtJumlahMinyak.getText());
        Toast.makeText(this, "Tabungan minyak Anda bertambah " + tambah + " Liter", Toast.LENGTH_SHORT).show();
        txtJumlahMinyak.setText("");
    }
}