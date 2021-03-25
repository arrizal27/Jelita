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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smkn4bdg.jelita.Models.User;
import com.smkn4bdg.jelita.riwayat.RiwayatActivity;

public class MainActivity extends AppCompatActivity {
    ImageView fotoProfil;
    TextView username, poin, kategori, tabunganMinyak, kapasitasMax;
    ProgressBar progressBarMinyak;

    MaterialButton btnNabung;

    MaterialCardView btnHelp, btnProfil, btnJerigen, btnRiwayat, btnPoin;

    FirebaseAuth firebaseAuth;
    FirebaseUser Fuser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Fuser = firebaseAuth.getCurrentUser();
        userInformation(Fuser.getUid());

        btnRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RiwayatActivity.class);
                startActivity(i);
            }
        });

        btnJerigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NabungActivity.class);
                startActivity(i);
            }
        });

        btnNabung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NabungActivity.class);
                startActivity(i);
            }
        });

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(MainActivity.this, Profile.class);
                startActivity(profile);
            }
        });
    }

    private void findView() {
        fotoProfil = findViewById(R.id.img_profil);
        username = findViewById(R.id.TVusername);
        poin = findViewById(R.id.TVpoin);
        kategori = findViewById(R.id.TVkategori);
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

    private void userInformation(String uID) {
        final Query q = databaseReference.child("Users").child("Pengguna").child(uID);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User Uinfo = dataSnapshot.getValue(User.class);
                    setData(Uinfo);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setData(User info) {
        kategori.setText(info.getRole());
        username.setText(info.getUsername());
        poin.setText(String.valueOf(info.getPoin()));
    }
}