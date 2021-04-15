package com.smkn4bdg.jelita.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.service.autofill.Dataset;
import android.service.autofill.SaveRequest;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smkn4bdg.jelita.Models.User;
import com.smkn4bdg.jelita.R;
import com.smkn4bdg.jelita.riwayat.RiwayatActivity;
import com.smkn4bdg.jelita.ui.nabung.NabungActivity;
import com.smkn4bdg.jelita.ui.point.PointActivity;
import com.smkn4bdg.jelita.ui.profile.ProfileActivity;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mdbUsers;
    private FirebaseDatabase mfirebaseInstance;
    private FirebaseAuth mfirebaseauth;
    private FirebaseUser mUser;
    private static final String  USERS = "users";
    public static final String EXTRA_POINT = "";
    private final String TAG = this.getClass().getName().toUpperCase();
    User user = new User();
//    Intent intent = getIntent();
//    String email = intent.getStringExtra("email");
    ImageView fotoProfil;
    TextView username, poin, kategori, tabunganMinyak, kapasitasMax;
    ProgressBar progressBarMinyak;

    MaterialButton btnNabung;
    MaterialCardView btnHelp, btnProfil, btnJerigen, btnRiwayat, btnPoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String email =  mfirebaseauth.getCurrentUser().getEmail();
        findView();
        getdata();

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

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
                Intent profile = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(profile);
            }
        });
        btnPoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent point = new Intent(MainActivity.this, PointActivity.class);
//                point.putExtra("data", (Parcelable) poin);
                startActivity(point);
            }
        });
    }

    private void getdata(){
        //read database
        FirebaseApp.initializeApp(this);
        mfirebaseInstance = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mdbUsers = mfirebaseInstance.getReference();
        mdbUsers.child(USERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mdatasnap : snapshot.getChildren()){
                    if (mdatasnap.child("id").getValue().equals(mUser.getUid())){
                        System.out.println(mdatasnap.child("username").getValue(String.class));
                        username.setText(mdatasnap.child("username").getValue(String.class).toUpperCase());
                        kategori.setText(mdatasnap.child("role").getValue(String.class));
                        poin.setText(mdatasnap.child("poin").getValue().toString() + " Poin");
                        tabunganMinyak.setText(String.valueOf(mdatasnap.child("jml_minyak").getValue() + " Liter"));

                        if(mdatasnap.child("role").getValue().toString().equals("Rumah Tangga")){
                            kapasitasMax.setText(Integer.valueOf(5) + " Liter");
                            progressBarMinyak.setMax(5);
                        }
                        else if (mdatasnap.child("role").getValue().toString().equals("Pedagang")){
                            kapasitasMax.setText(Integer.valueOf(10) + " Liter");
                            progressBarMinyak.setMax(10);
                        }
                        else if (mdatasnap.child("role").getValue().toString().equals("Cafe dan Rumah Makan")){
                            kapasitasMax.setText(Integer.valueOf(15) + " Liter");
                            progressBarMinyak.setMax(15);
                        }
                        else if (mdatasnap.child("role").getValue().toString().equals("Hotel dan Penginapan")){
                            kapasitasMax.setText(Integer.valueOf(20) + " Liter");
                            progressBarMinyak.setMax(20);
                        }
                        else{

                        }

                        progressBarMinyak.setProgress(Integer.valueOf(mdatasnap.child("jml_minyak").getValue().toString()));
                        user.setEmail(mdatasnap.child("email").getValue(String.class));



//                        tabunganMinyak.setText(mdatasnap.child("jml_minyak").getValue(String.class));
//                        Log.d(username.toString(), "kategori");
                        user.setEmail(mdatasnap.child("username").getValue(String.class));
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
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