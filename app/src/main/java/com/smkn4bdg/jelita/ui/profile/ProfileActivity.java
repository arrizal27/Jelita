package com.smkn4bdg.jelita.ui.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.smkn4bdg.jelita.ui.WelcomePageActivity;

public class ProfileActivity extends AppCompatActivity {
    MaterialButton back, editProfil;
    MaterialCardView btnLogout;
<<<<<<< HEAD
    Context context;
    TextView editPw, nama, kategori, username, email, password, alamat;
    private DatabaseReference mdbUsers;
    private FirebaseDatabase mfirebaseInstance;
    private FirebaseAuth mfirebaseAuth;
    private FirebaseUser mUser;
    private static final String  USERS = "users";
    private final String TAG = this.getClass().getName().toUpperCase();
    User user = new User();
=======
    private DatabaseReference mdbUsers;
    private FirebaseDatabase mfirebaseInstance;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser mUser;
    private final String TAG = this.getClass().getName().toUpperCase();
    Context context;
    TextView editPw, tvnama,tvkategori,tvusername, tvemail, tvpassword,tvalamat;

>>>>>>> b5b5b49e8120c885fdd3a4cefc3b6662befad3ef

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getdata();
        findView();
        mfirebaseAuth = FirebaseAuth.getInstance();

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
                mfirebaseAuth.signOut();
                Intent welcome = new Intent(ProfileActivity.this, WelcomePageActivity.class);
                startActivity(welcome);
                finish();
            }
        });
    }
    private void getdata(){
        FirebaseApp.initializeApp(this);
        mfirebaseInstance = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mdbUsers = mfirebaseInstance.getReference();
        mdbUsers.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mdata : snapshot.getChildren()){
                    if (mdata.child("id").getValue().equals(mUser.getUid())){
                        tvnama.setText(mdata.child("nama").getValue(String.class).toUpperCase());
                        tvkategori.setText(mdata.child("role").getValue(String.class));
                        tvalamat.setText(mdata.child("alamat").getValue(String.class));
                        tvemail.setText(mdata.child("email").getValue(String.class));
                        tvusername.setText(mdata.child("username").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void getDataProfile(){
        FirebaseApp.initializeApp(this);
        FirebaseApp.initializeApp(this);
        mfirebaseInstance = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mdbUsers = mfirebaseInstance.getReference();
        mdbUsers.child(USERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("id").getValue().equals(mUser.getUid())){
                        nama.setText(dataSnapshot.child("nama").getValue().toString().toUpperCase());
                        kategori.setText(dataSnapshot.child("role").getValue().toString());
                        username.setText(dataSnapshot.child("username").getValue().toString());
                        email.setText(dataSnapshot.child("email").getValue().toString());
                        password.setText(dataSnapshot.child("password").getValue().toString());
                        alamat.setText(dataSnapshot.child("alamat").getValue().toString());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void findView(){
        nama = findViewById(R.id.nama);
        kategori = findViewById(R.id.kategori);
        username = findViewById(R.id.username);
        password = findViewById(R.id.pass);
        alamat = findViewById(R.id.alamat);
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
    }
}