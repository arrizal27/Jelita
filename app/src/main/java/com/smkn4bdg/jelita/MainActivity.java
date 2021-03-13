package com.smkn4bdg.jelita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.smkn4bdg.jelita.riwayat.RiwayatActivity;

public class MainActivity extends AppCompatActivity {
    ImageView fotoProfil;
    TextView username, poin, kategori, tabunganMinyak, kapasitasMax;
    ProgressBar progressBarMinyak;

    MaterialButton btnNabung;

    MaterialCardView btnHelp, btnProfil, btnJerigen, btnRiwayat, btnPoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();

        btnRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RiwayatActivity.class);
                startActivity(i);
            }
        });
    }

    private void findView() {
        fotoProfil = findViewById(R.id.img_profil);
        username = findViewById(R.id.username);
        poin = findViewById(R.id.poin);
        kategori = findViewById(R.id.kategori);
        tabunganMinyak = findViewById(R.id.txt_tabungan);
        kapasitasMax = findViewById(R.id.txt_maks);
        progressBarMinyak = findViewById(R.id.progres_nabung);
        btnNabung = findViewById(R.id.btn_nabung);
        btnJerigen = findViewById(R.id.btn_jerigen);
        btnProfil = findViewById(R.id.btn_profil);
        btnHelp = findViewById(R.id.btn_help);
        btnRiwayat = findViewById(R.id.btn_riwayat);
        btnPoin = findViewById(R.id.btn_poin);
    }
}