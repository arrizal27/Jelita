package com.smkn4bdg.jelita.ui.nabung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.smkn4bdg.jelita.R;
import com.smkn4bdg.jelita.ui.setor.SetorActivity;

public class NabungActivity extends AppCompatActivity {
    MaterialButton btnBack, btnTabung, btnSetor;
    TextView txtTabungan, txtMaksTabung;
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

        btnSetor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NabungActivity.this, SetorActivity.class);
                startActivity(i);
                finish();
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

        progressBarMinyak.setMax(21);
        progressBarMinyak.setProgress(20);

    }
}