package com.smkn4bdg.jelita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

public class Profile extends AppCompatActivity {
    MaterialCardView btnLogout;
    FirebaseAuth firebaseAuth;
    FirebaseUser Fuser;
    TextView kategori, username,email, alamat;
    DatabaseReference databaseReference;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnLogout = findViewById(R.id.btn_help);
        kategori = findViewById(R.id.kategori);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        alamat = findViewById(R.id.alamat);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Fuser = firebaseAuth.getCurrentUser();

        userInformation(Fuser.getUid());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent welcome = new Intent(Profile.this, WelcomePage.class);
                startActivity(welcome);
                finish();
            }
        });
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
        email.setText(info.getEmail());
        alamat.setText(info.getAlamat());
    }
}