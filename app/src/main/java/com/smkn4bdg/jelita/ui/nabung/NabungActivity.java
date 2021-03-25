package com.smkn4bdg.jelita.ui.nabung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smkn4bdg.jelita.R;
import com.smkn4bdg.jelita.ui.setor.SetorActivity;

public class NabungActivity extends AppCompatActivity {
    private DatabaseReference mdbUsers;
    private FirebaseDatabase mfirebaseInstance;
    private FirebaseAuth mfirebaseauth;
    private FirebaseUser mUser;
    private final String TAG = this.getClass().getName().toUpperCase();
    MaterialButton btnBack, btnTabung, btnSetor;
    TextView tabunganMinyak, kapasitasMax;
    TextInputEditText txtJumlahMinyak;
    ProgressBar progressBarMinyak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nabung);
        initdata();
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
    private void initdata(){
        //for read. and crud
        // TODO: 25/03/2021 lanjutin crud tabungan minyak antisisapi error 
        FirebaseApp.initializeApp(this);
        mfirebaseInstance = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mdbUsers = mfirebaseInstance.getReference();
        mdbUsers.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mdata : snapshot.getChildren()){
                    if (mdata.child("id").getValue().equals(mUser.getUid())){
                        tabunganMinyak.setText(String.valueOf(mdata.child("jml_minyak").getValue() + " Liter"));
                        progressBarMinyak.setProgress(Integer.valueOf(mdata.child("jml_minyak").getValue().toString()));
                        if(mdata.child("role").getValue().toString().equals("Rumah Tangga")){
                            kapasitasMax.setText(Integer.valueOf(5) + " Liter");
                            progressBarMinyak.setMax(5);
                        }
                        if (mdata.child("role").getValue().toString().equals("Pedagang")){
                            kapasitasMax.setText(Integer.valueOf(10) + " Liter");
                            progressBarMinyak.setMax(10);
                        }
                        if (mdata.child("role").getValue().toString().equals("Cafe dan Rumah Makan")){
                            kapasitasMax.setText(Integer.valueOf(15) + " Liter");
                            progressBarMinyak.setMax(15);
                        }
                        if (mdata.child("role").getValue().toString().equals("Hotel dan Penginapan")){
                            kapasitasMax.setText(Integer.valueOf(20) + " Liter");
                            progressBarMinyak.setMax(20);
                        }
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

    private void findView(){
        btnBack = findViewById(R.id.btn_back);
        btnTabung = findViewById(R.id.btn_tambah_minyak);
        btnSetor = findViewById(R.id.btn_setor);
        txtJumlahMinyak = findViewById(R.id.txt_tambah_minyak);
        progressBarMinyak = findViewById(R.id.progres_nabung);
        tabunganMinyak = findViewById(R.id.txt_tabungan);
        kapasitasMax = findViewById(R.id.txt_maks);
    }

    private void nabungSuccess() {
        String tambah = String.valueOf(txtJumlahMinyak.getText());
        Toast.makeText(this, "Tabungan minyak Anda bertambah " + tambah + " Liter", Toast.LENGTH_SHORT).show();
        txtJumlahMinyak.setText("");

        progressBarMinyak.setMax(21);
        progressBarMinyak.setProgress(20);

    }
}