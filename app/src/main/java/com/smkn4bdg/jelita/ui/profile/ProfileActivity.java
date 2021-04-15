package com.smkn4bdg.jelita.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
import com.smkn4bdg.jelita.R;
import com.smkn4bdg.jelita.ui.WelcomePageActivity;

public class ProfileActivity extends AppCompatActivity {
    MaterialButton back, editProfil;
    MaterialCardView btnLogout;
    private DatabaseReference mdbUsers;
    private FirebaseDatabase mfirebaseInstance;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser mUser;
    private final String TAG = this.getClass().getName().toUpperCase();
    Context context;
    TextView editPw, tvnama,tvkategori,tvusername, tvemail, tvpassword,tvalamat, tvkota, tvkecamatan, tvkelurahan;
    String id, jeniskel, role;
    int poin, jmlminyak;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        mdbUsers = FirebaseDatabase.getInstance().getReference();
        mUser = firebaseAuth.getCurrentUser();

        userInformation(mUser.getUid());
        findView();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(i);
            }
        });

        editPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, EditPasswordActivity.class);
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent welcome = new Intent(ProfileActivity.this, WelcomePageActivity.class);
                startActivity(welcome);
                finish();
            }
        });
    }
    private void userInformation(String uID) {
        final Query q = mdbUsers.child("users").child(uID);
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
        id = info.getId();

        tvnama.setText(info.getNama());
        tvusername.setText(info.getUsername());
        tvemail.setText(info.getEmail());
        tvalamat.setText(info.getAlamat());
        tvkota.setText(info.getKota());
        tvkecamatan.setText(info.getKecamatan());
        tvkelurahan.setText(info.getKelurahan());
        jeniskel = info.getJenis_kelamin();
        role = info.getRole();
        poin = info.getPoin();
        jmlminyak = info.getJml_minyak();
    }

    private void findView(){
        back = findViewById(R.id.btn_back);
        editProfil = findViewById(R.id.btn_edit);
        editPw = findViewById(R.id.btn_edit_pw);
        btnLogout = findViewById(R.id.btn_logout);
        tvnama = findViewById(R.id.nama);
        tvkategori = findViewById(R.id.kategori);
        tvusername = findViewById(R.id.username);
        tvemail = findViewById(R.id.email);
        tvpassword = findViewById(R.id.pass);
        tvalamat = findViewById(R.id.alamat);
        tvkota = findViewById(R.id.tvkota);
        tvkecamatan = findViewById(R.id.tvkecamatan);
        tvkelurahan = findViewById(R.id.tvkelurahan);
    }
}